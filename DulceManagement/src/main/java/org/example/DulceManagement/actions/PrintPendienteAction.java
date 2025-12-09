package org.example.DulceManagement.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;
import org.example.DulceManagement.modelo.Pendiente;
import java.util.*;

public class PrintPendienteAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        List<Pendiente> pendientes = XPersistence.getManager()
                .createQuery("select p from Pendiente p", Pendiente.class)
                .getResultList();

        List<Map<String, ?>> rows = new ArrayList<>();

        for (Pendiente p : pendientes) {
            Map<String, Object> row = new HashMap<>();

            row.put("id", p.getId());
            row.put("ingrediente", p.getIngrediente());      // Objeto Ingrediente
            row.put("descripcion", p.getDescripcion());
            row.put("cantidad", p.getCantidad());

            // Estado: ahora es un ENUM (EstadoPendiente), lo convertimos a String
            String estado = "";
            if (p.getEstado() != null) {
                estado = p.getEstado().name(); // ej: PENDIENTE, COMPLETADO, etc.
            }
            row.put("estado", estado);

            rows.add(row);
        }

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
        return "PendienteListado.jrxml";
    }
}
