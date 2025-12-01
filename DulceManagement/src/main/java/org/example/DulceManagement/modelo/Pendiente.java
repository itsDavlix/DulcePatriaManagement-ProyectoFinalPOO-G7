package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import org.openxava.annotations.*;

@Entity
public class Pendiente {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @DescriptionsList
    private Ingrediente ingrediente;

    @Column(length = 100)
    private String descripcion;

    private BigDecimal cantidad;

    @Column(length = 200)
    private String notas;

    @Required
    @Column(length = 15)
    private String estado = "PENDIENTE";


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

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getNotas() {
        return notas;
    }
    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
