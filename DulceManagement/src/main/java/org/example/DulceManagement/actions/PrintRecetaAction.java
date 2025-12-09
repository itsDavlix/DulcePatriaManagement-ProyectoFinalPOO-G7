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

        List<Receta> recetas = XPersistence.getManager()
                .createQuery("select r from Receta r", Receta.class)
                .getResultList();
        if (recetas == null) {
            recetas = java.util.Collections.emptyList();
        }
        return new JRBeanCollectionDataSource(recetas);
    }

    @Override
    public Map getParameters() throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO", "Listado de Recetas");
        return parametros;
    }

    @Override
    protected String getJRXML() {
        return "Receta.jrxml";
    }
}
