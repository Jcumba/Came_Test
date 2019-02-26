package com.came.interfaces;

import com.came.model.EResultadoM;
import com.came.model.FDProgramaM;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

public interface FDProgramaI {

    void agregarEncuestaDPrograma(FDProgramaM fdprograma) throws Exception;

    List<FDProgramaM> listRespFDPrograma(String codSes) throws Exception;
    
    void exportarPDFPrograma(Map parameters) throws JRException, IOException, Exception;
    
    List<FDProgramaM> lstRFDP_DP_I(String codSes) throws Exception;

    List<EResultadoM> lstRFDP_DP_R(String item,String codSes) throws Exception;
    
    List<FDProgramaM> lstRFDP_P_I(String codSes) throws Exception;

    List<EResultadoM> lstRFDP_P_R(String item,String codSes) throws Exception;
    
    List<FDProgramaM> lstRFDP_PA_I(String codSes) throws Exception;

    List<EResultadoM> lstRFDP_PA_R(String item,String codSes) throws Exception;
    
    List<FDProgramaM> lstRFDP_PU_I(String codSes) throws Exception;

    List<EResultadoM> lstRFDP_PU_R(String item,String codSes) throws Exception;

    List<FDProgramaM> lstRFDP_SE_I(String codSes) throws Exception;

    List<EResultadoM> lstRFDP_SE_R(String item,String codSes) throws Exception;
    
    List<FDProgramaM> lstRFDP_SER_I(String codSes) throws Exception;

    List<EResultadoM> lstRFDP_SER_R(String item,String codSes) throws Exception;
    
    List<FDProgramaM> lstRFDP_T_I(String codSes) throws Exception;

    List<EResultadoM> lstRFDP_T_R(String item,String codSes) throws Exception;
}
