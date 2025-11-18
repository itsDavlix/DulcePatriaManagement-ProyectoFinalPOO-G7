package org.example.DulceManagement.modelo;

import org.example.DulceManagement.modelo.enums.EstadoReserva;
import javax.persistence.*;
import org.openxava.annotations.*;
import java.util.*;

@Entity
@View(members =
        "fecha, cliente, estado;" +
                "detalles"
)
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Required
    private Date fecha;

    @Column(length = 50)
    private String cliente;

    @Required
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    @ListProperties("producto.nombre, cantidad, estadoStock")
    private Collection<DetalleReserva> detalles;

    // ===== GETTERS Y SETTERS =====

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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Collection<DetalleReserva> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<DetalleReserva> detalles) {
        this.detalles = detalles;
    }
}
