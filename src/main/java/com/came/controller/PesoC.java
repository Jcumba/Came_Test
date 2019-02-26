package com.came.controller;

import com.came.dao.ImplPesoD;
import com.came.dao.ImplProgDetD;
import com.came.model.PesoM;
import com.came.model.ProgDetM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Named(value = "pesoC")
@SessionScoped
@Data
public class PesoC implements Serializable {

    PesoM peso = new PesoM();
    private List<PesoM> lstPesoM;
    String CODPROG, CODDETPROG;


    
    public void clear(){
    peso = new PesoM();
    }
    
    public void modificar(PesoM peso) throws Exception {
        ImplPesoD dao;
        try {
            dao = new ImplPesoD();
            dao.editar(peso);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Peso Modificado", "correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No Ingresado"));
            throw e;
        }
    }

    public void listarFase() throws Exception {
        ImplPesoD dao;
        try {
            dao = new ImplPesoD();
            lstPesoM = dao.listarFase(getCODDETPROG());
        } catch (Exception e) {
            throw e;
        }
    }


    public void limpiarConfigPeso() throws Exception {
        peso = new PesoM();
    }
}
