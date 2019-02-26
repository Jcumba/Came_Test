package com.came.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class FDProgramaM implements Serializable {

    private String Cod_FDProg;
    private String CodSes;
    private String ProfRpt1;
    private String ProfRpt2;
    private String ProfRpt3;
    private String ProfRpt4;
    private String PuntRpt1;
    private String PuntRpt2;
    private String AreaOporProf;
    private String DircProgRpt1;
    private String DircProgRpt2;
    private String TemRpt1;
    private String TemRpt2;
    private String TemSug;
    private String PartRpt1;
    private String PartRpt2;
    private String SesRpt;
    private String ServRpt1;
    private String ServRpt2;
    private String Sugerencia;

    private String RFDP_DP_I;
    private String RFDP_P_I;
    private String RFDP_PA_I;
    private String RFDP_PU_I;
    private String RFDP_SE_I;
    private String RFDP_SER_I;
    private String RFDP_T_I;

    List<EResultadoM> RFDP_DP_RS;
    List<EResultadoM> RFDP_P_RS;
    List<EResultadoM> RFDP_PA_RS;
    List<EResultadoM> RFDP_PU_RS;
    List<EResultadoM> RFDP_SE_RS;
    List<EResultadoM> RFDP_SER_RS;
    List<EResultadoM> RFDP_T_RS;

    public FDProgramaM() {
        RFDP_DP_RS = new ArrayList();
        RFDP_P_RS = new ArrayList();
        RFDP_PA_RS = new ArrayList();
        RFDP_PU_RS = new ArrayList();
        RFDP_SE_RS = new ArrayList();
        RFDP_SER_RS = new ArrayList();
        RFDP_T_RS = new ArrayList();
    }
}
