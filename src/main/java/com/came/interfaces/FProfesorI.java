package com.came.interfaces;

//import java.util.List;
import com.came.model.EResultadoM;
import com.came.model.FProfesorM;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

public interface FProfesorI {

    void agregarEncuestaProfesor(FProfesorM fprofesor) throws Exception;
    
    boolean validacionProfesor(String dni) throws Exception;

    boolean verificarUrlFProfesor(String codSes) throws Exception;

    void traerCodPer(FProfesorM fprofesor, String dni) throws Exception;

    boolean traerEstForm(FProfesorM fprofesor) throws Exception;
    
    void exportarPDFProfesor(Map parameters) throws JRException, IOException, Exception;
    
    List<FProfesorM> listarRespFProfesor(String codSes) throws Exception;
    
    List<FProfesorM> lstRFP_DP_I(String codSes) throws Exception;

    List<EResultadoM> lstRFP_DP_R(String item,String codSes) throws Exception;
    
    List<FProfesorM> lstRFP_P_I(String codSes) throws Exception;

    List<EResultadoM> lstRFP_P_R(String item,String codSes) throws Exception;
    
    List<FProfesorM> lstRFP_T_I(String codSes) throws Exception;

    List<EResultadoM> lstRFP_T_R(String item,String codSes) throws Exception;
}
