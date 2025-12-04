package org.example.DulceManagement.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.DulceManagement.modelo.Receta;

public class PrintRecetaAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {
        // IMPORTANTÍSIMO: NUNCA devolver null aquí

        // Obtenemos todas las recetas desde la base de datos
        List<Receta> recetas = XPersistence.getManager()
                .createQuery("select r from Receta r", Receta.class)
                .getResultList();

        // Si por alguna razón la lista viene null, devolvemos lista vacía
        if (recetas == null) {
            recetas = java.util.Collections.emptyList();
        }

        // JRBeanCollectionDataSource toma una lista de entidades/beans
        return new JRBeanCollectionDataSource(recetas);
    }

    @Override
    public Map getParameters() throws Exception {
        // IMPORTANTE: tampoco devolver null, siempre un Map (aunque esté vacío)
        Map<String, Object> parametros = new HashMap<>();

        // Mismo estilo que el de Ingrediente
        parametros.put("TITULO", "Listado de Recetas");

        return parametros;
    }

    @Override
    protected String getJRXML() {
        // Debe ser el nombre EXACTO del archivo en /resources/reports
        return "Receta.jrxml";
    }
}
