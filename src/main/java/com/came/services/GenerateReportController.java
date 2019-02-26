package com.came.services;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.came.dao.ImplSesionD;
import lombok.Data;

@Data
@Named(value = "generateReportController")
@SessionScoped
public class GenerateReportController implements Serializable {

    private String outLine;
    private String orden;
    
    public void descargarPDFOutLine() throws Exception {
        GenerateReport report;
        ImplSesionD dao;
        try {
            report = new GenerateReport();
            dao = new ImplSesionD();
            if (dao.validadCantidadActividades(outLine)) {
                Map<String, Object> parameters = new HashMap();
                parameters.put("CodDetProgOut", outLine);
                report.expotOutLine(parameters);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se encontraron Sesiones", "en el Programa"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void descargarPDFInscripcion() throws Exception {
        GenerateReport report;
        try {
            report = new GenerateReport();
            Map<String, Object> parameters = new HashMap();
            parameters.put("CodPerFicIns", SessionService.getCodigoPersona());
            report.exportFichaInscripcion(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public void descargarPDFDirectorio(String detPro) throws Exception {
        GenerateReport report;
        try {
            report = new GenerateReport();
            Map<String, Object> parameters = new HashMap();
            parameters.put("CodDetProgDir", detPro);
            report.exportDirectorio(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public void descargarPDFSesiones(String codSes) throws Exception {
        GenerateReport report;
        try {
            report = new GenerateReport();
            Map<String, Object> parameters = new HashMap();
            parameters.put("CodSesion", codSes);
            report.exportSesiones(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public void descargarPDFAsistenciaPorSesion(String codSes) throws Exception {
        GenerateReport report;
        try {
            report = new GenerateReport();
            Map<String, Object> parameters = new HashMap();
            parameters.put("ParCodSes", codSes);
            report.exportAsistenciaPorSesion(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public void descargarPDFNotasPorPrograma(String CODETPROG) throws Exception {
        GenerateReport report;
        try {
            report = new GenerateReport();
            Map<String, Object> parameters = new HashMap();
            parameters.put("CODETPROG",CODETPROG);
            parameters.put("NOMORDEN", getOrden());
            report.exportNotasPorPrograma(parameters);
        } catch (Exception e) {
            throw e;
        }
    }
}
