package com.came.interfaces;

import com.came.model.PesoM;
import java.util.List;

public interface PesoI {

    List<PesoM> listarFase(String codprog) throws Exception;

    void editar(PesoM pM) throws Exception;
    
    void eliminarPeso(String codPeso) throws Exception;
}
