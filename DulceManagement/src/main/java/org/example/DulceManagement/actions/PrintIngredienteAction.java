package org.example.DulceManagement.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.DulceManagement.modelo.Ingrediente;

public class PrintIngredienteAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        List<Ingrediente> ingredientes = XPersistence.getManager()
                .createQuery("select i from Ingrediente i", Ingrediente.class)
                .getResultList();

        return new JRBeanCollectionDataSource(ingredientes);
    }

    @Override
    public Map getParameters() throws Exception {
        Map parametros = new HashMap();

        parametros.put("TITULO", "Listado de Ingredientes");

        return parametros;
    }

    @Override
    protected String getJRXML() {
        return "Ingrediente.jrxml";
    }
}
