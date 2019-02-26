package com.came.controller;

import com.came.dao.ImplEmpresaD;
import com.came.model.EmpresaM;
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

@Named(value = "empresaC")
@SessionScoped
@Data
public class EmpresaC implements Serializable {

    EmpresaM empresa = new EmpresaM();
    private List<EmpresaM> lstEmpresa;
    private String uniqueEmpre;

    @PostConstruct
    public void init() {
        try {
            listarEmpresa();
        } catch (Exception ex) {
            Logger.getLogger(ProgramaC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEmpresa() throws Exception {
        ImplEmpresaD dao;
        try {
            dao = new ImplEmpresaD();
            this.setUniqueEmpre(dao.buscarRucEmpresa(empresa.getRUCEMP()));
            if (empresa.getRUCEMP().equals(this.uniqueEmpre)) {
                limpiar();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "La empresa ya está registrada"));
            } else {
                empresa.setCODUBI(dao.leerUbi(empresa.getCODUBI()));
                dao.guardar(empresa);
                listarEmpresa();
                limpiar();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Empresa registrada correctamente"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Los datos ingresados no son correctos"));
        }
    }

    public List<String> completeTextUbi(String query) throws Exception {
        ImplEmpresaD dao = new ImplEmpresaD();
        return dao.queryAutoCompleteUbi(query);
    }

    public void listarEmpresa() throws Exception {
        ImplEmpresaD dao;
        try {
            dao = new ImplEmpresaD();
            lstEmpresa = dao.listarEmpresa();
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateEmpresa() throws Exception {
        ImplEmpresaD dao;
        try {
            dao = new ImplEmpresaD();
            empresa.setCODUBI(dao.leerUbi(empresa.getUBIGEO()));
            dao.updateEmpresa(empresa);
            listarEmpresa();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Empresa actualizado"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarEmpresa() throws Exception {
        ImplEmpresaD dao;
        try {
            dao = new ImplEmpresaD();
            dao.deleteEmpresa(empresa);
            listarEmpresa();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Empresa eliminada completamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "La Empresa está en uso"));
        }
    }

    public void limpiar() {
        empresa = new EmpresaM();
    }

}
