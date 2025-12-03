package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
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

    @DecimalMin("0")
    @Required
    private BigDecimal cantidadDisponible = BigDecimal.ZERO;

    @Required
    @Money
    @DecimalMin("0")
    private BigDecimal costoUnitario = BigDecimal.ZERO;
}