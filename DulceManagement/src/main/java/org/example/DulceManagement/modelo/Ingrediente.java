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

    private BigDecimal cantidadDisponible = BigDecimal.ZERO;

    @Required
    @Money
    @DecimalMin("0")
    private BigDecimal costoUnitario = BigDecimal.ZERO;

    public BigDecimal consumirConFaltante(BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        if (cantidadDisponible == null) {
            cantidadDisponible = BigDecimal.ZERO;
        }

        if (cantidadDisponible.compareTo(cantidad) >= 0) {
            // Hay suficiente stock
            cantidadDisponible = cantidadDisponible.subtract(cantidad);
            return BigDecimal.ZERO;
        }

        BigDecimal faltante = cantidad.subtract(cantidadDisponible);
        cantidadDisponible = BigDecimal.ZERO;
        return faltante;
    }

    public void reponer(BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        if (cantidadDisponible == null) {
            cantidadDisponible = BigDecimal.ZERO;
        }
        cantidadDisponible = cantidadDisponible.add(cantidad);
    }
}
