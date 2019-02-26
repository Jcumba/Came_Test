package com.came.model;

import java.io.Serializable;

import com.came.services.SessionService;
import lombok.Data;

@Data
public class ProgDetM implements Serializable {

    private String CodDetProg;
    private String FechIni;
    private String FechFin;
    private String EstDetProg;
    private String GenDetProg;
    private String[] FrecDetProg;
    private String HorIniFin;
    private String CodPer, NomPer;
    private String CodProg, NomProg;

    //Sesiones
    private String codSes, nomSes;

    private ProgramaM programa;

    public ProgDetM() {
        programa = new ProgramaM();
    }

    public String getFrecDetProgMasc() {
        return SessionService.arrayToString(FrecDetProg).replace(","," / ");
    }

}
