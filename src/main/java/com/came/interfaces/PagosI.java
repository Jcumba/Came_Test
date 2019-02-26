package com.came.interfaces;

import com.came.model.InscripcionM;
import com.came.model.PagosM;
import com.came.model.PersonaDetM;
import com.came.model.PersonaM;
import java.util.List;

public interface PagosI {

    void guardarPago(PagosM pa) throws Exception;
    
    void updatePago(PagosM pa) throws Exception;

    void cambioEstadoE(InscripcionM ins) throws Exception;

    List<PersonaM> postulantesProgramaA(String CODPROG) throws Exception;
    
    List<PagosM> listarPagos() throws Exception;

    void listaradministracion(PersonaDetM per, String CODPER) throws Exception;

    void guardarPagosDet(PagosM pa) throws Exception;

    String traeCodPag(String a) throws Exception;

}
