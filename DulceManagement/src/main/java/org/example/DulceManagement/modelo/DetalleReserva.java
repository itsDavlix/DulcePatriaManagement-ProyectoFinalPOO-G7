package org.example.DulceManagement.modelo;

import org.example.DulceManagement.modelo.enums.EstadoStock;
import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
public class DetalleReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Required
    private Reserva reserva;

    @ManyToOne @Required
    private Producto producto;

    @Required
    private int cantidad;

    @Enumerated(EnumType.STRING)
    private EstadoStock estadoStock = EstadoStock.INCOMPLETO;

    // Getters y setters
}
