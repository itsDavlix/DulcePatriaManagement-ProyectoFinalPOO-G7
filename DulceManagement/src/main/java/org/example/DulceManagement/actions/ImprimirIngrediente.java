package org.example.DulceManagement.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.openxava.actions.*;

import java.util.Map;


public class ImprimirIngrediente extends JasperReportBaseAction {
    @Override
    protected JRDataSource getDataSource() throws Exception {
        return new JREmptyDataSource();
    }

    @Override
    protected String getJRXML() throws Exception {
        return "Ingredientes.jrxml";
    }

    @Override
    protected Map getParameters() throws Exception {
        return null;
    }
}



