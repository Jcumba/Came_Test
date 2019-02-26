package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class FaseM implements Serializable {

    private String CODFASE;
    private String CODDETPROG;
    private String NOMFASE;
    private String PROMFASE;
    private String ESTFASE;
    
    //Detalle de las Notas
    private FormulaM formula;

    public FaseM() {
        formula = new FormulaM();
    }
 
}
