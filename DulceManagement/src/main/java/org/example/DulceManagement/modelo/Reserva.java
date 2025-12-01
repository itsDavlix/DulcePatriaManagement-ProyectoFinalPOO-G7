package org.example.DulceManagement.modelo;

import javax.persistence.*;
import org.openxava.jpa.XPersistence;
import javax.validation.constraints.Digits;
import java.util.*;
import java.util.Date;
import java.math.BigDecimal;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentDateCalculator;

@Entity
@View(members =
        "fecha, nombreCliente, comentarios;" +
                "porcentajeIVA, iva, importeTotal, costoTotal, beneficioEstimado;" +
                "lineasReserva"
)
public class Reserva {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Required
    @DefaultValueCalculator(CurrentDateCalculator.class)
    private Date fecha;

    @Required
    @Column(length = 80)
    private String nombreCliente;

    @Column(length = 200)
    private String comentarios;

    // ? IVA y totales de venta con @Calculation sobre la colección
    @Digits(integer = 2, fraction = 0)
    private BigDecimal porcentajeIVA;

    @ReadOnly
    @Money
    @Calculation("sum(lineasReserva.importe) * porcentajeIVA / 100")
    private BigDecimal iva;

    @ReadOnly
    @Money
    @Calculation("sum(lineasReserva.importe) + iva")
    private BigDecimal importeTotal;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    @ListProperties(
            "receta.nombre, cantidad, precioUnitario, " +
                    "importe+[reserva.porcentajeIVA, reserva.iva, reserva.importeTotal], " +
                    "costoTotal, margenLinea, notas"
    )
    private Collection<LineaReserva> lineasReserva;

    // ? Total de venta sin IVA (solo sumando importes)
    @Money
    @Depends("lineasReserva")
    public BigDecimal getImporteSinIVA() {
        if (lineasReserva == null) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (LineaReserva lr : lineasReserva) {
            if (lr == null) continue;
            BigDecimal imp = lr.getImporte();
            if (imp != null) {
                total = total.add(imp);
            }
        }
        return total;
    }

    // ? Costo total de la reserva (suma de costos de cada línea)
    @Money
    @Depends("lineasReserva")
    public BigDecimal getCostoTotal() {
        if (lineasReserva == null) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (LineaReserva lr : lineasReserva) {
            if (lr == null) continue;
            BigDecimal costo = lr.getCostoTotal();
            if (costo != null) {
                total = total.add(costo);
            }
        }
        return total;
    }

    // ? Beneficio estimado = venta sin IVA ? costo
    @Money
    @Depends("lineasReserva")
    public BigDecimal getBeneficioEstimado() {
        BigDecimal venta = getImporteSinIVA();
        BigDecimal costo = getCostoTotal();
        return venta.subtract(costo);
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getComentarios() {
        return comentarios;
    }
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getPorcentajeIVA() {
        return porcentajeIVA;
    }
    public void setPorcentajeIVA(BigDecimal porcentajeIVA) {
        this.porcentajeIVA = porcentajeIVA;
    }

    public BigDecimal getIva() {
        return iva;
    }
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }
    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Collection<LineaReserva> getLineasReserva() {
        return lineasReserva;
    }
    public void setLineasReserva(Collection<LineaReserva> lineasReserva) {
        this.lineasReserva = lineasReserva;
    }

    @PrePersist
    private void actualizarInventarioYGenerarPendientes() {
        if (lineasReserva == null) return;

        for (LineaReserva lr : lineasReserva) {
            if (lr == null) continue;
            Receta receta = lr.getReceta();
            BigDecimal cantidadReserva = lr.getCantidad();

            if (receta == null || cantidadReserva == null) continue;
            if (receta.getIngredientesEnReceta() == null) continue;

            for (IngredienteEnReceta det : receta.getIngredientesEnReceta()) {
                if (det == null) continue;

                Ingrediente ingrediente = det.getIngrediente();
                BigDecimal cantidadPorUnidad = det.getCantidad(); // cantidad de ingrediente por 1 unidad de receta

                if (ingrediente == null || cantidadPorUnidad == null) continue;

                // Cantidad total requerida de este ingrediente para esta línea
                BigDecimal requerida = cantidadPorUnidad.multiply(cantidadReserva);
                if (requerida == null) continue;

                BigDecimal disponible = ingrediente.getCantidadDisponible();
                if (disponible == null) disponible = BigDecimal.ZERO;

                // Hay inventario suficiente
                if (disponible.compareTo(requerida) >= 0) {
                    ingrediente.setCantidadDisponible(disponible.subtract(requerida));
                }
                // No hay suficiente inventario -> crear Pendiente
                else {
                    BigDecimal faltante = requerida.subtract(disponible);

                    // Consumimos todo lo disponible y dejamos en 0
                    ingrediente.setCantidadDisponible(BigDecimal.ZERO);

                    // Crear registro Pendiente con la cantidad faltante
                    Pendiente pendiente = new Pendiente();
                    pendiente.setIngrediente(ingrediente);
                    pendiente.setDescripcion("Falta para reserva de " + receta.getNombre());
                    pendiente.setCantidad(faltante);
                    pendiente.setNotas("Generado automáticamente al crear la reserva");
                    // estado queda por defecto "PENDIENTE"

                    XPersistence.getManager().persist(pendiente);
                }
            }
        }
    }
}
