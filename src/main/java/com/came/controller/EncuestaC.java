package com.came.controller;

import com.came.dao.ImplEncuestaD;
import com.came.dao.ImplProgDetD;
import com.came.model.EncuestaM;
import com.came.model.ProgDetM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Data;

@Named(value = "encuestaC")
@SessionScoped
@Data
public class EncuestaC implements Serializable {

    private EncuestaM encuesta = new EncuestaM();
    private List<EncuestaM> listLinks;
    private List<ProgDetM> lstprogGen;

    @PostConstruct
    public void init() {
        try {
            listarCbProgGen();
        } catch (Exception e) {
        }
    }

    public void listarCbProgGen() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            lstprogGen = dao.listarCbGeneracion();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarDetSesion(String codses) throws Exception {
        ImplEncuestaD dao;
        try {
            dao = new ImplEncuestaD();
            dao.detalleSesion(encuesta, codses);
            listarLinks(codses);
        } catch (Exception e) {
            throw e;
        }
    }

    public void generarLink(String codSes) throws Exception {
        ImplEncuestaD dao;
        try {
            dao = new ImplEncuestaD();
            dao.insertarLink(codSes);
            listarLinks(codSes);
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarLinks(String codSes) throws Exception {
        ImplEncuestaD dao;
        try {
            dao = new ImplEncuestaD();
            listLinks = dao.listarLinks(codSes);
        } catch (Exception e) {
            throw e;
        }
    }

}
