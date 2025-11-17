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

    // Getters y setters
}
