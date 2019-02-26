package com.came.interfaces;

import com.came.model.CompetenciaM;
import com.came.model.FaseM;
import com.came.model.SesionM;
import java.util.List;

public interface SesionI {

    void agregarSesion(SesionM sesion) throws Exception;

    /*Modifica el estado de la tabla sesiones*/
    void modificarConfiguracion(SesionM sesion) throws Exception;

    /*Listara las sesiones*/
    List<SesionM> filSesion(String CODDETPROG) throws Exception;

    /*Listara las Fases*/
    List<FaseM> listarFases(String CODGEN) throws Exception;

    /*Listara las Profesores*/
    List<SesionM> listarProfesores() throws Exception;

    /*Listara el nombre de la sesion, descripcion y fecha*/
    List<SesionM> listar(String NOMB, String FECH, String CodDetProg) throws Exception;

    void agregarsesionanexo(SesionM sesion) throws Exception;

    List<CompetenciaM> competencia() throws Exception;

    void agregarActividades(SesionM sesion) throws Exception;

    void eliminarActividad(String codSesAct) throws Exception;

    List<SesionM> listarActividades(String codSes) throws Exception;

    void eliminarSesion(String CODSES) throws Exception;

    boolean validadCantidadActividades(String CodDetPrograma) throws Exception;
    
    void actualisarSesion(SesionM sesion)throws Exception;
    
}
