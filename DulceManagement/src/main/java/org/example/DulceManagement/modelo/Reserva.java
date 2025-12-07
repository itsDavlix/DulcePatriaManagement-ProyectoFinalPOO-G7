package org.example.DulceManagement.modelo;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.*;
import java.util.Date;
import java.math.BigDecimal;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentDateCalculator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Setter
@Getter
@Entity
@View(members =
        "fecha, nombreCliente, estado, comentarios;" +
                "porcentajeIVA, iva, importeTotal, " +
                "costoTotal, beneficioEstimado;" +
                "lineasReserva"
)
public class Reserva {
    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Required
    @Temporal(TemporalType.DATE)
    @DefaultValueCalculator(CurrentDateCalculator.class)
    private Date fecha;

    @Required
    @Column(length = 80)
    private String nombreCliente;

    @Required
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private EstadoReserva estado = EstadoReserva.PENDIENTE;

    @Column(length = 200)
    private String comentarios;

    @Min(0)
    @Max(100)
    @Digits(integer = 2, fraction = 0)
    private BigDecimal porcentajeIVA = new BigDecimal("15");

    @ReadOnly
    @Money
    @Calculation("sum(lineasReserva.importe) * porcentajeIVA / 100")
    private BigDecimal iva;

    @ReadOnly
    @Money
    @Calculation("sum(lineasReserva.importe) + iva")
    private BigDecimal importeTotal;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    @ListProperties(
            "receta.nombre, cantidad, precioUnitario, " +
                    "importe+[reserva.porcentajeIVA, reserva.iva, reserva.importeTotal], " +
                    "costoTotal, margenLinea, notas"
    )
    private Collection<LineaReserva> lineasReserva = new ArrayList<>();

    @Money
    @Depends("lineasReserva")
    public BigDecimal getImporteSinIVA() {
        if (lineasReserva == null) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (LineaReserva lr : lineasReserva) {
            if (lr == null) continue;
            BigDecimal imp = lr.getImporte();
            if (imp != null) {
                total = total.add(imp);
            }
        }
        return total;
    }

    @Money
    @Depends("lineasReserva")
    public BigDecimal getCostoTotal() {
        if (lineasReserva == null) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (LineaReserva lr : lineasReserva) {
            if (lr == null) continue;
            BigDecimal costo = lr.getCostoTotal();
            if (costo != null) {
                total = total.add(costo);
            }
        }
        return total;
    }

    @Money
    @Depends("lineasReserva")
    public BigDecimal getBeneficioEstimado() {
        BigDecimal venta = getImporteSinIVA();
        BigDecimal costo = getCostoTotal();
        return venta.subtract(costo);
    }
}
