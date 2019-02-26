package com.came.controller;

import com.came.dao.ImplFParticipanteD;
import com.came.model.EResultadoM;
import com.came.model.FParticipanteM;
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

@Named(value = "fParticipanteC")
@SessionScoped
@Data
public class FParticipanteC implements Serializable {

    FParticipanteM model = new FParticipanteM();
    private List<FParticipanteM> listRespFParticipante;
    private String dniPer;
    private String codSes;
    private boolean verificarPer = true;
    private boolean verificarUrl = true;
    private boolean verificarForm;

    private BarChartModel barModelRFPAR_P;
    private BarChartModel barModelRFPAR_T;
    private BarChartModel barModelRFPAR_SE;
    private BarChartModel barModelRFPAR_CA;
    private BarChartModel barModelRFPAR_TD;
    private BarChartModel barModelRFPAR_PUNT;
    private BarChartModel barModelRFPAR_PART;
    private BarChartModel barModelRFPAR_CB;

    public void pdfRFParticipante(String codses) throws Exception {
        ImplFParticipanteD rs;
        try {
            rs = new ImplFParticipanteD();
            if (codses != null) {
                Map<String, Object> parameters = new HashMap();
                parameters.put("CODSES", codses);
                rs.exportarPDFParticipante(parameters);
            } else {
                System.err.println("Reporte de Participante -> CODSES IS NULL");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void createBarModels(String sesion) throws Exception {
        createBar_RFPAR_CA(sesion);
        createBar_RFPAR_CB(sesion);
        createBar_RFPAR_P(sesion);
        createBar_RFPAR_PART(sesion);
        createBar_RFPAR_PUNT(sesion);
        createBar_RFPAR_SE(sesion);
        createBar_RFPAR_T(sesion);
        createBar_RFPAR_TD(sesion);
    }

    public BarChartModel initBarModelRFP_DP(String sesion) throws Exception {
        ImplFParticipanteD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont;
        try {
            dao = new ImplFParticipanteD();
            List<FParticipanteM> lista = dao.lstRFPAR_CA_I(sesion);
            for (FParticipanteM fPar : lista) {
                item = new ChartSeries();
                item.setLabel(fPar.getRFPAR_CA_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fPar.getRFPAR_CA_RS().size()) {
                        result = fPar.getRFPAR_CA_RS().get(cont - 1);
                        if (result.getRFPAR_CA_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFPAR_CA_Opcion(), result.getRFPAR_CA_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }

            return modelchar;
        } catch (Exception e) {
            throw e;
        }
    }

    public BarChartModel initBarModelRFPAR_CB(String sesion) throws Exception {
        ImplFParticipanteD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont;
        try {
            dao = new ImplFParticipanteD();
            List<FParticipanteM> lista = dao.lstRFPAR_CB_I(sesion);
            for (FParticipanteM fPar : lista) {
                item = new ChartSeries();
                item.setLabel(fPar.getRFPAR_CB_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fPar.getRFPAR_CB_RS().size()) {
                        result = fPar.getRFPAR_CB_RS().get(cont - 1);
                        if (result.getRFPAR_CB_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFPAR_CB_Opcion(), result.getRFPAR_CB_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }

            return modelchar;
        } catch (Exception e) {
            throw e;
        }
    }

    public BarChartModel initBarModelRFPAR_P(String sesion) throws Exception {
        ImplFParticipanteD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont;
        try {
            dao = new ImplFParticipanteD();
            List<FParticipanteM> lista = dao.lstRFPAR_P_I(sesion);
            for (FParticipanteM fPar : lista) {
                item = new ChartSeries();
                item.setLabel(fPar.getRFPAR_P_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fPar.getRFPAR_P_RS().size()) {
                        result = fPar.getRFPAR_P_RS().get(cont - 1);
                        if (result.getRFPAR_P_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFPAR_P_Opcion(), result.getRFPAR_P_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }

            return modelchar;
        } catch (Exception e) {
            throw e;
        }
    }

    public BarChartModel initBarModelRFPAR_PART(String sesion) throws Exception {
        ImplFParticipanteD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Nula", "Baja", "Media", "Alta"};
        int cont;
        try {
            dao = new ImplFParticipanteD();
            List<FParticipanteM> lista = dao.lstRFPAR_PART_I(sesion);
            for (FParticipanteM fPar : lista) {
                item = new ChartSeries();
                item.setLabel(fPar.getRFPAR_PART_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fPar.getRFPAR_PART_RS().size()) {
                        result = fPar.getRFPAR_PART_RS().get(cont - 1);
                        if (result.getRFPAR_PART_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFPAR_PART_Opcion(), result.getRFPAR_PART_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }

            return modelchar;
        } catch (Exception e) {
            throw e;
        }
    }

    public BarChartModel initBarModelRFPAR_PUNT(String sesion) throws Exception {
        ImplFParticipanteD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Más de 15min tarde", "Hasta 10min tarde", "Más de 5min tarde", "A tiempo"};
        int cont;
        try {
            dao = new ImplFParticipanteD();
            List<FParticipanteM> lista = dao.lstRFPAR_PUNT_I(sesion);
            for (FParticipanteM fPar : lista) {
                item = new ChartSeries();
                item.setLabel(fPar.getRFPAR_PUNT_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fPar.getRFPAR_PUNT_RS().size()) {
                        result = fPar.getRFPAR_PUNT_RS().get(cont - 1);
                        if (result.getRFPAR_PUNT_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFPAR_PUNT_Opcion(), result.getRFPAR_PUNT_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }
            return modelchar;

        } catch (Exception e) {
            throw e;
        }
    }

    public BarChartModel initBarModelRFPAR_SE(String sesion) throws Exception {
        ImplFParticipanteD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont;
        try {
            dao = new ImplFParticipanteD();
            List<FParticipanteM> lista = dao.lstRFPAR_SE_I(sesion);
            for (FParticipanteM fPar : lista) {
                item = new ChartSeries();
                item.setLabel(fPar.getRFPAR_SE_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fPar.getRFPAR_SE_RS().size()) {
                        result = fPar.getRFPAR_SE_RS().get(cont - 1);
                        if (result.getRFPAR_SE_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFPAR_SE_Opcion(), result.getRFPAR_SE_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }
            return modelchar;

        } catch (Exception e) {
            throw e;
        }
    }

    public BarChartModel initBarModelRFPAR_T(String sesion) throws Exception {
        ImplFParticipanteD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont;
        try {
            dao = new ImplFParticipanteD();
            List<FParticipanteM> lista = dao.lstRFPAR_T_I(sesion);
            for (FParticipanteM fPar : lista) {
                item = new ChartSeries();
                item.setLabel(fPar.getRFPAR_T_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fPar.getRFPAR_T_RS().size()) {
                        result = fPar.getRFPAR_T_RS().get(cont - 1);
                        if (result.getRFPAR_T_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFPAR_T_Opcion(), result.getRFPAR_T_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }
            return modelchar;

        } catch (Exception e) {
            throw e;
        }
    }

    public BarChartModel initBarModelRFPAR_TD(String sesion) throws Exception {
        ImplFParticipanteD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Menos de 1 hora", "1 hora", "2 horas", "Más de 2 horas"};
        int cont;
        try {
            dao = new ImplFParticipanteD();
            List<FParticipanteM> lista = dao.lstRFPAR_TD_I(sesion);
            for (FParticipanteM fPar : lista) {
                item = new ChartSeries();
                item.setLabel(fPar.getRFPAR_TD_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fPar.getRFPAR_TD_RS().size()) {
                        result = fPar.getRFPAR_TD_RS().get(cont - 1);
                        if (result.getRFPAR_TD_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFPAR_TD_Opcion(), result.getRFPAR_TD_Resultado());
                        } else {
                            item.set(String.valueOf(i), 0);
                        }
                    } else {
                        item.set(String.valueOf(i), 0);
                    }
                }
                modelchar.addSeries(item);
            }

            return modelchar;
        } catch (Exception e) {
            throw e;
        }
    }

    public void createBar_RFPAR_CA(String sesion) throws Exception {
        barModelRFPAR_CA = initBarModelRFP_DP(sesion);

        barModelRFPAR_CA.setTitle("4. Caso");
        barModelRFPAR_CA.setLegendPosition("ne");
        barModelRFPAR_CA.setAnimate(true);
        barModelRFPAR_CA.setShowPointLabels(true);
        barModelRFPAR_CA.setShowDatatip(true);
        barModelRFPAR_CA.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFPAR_CA.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFPAR_CA.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    public void createBar_RFPAR_CB(String sesion) throws Exception {
        barModelRFPAR_CB = initBarModelRFPAR_CB(sesion);

        barModelRFPAR_CB.setTitle("6. Servicios - Coffee Break:");
        barModelRFPAR_CB.setLegendPosition("ne");
        barModelRFPAR_CB.setAnimate(true);
        barModelRFPAR_CB.setShowPointLabels(true);
        barModelRFPAR_CB.setShowDatatip(true);
        barModelRFPAR_CB.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFPAR_CB.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFPAR_CB.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    public void createBar_RFPAR_P(String sesion) throws Exception {
        barModelRFPAR_P = initBarModelRFPAR_P(sesion);

        barModelRFPAR_P.setTitle("1. Profesor");
        barModelRFPAR_P.setLegendPosition("ne");
        barModelRFPAR_P.setAnimate(true);
        barModelRFPAR_P.setShowPointLabels(true);
        barModelRFPAR_P.setShowDatatip(true);
        barModelRFPAR_P.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFPAR_P.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFPAR_P.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    public void createBar_RFPAR_PART(String sesion) throws Exception {
        barModelRFPAR_PART = initBarModelRFPAR_PART(sesion);

        barModelRFPAR_PART.setTitle("5. Autoevaluación - Participación");
        barModelRFPAR_PART.setLegendPosition("ne");
        barModelRFPAR_PART.setAnimate(true);
        barModelRFPAR_PART.setShowPointLabels(true);
        barModelRFPAR_PART.setShowDatatip(true);
        barModelRFPAR_PART.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFPAR_PART.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFPAR_PART.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    public void createBar_RFPAR_PUNT(String sesion) throws Exception {
        barModelRFPAR_PUNT = initBarModelRFPAR_PUNT(sesion);

        barModelRFPAR_PUNT.setTitle("5. Autoevaluación - Puntualidad");
        barModelRFPAR_PUNT.setLegendPosition("ne");
        barModelRFPAR_PUNT.setAnimate(true);
        barModelRFPAR_PUNT.setShowPointLabels(true);
        barModelRFPAR_PUNT.setShowDatatip(true);
        barModelRFPAR_PUNT.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFPAR_PUNT.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFPAR_PUNT.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    public void createBar_RFPAR_SE(String sesion) throws Exception {
        barModelRFPAR_SE = initBarModelRFPAR_SE(sesion);

        barModelRFPAR_SE.setTitle("3. Sesión en su conjunto");
        barModelRFPAR_SE.setLegendPosition("ne");
        barModelRFPAR_SE.setAnimate(true);
        barModelRFPAR_SE.setShowPointLabels(true);
        barModelRFPAR_SE.setShowDatatip(true);
        barModelRFPAR_SE.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFPAR_SE.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFPAR_SE.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    public void createBar_RFPAR_T(String sesion) throws Exception {
        barModelRFPAR_T = initBarModelRFPAR_T(sesion);

        barModelRFPAR_T.setTitle("2. Te°ma");
        barModelRFPAR_T.setLegendPosition("ne");
        barModelRFPAR_T.setAnimate(true);
        barModelRFPAR_T.setShowPointLabels(true);
        barModelRFPAR_T.setShowDatatip(true);
        barModelRFPAR_T.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFPAR_T.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFPAR_T.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    public void createBar_RFPAR_TD(String sesion) throws Exception {
        barModelRFPAR_TD = initBarModelRFPAR_TD(sesion);

        barModelRFPAR_TD.setTitle("4. Caso - Tiempo dedicado");
        barModelRFPAR_TD.setLegendPosition("ne");
        barModelRFPAR_TD.setAnimate(true);
        barModelRFPAR_TD.setShowPointLabels(true);
        barModelRFPAR_TD.setShowDatatip(true);
        barModelRFPAR_TD.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFPAR_TD.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFPAR_TD.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    public void registrarEncFParticipante() throws Exception {
        ImplFParticipanteD dao;
        try {
            dao = new ImplFParticipanteD();
            dao.agregarEncuestaParticipante(model);
            verificarParticipante();
        } catch (Exception e) {
            throw e;
        }
    }

    public void verificarParticipante() throws Exception {
        ImplFParticipanteD dao;
        try {
            dao = new ImplFParticipanteD();
            verificarPer = dao.validacionParticipante(dniPer);
            if (verificarPer == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No eres un participante del CAME", "no puedes acceder al formulario."));
            } else {
                dao.traerCodAsig(model, dniPer);
                verificarForm = dao.traerEstForm(model);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void verificarUrlFParticipante() throws Exception {
        ImplFParticipanteD dao;
        try {
            dao = new ImplFParticipanteD();
            setCodSes(model.getCodSes());
            verificarUrl = dao.verificarUrlFParticipante(codSes);
            if (verificarUrl == true) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/page-not-found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarRespFParticipante(String codSes) throws Exception {
        ImplFParticipanteD dao;
        try {
            dao = new ImplFParticipanteD();
            listRespFParticipante = dao.listRespFParticipante(codSes);
        } catch (Exception e) {
            throw e;
        }
    }

    public void limpiar() {
        model = new FParticipanteM();
    }

    public void clean() {
        limpiar();
        model.setCodSes(codSes);
        verificarPer = true;
        verificarUrl = true;
        verificarForm = false;
        setDniPer("");
    }

}
