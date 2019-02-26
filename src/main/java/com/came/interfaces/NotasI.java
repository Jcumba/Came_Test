package com.came.interfaces;

import com.came.model.NotasM;
import java.util.List;

public interface NotasI {

    List<NotasM> listarNuevasNotas(String detProg, String codSes) throws Exception;

    void traerConfSesion(NotasM nota, String codSes) throws Exception;

    void agregarNuevasNotas(NotasM nota, String codSes) throws Exception;
    
    void editarNotas(NotasM nota) throws Exception;

    boolean validacionRegistros(String codSes) throws Exception;

    List<NotasM> listarRegistroNotas(String codSes) throws Exception;

}
