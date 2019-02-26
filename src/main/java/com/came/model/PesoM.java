package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class PesoM implements Serializable {

    private String CODCONFPES;
    private String PESOCASO;
    private String PESOTRA;
    private String PESOPARC;
    private String PESOFINAL;
    private String PESOPART;

    
    private FaseM fase;

    public PesoM() {
        fase = new FaseM();
    }

}
