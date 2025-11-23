package org.example.DulceManagement.modelo;

import javax.persistence.*;
import java.util.*;
import org.openxava.annotations.*;

@Entity
@View(members =
        "nombre;" +
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

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    @ListProperties("ingrediente.nombre, cantidad, ingrediente.unidadMedida")
    private Collection<IngredienteEnReceta> ingredientesEnReceta;

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

    public Collection<IngredienteEnReceta> getIngredientesEnReceta() {
        return ingredientesEnReceta;
    }
    public void setIngredientesEnReceta(Collection<IngredienteEnReceta> ingredientesEnReceta) {
        this.ingredientesEnReceta = ingredientesEnReceta;
    }
}
