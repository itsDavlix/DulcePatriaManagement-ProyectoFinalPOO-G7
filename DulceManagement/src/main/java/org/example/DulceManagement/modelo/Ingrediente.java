package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Setter
@Getter
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
    private UnidadMedida unidadMedida;
    @Required
    private BigDecimal cantidadDisponible = BigDecimal.ZERO;

    @Required
    @Money
    private BigDecimal costoUnitario = BigDecimal.ZERO;
}