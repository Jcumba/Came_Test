package com.came.dao;

import com.came.interfaces.EntrevistaI;
import com.came.model.EntrevistaM;
import com.came.model.InscripcionM;
import com.came.model.PersonaDetM;
import com.came.model.PersonaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplEntrevistaD extends Dao implements EntrevistaI {

    @Override
    public void guardarEntrevista(EntrevistaM en) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO ENTREVISTA(CODPER,FECENT,PRE1,PRE2,COMENT,ENTREVISTADOR,ESTENT)"
                    + "VALUES (?,sysdate,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, en.getCODPER());
            ps.setString(2, en.getPRE1());
            ps.setString(3, en.getPRE2());
            ps.setString(4, en.getCOMENT());
            ps.setString(5, en.getENTREVISTADOR());
            ps.setBoolean(6, en.isESTENT());
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void cambioEstadoR(InscripcionM ins) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE ASIGNACION SET ESTREG = 'A' WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, ins.getCODPER());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<PersonaM> postulantesProgramaE(String CODPROG) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<PersonaM> lista;
        String sql;
        try {
            sql = "SELECT * FROM VW_PARXPRO WHERE PROGRAMA = ? AND ESTADO = 'E' ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CODPROG);
            rs = ps.executeQuery();
            lista = new ArrayList();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setNomPer(rs.getString("NOMBRE"));
                per.setApePer(rs.getString("APELLIDO"));
                per.setDniPer(rs.getString("DNI"));
                lista.add(per);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void listarentrevista(PersonaDetM per, String CODPER) throws Exception {
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PARTICIPANTEE WHERE CODIGO = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODPER);
            rs = ps.executeQuery();
            while (rs.next()) {
                per.setFOTO(rs.getString("FOTO"));
                per.setDNI(rs.getString("DNI"));
                per.setNOMBRE(rs.getString("NOMBRE"));
                per.setAPELLIDO(rs.getString("APELLIDO"));
                per.setNACIMIENTO(rs.getString("NACIMIENTO"));
                per.setLUGARNAC(rs.getString("LUGARNAC"));
                per.setESTADOCIVIL(rs.getString("ESTADOCIVIL"));
                per.setSEXO(rs.getString("SEXO"));
                per.setCELULAR(rs.getString("CELULAR"));
                per.setDOMICILIO(rs.getString("DOMICILIO"));
                per.setDISTRITO(rs.getString("DISTRITO"));
                per.setCONYUGE(rs.getString("CONYUGE"));
                per.setPROFCONYUGE(rs.getString("PROFCONYUGE"));
                per.setEMPRESA(rs.getString("EMPRESA"));
                per.setRUC(rs.getString("RUC"));
                per.setDOMICILIOFISCAL(rs.getString("DOMICILIOFISCAL"));
                per.setTELETRAB(rs.getString("TELETRAB"));
                per.setEMAILTRABA(rs.getString("EMAILTRABA"));
                per.setACTIEMP(rs.getString("ACTIEMP"));
                per.setAREA(rs.getString("AREA"));
                per.setPUESTO(rs.getString("PUESTO"));
                per.setYEARPUES(rs.getString("YEARPUES"));
                per.setNPERSONAS(rs.getString("NPERSONAS"));
                per.setJEFE(rs.getString("JEFE"));
                per.setCARGOJEFE(rs.getString("CARGOJEFE"));
                per.setTELFJEFE(rs.getString("TELFJEFE"));
                per.setEMAILJEFE(rs.getString("EMAILJEFE"));
                per.setACTIVIDADPER(rs.getString("ACTIVIDADPER"));
                per.setAUTO(rs.getString("AUTO"));
                per.setMARCA(rs.getString("MARCA"));
                per.setPLACA(rs.getString("PLACA"));
                per.setORGANIGRAMA(rs.getString("ORGANIGRAMA"));
                per.setGRADOACA(rs.getString("GRADOACA"));
                per.setUNIVACA(rs.getString("UNIVACA"));
                per.setESPACA(rs.getString("ESPACA"));
                per.setPRO1(rs.getString("PRO1"));
                per.setPRO2(rs.getString("PRO2"));
                per.setPRO3(rs.getString("PRO3"));
                per.setDURA1(rs.getString("DURA1"));
                per.setDURA2(rs.getString("DURA2"));
                per.setDURA3(rs.getString("DURA3"));
                per.setINST1(rs.getString("INST1"));
                per.setINST2(rs.getString("INST2"));
                per.setINST3(rs.getString("INST3"));
                per.setCON1(rs.getString("CON1"));
                per.setCON2(rs.getString("CON2"));
                per.setCON3(rs.getString("CON3"));
                per.setCON4(rs.getString("CON4"));
                per.setCON5(rs.getString("CON5"));
                per.setINTE1(rs.getString("INTE1"));
                per.setINTE2(rs.getString("INTE2"));
                per.setINTE3(rs.getString("INTE3"));
                per.setINTE4(rs.getString("INTE4"));
                per.setINTE5(rs.getString("INTE5"));
                per.setSEGUROPER(rs.getString("SEGUROPER"));
                per.setTELEFONOSEG(rs.getString("TELEFONOSEG"));
                per.setPEREMER(rs.getString("PEREMER"));
                per.setTELFEMER(rs.getString("TELFEMER"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void updateEntrevista(EntrevistaM en) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE ENTREVISTA SET PRE1 = ?, PRE2 = ?, COMENT = ? WHERE CODENT = ? ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, en.getPRE1());
            ps.setString(2, en.getPRE2());
            ps.setString(3, en.getCOMENT());
            ps.setString(4, en.getCODENT());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<EntrevistaM> listarEntrevistas() throws Exception {
        this.Conexion();
        ResultSet rs;
        List<EntrevistaM> lista;
        String sql;
        try {
            sql = "SELECT * FROM VW_ENTREVISTA";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            rs = ps.executeQuery();
            lista = new ArrayList();
            EntrevistaM en;
            while (rs.next()) {
                en = new EntrevistaM();
                en.setCODENT(rs.getString("CODIGO"));
                en.setCODPER(rs.getString("PARTICIPANTE"));
                en.setPRE1(rs.getString("PREGUNTA1"));
                en.setPRE2(rs.getString("PREGUNTA2"));
                en.setCOMENT(rs.getString("COMENTARIOS"));
                lista.add(en);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
