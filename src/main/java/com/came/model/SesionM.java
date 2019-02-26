package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class SesionM implements Serializable {

    /*Campos de la tabla Sesion*/
    private String CODSES, NOMSES, DECSES, ASISTENCIA, TIPSES, FECHASES;
    private String CODDETPROG;
    private String CODPROF;
    private String PROFESORES;
    private String HORA_INI;
    private String HORA_FIN;
    private String MODOSES;
    private String CODCOM;
    private String CANTDOC;
//    private String FASES;
    //    private String CODFASE;
    /*Campos de configuracion*/
    private boolean PARTICIPACION, CASO, CASO2, CONTROL, EXAMPARC, EXAMFINAL, TRABAJO;

    /*Campos para actividades*/
    private String codSesAct;
    private String iniAct;
    private String finAct;
    private String descAct;

    /*biblioteca*/
    private BibliotecaM Biblioteca;
    private PesoM pesos;
    private CompetenciaM competencia;
    private FaseM fase;

    public SesionM() {
        pesos = new PesoM();
        Biblioteca = new BibliotecaM();
        competencia = new CompetenciaM();
        fase = new FaseM();
    }

}
