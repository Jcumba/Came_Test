package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class EvalImpParcM implements Serializable {

    private String Sesiones;
    private String DECSES, ALUMNO, DNIPER, CODPROG, NOMPROG, ESTPROG, CODDETPROG, ESTDETPROG, CODFASE, ESTFASE, NOMFASE, CODSES, NOMSES, FECEVAIMP;

    //Variables para la tabla de respuesta en la evaluación de impacto parcial Cabecera
    private String CODIMPPARC, RPTA01, RPTA02, RPTA03, RPTA07, RPTA07_1, RPTA08, RPTA06_1, RPTA06_2, RPTA06_3, RPTA06_4, RPTA06_5;

    //Variables para obtener los profesores de un alumnos según sus sesiones, fases y programas
    private String PROFESOR, DIRECTOR,CODDIRECTOR, CODPROG_PROF, NOMPROG_PROF, ESTPROG_PROF, CODFASE_PROF, NOMFASE_PROF, ESTFASE_PROF, CODPROF, ALUMNO_PROF, DNIPER_PROF, CODSES_PROF;
    private String PROFESOR_DET, DIRECTOR_DET,CODDIRECTOR_DET, CODPROG_PROF_DET, NOMPROG_PROF_DET, ESTPROG_PROF_DET, CODFASE_PROF_DET, NOMFASE_PROF_DET, ESTFASE_PROF_DET, CODPROF_DET, ALUMNO_PROF_DET, DNIPER_PROF_DET, DECSES_DET;

    //Variables para guardar En el detalle de profesores
    private String CODIMPPARPROF_DETP, RPTA_DETP, CODPER_DETP, CODIMPPARC_DETP, PREGUNTA_DETP, RPTA04,RPTA04_1,RPTA05;
    
    //Variables para guardar En el detalle de sesiones
    private String CODIMPPARSES_DETS, RPTA_DETS, CODSES_DETS, CODIMPPARC_DETS, PREGUNTA_DETS, CODASIG;
    
    //variables para los informes de estado de las encuestas parciales
    private String CODDETPROG_EST,CODFASE_EST, NOMFASE_EST, PERSONA_EST, DNIPER_EST, ESTADO_EST;
}
