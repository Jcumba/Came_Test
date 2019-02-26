package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class NotificacionM implements Serializable {

    private String codNot;
    private String menNot;
    private String fecNot;
    private ProgDetM progdet;

    public NotificacionM() {
        progdet = new ProgDetM();
    }

}
