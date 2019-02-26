package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class CoachM implements Serializable {

    private String CODCOA;
    private String HORINI;
    private String HORFIN;
    private String LUGAR;
    private String DESCRI;
    private String CODASICOA;
    private String FECHA;

    /**/
    private String CODDETPROG;
    private String PROGRAMA;
    private String COACH;
    private String DNICOA;

    private PersonaM persona;

    public CoachM() {
        persona = new PersonaM();
    }

}
