package com.came.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class FParticipanteM implements Serializable {

    private String CodAsig;
    private String EstForm;
    private String CodFParticipante;
    private String CodSes;
    private String ProfRpt;
    private String ProfSug;
    private String TemRpt;
    private String TemSug;
    private String SeCoRpt;
    private String SeCoRpt1;
    private String SeCoRpt2;
    private String CasoRpt1;
    private String CasoRpt2;
    private String CasoRpt3;
    private String AutRpt1;
    private String AutRpt2;
    private String ServRpt1;
    private String Sugerencias;
    private String Quejas;
    
    
    private String RFPAR_CA_I;
    private String RFPAR_CB_I;
    private String RFPAR_P_I;
    private String RFPAR_PART_I;
    private String RFPAR_PUNT_I;
    private String RFPAR_SE_I;
    private String RFPAR_T_I;
    private String RFPAR_TD_I;
    List<EResultadoM> RFPAR_CA_RS;
    List<EResultadoM> RFPAR_CB_RS;
    List<EResultadoM> RFPAR_P_RS;
    List<EResultadoM> RFPAR_PART_RS;
    List<EResultadoM> RFPAR_PUNT_RS;
    List<EResultadoM> RFPAR_SE_RS;
    List<EResultadoM> RFPAR_T_RS;
    List<EResultadoM> RFPAR_TD_RS;
    
    public FParticipanteM() {
        RFPAR_CA_RS = new ArrayList();
        RFPAR_CB_RS = new ArrayList();
        RFPAR_P_RS = new ArrayList();
        RFPAR_PART_RS = new ArrayList();
        RFPAR_PUNT_RS = new ArrayList();
        RFPAR_SE_RS = new ArrayList();
        RFPAR_T_RS = new ArrayList();
        RFPAR_TD_RS = new ArrayList();
    }
}
