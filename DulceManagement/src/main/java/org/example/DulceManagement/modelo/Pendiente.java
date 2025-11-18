package org.example.DulceManagement.modelo;

import javax.persistence.*;
import org.openxava.annotations.*;
import java.time.LocalDateTime;

@Entity
@View(members = "ingrediente; cantidadFaltante; fechaGeneracion; origen; atendido")
public class Pendiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Required
    private Ingrediente ingrediente;

    @Required
    private double cantidadFaltante;

    @Required
    private LocalDateTime fechaGeneracion;

    @Column(length = 100)
    private String origen;

    private boolean atendido = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public double getCantidadFaltante() {
        return cantidadFaltante;
    }

    public void setCantidadFaltante(double cantidadFaltante) {
        this.cantidadFaltante = cantidadFaltante;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }
}
