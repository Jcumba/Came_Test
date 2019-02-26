package com.came.controller;

import com.came.dao.ImplNotasD;
import com.came.dao.ImplProgDetD;
import com.came.dao.ImplProgramaD;
import com.came.model.NotasM;
import com.came.model.ProgDetM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Named(value = "notasC")
@SessionScoped
@Data
public class NotasC implements Serializable {

    private NotasM nota = new NotasM();
    private List<NotasM> listNewNotas;
    private List<NotasM> lstPromDet;
    private List<NotasM> lstPromProg;

    private List<ProgDetM> listaCbGen;
    private boolean verified = true;

    private String CODPROG, CODDETPROG;

    @PostConstruct
    public void inicio() {
        try {
            listarCbGeneracion();
        } catch (Exception e) {
        }
    }

    public void limpiar() {
        listNewNotas = null;
        nota.setNomFase(null);
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

    public void agregarNuevasNotas(String codSes) throws Exception {
        ImplNotasD dao;
        try {
            dao = new ImplNotasD();
            for (NotasM model : listNewNotas) {
                model.setPromedio(0);
                dao.agregarNuevasNotas(model, codSes);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Notas agregadas", "correctamente"));
            nota = new NotasM();
        } catch (Exception e) {
            throw e;
        }
    }

    public void editarNotas(NotasM nota) throws Exception {
        ImplNotasD dao;
        try {
            dao = new ImplNotasD();
            if (verified == false) {
                dao.editarNotas(nota);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota modificada", "correctamente"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void viewRegistros(String detProg, String codSes) throws Exception {
        ImplNotasD dao;
        try {
            dao = new ImplNotasD();
            verified = dao.validacionRegistros(codSes);
            dao.traerConfSesion(nota, codSes);
            if (verified == true) {
                //Vista para registrar nuevas notas
                listNewNotas = dao.listarNuevasNotas(detProg, codSes);
            } else {
                //Mostrar los registros anteriores
                listNewNotas = dao.listarRegistroNotas(codSes);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Notas registradas", "ya se han registrado notas en esta sesión"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void validacionAsis(NotasM nota) throws Exception {
        ImplNotasD dao;
        try {
            dao = new ImplNotasD();
            if (nota.getNotaAsis() == "0") {
                nota.setNotaPlenaria(0);
                nota.setNotaCaso(0);
                nota.setNotaCaso2(0);
                nota.setNotaControl(0);
                nota.setNotaExamParc(0);
                nota.setNotaExamFinal(0);
                nota.setNotaTrabajo(0);
                if (verified == false) {
                    dao.editarNotas(nota);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asistencia modificada", "correctamente"));
                }
            } else {
                if (verified == false) {
                    dao.editarNotas(nota);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asistencia modificada", "correctamente"));
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarPromSesion(String genProg) throws Exception {
        ImplNotasD dao;
        try {
            dao = new ImplNotasD();
            lstPromDet = dao.promedioSesiones(genProg);
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarPromProg() throws Exception {
        ImplNotasD dao;
        try {
            dao = new ImplNotasD();
            lstPromProg = dao.promedioPrograma(getCODDETPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void clean() {
        setCODDETPROG(null);
        setCODPROG(null);
        lstPromProg = null;
    }

    public void agregarFaseDet(String genProg) throws Exception {
        ImplNotasD dao;
        try {
            dao = new ImplNotasD();
            dao.agregarFaseDet(genProg);
            lstPromDet = dao.promedioSesiones(genProg);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("CORRECTO", "Pomedios calculados"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void cerrarPrograma() throws Exception {
        ImplProgramaD dao;
        try {
            dao = new ImplProgramaD();
            dao.cerrarPrograma(dao.traerCodDetProg(getCODPROG(), getCODDETPROG()));
            clean();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Programa Cerrado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Verificar datos antes de cerrarlo"));
            throw e;
        }
    }

}
