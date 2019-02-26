package com.came.controller;

import com.came.dao.ImplFMonitorD;
import com.came.dao.ImplPersonaD;
import com.came.model.FMonitorM;
import com.came.model.PersonaM;
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
@Named(value = "monitorC")
@SessionScoped
public class MonitorC implements Serializable {

    //Asignacion de Monitor
    private String CodDetPrograma;
    private String CodSes;
    private String NombreMonitor;
    private List<PersonaM> LstAsigMonitor;
    private List<PersonaM> LstAsigMonitorSelect;
    private List<FMonitorM> lstPersonasPorMonitorear;
    private FMonitorM fmon;

    @PostConstruct
    public void start() {

    }

    public void listarAlumnosAsigMonitor() throws SQLException {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            LstAsigMonitor = dao.listarAsigMonitor(CodDetPrograma);
            if (LstAsigMonitor.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay Alumnos", "En este Programa"));
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void asignarMonitor() {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            String CodigoMonitor = dao.leerVW_PERSONA(NombreMonitor);
            dao.asignarMonitor(CodigoMonitor, LstAsigMonitorSelect, CodDetPrograma);
            listarAlumnosAsigMonitor();
            setNombreMonitor(null);
            LstAsigMonitorSelect = new ArrayList();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alumno", "Asignado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se pudo Asignar Monitor", e.getMessage()));
        }
    }

    public void listarPersonasPorMonitorear() throws Exception {
        ImplFMonitorD dao;
        try {
            dao = new ImplFMonitorD();
            lstPersonasPorMonitorear = dao.listarPersonasPorMonitorear(CodDetPrograma);
            if (lstPersonasPorMonitorear.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sin alumnos", "No se han asignado alumnos"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void agregarMonitoreo(FMonitorM fmodel) throws Exception {
        ImplFMonitorD dao;
        try {
            dao = new ImplFMonitorD();
            dao.agregarMonitoreo(fmodel, CodSes);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingresado", "correctamente"));
        } catch (Exception e) {
            throw e;
        }
    }

}
