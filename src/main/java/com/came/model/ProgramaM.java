package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class ProgramaM implements Serializable {

    //Programas
    private String CodProg;
    private String NomProg;
    private String IniProg;
    private String EstProg;
    private String CodEmp;
    private String NombEmp;
    //Tipo de programas
    private String CodTipPg;
    private String NomTipPg;
    private String EstTipPg;
    //Para desactivar el estado del programa detalle (CERRAR UN PROGRAMA)
    private String EstDetProg;
    
    //Areas
    private String codArea;
    private String nomArea;
    private String abreArea;
    
}
