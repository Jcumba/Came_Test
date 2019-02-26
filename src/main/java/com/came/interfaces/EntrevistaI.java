package com.came.interfaces;

import com.came.model.EntrevistaM;
import com.came.model.InscripcionM;
import com.came.model.PersonaDetM;
import com.came.model.PersonaM;
import java.util.List;

public interface EntrevistaI {

    void guardarEntrevista(EntrevistaM en) throws Exception;
    
    void updateEntrevista(EntrevistaM en) throws Exception;

    void cambioEstadoR(InscripcionM ins) throws Exception;

    List<PersonaM> postulantesProgramaE(String CODPROG) throws Exception;

    void listarentrevista(PersonaDetM per, String CODPER) throws Exception;
    
    List<EntrevistaM> listarEntrevistas() throws Exception;

}
