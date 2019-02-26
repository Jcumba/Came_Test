package com.came.controller;

import com.came.dao.ImplEntrevistaD;
import com.came.dao.ImplPagosD;
import com.came.dao.ImplParticipanteD;
import com.came.model.EntrevistaM;
import com.came.model.InscripcionM;
import com.came.model.PagosM;
import com.came.model.PersonaDetM;
import com.came.model.PersonaM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Named(value = "entrevistaC")
@SessionScoped
@Data
public class EntrevistaC implements Serializable {

    ImplParticipanteD pard = new ImplParticipanteD();
    InscripcionM ins = new InscripcionM();
    EntrevistaM en = new EntrevistaM();
    PagosM pa = new PagosM();
    private PersonaDetM listabloqent = new PersonaDetM();
    private PersonaDetM listatipoProg = new PersonaDetM();
    private List<PersonaM> listPar;
    private List<EntrevistaM> listEntrevista;
    private String CODPROG;
    private String CODPER;
    private EntrevistaM select;

    @PostConstruct
    public void init() {
        try {
            listar();
        } catch (Exception e) {
        }
    }

    public void listar() throws Exception {
        ImplEntrevistaD dao;
        try {
            dao = new ImplEntrevistaD();
            listEntrevista = dao.listarEntrevistas();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarTipoPrograma() throws Exception {
        try {
            pard.listarTipoPrograma(listatipoProg, getCODPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarEntrevista(String CODPER) throws Exception {
        ImplEntrevistaD dao;
        try {
            dao = new ImplEntrevistaD();
            dao.listarentrevista(listabloqent, pard.traecodpar(CODPER));
            listarTipoPrograma();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarPostulantesE() throws Exception {
        ImplEntrevistaD dao;
        try {
            dao = new ImplEntrevistaD();
            listPar = dao.postulantesProgramaE(getCODPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateEntrevista() throws Exception {
        ImplEntrevistaD dao;
        try {
            dao = new ImplEntrevistaD();
            dao.updateEntrevista(select);
            select = new EntrevistaM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizó correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo actualizar"));
            throw e;
        }
    }

    public void addEntrevista(String a) throws Exception {
        ImplEntrevistaD dao;
        ImplPagosD daoP;
        try {
            dao = new ImplEntrevistaD();
            daoP = new ImplPagosD();
            listarTipoPrograma();
            en.setCODPER(pard.traecodpar(CODPER));
            en.setENTREVISTADOR(a);
            dao.guardarEntrevista(en);
            ins.setCODPER(pard.traecodpar(CODPER));
            if ("2".equals(listatipoProg.getTipoProg()) || "3".equals(listatipoProg.getTipoProg())) {
                pa.setCODPER(en.getCODPER());
                pa.setADMINISTRADOR(en.getENTREVISTADOR());
                daoP.guardarPago(pa);
                daoP.cambioEstadoE(ins);
            } else {
                dao.cambioEstadoR(ins);
            }
            setCODPER(null);
            setCODPROG(null);
            en = new EntrevistaM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Se agregó correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo agregar"));
            throw e;
        }
    }

}
