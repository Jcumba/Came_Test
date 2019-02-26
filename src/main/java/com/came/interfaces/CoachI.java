package com.came.interfaces;

import com.came.model.CoachM;
import com.came.model.PersonaM;
import java.util.List;

public interface CoachI {

    List<CoachM> listaPrograma() throws Exception;

    List<PersonaM> listaAlum(String CODCOACH, String CODDETPROG) throws Exception;

    void addCoach(CoachM coach) throws Exception;

    List<CoachM> listarCoachines(String CODCOACH, String CODDETPROG) throws Exception;

    String contarSesionesCoaching(String codPer, String codDetProg) throws Exception;

    List<CoachM> listarSesionesCoaching(String codPer) throws Exception;
}
