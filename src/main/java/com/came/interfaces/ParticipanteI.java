package com.came.interfaces;

import com.came.model.PersonaDetM;
import com.came.model.PersonaM;

import java.util.List;

public interface ParticipanteI {

    void guardar(PersonaM par) throws Exception;

    void guardarDocente(PersonaM par) throws Exception;

    void actualizar(PersonaM par) throws Exception;

    void cambioEstadoES(String CODPER) throws Exception;

    void cambioEstadoS(String CODPER) throws Exception;

    String traecodpar(String a) throws Exception;

    void listarDatosParticipantes(PersonaDetM per, String CODPER) throws Exception;

    List<PersonaM> listar(String CODPROG) throws Exception;

    List<PersonaM> listarInscritos(String CODPROG) throws Exception;

    void retirarParticipante(String codPer) throws Exception;

    void restablecerParticipante(String codPer) throws Exception;

    List<PersonaM> listarPartiNoAdmitidos(String CODPROG) throws Exception;

    void listarTipoPrograma(PersonaDetM per, String CODPROG) throws Exception;

    PersonaM getDatosParticipante(String CodPersona) throws Exception;

}
