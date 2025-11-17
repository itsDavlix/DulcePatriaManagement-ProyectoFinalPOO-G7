package org.example.DulceManagement.modelo;

import org.example.DulceManagement.modelo.enums.EstadoReserva;
import javax.persistence.*;
import org.openxava.annotations.*;
import java.util.*;

@Entity
@View(members =
        "fecha, cliente, estado;" +
                "detalles"
)
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Required
    private Date fecha;

    @Column(length = 50)
    private String cliente;

    @Required
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    @ListProperties("producto.nombre, cantidad, estadoStock")
    private Collection<DetalleReserva> detalles;

    // Getters y setters
}
