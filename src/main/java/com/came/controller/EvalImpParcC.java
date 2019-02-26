package com.came.controller;

import com.came.dao.ImplEvalImpParcD;
import com.came.model.EvalImpParcM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Data
@Named(value = "evalImpParcC")
@SessionScoped
public class EvalImpParcC implements Serializable {

    EvalImpParcM evImParc = new EvalImpParcM();
    private List<EvalImpParcM> lstSesiones;
    private List<EvalImpParcM> lstProfesores;
    private List<EvalImpParcM> lstDirector;
    private List<EvalImpParcM> lstEstEvalParc;
    private String DniPer;
    private boolean validarDNI = false;
    private String CODDETPROG;

    public void listarTablasEvImParc() throws Exception, Exception {
        ImplEvalImpParcD dao;
        try {
            dao = new ImplEvalImpParcD();
            listarSesFase();
            listarProf();
            listarDirector();
            if (lstSesiones.size() > 0) {
                if (dao.validarImpcParc(DniPer, lstSesiones.get(0).getCODFASE())) {
                    validarDNI = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Resuelva su encuesta"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Listo!", "Usted ya resolvió la encuesta"));
                }
            } else {
                validarDNI = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "El DNI no está registrado"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarSesFase() throws Exception {
        ImplEvalImpParcD dao;
        try {
            dao = new ImplEvalImpParcD();
            lstSesiones = dao.lstSesionFase(getDniPer());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarProf() throws Exception {
        ImplEvalImpParcD dao;
        try {
            dao = new ImplEvalImpParcD();
            lstProfesores = dao.lstProfes(getDniPer());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarDirector() throws Exception {
        ImplEvalImpParcD dao;
        try {
            dao = new ImplEvalImpParcD();
            lstDirector = dao.lstDirectores(DniPer);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addEvalImpParc() throws Exception {
        ImplEvalImpParcD dao;
        try {
            dao = new ImplEvalImpParcD();
            String CodFase = "0";
            if (lstSesiones.size() > 0) {
                CodFase = lstSesiones.get(0).getCODFASE();
                dao.addEvalImpacParc(evImParc, DniPer, CodFase);
                agregarImpParcProfes();
                agregarImpParcSes();
                validarDNI = false;
                clearEvalImpParc();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Encuesta enviada Correctamente"));
            }
        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Los datos ingresados no son correctos"));
        }
    }

    public void agregarImpParcProfes() throws Exception {
        ImplEvalImpParcD dao;
        try {
            dao = new ImplEvalImpParcD();
            evImParc.setCODIMPPARC(dao.leerImpParcCreado(evImParc.getRPTA03(), evImParc.getRPTA07_1(), evImParc.getRPTA08()));
            dao.addEvalImpacParcProf(lstProfesores, evImParc.getCODIMPPARC());
        } catch (Exception e) {
            throw e;
        }
    }

    public void agregarImpParcSes() throws Exception {
        ImplEvalImpParcD dao;
        try {
            dao = new ImplEvalImpParcD();
            evImParc.setCODIMPPARC(dao.leerImpParcCreado(evImParc.getRPTA03(), evImParc.getRPTA07_1(), evImParc.getRPTA08()));
            dao.addEvalImpacParcSes(lstSesiones, evImParc.getCODIMPPARC());
        } catch (Exception e) {
            throw e;
        }
    }

    public void clearEvalImpParc() {
        evImParc = new EvalImpParcM();
        lstProfesores = new ArrayList<>();
        lstSesiones = new ArrayList<>();
        DniPer = null;
    }

    public void lstEstadoEvaluParc() throws Exception {
        ImplEvalImpParcD dao;
        try {
            dao = new ImplEvalImpParcD();
            lstEstEvalParc = dao.estadoImpacParc(CODDETPROG);
        } catch (Exception e) {
            throw e;
        }
    }
    
}
