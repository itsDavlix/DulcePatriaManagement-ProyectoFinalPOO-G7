package org.example.DulceManagement.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import org.example.DulceManagement.modelo.Pendiente;

import java.util.*;

/**
 * Acción para generar el reporte de Pendientes.
 * Usa JRMapCollectionDataSource para controlar exactamente
 * los campos que recibe el reporte, incluyendo "estado".
 */
public class PrintPendienteAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        // Obtener todos los pendientes de la base de datos
        List<Pendiente> pendientes = XPersistence.getManager()
                .createQuery("select p from Pendiente p", Pendiente.class)
                .getResultList();

        // Lista de filas en forma de Map<String, ?>
        List<Map<String, ?>> rows = new ArrayList<>();

        for (Pendiente p : pendientes) {
            Map<String, Object> row = new HashMap<>();

            // Campos que el JRXML espera
            row.put("id", p.getId());
            row.put("ingrediente", p.getIngrediente());      // Objeto Ingrediente
            row.put("descripcion", p.getDescripcion());
            row.put("cantidad", p.getCantidad());

            // Estado: ahora es un ENUM (EstadoPendiente), lo convertimos a String
            String estado = "";
            if (p.getEstado() != null) {
                // Puedes usar name() o toString(), según cómo quieras ver el texto
                estado = p.getEstado().name(); // ej: PENDIENTE, COMPLETADO, etc.
                // estado = p.getEstado().toString(); // si has sobrescrito toString() en el enum
            }
            row.put("estado", estado);

            rows.add(row);
        }

        // JRMapCollectionDataSource espera Collection<? extends Map<String, ?>>
        return new JRMapCollectionDataSource(rows);
    }

    @Override
    public Map getParameters() throws Exception {
        Map params = new HashMap();
        params.put("TITULO", "Listado de Pendientes");
        return params;
    }

    @Override
    protected String getJRXML() {
        // Debe coincidir EXACTAMENTE con el nombre del .jrxml en resources/reports
        return "PendienteListado.jrxml";
    }
}
