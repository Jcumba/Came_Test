package com.came.controller;

import com.came.dao.ImplPersonaD;
import com.came.dao.ImplProgDetD;
import com.came.model.ProgDetM;
import com.came.services.SessionService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;
import org.primefaces.PrimeFaces;

@Named(value = "progDetC")
@SessionScoped
@Data
public class ProgDetC implements Serializable {

    private ProgDetM progdet = new ProgDetM();
    private List<ProgDetM> listaProgDet;
    private List<ProgDetM> listaCbGen;
    private List<ProgDetM> listaCbSes;

    @PostConstruct
    public void init() {
        try {
            listarProDet();
        } catch (Exception ex) {
            Logger.getLogger(ProgDetC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiar() {
        progdet = new ProgDetM();
    }

    public void agregarProgDet() throws Exception {
        ImplProgDetD dao;
        ImplPersonaD pd;
        try {
            dao = new ImplProgDetD();
            pd = new ImplPersonaD();
            progdet.setCodPer(pd.leerDirector(progdet.getCodPer()));
            dao.agregarProDet(progdet);
            limpiar();
            listarProDet();
            PrimeFaces.current().executeScript("listarComboPrograma();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Detalle agregado", "correctamente"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarProDet() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            listaProgDet = dao.listarProDet();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarCbGeneracion() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            listaCbGen = dao.listarCbGeneracion();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarCbSesion() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            listaCbSes = dao.listarCbSesiones(progdet.getCodDetProg());
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarPrgDet(String codDetProg) throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            progdet.setCodDetProg(codDetProg);
            dao.deleteProgDet(progdet);
            listarProDet();
            PrimeFaces.current().executeScript("listarComboPrograma();");
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Programa detalle eliminado completamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "El Detalle del programa está en uso"));
            throw e;
        }
    }

    public void updateProgDet() throws Exception {
        ImplProgDetD dao;
        ImplPersonaD pd;
        try {
            dao = new ImplProgDetD();
            pd = new ImplPersonaD();
            progdet.setCodPer(pd.leerDirector(progdet.getNomPer()));
            dao.updateProgDet(progdet);
            listarProDet();
            PrimeFaces.current().executeScript("updateFormProgDet();");
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Detalle de Programa actualizado"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void iniciarProgDet(String codDetProg) throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            dao.iniciarProgDet(codDetProg);
            PrimeFaces.current().executeScript("listarComboPrograma();");
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Programa iniciado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo iniciar el el programa"));
            throw e;
        }
    }
}
