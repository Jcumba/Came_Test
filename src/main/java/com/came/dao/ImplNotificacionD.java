package com.came.dao;

import com.came.interfaces.NotificacionI;
import com.came.model.NotificacionM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplNotificacionD extends Dao implements NotificacionI {

    @Override
    public void agregarNotificacion(NotificacionM notificacion) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO NOTIFICACION(CODDETPROG,MENNOT,FECNOT) VALUES(?,?,CURRENT_TIMESTAMP)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, notificacion.getProgdet().getCodDetProg());
            ps.setString(2, notificacion.getMenNot());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<NotificacionM> listarNotificacion(String codDetProg) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<NotificacionM> lista;
        String sql;
        try {
            sql = "SELECT CODNOT,MENNOT,FECNOT FROM VW_NOTIFICACION WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codDetProg);
            rs = ps.executeQuery();
            lista = new ArrayList();
            NotificacionM not;
            while (rs.next()) {
                not = new NotificacionM();
                not.setCodNot(rs.getString("CODNOT"));
                not.setMenNot(rs.getString("MENNOT"));
                not.setFecNot(rs.getString("FECNOT"));
                lista.add(not);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<NotificacionM> listarNotificacionParticipante(String codDetProg) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<NotificacionM> lista;
        String sql;
        try {
            sql = "SELECT MENNOT,FECNOT FROM VW_NOTIFICACION WHERE CODDETPROG = ? AND TO_CHAR(SYSDATE, 'DD/MM/YYYY') <= FECNOT2";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codDetProg);
            rs = ps.executeQuery();
            lista = new ArrayList();
            NotificacionM not;
            while (rs.next()) {
                not = new NotificacionM();
                not.setMenNot(rs.getString("MENNOT"));
                not.setFecNot(rs.getString("FECNOT"));
                lista.add(not);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
