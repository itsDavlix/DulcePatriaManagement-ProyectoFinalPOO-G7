package org.example.DulceManagement.modelo;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import org.openxava.annotations.*;

@Entity
@View(members = "nombre; unidadMedida; stockActual; stockMinimo; activo")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Required
    @Column(length = 50)
    private String nombre;

    @Required
    @Column(length = 10)
    private String unidadMedida;

    @Required
    @Digits(integer = 10, fraction = 2)
    private double stockActual;

    @Digits(integer = 10, fraction = 2)
    private double stockMinimo;

    private boolean activo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {            // ?? IMPORTANTE
        return nombre;
    }

    public void setNombre(String nombre) {  // ?? IMPORTANTE
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getStockActual() {
        return stockActual;
    }

    public void setStockActual(double stockActual) {
        this.stockActual = stockActual;
    }

    public double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
