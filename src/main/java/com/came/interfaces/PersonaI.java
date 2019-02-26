package com.came.interfaces;

import com.came.model.PersonaDetM;
import com.came.model.PersonaM;
import java.sql.SQLException;
import java.util.List;

public interface PersonaI {

    List<PersonaM> listarPersona() throws SQLException;

    List<String> queryAutoCompleteUbigeo(String a) throws SQLException, Exception;

    List<String> queryAutoCompleteDirector(String a) throws SQLException, Exception;

    void agregarPersona(PersonaM persona) throws Exception;

    void asignarAlumno(String CODDETPROG, String CODPER) throws Exception;

    String leerUbigeo(String a) throws Exception;

    String leerDirector(String a) throws Exception;

    List<PersonaM> listarAlumno() throws SQLException;

    List<PersonaM> listarBirthday(String day) throws SQLException;
    
    List<PersonaM> listarBirthday() throws SQLException;

    List<PersonaM> listarBirthdayProgram(String codDetProg, String day) throws SQLException;

    List<PersonaM> listarBirthdayProgram(String codDetProg) throws SQLException;

    void traerResultados(PersonaM persona, String codPer, String codDetProg) throws Exception;

    List<PersonaM> listarDirectorioParticipante(String codDetProg) throws SQLException;
    
    List<PersonaM> listarDirectorioDirector(String codDetProg) throws SQLException;

    public String buscarDniPersona(String dniPer) throws Exception;

    void modificarFotoPersona(PersonaM per) throws Exception;

    void modificarOrganigramaPersona(PersonaDetM perd) throws Exception;

}
