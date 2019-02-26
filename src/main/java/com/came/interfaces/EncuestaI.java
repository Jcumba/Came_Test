package com.came.interfaces;

import com.came.model.EncuestaM;
import java.util.List;

public interface EncuestaI {

    public void detalleSesion(EncuestaM enc, String codSes) throws Exception;

    void insertarLink(String codSes) throws Exception;

    List<EncuestaM> listarLinks(String codSes) throws Exception;
}
