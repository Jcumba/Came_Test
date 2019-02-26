package com.came.controller;

import com.came.dao.ImplPagosD;
import com.came.dao.ImplParticipanteD;
import com.came.model.InscripcionM;
import com.came.model.PagosDetM;
import com.came.model.PagosM;
import com.came.model.PersonaDetM;
import com.came.model.PersonaM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Named(value = "pagosC")
@SessionScoped
@Data
public class PagosC implements Serializable {

    PagosM pa = new PagosM();
    InscripcionM ins = new InscripcionM();
    ImplParticipanteD pard = new ImplParticipanteD();
    private PersonaDetM listabloqadm = new PersonaDetM();
    private List<PersonaM> listPar;
    private List<PagosM> listPagos;
    private String CODPROG;
    private String CODPER;
    private int numpagos;
    private PagosM select;

    @PostConstruct
    public void init() {
        try {
            listarPagos();
        } catch (Exception e) {
        }
    }

    public void listapagos() {
        try {
            PagosDetM det;
            List<PagosDetM> lstPagos = new ArrayList();
            for (int i = 0; i < numpagos; i++) {
                det = new PagosDetM();
                lstPagos.add(det);
            }
            pa.setNumpagos(lstPagos);
        } catch (Exception e) {
        }
    }

    public void listarPagos() throws Exception {
        ImplPagosD dao;
        try {
            dao = new ImplPagosD();
            listPagos = dao.listarPagos();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarAdministracion(String CODPER) throws Exception {
        ImplPagosD dao;
        try {
            dao = new ImplPagosD();
            dao.listaradministracion(listabloqadm, pard.traecodpar(CODPER));
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarPostulantesA() throws Exception {
        ImplPagosD dao;
        try {
            dao = new ImplPagosD();
            listPar = dao.postulantesProgramaA(getCODPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void updatePagos() throws Exception {
        ImplPagosD dao;
        try {
            dao = new ImplPagosD();
            dao.updatePago(select);
            select = new PagosM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se actualizo correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo actualizar"));
            throw e;
        }
    }

    public void addPagos(String a) throws Exception {
        ImplPagosD dao;
        try {
            dao = new ImplPagosD();
            pa.setCODPER(pard.traecodpar(CODPER));
            pa.setADMINISTRADOR(a);
            dao.guardarPago(pa);
            pa.setCODPAG(dao.traeCodPag(pa.getCODPER()));
            dao.guardarPagosDet(pa);
            ins.setCODPER(pard.traecodpar(CODPER));
            dao.cambioEstadoE(ins);
            setCODPER(null);
            setCODPROG(null);
            setNumpagos(0);
            pa = new PagosM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Se agregó correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo agregar"));
            throw e;
        }
    }

}
