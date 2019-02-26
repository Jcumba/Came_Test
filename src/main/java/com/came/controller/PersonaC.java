package com.came.controller;

import com.came.dao.ImplCoachD;
import com.came.dao.ImplNotificacionD;
import com.came.dao.ImplPersonaD;
import com.came.model.*;
import com.came.services.SessionService;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Data;
import org.primefaces.PrimeFaces;
import sun.security.util.Password;

@Data
@Named(value = "personaC")
@SessionScoped
public class PersonaC implements Serializable {

    //Variable de Session
    private PersonaM usuario = new PersonaM();
    private List<FaseM> Lstfase = new ArrayList();

    private PersonaM persona = new PersonaM();
    private List<PersonaM> listaPersona;
    private List<PersonaM> listaFilterPersona;
    private List<PersonaM> listHbHoy;
    private List<PersonaM> listHbMan;
    private List<PersonaM> listHbPm;
    private List<PersonaM> listHb7;
    private List<PersonaM> lstDirectorioPart;
    private List<PersonaM> lstDirectorioDirec;
    private List<NotificacionM> lstNotificacionParticipante;
    private List<CoachM> lstSesionCoach;
    private String CODDETPROG;

    //Asignacion
    private List<PersonaM> lstAlumno;
    private List<PersonaM> lstselect;

    //Participante
    private List<roles> LtsInasistencias;
    private List<roles> LtsTardanzas;
    private String antiguoPassword;
    private ProgDetM horario;

    @PostConstruct
    public void init() {
        try {
            listarPersona();
            listarAlumnos();
            listarBirthday();
        } catch (Exception ex) {
            Logger.getLogger(PersonaC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarAlumnos() throws SQLException {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            lstAlumno = dao.listarAlumno();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void asignar() {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            for (PersonaM personaM : lstselect) {
                dao.asignarAlumno(getCODDETPROG(), personaM.getCodPer());
                Thread.sleep(80);
            }
            listarAlumnos();
            setCODDETPROG(null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alumno", "Asignado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se pudo Asignar", e.getMessage()));
        }
    }

    public void listarPersona() throws SQLException {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            listaPersona = dao.listarPersona();
            listaFilterPersona = listaPersona;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void listarDirectorioParticipante(String codDetProg) throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            lstDirectorioDirec = dao.listarDirectorioDirector(codDetProg);
            lstDirectorioPart = dao.listarDirectorioParticipante(codDetProg);
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompleteProfesor(String query) throws Exception {
        ImplPersonaD dao = new ImplPersonaD();
        return dao.ListProfesorAutoComplete(query);
    }

    public List<String> autocompleteCoach(String query) throws Exception {
        ImplPersonaD dao = new ImplPersonaD();
        return dao.ListCoachAutoComplete(query);
    }

    public List<String> autocompleteMonitor(String query) throws Exception {
        ImplPersonaD dao = new ImplPersonaD();
        return dao.ListMonitorAutoComplete(query);
    }

    public void limpiarPersona() {
        persona = new PersonaM();
    }

    public List<String> autocompleteUbigeo(String query) throws SQLException, Exception {
        ImplPersonaD dao = new ImplPersonaD();
        return dao.queryAutoCompleteUbigeo(query);
    }

    public List<String> autocompleteDirector(String query) throws SQLException, Exception {
        ImplPersonaD dao = new ImplPersonaD();
        return dao.queryAutoCompleteDirector(query);
    }

    //INSERTAR O AGREGAR UNA NUEVA PERSONA
    public void agregarPersona() throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            if (dao.validarUsuario(persona.getUserPer(), "-")) {
                persona.setCodUbi(dao.leerUbigeo(persona.getNomUbi()));
                dao.agregarPersona(persona);
                listarPersona();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Persona", "Agregada"));
                limpiarPersona();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario Existente"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    //ACTUALIZAR DATOS DE LA PERSONA
    public void updatePersona() throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            if (dao.validarUsuario(persona.getUserPer(), persona.getCodPer())) {
                persona.setCodUbi(dao.leerUbigeo(persona.getCodUbi()));
                dao.updatePersona(persona);
                listarPersona();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "persona", "Actualizada"));
                PrimeFaces.current().executeScript("PF('UpPer').hide();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario Existente"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void cancelarPersona() throws Exception {
        persona = new PersonaM();
    }

    public void updateMyPassword() throws SQLException {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            if (dao.validarPassword(usuario.getCodPer(), antiguoPassword)) {
                dao.updatePassword(usuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña", "Actualizada"));
                PrimeFaces.current().executeScript("PF('UpMyPass').hide();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña", "Incorrecta"));
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    //ACTUALIZAR CONTRASEÑA DE LA PERSONA
    public void updatePassword() throws SQLException {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            dao.updatePassword(persona);
            listarPersona();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña", "Actualizada"));
        } catch (SQLException e) {
            throw e;
        }
    }

    public void prepararActualizarPassword() {
        persona.setCodPer(usuario.getCodPer());
    }

    //ELIMINAR UNA PERSONA 
    public void deletePersona() throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            dao.deletePersona(persona.getCodPer());
            listarPersona();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "persona", "Eliminado"));
        } catch (SQLException e) {
            throw e;
        }
    }

    // LOGIN
    private String user, psw;

    public void iniciarSesion() throws Exception {
        ImplPersonaD dao;
        ImplNotificacionD notiDao;
        ImplCoachD coachDao;
        try {
            dao = new ImplPersonaD();
            notiDao = new ImplNotificacionD();
            coachDao = new ImplCoachD();
            usuario = dao.iniciarSesion(user, psw);
            if (usuario != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Persona", usuario);
                setUser(null);
                setPsw(null);
                if (SessionService._isEstudiante() && SessionService._isRetirado()) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/faces/vistas/user/user.xhtml");
                    setLstfase(dao.fasesEstudiante(getUsuario().getAsignacion().getCODASIG(), getUsuario().getAsignacion().getCODDETPROG()));
                    setLtsInasistencias(dao.listarInasistencias(getUsuario().getAsignacion().getCODASIG(), getUsuario().getAsignacion().getCODDETPROG()));
                    setLtsTardanzas(dao.listarTardanzas(getUsuario().getAsignacion().getCODASIG(), getUsuario().getAsignacion().getCODDETPROG()));
                    listarBirthdayProgram(usuario.getAsignacion().getCODDETPROG());
                    horario = dao.listarHorario(usuario.getAsignacion().getCODDETPROG());
                    lstNotificacionParticipante = notiDao.listarNotificacionParticipante(usuario.getAsignacion().getCODDETPROG());
                    lstSesionCoach = coachDao.listarSesionesCoaching(SessionService.getCodigoPersona());
                } else {
                    PrimeFaces.current().executeScript("PF('roles').show();");
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario/Contraseña incorrecto"));
            }
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error de conexcion"));
            throw e;
        }
    }

    public void listarInasistenciasTardanzas() {

    }

    public void selectRol() throws IOException {
        switch (usuario.getTipoPer()) {
            case "RE":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/faces/vistas/user/user.xhtml");
                break;
            case "AD":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/dashboard");
                break;
            case "DI":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/dashboard");
                break;
            case "PR":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/dashboard");
                break;
            case "EN":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/dashboard");
                break;
            case "MO":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/monitor/sesiones");
                break;
            case "CO":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/dashboard");
                break;
            case "AM":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/dashboard");
                break;
            case "PM":
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/dashboard");
                break;
        }
    }

    public void listarBirthday() throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            listHbHoy = dao.listarBirthday("0");
            listHbMan = dao.listarBirthday("1");
            listHbPm = dao.listarBirthday("2");
            listHb7 = dao.listarBirthday();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void traerResultados(String codDetProg) throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            dao.traerResultados(usuario, SessionService.getCodigoPersona(), codDetProg);
            setLstfase(dao.fasesEstudiante(getUsuario().getAsignacion().getCODASIG(), codDetProg));
        } catch (SQLException e) {
            throw e;
        }
    }

    public void listarBirthdayProgram(String codDetProg) throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            listHbHoy = dao.listarBirthdayProgram(codDetProg, "0");
            listHbMan = dao.listarBirthdayProgram(codDetProg, "1");
            listHbPm = dao.listarBirthdayProgram(codDetProg, "2");
            listHb7 = dao.listarBirthdayProgram(codDetProg);
        } catch (SQLException e) {
            throw e;
        }
    }

    public void finishSession() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/");
    }

    //VALIDACION DE ACCESO POR URL
    public void securityLogin() throws Exception {
        if (SessionService._isLoginStart()) {
            if (SessionService._isTypeNotNull()) {
                if (SessionService._isEstudiante() || SessionService._isRetirado()) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/faces/vistas/user/user.xhtml");
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/dashboard");
                }
            }
        }
    }

    public void securitySessionAdministrativo() throws Exception {
        if (SessionService._isLoginStart()) {
            if (SessionService._isEstudiante()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/faces/vistas/user/user.xhtml");
            }
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/");
        }
    }

    public void securitySessionParticipante() throws Exception {
        if (SessionService._isLoginStart()) {
            if (!SessionService._isEstudiante() && !SessionService._isRetirado()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/faces/vistas/user/user.xhtml");
            }
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Came/");

        }
    }

}
