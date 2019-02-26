package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class EncuestaM implements Serializable {

    //DETALLE DE LA SESION
    private String Director, Profesor, FechaSes, nomSes, decSes, nomProg;
    private String linkPart, linkMonitor, linkDirec, linkProf;

    //GFORM PROFESOR
    private String CODFPRO, FECHAFPRO, DPINF, DPRET, DPPER, DPAPO, DPPUN, DPPAR, PARNE, PARMI, TMAA, TMUE, SUG;

    //GFORM PARTICIPANTE
    private String CODFPA, CODSES, FECHAFPA, COSE, COMO1, COMO2, FOPR1, FOPR2, FOPR3, MAEC, SECO, DPPI, DPPT, SUGPAR, AUTO;

    //GFORM DIRECTOR
    private String CODFDI, FECHAFDIR, COSEDIR, COMO, FOCO1, FOCO2, FOCO3, MAECDIR, SECODIR, ORSE, PRVI, PRLO1, PRLO2, PRLO3, PRLO4, PROES1, PROES2, SUGDIR;
}
