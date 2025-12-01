package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import org.openxava.annotations.*;
import java.util.Collection;              // ? NUEVO
import org.openxava.jpa.XPersistence;    // ? NUEVO


@Entity
public class LineaReserva {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Required
    private Reserva reserva;

    @ManyToOne
    @Required
    @DescriptionsList
    private Receta receta;

    @Required
    private BigDecimal cantidad;

    // ? Precio de venta por unidad (editable por usuario)
    @Required
    @Money
    private BigDecimal precioUnitario;

    // ? Importe de venta de la línea
    @ReadOnly
    @Money
    @Calculation("cantidad * precioUnitario")
    private BigDecimal importe;

    // ? Costo total de esta línea (según costo de la receta)
    @Money
    @Depends("receta, cantidad")
    public BigDecimal getCostoTotal() {
        if (receta == null || cantidad == null) return BigDecimal.ZERO;
        BigDecimal costoUnitarioReceta = receta.getCostoTotal();
        if (costoUnitarioReceta == null) return BigDecimal.ZERO;
        return costoUnitarioReceta.multiply(cantidad);
    }

    // ? Margen de esta línea = venta - costo
    @Money
    @Depends("receta, cantidad, precioUnitario")
    public BigDecimal getMargenLinea() {
        BigDecimal venta = (importe != null) ? importe : BigDecimal.ZERO;
        BigDecimal costo = getCostoTotal();
        return venta.subtract(costo);
    }

    @Column(length = 200)
    private String notas;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Receta getReceta() {
        return receta;
    }
    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getImporte() {
        return importe;
    }
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getNotas() {
        return notas;
    }
    public void setNotas(String notas) {
        this.notas = notas;
    }

    @PostPersist
    private void actualizarInventarioYGenerarPendientes() {

        // ? Obtenemos la receta y la cantidad de ESTA línea de reserva
        Receta receta = getReceta();          // usa el getter ya existente
        BigDecimal cantidadReserva = getCantidad(); // idem

        if (receta == null || cantidadReserva == null) return;

        Collection<IngredienteEnReceta> ingredientes = receta.getIngredientesEnReceta();
        if (ingredientes == null) return;

        for (IngredienteEnReceta det : ingredientes) {
            if (det == null) continue;

            Ingrediente ingrediente = det.getIngrediente();
            BigDecimal cantidadPorUnidad = det.getCantidad(); // cantidad de ingrediente por 1 unidad de receta

            if (ingrediente == null || cantidadPorUnidad == null) continue;

            // Cantidad total requerida de este ingrediente para ESTA línea
            BigDecimal requerida = cantidadPorUnidad.multiply(cantidadReserva);
            if (requerida == null) continue;

            BigDecimal disponible = ingrediente.getCantidadDisponible();
            if (disponible == null) disponible = BigDecimal.ZERO;

            // ? Inventario suficiente: se descuenta y no hay pendiente
            if (disponible.compareTo(requerida) >= 0) {
                ingrediente.setCantidadDisponible(disponible.subtract(requerida));
            }
            // ? Inventario insuficiente: se consume lo que hay y se genera pendiente
            else {
                BigDecimal faltante = requerida.subtract(disponible);

                // Consumimos todo lo disponible y dejamos en 0
                ingrediente.setCantidadDisponible(BigDecimal.ZERO);

                // Creamos el pendiente con la cantidad que falta
                Pendiente pendiente = new Pendiente();
                pendiente.setIngrediente(ingrediente);
                pendiente.setCantidad(faltante);
                pendiente.setDescripcion(
                        "Falta de " + ingrediente.getNombre() +
                                " para receta " + receta.getNombre()
                );
                pendiente.setNotas("Generado automáticamente al guardar la línea de reserva");
                // Si Pendiente tiene campo estado con valor por defecto, lo respetamos

                XPersistence.getManager().persist(pendiente);
            }
        }
    }
}
