package com.came.interfaces;

import com.came.model.FaseM;
import java.util.List;

public interface FaseI {

    void agregarFase(FaseM fase) throws Exception;

    List<FaseM> listarFase(String CODDETPROG) throws Exception;

    void eliminarFase(String Codfase)throws Exception;
    
    void activarFase(String Codfase) throws Exception;
}
