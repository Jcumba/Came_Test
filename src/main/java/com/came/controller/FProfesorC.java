package com.came.controller;

import com.came.dao.ImplFProfesorD;
import com.came.model.EResultadoM;
import com.came.model.FProfesorM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named(value = "fProfesorC")
@SessionScoped
@Data
public class FProfesorC implements Serializable {

    FProfesorM model = new FProfesorM();
    private List<FProfesorM> listRespFProfesor;
    private String dniProf;
    private String codSes;
    private boolean verificarProf = true;
    private boolean verificarUrl = true;
    private boolean verificarForm;

    private BarChartModel barModelRFP_DP;
    private BarChartModel barModelRFP_P;
    private BarChartModel barModelRFP_T;
    
    public void pdfRFProfesor(String codses) throws Exception {
        ImplFProfesorD rs;
        try {
            rs = new ImplFProfesorD();
            Map<String, Object> parameters = new HashMap();
            parameters.put("CODSES", codses);
            rs.exportarPDFProfesor(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public void createBarModels(String sesion) throws Exception {
        createBar_RFP_DP(sesion);
        createBar_RFP_P(sesion);
        createBar_RFP_T(sesion);
    }

    public BarChartModel initBarModelRFP_DP(String sesion) throws Exception {
        ImplFProfesorD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont ;
        try {
            dao = new ImplFProfesorD();
            List<FProfesorM> lista = dao.lstRFP_DP_I(sesion);
            for (FProfesorM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFP_DP_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFP_DP_RS().size()) {
                        result = fProfe.getRFP_DP_RS().get(cont - 1);
                        if (result.getRFP_DP_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFP_DP_Opcion(), result.getRFP_DP_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }

        } catch (Exception e) {
            throw e;
        }
        return modelchar;
    }
    
    public BarChartModel initBarModelRFP_P(String sesion) throws Exception {
        ImplFProfesorD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont ;
        try {
            dao = new ImplFProfesorD();
            List<FProfesorM> lista = dao.lstRFP_P_I(sesion);
            for (FProfesorM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFP_P_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFP_P_RS().size()) {
                        result = fProfe.getRFP_P_RS().get(cont - 1);
                        if (result.getRFP_P_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFP_P_Opcion(), result.getRFP_P_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }

        } catch (Exception e) {
            throw e;
        }
        return modelchar;
    }
    
    public BarChartModel initBarModelRFP_T(String sesion) throws Exception  {
        ImplFProfesorD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont ;
        try {
            dao = new ImplFProfesorD();
            List<FProfesorM> lista = dao.lstRFP_T_I(sesion);
            for (FProfesorM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFP_T_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFP_T_RS().size()) {
                        result = fProfe.getRFP_T_RS().get(cont - 1);
                        if (result.getRFP_T_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFP_T_Opcion(), result.getRFP_T_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }

        } catch (Exception e) {
            throw e;
        }
        return modelchar;
    }

    public void createBar_RFP_DP(String sesion) throws Exception {
        barModelRFP_DP = initBarModelRFP_DP(sesion);

        barModelRFP_DP.setTitle("1. Director de Programa");
        barModelRFP_DP.setLegendPosition("ne");
        barModelRFP_DP.setAnimate(true);
        barModelRFP_DP.setShowPointLabels(true);
        barModelRFP_DP.setShowDatatip(true);
        barModelRFP_DP.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFP_DP.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFP_DP.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }
    
    public void createBar_RFP_P(String sesion) throws Exception {
        barModelRFP_P = initBarModelRFP_P(sesion);

        barModelRFP_P.setTitle("2. Participantes");
        barModelRFP_P.setLegendPosition("ne");
        barModelRFP_P.setAnimate(true);
        barModelRFP_P.setShowPointLabels(true);
        barModelRFP_P.setShowDatatip(true);
        barModelRFP_P.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis1 = barModelRFP_P.getAxis(AxisType.X);
        xAxis1.setLabel("Opciones");

        Axis yAxis1 = barModelRFP_P.getAxis(AxisType.Y);
        yAxis1.setLabel("Resultados");
        yAxis1.setMin(0);
        yAxis1.setMax(5);
    }
    
    public void createBar_RFP_T(String sesion) throws Exception {
        barModelRFP_T = initBarModelRFP_T(sesion);

        barModelRFP_T.setTitle("3. Tema");
        barModelRFP_T.setLegendPosition("ne");
        barModelRFP_T.setAnimate(true);
        barModelRFP_T.setShowPointLabels(true);
        barModelRFP_T.setShowDatatip(true);
        barModelRFP_T.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis2 = barModelRFP_T.getAxis(AxisType.X);
        xAxis2.setLabel("Opciones");

        Axis yAxis2 = barModelRFP_T.getAxis(AxisType.Y);
        yAxis2.setLabel("Resultados");
        yAxis2.setMin(0);
        yAxis2.setMax(5);
    }

    public void registrarEncFProfesor() throws Exception {
        ImplFProfesorD dao;
        try {
            dao = new ImplFProfesorD();
            dao.agregarEncuestaProfesor(model);
            verificarProfesor();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarRespFProfesor(String codSes) throws Exception {
        ImplFProfesorD dao;
        try {
            dao = new ImplFProfesorD();
            listRespFProfesor = dao.listarRespFProfesor(codSes);
        } catch (Exception e) {
            throw e;
        }
    }

    public void limpiar() {
        model = new FProfesorM();
    }
    
    public void verificarProfesor() throws Exception {
        ImplFProfesorD dao;
        try {
            dao = new ImplFProfesorD();
            verificarProf = dao.validacionProfesor(dniProf);
            if (verificarProf == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No eres un profesor del CAME", "no puedes acceder al formulario."));
            } else {
                dao.traerCodPer(model, dniProf);
                verificarForm = dao.traerEstForm(model);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void verificarUrlFProfesor() throws Exception {
        ImplFProfesorD dao;
        try {
            dao = new ImplFProfesorD();
            setCodSes(model.getCodSes());
            verificarUrl = dao.verificarUrlFProfesor(codSes);
            if (verificarUrl == true) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/page-not-found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void clean() {
        limpiar();
        model.setCodSes(codSes);
        verificarProf = true;
        verificarUrl = true;
        verificarForm = false;
        setDniProf("");
    }

}
