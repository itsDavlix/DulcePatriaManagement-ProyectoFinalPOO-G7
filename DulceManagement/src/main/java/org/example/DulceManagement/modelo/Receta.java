package org.example.DulceManagement.modelo;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.*;
import java.math.BigDecimal;
import org.openxava.annotations.*;

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

    // ? Precio de venta definido para esta receta (por unidad)
    @Money
    private BigDecimal precioVenta;

    // ? Margen deseado (ej. 30 = 30%)
    @Digits(integer = 3, fraction = 0)
    private BigDecimal margenPorcentaje = new BigDecimal("30");

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    @ListProperties("ingrediente.nombre, cantidad, ingrediente.unidadMedida, costo")
    private Collection<IngredienteEnReceta> ingredientesEnReceta;

    // ? Costo total de producir 1 unidad de la receta
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

    // ? Precio sugerido = costo + margen%
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

    // ? Margen estimado = (precioVenta o sugerido) - costo
    @Money
    @Depends("ingredientesEnReceta, margenPorcentaje, precioVenta")
    public BigDecimal getMargenEstimado() {
        BigDecimal costo = getCostoTotal();
        if (costo == null) costo = BigDecimal.ZERO;
        BigDecimal precio = (precioVenta != null) ? precioVenta : getPrecioSugerido();
        return precio.subtract(costo);
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }
    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getMargenPorcentaje() {
        return margenPorcentaje;
    }
    public void setMargenPorcentaje(BigDecimal margenPorcentaje) {
        this.margenPorcentaje = margenPorcentaje;
    }

    public Collection<IngredienteEnReceta> getIngredientesEnReceta() {
        return ingredientesEnReceta;
    }
    public void setIngredientesEnReceta(Collection<IngredienteEnReceta> ingredientesEnReceta) {
        this.ingredientesEnReceta = ingredientesEnReceta;
    }
}
