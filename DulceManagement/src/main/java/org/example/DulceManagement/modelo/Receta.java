package org.example.DulceManagement.modelo;

import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.RoundingMode;
import java.util.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.openxava.annotations.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Setter
@Getter
@Entity
@View(members =
        "nombre;" +
                "precioVenta;" +
                "margenPorcentaje;" +
                "costoTotal;" +
                "precioSugerido;" +
                "margenEstimado;" +
                "ingredientesEnReceta"
)

@Tab(
        properties = "nombre, precioVenta, costoTotal, precioSugerido, margenEstimado",
        defaultOrder = "${nombre}"
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
    @DecimalMin("0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal precioVenta;

    @Min(0)
    @Max(100)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal margenPorcentaje = new BigDecimal("30");

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    @ListProperties("ingrediente.nombre, cantidad, ingrediente.unidadMedida, costo")
    private Collection<IngredienteEnReceta> ingredientesEnReceta = new ArrayList<>();

    @OneToMany(mappedBy = "receta")
    private Collection<LineaReserva> lineasReserva = new ArrayList<>();

    @Money
    @Depends("precioVenta, ingredientesEnReceta, margenPorcentaje")
    public BigDecimal getPrecioBase() {
        BigDecimal precio = precioVenta != null ? precioVenta : getPrecioSugerido();
        return precio != null ? precio : BigDecimal.ZERO;
    }

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

        if (margenPorcentaje == null) {
            return costo;
        }

        BigDecimal cien = new BigDecimal("100");
        BigDecimal margenDecimal = margenPorcentaje
                .divide(cien, 4, RoundingMode.HALF_UP);

        if (margenDecimal.compareTo(BigDecimal.ONE) >= 0) {
            return costo;
        }

        BigDecimal unoMenosMargen = BigDecimal.ONE.subtract(margenDecimal);

        return costo
                .divide(unoMenosMargen, 2, RoundingMode.HALF_UP);
    }

    @Money
    @Depends("ingredientesEnReceta, margenPorcentaje, precioVenta")
    public BigDecimal getMargenEstimado() {
        BigDecimal costo = getCostoTotal();
        if (costo == null) costo = BigDecimal.ZERO;

        BigDecimal precio = getPrecioBase();
        if (precio == null) precio = BigDecimal.ZERO;

        return precio.subtract(costo);
    }

    @PreRemove
    private void validarAntesDeEliminar() {
        if (lineasReserva != null && !lineasReserva.isEmpty()) {
            throw new ValidationException(
                    "No se puede eliminar la receta porque ya está usada en reservas"
            );
        }
    }

    @ReadOnly
    public String getDescripcionCorta() {
        BigDecimal precio = getPrecioBase();
        BigDecimal margen = getMargenEstimado();

        if (precio == null) precio = BigDecimal.ZERO;
        if (margen == null) margen = BigDecimal.ZERO;

        precio = precio.setScale(2, RoundingMode.HALF_UP);
        margen = margen.setScale(2, RoundingMode.HALF_UP);

        return nombre + " (Precio: " + precio.toPlainString() +
                ", Margen: " + margen.toPlainString() + ")";
    }

    @Depends("ingredientesEnReceta, margenPorcentaje, precioVenta")
    public BigDecimal getMargenPorcentual() {
        BigDecimal costo = getCostoTotal();
        if (costo == null || costo.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal margen = getMargenEstimado();
        return margen
                .multiply(new BigDecimal("100"))
                .divide(costo, 2, RoundingMode.HALF_UP);
    }

    @Depends("ingredientesEnReceta, margenPorcentaje, precioVenta")
    public BigDecimal getMargenPorcentualReal() {
        BigDecimal costo = getCostoTotal();
        if (costo == null || costo.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal margen = getMargenEstimado();
        return margen
                .multiply(new BigDecimal("100"))
                .divide(costo, 2, RoundingMode.HALF_UP);
    }
}
