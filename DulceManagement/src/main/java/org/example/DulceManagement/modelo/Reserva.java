package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.util.*;
import java.util.Date;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentDateCalculator;

@Entity
@View(members =
        "fecha, nombreCliente, comentarios;" +
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

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    @ListProperties("receta.nombre, cantidad, notas")
    private Collection<LineaReserva> lineasReserva;

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

    public Collection<LineaReserva> getLineasReserva() {
        return lineasReserva;
    }
    public void setLineasReserva(Collection<LineaReserva> lineasReserva) {
        this.lineasReserva = lineasReserva;
    }
}
