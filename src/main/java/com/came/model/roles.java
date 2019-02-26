package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class roles implements Serializable {

    private String Tipo;
    private String Descripcion;

    public roles(String Tipo, String Descripcion) {
        this.Tipo = Tipo;
        this.Descripcion = Descripcion;
    }

}
