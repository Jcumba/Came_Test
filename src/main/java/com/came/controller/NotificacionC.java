package com.came.controller;

import com.came.dao.ImplNotificacionD;
import com.came.model.NotificacionM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Named(value = "notificacionC")
@SessionScoped
@Data
public class NotificacionC implements Serializable {

    private NotificacionM notificacion = new NotificacionM();
    private List<NotificacionM> lstNotificacion;

    public void traerCodDetProg(String codDetProg) {
        notificacion.getProgdet().setCodDetProg(codDetProg);
    }

    public void agregarNotificacion() throws Exception {
        ImplNotificacionD dao;
        try {
            dao = new ImplNotificacionD();
            dao.agregarNotificacion(notificacion);
            listarNotificacion();
            limpiarNotificacion();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "enviado correctamente"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarNotificacion() throws Exception {
        ImplNotificacionD dao;
        try {
            dao = new ImplNotificacionD();
            lstNotificacion = dao.listarNotificacion(notificacion.getProgdet().getCodDetProg());
        } catch (Exception e) {
            throw e;
        }
    }

    public void limpiarNotificacion() {
        notificacion = new NotificacionM();
    }

}
