package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class BibliotecaM implements Serializable {

    // Campos para subir documentos
    private String cod, nomb, fecha, tipocont, programa;
    private String tamano;
    private String link;
    private String codses;
    private String Ubicacion;
    private String Privilegio;

    //Campos para subir videos y/o peliculas
    private String codVid, nombVid, fecVid, tipoVid, progVid, durVid, priVid, ubiVid;

    public BibliotecaM() {
    }

    public BibliotecaM(String nomb, String fecha, String tipocont, String tamano, String Ubicacion, String Privilegio) {
        this.nomb = nomb;
        this.fecha = fecha;
        this.tipocont = tipocont;
        this.tamano = tamano;
        this.Ubicacion = Ubicacion;
        this.Privilegio = Privilegio;
    }

    public String getPrivilegioMask() {
        switch (Privilegio) {
            case "E":
                return "Participante";
            case "P":
                return "Profesor";
            default:
                return Privilegio;
        }
    }

}
