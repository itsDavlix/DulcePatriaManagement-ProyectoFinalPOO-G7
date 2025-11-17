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

    // Getters y setters
}
