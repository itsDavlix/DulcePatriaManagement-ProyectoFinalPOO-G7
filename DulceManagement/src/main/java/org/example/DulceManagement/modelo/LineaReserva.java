package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;
import java.util.Collection;
import org.openxava.jpa.XPersistence;


@Setter
@Getter
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

    @Required
    @Money
    private BigDecimal precioUnitario;

    @ReadOnly
    @Money
    @Calculation("cantidad * precioUnitario")
    private BigDecimal importe;

    @Money
    @Depends("receta, cantidad")
    public BigDecimal getCostoTotal() {
        if (receta == null || cantidad == null) return BigDecimal.ZERO;
        BigDecimal costoUnitarioReceta = receta.getCostoTotal();
        if (costoUnitarioReceta == null) return BigDecimal.ZERO;
        return costoUnitarioReceta.multiply(cantidad);
    }

    @Money
    @Depends("receta, cantidad, precioUnitario")
    public BigDecimal getMargenLinea() {
        BigDecimal venta = (importe != null) ? importe : BigDecimal.ZERO;
        BigDecimal costo = getCostoTotal();
        return venta.subtract(costo);
    }

    @Column(length = 200)
    private String notas;

    @Transient
    private BigDecimal cantidadAnterior;

    @PostLoad
    private void guardarCantidadAnterior() {
        this.cantidadAnterior = this.cantidad;
    }


    @PostPersist
    @PostUpdate
    private void actualizarInventarioYGenerarPendientes() {

        Receta receta = getReceta();
        BigDecimal cantidadReserva = getCantidad(); // idem

        if (receta == null || cantidadReserva == null) return;

        Collection<IngredienteEnReceta> ingredientes = receta.getIngredientesEnReceta();
        if (ingredientes == null) return;

        for (IngredienteEnReceta det : ingredientes) {
            if (det == null) continue;

            Ingrediente ingrediente = det.getIngrediente();
            BigDecimal cantidadPorUnidad = det.getCantidad();

            if (ingrediente == null || cantidadPorUnidad == null) continue;

            BigDecimal requerida = cantidadPorUnidad.multiply(cantidadReserva);
            if (requerida == null) continue;

            BigDecimal disponible = ingrediente.getCantidadDisponible();
            if (disponible == null) disponible = BigDecimal.ZERO;

            if (disponible.compareTo(requerida) >= 0) {
                ingrediente.setCantidadDisponible(disponible.subtract(requerida));
            }
            else {
                BigDecimal faltante = requerida.subtract(disponible);

                ingrediente.setCantidadDisponible(BigDecimal.ZERO);

                Pendiente pendiente = new Pendiente();
                pendiente.setIngrediente(ingrediente);
                pendiente.setCantidad(faltante);
                pendiente.setDescripcion(
                        "Falta de " + ingrediente.getNombre() +
                                " para receta " + receta.getNombre()
                );
                pendiente.setNotas("Generado automáticamente al guardar la línea de reserva");

                XPersistence.getManager().persist(pendiente);
            }
        }
    }

    @PreRemove
    private void devolverInventarioAlEliminar() {

        Receta receta = getReceta();
        BigDecimal cantidadReserva = getCantidad();

        if (receta == null || cantidadReserva == null) return;

        Collection<IngredienteEnReceta> ingredientes = receta.getIngredientesEnReceta();
        if (ingredientes == null) return;

        for (IngredienteEnReceta det : ingredientes) {
            if (det == null) continue;

            Ingrediente ingrediente = det.getIngrediente();
            BigDecimal cantidadPorUnidad = det.getCantidad();

            if (ingrediente == null || cantidadPorUnidad == null) continue;

            BigDecimal devolver = cantidadPorUnidad.multiply(cantidadReserva);
            if (devolver == null) continue;

            BigDecimal disponible = ingrediente.getCantidadDisponible();
            if (disponible == null) disponible = BigDecimal.ZERO;

            // Sumamos de vuelta al inventario
            ingrediente.setCantidadDisponible(disponible.add(devolver));
        }
    }
}