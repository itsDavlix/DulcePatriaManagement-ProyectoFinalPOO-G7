package org.example.DulceManagement.modelo;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Setter
@Getter
@Entity
@View(members =
        "nombre, precioVenta, margenPorcentaje, costoTotal, precioSugerido, margenEstimado;" +
                "ingredientesEnReceta"
)
public class Receta {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Required
    @Column(length = 80)
    private String nombre;

    @Money
    private BigDecimal precioVenta;

    @Digits(integer = 3, fraction = 0)
    private BigDecimal margenPorcentaje = new BigDecimal("30");

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    @ListProperties("ingrediente.nombre, cantidad, ingrediente.unidadMedida, costo")
    private Collection<IngredienteEnReceta> ingredientesEnReceta = new ArrayList<>();

    @Money
    @Depends("ingredientesEnReceta")
    public BigDecimal getCostoTotal() {
        if (ingredientesEnReceta == null) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (IngredienteEnReceta det : ingredientesEnReceta) {
            if (det == null) continue;
            BigDecimal costoLinea = det.getCosto();
            if (costoLinea != null) {
                total = total.add(costoLinea);
            }
        }
        return total;
    }

    @Money
    @Depends("ingredientesEnReceta, margenPorcentaje")
    public BigDecimal getPrecioSugerido() {
        BigDecimal costo = getCostoTotal();
        if (costo == null) costo = BigDecimal.ZERO;
        if (margenPorcentaje == null) return costo;
        return costo.add(
                costo.multiply(margenPorcentaje)
                        .divide(new BigDecimal("100"))
        );
    }

    @Money
    @Depends("ingredientesEnReceta, margenPorcentaje, precioVenta")
    public BigDecimal getMargenEstimado() {
        BigDecimal costo = getCostoTotal();
        if (costo == null) costo = BigDecimal.ZERO;
        BigDecimal precio = precioVenta;
        if (precio == null) {
            precio = getPrecioSugerido();
            if (precio == null) precio = BigDecimal.ZERO;
        }
        return precio.subtract(costo);
    }
}