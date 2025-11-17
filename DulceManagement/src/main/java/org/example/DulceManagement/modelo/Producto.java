package org.example.DulceManagement.modelo;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
@View(members = "nombre, precioVenta, activo")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Required
    @Column(length = 50)
    private String nombre;

    @Required
    @Money
    private double precioVenta;

    private boolean activo = true;

    // Getters y setters
}
