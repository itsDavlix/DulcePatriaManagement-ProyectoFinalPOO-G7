package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import org.openxava.annotations.*;

@Entity
public class Ingrediente {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Required
    @Column(length = 50)
    private String nombre;

    @Required
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UnidadMedida unidadMedida; // GRAMO, KILOGRAMO, etc.

    @Required
    private BigDecimal cantidadDisponible = BigDecimal.ZERO; // inventario

    // ? NUEVO: costo unitario de compra (por unidadMedida)
    @Required
    @Money
    private BigDecimal costoUnitario = BigDecimal.ZERO;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getCantidadDisponible() {
        return cantidadDisponible;
    }
    public void setCantidadDisponible(BigDecimal cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }
    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }
}
