package org.example.DulceManagement.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.DulceManagement.modelo.Pendiente;

public class PrintPendienteAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        List<Pendiente> pendientes = XPersistence.getManager()
                .createQuery("select p from Pendiente p", Pendiente.class)
                .getResultList();

        return new JRBeanCollectionDataSource(pendientes);
    }

    @Override
    public Map getParameters() throws Exception {
        Map params = new HashMap();
        params.put("TITULO", "Listado de Pendientes");
        return params;
    }

    @Override
    protected String getJRXML() {
        return "PendienteListado.jrxml";  // El nombre del archivo JRXML
    }
}