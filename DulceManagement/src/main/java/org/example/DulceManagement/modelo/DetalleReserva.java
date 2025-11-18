package org.example.DulceManagement.modelo;

import org.example.DulceManagement.modelo.enums.EstadoStock;
import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
public class DetalleReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Required
    private Reserva reserva;

    @ManyToOne @Required
    private Producto producto;

    @Required
    private int cantidad;

    @Enumerated(EnumType.STRING)
    private EstadoStock estadoStock = EstadoStock.INCOMPLETO;

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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public EstadoStock getEstadoStock() {
        return estadoStock;
    }

    public void setEstadoStock(EstadoStock estadoStock) {
        this.estadoStock = estadoStock;
    }
}
