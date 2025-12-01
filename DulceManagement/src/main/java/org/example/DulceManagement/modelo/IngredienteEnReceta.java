package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import org.openxava.annotations.*;

@Entity
public class IngredienteEnReceta {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Required
    private Receta receta;

    @ManyToOne
    @Required
    @DescriptionsList
    private Ingrediente ingrediente;

    @Required
    private BigDecimal cantidad;

    @Money
    @Depends("ingrediente, cantidad")
    public BigDecimal getCosto() {
        if (ingrediente == null || ingrediente.getCostoUnitario() == null || cantidad == null) {
            return BigDecimal.ZERO;
        }
        return ingrediente.getCostoUnitario().multiply(cantidad);
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Receta getReceta() {
        return receta;
    }
    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
}
