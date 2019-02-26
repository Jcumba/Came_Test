package com.came.controller;

import com.came.dao.ImplAsigCoachD;
import com.came.model.AsigCoachM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Named(value = "asigCoachC")
@SessionScoped
@Data
public class AsigCoachC implements Serializable {

    AsigCoachM asignacion = new AsigCoachM();
    private List<AsigCoachM> listaAsig;
    private List<AsigCoachM> listaFilterAsig;
    private List<AsigCoachM> lstAsig;

    @PostConstruct
    public void init(){
        try {
            listarAsigCoach();
        } catch (Exception ex) {
            Logger.getLogger(AsigCoachC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listarAsigCoach() throws Exception {
        ImplAsigCoachD dao;
        try {
            dao = new ImplAsigCoachD();
            lstAsig = dao.listarAsigCoach();
        } catch (Exception e) {
            throw e;
        }
    }

    public void insetAsigCoach() throws Exception {
        ImplAsigCoachD dao;
        try {
            dao = new ImplAsigCoachD();
            dao.insertAsigCoach(asignacion);
            listarAsigCoach();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignacion", "Agregada"));
            cleanAsigCoach();
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateAsigCoach() throws Exception {
        ImplAsigCoachD dao;
        try {
            dao = new ImplAsigCoachD();
            dao.updateAsigCoach(asignacion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignacion", "Actualizada"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void cleanAsigCoach() {
        asignacion = new AsigCoachM();
    }
}
