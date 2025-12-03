package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Setter
@Getter
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

    @Required
    private BigDecimal cantidad;

    @Column(length = 200)
    private String notas;

    @Required
    @Column(length = 15)
    private String estado = "PENDIENTE";
}