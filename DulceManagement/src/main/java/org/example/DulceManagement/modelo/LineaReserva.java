package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import org.openxava.annotations.*;

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
}
