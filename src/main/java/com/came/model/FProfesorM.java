package com.came.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class FProfesorM implements Serializable {

    private String CodFProfesor;
    private String CodSes;
    private String DireProg1;
    private String DireProg2;
    private String DireProg3;
    private String DireProg4;
    private String DireProg5;
    private String Part1;
    private String Part2;
    private String Tem1;
    private String Tem2;
    private String Sugerencia;

    private String RFP_DP_I;
    private String RFP_P_I;
    private String RFP_T_I;
    List<EResultadoM> RFP_DP_RS;
    List<EResultadoM> RFP_P_RS;
    List<EResultadoM> RFP_T_RS;

    public FProfesorM() {
        RFP_DP_RS = new ArrayList();
        RFP_P_RS = new ArrayList();
        RFP_T_RS = new ArrayList();
    }
    

}
