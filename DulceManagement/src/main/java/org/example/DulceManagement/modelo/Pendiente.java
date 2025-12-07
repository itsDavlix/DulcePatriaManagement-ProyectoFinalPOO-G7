package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import org.openxava.annotations.*;


@Setter
@Getter
@Entity
@Tab(
        properties = "ingrediente.descripcion, descripcion, cantidad, estado",
        defaultOrder = "${estado} asc, ${ingrediente.nombre} asc"
)
public class Pendiente {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @DescriptionsList(descriptionProperties = "descripcion")
    private Ingrediente ingrediente;

    @Column(length = 100)
    private String descripcion;

    @Required
    @DecimalMin("0.01")
    @Digits(integer = 12, fraction = 3)
    private BigDecimal cantidad;

    @Column(length = 200)
    private String notas;

    @Required
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private EstadoPendiente estado = EstadoPendiente.PENDIENTE;
}
