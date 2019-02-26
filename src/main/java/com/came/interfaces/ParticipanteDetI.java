package com.came.interfaces;

import com.came.model.PersonaDetM;

public interface ParticipanteDetI {

    void guardar(PersonaDetM det) throws Exception;
    
    void actualizar(PersonaDetM det) throws Exception;
    
    String traeCodDet(String a) throws Exception;
    
    void guardarhijo(PersonaDetM par) throws Exception;
}
