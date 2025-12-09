package org.example.DulceManagement.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;
import org.example.DulceManagement.modelo.Reserva; // ajusta el paquete si es necesario

import java.math.BigDecimal;
import java.util.*;

public class PrintReservaAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        List<Reserva> reservas = XPersistence.getManager()
                .createQuery("select r from Reserva r", Reserva.class)
                .getResultList();

        // OBS: usamos Map<String,?> en la lista para encajar con el constructor de Jasper
        List<Map<String, ?>> rows = new ArrayList<>();

        for (Reserva r : reservas) {

            // creamos el mapa concreto (Map<String,Object>)
            Map<String, Object> row = new HashMap<>();

            row.put("id", r.getId());
            row.put("nombreCliente", r.getNombreCliente());
            row.put("fecha", r.getFecha());
            row.put("importeTotal", r.getImporteTotal());

            String estado;
            if (r.getImporteTotal() == null) {
                estado = "SIN CALCULO";
            } else if (r.getImporteTotal().compareTo(BigDecimal.ZERO) == 0) {
                estado = "PENDIENTE";
            } else {
                estado = "PROCESADA";
            }
            row.put("estado", estado);

            // agregar el mapa concreto a la lista tipada como Map<String,?>
            rows.add(row);
        }

        // esto ahora encaja en: JRMapCollectionDataSource(Collection<? extends Map<String, ?>>)
        return new JRMapCollectionDataSource(rows);
    }

    @Override
    public Map getParameters() throws Exception {
        Map parametros = new HashMap();
        parametros.put("TITULO", "Listado de Reservas");
        return parametros;
    }

    @Override
    protected String getJRXML() {
        return "ReservasListado.jrxml";
    }
}