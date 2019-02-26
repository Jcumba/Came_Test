package com.came.controller;

import com.came.dao.ImplBibliotecaD;
import com.came.model.BibliotecaM;
import com.came.services.UploadFile;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.primefaces.model.UploadedFile;

@Named(value = "bibliotecaC")
@SessionScoped
@Data
public class BibliotecaC implements Serializable {

    private BibliotecaM model = new BibliotecaM();
    private UploadedFile file;
    private List<BibliotecaM> lstbi;
    private List<BibliotecaM> lstVideos;
    private List<BibliotecaM> lstDocs;
    private List<BibliotecaM> lstDocSes;
    private String Doc;
    private String video;
    private BibliotecaM select;
    private BibliotecaM selectVideo;

    @PostConstruct
    public void inicio() {
        try {
//            listarDocs();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void actualizarVideo() throws Exception {
        ImplBibliotecaD dao;
        try {
            dao = new ImplBibliotecaD();
            dao.actualizarVideo(selectVideo);
            listar();
            selectVideo = new BibliotecaM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Video"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void actualizar() throws Exception {
        ImplBibliotecaD dao;
        try {
            dao = new ImplBibliotecaD();
            dao.actualizar(select);
            listar();
            select = new BibliotecaM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Documento"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void guardarVideo() throws Exception {
        ImplBibliotecaD dao;
        try {
            dao = new ImplBibliotecaD();
            dao.registrarVideo(model);
            model = new BibliotecaM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se registro"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Seleccione Archivo"));
            throw e;
        }
    }

    public void guardar() throws Exception {
        DecimalFormat mb = new DecimalFormat("#.00");//Para redondear en MB
        ImplBibliotecaD dao;
        String tam;
        String FileName;
        try {
            dao = new ImplBibliotecaD();
            FileName = uploadFile();
            if (FileName != null) {
                if (FileName.equals("Existe")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "El archivo ya existe"));
                } else {
                    double b = (int) (file.getSize() / 1000); //Convierte a KB
                    tam = String.valueOf((int) b) + "kb";
                    //Si en mayor a Mil se convierte a MB
                    if (b > 1000) {
                        b = b / 1000;  //Condicional
                        tam = String.valueOf(mb.format(b)) + "Mb";
                    }
                    model.setNomb(FileName);
                    model.setTamano(tam);
                    dao.registrar(model);
                }
            }
            model = new BibliotecaM();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listar() throws Exception {
        ImplBibliotecaD dao;
        try {
            dao = new ImplBibliotecaD();
            lstbi = dao.listar(Doc);
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarVideos() throws Exception {
        ImplBibliotecaD dao;
        try {
            dao = new ImplBibliotecaD();
            lstVideos = dao.listarVideos(video);
        } catch (Exception e) {
            throw e;
        }
    }

    public void elimarAnexo(String CODBIB, String Codses) throws Exception {
        ImplBibliotecaD dao;
        try {
            dao = new ImplBibliotecaD();
            dao.eliminarAnexoDoc(CODBIB);
            PrimeFaces.current().executeScript("fmviewDocument");
            listardocanexados(Codses);
        } catch (Exception e) {
            throw e;
        }
    }

    public void listardocanexados(String CODSES) throws Exception {
        ImplBibliotecaD dao;
        try {
            dao = new ImplBibliotecaD();
            lstDocSes = dao.listarDocSes(CODSES);
        } catch (Exception e) {
            throw e;
        }
    }

    public String uploadFile() throws Exception {
        UploadFile service;
        String FileName;
        try {
            service = new UploadFile();
            if (file != null) {
                FileName = service.saveDocument(file, model.getUbicacion());
                if (FileName == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Archivo no aceptado"));
                    return null;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione Archivo"));
                return null;
            }
            return FileName;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public void listarDocs(String CODDETPROG) throws Exception {
        ImplBibliotecaD dao;
        try {
            dao = new ImplBibliotecaD();
            lstDocs = dao.listarDocumentos(dao.traerCodtippg(CODDETPROG));
        } catch (Exception e) {
            throw e;
        }
    }
}
