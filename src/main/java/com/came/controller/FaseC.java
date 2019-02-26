package com.came.controller;

import com.came.dao.ImplFaseD;
import com.came.dao.ImplPesoD;
import com.came.model.FaseM;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Data;
import org.primefaces.PrimeFaces;

@Named(value = "faseC")
@SessionScoped
@Data
public class FaseC implements Serializable {

    FaseM fase = new FaseM();
    private List<FaseM> lstFase;
    private String CodDetProg;

    public void agregarFase(String CODDDETPROG) throws Exception {
        ImplFaseD dao;
        try {
            dao = new ImplFaseD();
            fase.setCODDETPROG(CODDDETPROG);
            dao.agregarFase(fase);
            agregarConfigPesos();
            PrimeFaces.current().executeScript("lstsesion();");
            PrimeFaces.current().executeScript("lstpesos();");
            fase.setNOMFASE(null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Módulo registrado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Al registrar módulo"));
            throw e;
        }
    }

    public void agregarConfigPesos() throws Exception {
        ImplFaseD dao;
        try {
            dao = new ImplFaseD();
            fase.setCODFASE(dao.leerSesionCreada(fase.getNOMFASE(), fase.getCODDETPROG()));
            dao.agregarConfigPesos(fase);
        } catch (Exception e) {
            throw e;
        }
    }

//    public List<String> completeTextPrograma(String query) throws Exception {
//        ImplFaseD dao = new ImplFaseD();
//        return dao.queryAutoCompletePrograma(query);
//    }
    public void listarFase() throws Exception {
        ImplFaseD dao;
        try {
            dao = new ImplFaseD();
            lstFase = dao.listarFase(fase.getCODDETPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void clear() {
        fase = new FaseM();
        fase.setNOMFASE(null);
    }

    public void cerrarModulo(String codMod) throws Exception {
        ImplFaseD dao;
        try {
            dao = new ImplFaseD();
            dao.cerrarModulo(codMod);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Modulo Cerrado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cerrar"));
            throw e;
        }
    }

    public void cerrarEcuesta(String codMod) throws Exception {
        ImplFaseD dao;
        try {
            dao = new ImplFaseD();
            dao.cerrarEncuesta(codMod);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "La encuesta de Impacto Parcial se aperturó correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo aperturar la encuesta"));
            throw e;
        }
    }

    public void activarModulo(String CodMod) {
        ImplFaseD dao;
        try {
            dao = new ImplFaseD();
            dao.activarFase(CodMod);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Modulo Activar"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo activar Modulo"));
        }
    }

    public void eliminarModulo(String CodMod, String CodPes) {
        ImplFaseD dao;
        ImplPesoD dao2;
        try {
            dao = new ImplFaseD();
            dao2 = new ImplPesoD();
            dao.eliminarFase(CodMod);
            dao2.eliminarPeso(CodPes);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Modulo Eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El modulo está en uso"));
        }
    }
}
