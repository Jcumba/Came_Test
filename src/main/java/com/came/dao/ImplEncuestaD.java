package com.came.dao;

import com.came.interfaces.EncuestaI;
import com.came.model.EncuestaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplEncuestaD extends Dao implements EncuestaI {

    @Override
    public void detalleSesion(EncuestaM enc, String codSes) throws Exception {
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_SESION_FORM WHERE SESION = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            rs.next();
            enc.setDirector(rs.getString("DIRECTOR"));
            enc.setProfesor(rs.getString("PROFESOR"));
            enc.setFechaSes(rs.getString("FECHASES"));
            enc.setNomSes(rs.getString("NOMSES"));
            enc.setDecSes(rs.getString("DECSES"));
            enc.setNomProg(rs.getString("NOMPROG"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void insertarLink(String codSes) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE SESION SET LINK_PART=?,LINK_MONITOR=?,LINK_DIREC=?,LINK_PROF=? WHERE CODSES=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            ps.setString(2, codSes);
            ps.setString(3, codSes);
            ps.setString(4, codSes);
            ps.setString(5, codSes);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<EncuestaM> listarLinks(String codSes) throws Exception {
        List<EncuestaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT CODSES,LINK_PART,LINK_MONITOR,LINK_DIREC,LINK_PROF FROM SESION WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EncuestaM enc;
            while (rs.next()) {
                enc = new EncuestaM();
                enc.setCODSES(rs.getString("CODSES"));
                enc.setLinkDirec(rs.getString("LINK_DIREC"));
                enc.setLinkProf(rs.getString("LINK_PROF"));
                enc.setLinkPart(rs.getString("LINK_PART"));
                enc.setLinkMonitor(rs.getString("LINK_MONITOR"));
                lista.add(enc);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

        }
