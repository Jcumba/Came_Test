package com.came.controller;

import com.came.dao.ImplProgramaD;
import com.came.model.ProgramaM;
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
import org.primefaces.PrimeFaces;

@Named(value = "programaC")
@SessionScoped
@Data
public class ProgramaC implements Serializable {

    private ProgramaM programa = new ProgramaM();
    private List<ProgramaM> listProgramas;
    private List<ProgramaM> listProgFiltr;
    private List<ProgramaM> listTipoPg;
    private List<ProgramaM> listCbTipoPg;
    private List<ProgramaM> listCbProg;
    private List<ProgramaM> listCbArea;
    private List<ProgramaM> lstprogygen;
    private String compararNomTipo;

    @PostConstruct
    public void init() {
        try {
            listarProgramas();
            listarCbTipoProg();
            listarTipoPg();
            listarCbProgramas();
            listarCbAreas();
            listarprogygen();
        } catch (Exception ex) {
            Logger.getLogger(ProgramaC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiar() {
        programa = new ProgramaM();
    }

    public void listarprogygen() throws Exception { // combo de programa y generacion
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            lstprogygen = dao.listarProgramasyGen();
        } catch (Exception e) {
            throw e;
        }
    }

    public void agregarPrograma() throws Exception {
        ImplProgramaD dao;
        try {
            if (!"3".equals(programa.getCodTipPg())) {
                programa.setCodEmp(null);
            }
            dao = new ImplProgramaD();
            programa.setCodEmp(dao.leerEmpre(programa.getCodEmp()));
            dao.agregarPrograma(programa);
            listarProgramas();
            listarCbProgramas();
            limpiar();
            PrimeFaces.current().ajax().update("formLista");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Programa registrado"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarProgramas() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            listProgramas = dao.listarProgramas();
            listProgFiltr = listProgramas;
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarCbProgramas() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            listCbProg = dao.listarCbProgramas();
        } catch (Exception e) {
            throw e;
        }
    }

    public void agregarTipoPg() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            dao.agregarTipoPg(programa);
            listarTipoPg();
            listarCbTipoProg();
            limpiar();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarTipoPg() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            listTipoPg = dao.listarTipoPg();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarCbTipoProg() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            listCbTipoPg = dao.listarCbTipoProg();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarCbAreas() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            listCbArea = dao.listarCbAreas();
        } catch (Exception e) {
            throw e;
        }
    }

    public void updatePrograma() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            if (!"3".equals(programa.getCodTipPg())) {
                programa.setCodEmp(null);
            } else {
                programa.setCodEmp(dao.leerEmpre(programa.getNombEmp()));
            }
            dao.updatePrograma(programa);
            listarCbProgramas();
            listarProgramas();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Programa actualizado"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarPrograma() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            dao.deletePrograma(programa);
            listarProgramas();
            listarCbProgramas();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Programa eliminado completamente"));
        } catch (Exception e) {
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "El programa está en uso"));
        }
    }

    public void updateTipoProg() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            dao.updateTipoPrograma(programa);
            listarTipoPg();
            listarCbTipoProg();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Tipo de prgrama actualizado"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarTipoProg() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            dao.deleteTipoProg(programa);
            listarTipoPg();
            limpiar();
            listarCbTipoProg();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Tipo de Programa eliminado completamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "El tipo de programa está en uso"));
        }
    }

    public List<String> completeTextEmpre(String query) throws Exception {
        ImplProgramaD dao = new ImplProgramaD();
        return dao.queryAutoCompleteEmpre(query);
    }

}
