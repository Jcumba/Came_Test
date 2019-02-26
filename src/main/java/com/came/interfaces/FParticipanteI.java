package com.came.interfaces;

import com.came.model.EResultadoM;
import com.came.model.FParticipanteM;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

public interface FParticipanteI {

    void agregarEncuestaParticipante(FParticipanteM fpartitipante) throws Exception;

    boolean validacionParticipante(String dni) throws Exception;

    boolean verificarUrlFParticipante(String codSes) throws Exception;

    void traerCodAsig(FParticipanteM fpartitipante, String dni) throws Exception;

    boolean traerEstForm(FParticipanteM fpartitipante) throws Exception;

    List<FParticipanteM> listRespFParticipante(String codSes) throws Exception;

    void exportarPDFParticipante(Map parameters) throws JRException, IOException, Exception;

    List<FParticipanteM> lstRFPAR_CA_I(String codSes) throws Exception;

    List<EResultadoM> lstRFPAR_CA_R(String item, String codSes) throws Exception;

    List<FParticipanteM> lstRFPAR_CB_I(String codSes) throws Exception;

    List<EResultadoM> lstRFPAR_CB_R(String item, String codSes) throws Exception;

    List<FParticipanteM> lstRFPAR_P_I(String codSes) throws Exception;

    List<EResultadoM> lstRFPAR_P_R(String item, String codSes) throws Exception;

    List<FParticipanteM> lstRFPAR_PART_I(String codSes) throws Exception;

    List<EResultadoM> lstRFPAR_PART_R(String item, String codSes) throws Exception;

    List<FParticipanteM> lstRFPAR_PUNT_I(String codSes) throws Exception;

    List<EResultadoM> lstRFPAR_PUNT_R(String item, String codSes) throws Exception;

    List<FParticipanteM> lstRFPAR_SE_I(String codSes) throws Exception;

    List<EResultadoM> lstRFPAR_SE_R(String item, String codSes) throws Exception;

    List<FParticipanteM> lstRFPAR_T_I(String codSes) throws Exception;

    List<EResultadoM> lstRFPAR_T_R(String item, String codSes) throws Exception;

    List<FParticipanteM> lstRFPAR_TD_I(String codSes) throws Exception;

    List<EResultadoM> lstRFPAR_TD_R(String item, String codSes) throws Exception;

}
