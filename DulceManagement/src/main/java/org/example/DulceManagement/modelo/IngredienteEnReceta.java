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
public class IngredienteEnReceta {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Required
    private Receta receta;

    @ManyToOne
    @Required
    @DescriptionsList
    private Ingrediente ingrediente;

    @Required
    @DecimalMin("0.01")
    private BigDecimal cantidad;

    @Money
    @Depends("ingrediente, cantidad")
    public BigDecimal getCosto() {
        if (ingrediente == null || ingrediente.getCostoUnitario() == null || cantidad == null) {
            return BigDecimal.ZERO;
        }
        return ingrediente.getCostoUnitario().multiply(cantidad);
    }
}
