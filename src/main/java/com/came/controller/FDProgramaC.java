package com.came.controller;

import com.came.dao.ImplFDProgramaD;
import com.came.model.EResultadoM;
import com.came.model.FDProgramaM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named(value = "fDProgramaC")
@SessionScoped
@Data
public class FDProgramaC implements Serializable {

    FDProgramaM model = new FDProgramaM();
    private List<FDProgramaM> listRespFDPrograma;

    private BarChartModel barModelRFDP_DP;
    private BarChartModel barModelRFDP_P;
    private BarChartModel barModelRFDP_PA;
    private BarChartModel barModelRFDP_PU;
    private BarChartModel barModelRFDP_SE;
    private BarChartModel barModelRFDP_SER;
    private BarChartModel barModelRFDP_T;
    
    public void pdfRFParticipante(String codses) throws Exception {
        ImplFDProgramaD rs;
        try {
            rs = new ImplFDProgramaD();
            Map<String, Object> parameters = new HashMap();
            parameters.put("CODSES", codses);
            rs.exportarPDFPrograma(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public void createBarModels(String sesion) throws Exception {

        createBar_RFDP_DP(sesion);
        createBar_RFDP_P(sesion);
        createBar_RFDP_PA(sesion);
        createBar_RFDP_PU(sesion);
        createBar_RFDP_SE(sesion);
        createBar_RFDP_SER(sesion);
        createBar_RFDP_T(sesion);
    }
    
    public BarChartModel initBarModelRFDP_DP(String sesion) throws Exception {
        ImplFDProgramaD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Si", "No"};
        int cont ;
        try {
            dao = new ImplFDProgramaD();
            List<FDProgramaM> lista = dao.lstRFDP_DP_I(sesion);
            for (FDProgramaM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFDP_DP_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFDP_DP_RS().size()) {
                        result = fProfe.getRFDP_DP_RS().get(cont - 1);
                        if (result.getRFDP_DP_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFDP_DP_Opcion(), result.getRFDP_DP_Resultado());
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
    
    public BarChartModel initBarModelRFDP_P(String sesion) throws Exception {
        ImplFDProgramaD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont ;
        try {
            dao = new ImplFDProgramaD();
            List<FDProgramaM> lista = dao.lstRFDP_P_I(sesion);
            for (FDProgramaM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFDP_P_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFDP_P_RS().size()) {
                        result = fProfe.getRFDP_P_RS().get(cont - 1);
                        if (result.getRFDP_P_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFDP_P_Opcion(), result.getRFDP_P_Resultado());
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
    
    public BarChartModel initBarModelRFDP_PA(String sesion) throws Exception {
        ImplFDProgramaD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont ;
        try {
            dao = new ImplFDProgramaD();
            List<FDProgramaM> lista = dao.lstRFDP_PA_I(sesion);
            for (FDProgramaM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFDP_PA_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFDP_PA_RS().size()) {
                        result = fProfe.getRFDP_PA_RS().get(cont - 1);
                        if (result.getRFDP_PA_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFDP_PA_Opcion(), result.getRFDP_PA_Resultado());
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
    
    public BarChartModel initBarModelRFDP_PU(String sesion) throws Exception {
        ImplFDProgramaD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Si", "No"};
        int cont ;
        try {
            dao = new ImplFDProgramaD();
            List<FDProgramaM> lista = dao.lstRFDP_PU_I(sesion);
            for (FDProgramaM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFDP_PU_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFDP_PU_RS().size()) {
                        result = fProfe.getRFDP_PU_RS().get(cont - 1);
                        if (result.getRFDP_PU_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFDP_PU_Opcion(), result.getRFDP_PU_Resultado());
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
    
    public BarChartModel initBarModelRFDP_SE(String sesion) throws Exception {
        ImplFDProgramaD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont ;
        try {
            dao = new ImplFDProgramaD();
            List<FDProgramaM> lista = dao.lstRFDP_SE_I(sesion);
            for (FDProgramaM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFDP_SE_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFDP_SE_RS().size()) {
                        result = fProfe.getRFDP_SE_RS().get(cont - 1);
                        if (result.getRFDP_SE_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFDP_SE_Opcion(), result.getRFDP_SE_Resultado());
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
    
    public BarChartModel initBarModelRFDP_SER(String sesion) throws Exception {
        ImplFDProgramaD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont ;
        try {
            dao = new ImplFDProgramaD();
            List<FDProgramaM> lista = dao.lstRFDP_SER_I(sesion);
            for (FDProgramaM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFDP_SER_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFDP_SER_RS().size()) {
                        result = fProfe.getRFDP_SER_RS().get(cont - 1);
                        if (result.getRFDP_SER_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFDP_SER_Opcion(), result.getRFDP_SER_Resultado());
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
    
    public BarChartModel initBarModelRFDP_T(String sesion) throws Exception {
        ImplFDProgramaD dao;
        BarChartModel modelchar = new BarChartModel();
        ChartSeries item;
        String[] plantilla = {"Mal", "Regular", "Bien", "Muy bien", "Excelente"};
        int cont ;
        try {
            dao = new ImplFDProgramaD();
            List<FDProgramaM> lista = dao.lstRFDP_T_I(sesion);
            for (FDProgramaM fProfe : lista) {
                item = new ChartSeries();
                item.setLabel(fProfe.getRFDP_T_I());
                EResultadoM result;
                cont = 1;
                for (String i : plantilla) {
                    if (cont <= fProfe.getRFDP_T_RS().size()) {
                        result = fProfe.getRFDP_T_RS().get(cont - 1);
                        if (result.getRFDP_T_Opcion().equals(i)) {
                            cont++;
                            item.set(result.getRFDP_T_Opcion(), result.getRFDP_T_Resultado());
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
        
    
    public void createBar_RFDP_DP(String sesion) throws Exception {
        barModelRFDP_DP = initBarModelRFDP_DP(sesion);

        barModelRFDP_DP.setTitle("2. Director de Programa: *");
        barModelRFDP_DP.setLegendPosition("ne");
        barModelRFDP_DP.setAnimate(true);
        barModelRFDP_DP.setShowPointLabels(true);
        barModelRFDP_DP.setShowDatatip(true);
        barModelRFDP_DP.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFDP_DP.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFDP_DP.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }
    
    public void createBar_RFDP_P(String sesion) throws Exception {
        barModelRFDP_P = initBarModelRFDP_P(sesion);

        barModelRFDP_P.setTitle("1. Profesor: ");
        barModelRFDP_P.setLegendPosition("ne");
        barModelRFDP_P.setAnimate(true);
        barModelRFDP_P.setShowPointLabels(true);
        barModelRFDP_P.setShowDatatip(true);
        barModelRFDP_P.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFDP_P.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFDP_P.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }
    
    public void createBar_RFDP_PA(String sesion) throws Exception {
        barModelRFDP_PA = initBarModelRFDP_PA(sesion);

        barModelRFDP_PA.setTitle("4. Participantes: ");
        barModelRFDP_PA.setLegendPosition("ne");
        barModelRFDP_PA.setAnimate(true);
        barModelRFDP_PA.setShowPointLabels(true);
        barModelRFDP_PA.setShowDatatip(true);
        barModelRFDP_PA.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis = barModelRFDP_PA.getAxis(AxisType.X);
        xAxis.setLabel("Opciones");

        Axis yAxis = barModelRFDP_PA.getAxis(AxisType.Y);
        yAxis.setLabel("Resultados");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }
    
    public void createBar_RFDP_PU(String sesion) throws Exception {
        barModelRFDP_PU = initBarModelRFDP_PU(sesion);

        barModelRFDP_PU.setTitle("Puntualidad");
        barModelRFDP_PU.setLegendPosition("ne");
        barModelRFDP_PU.setAnimate(true);
        barModelRFDP_PU.setShowPointLabels(true);
        barModelRFDP_PU.setShowDatatip(true);
        barModelRFDP_PU.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis1 = barModelRFDP_PU.getAxis(AxisType.X);
        xAxis1.setLabel("Opciones");

        Axis yAxis1 = barModelRFDP_PU.getAxis(AxisType.Y);
        yAxis1.setLabel("Resultados");
        yAxis1.setMin(0);
        yAxis1.setMax(5);
    }
    
    public void createBar_RFDP_SE(String sesion) throws Exception {
        barModelRFDP_SE = initBarModelRFDP_SE(sesion);

        barModelRFDP_SE.setTitle("5. Sesión ");
        barModelRFDP_SE.setLegendPosition("ne");
        barModelRFDP_SE.setAnimate(true);
        barModelRFDP_SE.setShowPointLabels(true);
        barModelRFDP_SE.setShowDatatip(true);
        barModelRFDP_SE.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis1 = barModelRFDP_SE.getAxis(AxisType.X);
        xAxis1.setLabel("Opciones");

        Axis yAxis1 = barModelRFDP_SE.getAxis(AxisType.Y);
        yAxis1.setLabel("Resultados");
        yAxis1.setMin(0);
        yAxis1.setMax(5);
    }
    
    public void createBar_RFDP_SER(String sesion) throws Exception {
        barModelRFDP_SER = initBarModelRFDP_SER(sesion);

        barModelRFDP_SER.setTitle("6. Servicios: ");
        barModelRFDP_SER.setLegendPosition("ne");
        barModelRFDP_SER.setAnimate(true);
        barModelRFDP_SER.setShowPointLabels(true);
        barModelRFDP_SER.setShowDatatip(true);
        barModelRFDP_SER.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis1 = barModelRFDP_SER.getAxis(AxisType.X);
        xAxis1.setLabel("Opciones");

        Axis yAxis1 = barModelRFDP_SER.getAxis(AxisType.Y);
        yAxis1.setLabel("Resultados");
        yAxis1.setMin(0);
        yAxis1.setMax(5);
    }
    
    public void createBar_RFDP_T(String sesion) throws Exception {
        barModelRFDP_T = initBarModelRFDP_T(sesion);

        barModelRFDP_T.setTitle("3. Tema:");
        barModelRFDP_T.setLegendPosition("ne");
        barModelRFDP_T.setAnimate(true);
        barModelRFDP_T.setShowPointLabels(true);
        barModelRFDP_T.setShowDatatip(true);
        barModelRFDP_T.setSeriesColors("3366CC,DC3912,FF9900,109618,990099");

        Axis xAxis1 = barModelRFDP_T.getAxis(AxisType.X);
        xAxis1.setLabel("Opciones");

        Axis yAxis1 = barModelRFDP_T.getAxis(AxisType.Y);
        yAxis1.setLabel("Resultados");
        yAxis1.setMin(0);
        yAxis1.setMax(5);
    }

    public void registrarEncFDPrograma() throws Exception {
        ImplFDProgramaD dao;
        try {
            dao = new ImplFDProgramaD();
            dao.agregarEncuestaDPrograma(model);
            limpiar();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarRespFDPrograma(String codSes) throws Exception {
        ImplFDProgramaD dao;
        try {
            dao = new ImplFDProgramaD();
            listRespFDPrograma = dao.listRespFDPrograma(codSes);
        } catch (Exception e) {
            throw e;
        }
    }

    public void limpiar() {
        model = new FDProgramaM();
    }

}
