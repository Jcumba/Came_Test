package com.came.controller;

import com.came.dao.ImplCoachD;
import com.came.dao.ImplPersonaD;
import com.came.model.CoachM;
import com.came.model.PersonaM;
import com.came.services.SessionService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Data
@Named(value = "coachC")
@SessionScoped
public class CoachC implements Serializable {

    private CoachM coach = new CoachM();
    private List<CoachM> lstprogram;
    private List<PersonaM> lstAlumno;
    private String nombreCoach;
    /*Variables de sesion*/
    private String contadorCoaching;

    //Vista Coach realizados
    List<CoachM> lstvwcoach;

    //Asignacion de Coach
    private String CodDetPrograma;
    private String NombreProfesor;
    private List<PersonaM> LstAsigCoach;
    private List<PersonaM> LstAsigCoachSelect;

    @PostConstruct
    public void init() {
        try {
            listaPrograma();
            if (SessionService._isEstudiante()) {
                contarSesionesCoaching(SessionService.getPersona().getAsignacion().getCODDETPROG());
            }
            if (SessionService._isCoach()) {
                listarAlumnosAsigCoach();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void contarSesionesCoaching(String codDetProg) throws Exception {
        ImplCoachD dao;
        try {
            dao = new ImplCoachD();
            contadorCoaching = dao.contarSesionesCoaching(SessionService.getCodigoPersona(), codDetProg);
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarcoachrealizados(String CODPER) throws Exception {
        ImplCoachD dao;
        try {
            dao = new ImplCoachD();
            lstvwcoach = dao.listarCoachines(CODPER, coach.getCODDETPROG());
        } catch (Exception e) {
            throw e;
        }

    }

    public void listaPrograma() throws Exception {
        ImplCoachD dao;
        try {
            dao = new ImplCoachD();
            lstprogram = dao.listaPrograma();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarAlumnosAsigCoach() throws SQLException {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            LstAsigCoach = dao.listarAsigCoach(CodDetPrograma);
            if (LstAsigCoach.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay Participante", "En este Programa"));
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void asignarCoach() {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            String CodigoCoach = dao.leerVW_PERSONA(NombreProfesor);
            dao.asignarCoach(CodigoCoach, LstAsigCoachSelect, CodDetPrograma);
            listarAlumnosAsigCoach();
            setNombreProfesor(null);
            LstAsigCoachSelect = new ArrayList();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alumno", "Asignado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo Asignar Coach", e.getMessage()));
        }
    }

    public void listarAlumnos() throws Exception {
        ImplCoachD dao = new ImplCoachD();
        try {
            lstAlumno = dao.listaAlum(SessionService.getCodigoPersona(), coach.getCODDETPROG());
            if (lstAlumno.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay Participante", "En este Programa"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void insertarCoach() throws Exception {
        ImplCoachD dao = new ImplCoachD();
        try {
            dao.addCoach(coach);
            clear();
            listaPrograma();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agregado Correctamente"));
        } catch (Exception e) {
            clear();
            throw e;
        }
    }

    public void clear() {
        coach.setHORFIN(null);
        coach.setHORINI(null);
        coach.setLUGAR(null);
        coach.setPersona(null);
        coach.setCODCOA(null);
        coach.setFECHA(null);
        coach.setDESCRI(null);
        coach.setDNICOA(null);
        coach.setCODDETPROG(null);
        coach.setCOACH(null);
        setNombreCoach(null);
        setLstAlumno(null);
    }
}
