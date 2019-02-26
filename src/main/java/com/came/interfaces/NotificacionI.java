package com.came.interfaces;

import com.came.model.NotificacionM;
import java.util.List;

public interface NotificacionI {

    void agregarNotificacion(NotificacionM notificacion) throws Exception;

    List<NotificacionM> listarNotificacion(String codDetProg) throws Exception;
    
    List<NotificacionM> listarNotificacionParticipante(String codDetProg) throws Exception;
}
