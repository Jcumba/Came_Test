package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class AuditoriaM implements Serializable {

    private String CODAUD, USUARIO, EVENTO, VALOR, CODDETPRO, CODBIB;
}
