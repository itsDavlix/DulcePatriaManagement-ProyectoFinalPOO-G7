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

    // Getters y setters
}
