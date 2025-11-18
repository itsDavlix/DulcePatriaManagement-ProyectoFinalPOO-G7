package org.example.DulceManagement.modelo;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import org.openxava.annotations.*;

@Entity
@View(members = "producto; ingrediente; cantidadNecesaria")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Required
    private Producto producto;

    @ManyToOne @Required
    private Ingrediente ingrediente;

    @Required
    @Digits(integer = 10, fraction = 2)
    private double cantidadNecesaria;

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public double getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(double cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }
}
