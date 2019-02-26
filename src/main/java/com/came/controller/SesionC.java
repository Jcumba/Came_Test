package com.came.controller;

import com.came.dao.ImplProgDetD;
import com.came.dao.ImplSesionD;
import com.came.model.BibliotecaM;
import com.came.model.CompetenciaM;
import com.came.model.FaseM;
import com.came.model.PesoM;
import com.came.model.ProgDetM;
import com.came.model.SesionM;
import com.came.services.SessionService;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Data;

@Named(value = "sesionC")
@SessionScoped
@Data
public class SesionC implements Serializable {

    private SesionM selected = new SesionM();
    private ProgDetM programa = new ProgDetM();
    private BibliotecaM materiales = new BibliotecaM();
    private List<SesionM> lstsesion;
    private List<SesionM> lstActividades;
    private List<SesionM> lstProfes;
    private List<FaseM> lstFase;
    private List<ProgDetM> listaCbGen;
    private List<ProgDetM> listaCbSes;
    private PesoM peso = new PesoM();
    private String CODPROG, CODDETPROG, CODSES;
    private List<SesionM> lstSes;
    private List<CompetenciaM> lstcomp;
    private String NOMB = "";
    private String FECH = "";
    private String[] CODBIB;//CODIGO BIBLIOTECA
    private String[] CODCOM;//CODIGO COMPETENCIAS

    @PostConstruct
    public void init() {
        try {
            listarProfesores();

            if (SessionService._isDirectorPrograma()) {
                listarCbGeneracionDirector();
            } else {
                listarCbGeneracion();
            }
            listarcbCompetencias();
            if (SessionService._isEstudiante()) {
                setCODDETPROG(SessionService.getPersona().getAsignacion().getCODDETPROG());
                listarCbProgenUser();
                listarSesiones(CODDETPROG);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
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

    public void listarCbGeneracionDirector() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            listaCbGen = dao.listarCbGeneracion(SessionService.getCodigoPersona());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarCbProgenUser() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            listaCbGen = dao.listarCbProgenUser(SessionService.getCodigoPersona());
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarSesion(String CODSES) throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            dao.eliminarSesion(CODSES);
            listarSes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminad", "Sesión eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "La sesion está en uso"));
        }
    }

    public void listarCbSesion(String codDetProg) throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            selected.setCODDETPROG(codDetProg);
            listaCbSes = dao.listarCbSesiones(selected.getCODDETPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void pasoDeDatos() {
        selected.setCODSES(CODSES);
        setCODDETPROG(null);
        setCODPROG(null);
        setCODSES(null);
    }

    public void listarcbCompetencias() throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            lstcomp = dao.competencia();
        } catch (Exception e) {
            throw e;
        }

    }

    public void listarProfesores() {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            lstProfes = dao.listarProfesores();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }
    }

    public void listarFases() throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            lstFase = dao.listarFases(getCODDETPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarSesiones(String codDetProg) throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            lstSes = dao.listar(NOMB, FECH, codDetProg);
        } catch (Exception e) {
            throw e;
        }
    }

    public void agregarSesion() throws Exception {
        ImplSesionD dao;
        materiales.setCod(Arrays.toString(CODBIB));
        try {
            dao = new ImplSesionD();
            selected.setCODCOM(Arrays.toString(getCODCOM()));
            selected.setCODCOM(selected.getCODCOM().substring(1, selected.getCODCOM().length() - 1));// quitar corchetes
            selected.setCODCOM(selected.getCODCOM().replace(" ", ""));//quitar espacio
            dao.agregarSesion(selected);
            setCODCOM(null);
            listarSes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Satisfactorio", "Sesión Ingresada Correctamente"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void selecciondatos(String CODSES, String CODFASE) {
        selected.getFase().setCODFASE(CODFASE);
        selected.setCODSES(CODSES);
    }

    public void anexarBib() throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            materiales.setCod(Arrays.toString(CODBIB));
            if (selected.getCODSES() != null) {
                if (!"[]".equals(materiales.getCod())) {
                    materiales.setCod(materiales.getCod().substring(1, materiales.getCod().length() - 1));
                    String[] arrayCODLIB = materiales.getCod().split(",");
                    for (String string : arrayCODLIB) {
                        BibliotecaM bli = new BibliotecaM();
                        bli.setCod(string);
                        selected.setBiblioteca(bli);
                        dao.agregarsesionanexo(selected);
                        Thread.sleep(80);
                    }
                    setCODBIB(null);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Satisfactorio", "Documento Anexado Correctamente"));
                    listarSes();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se pudo Anexar"));
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public void actualizarConfig(SesionM model) {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            model.setTIPSES("");

            if (model.isCASO() && model.isCASO2()) {
                model.setTIPSES("S2C");
            }
            if (model.isPARTICIPACION()) {
                model.setTIPSES(model.getTIPSES() + "S");
            }
            if (model.isCASO()) {
                model.setPARTICIPACION(true);
                model.setTIPSES("S");
            }
            if (model.isCASO2()) {
                model.setPARTICIPACION(true);
                model.setCASO(true);
                model.setTIPSES("S2");
            }
            if (model.isEXAMPARC()) {
                model.setPARTICIPACION(false);
                model.setEXAMFINAL(false);
                model.setCONTROL(false);
                model.setCASO(false);
                model.setTIPSES(model.getTIPSES() + "P");
            }
            if (model.isEXAMFINAL()) {
                model.setPARTICIPACION(false);
                model.setEXAMPARC(false);
                model.setCONTROL(false);
                model.setCASO(false);
                model.setTIPSES(model.getTIPSES() + "F");
            }

            if (model.isTRABAJO()) {
                model.setTIPSES(model.getTIPSES() + "T");
            }
            if (!model.isTRABAJO() && !model.isCONTROL() && !model.isPARTICIPACION() && !model.isEXAMPARC() && !model.isEXAMFINAL() && !model.isCASO2()) {
                model.setTIPSES("I"); //SESION DE INTRODUCCION "I"
            }
            dao.modificarConfiguracion(model);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Satisfactorio", "Configuracion Satisfactoria"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.toString()));
        }
    }

    public void listarSes() throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            lstsesion = dao.filSesion(CODDETPROG);
        } catch (Exception e) {
            throw e;
        }
    }

    public void actualizarSesion() throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            dao.actualisarSesion(selected);
            listarSes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Satisfactorio", "Sesion Modificada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo modificar"));
            throw e;
        }
    }

    public void limpiarSesion() throws Exception {
        selected = new SesionM();
    }

    public void agregarActividad() throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            dao.agregarActividades(selected);
            listarActividades(selected.getCODSES(), selected.getNOMSES());
            selected.setIniAct(null);
            selected.setFinAct(null);
            selected.setDescAct(null);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarActividad(String codSesAct) throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            dao.eliminarActividad(codSesAct);
            listarActividades(selected.getCODSES(), selected.getNOMSES());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarActividades(String codSes, String nomSes) throws Exception {
        ImplSesionD dao;
        try {
            dao = new ImplSesionD();
            selected.setIniAct(null);
            selected.setFinAct(null);
            selected.setDescAct(null);
            lstActividades = dao.listarActividades(codSes);
            selected.setNOMSES(nomSes);
            selected.setCODSES(codSes);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clean() {
        setCODDETPROG(null);
        setCODPROG(null);
        setCODSES(null);
        materiales.setCod(null);
        setCODCOM(null);
        setLstsesion(null);
        setListaCbSes(null);
    }
}
