package com.came.interfaces;

import com.came.model.ProgDetM;
import java.util.ArrayList;
import java.util.List;

public interface ProgDetI {

    void agregarProDet(ProgDetM prodet) throws Exception;

    List<ProgDetM> listarProDet() throws Exception;

    ArrayList<ProgDetM> listarCbGeneracion() throws Exception;

    ArrayList<ProgDetM> listarCbGeneracion(String CodDirectorPrograma) throws Exception;

    ArrayList<ProgDetM> listarCbProgenUser(String codPer) throws Exception;

    ArrayList<ProgDetM> listarCbSesiones(String detProg) throws Exception;
}
