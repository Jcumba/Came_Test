package com.came.dao;

import com.came.model.EvalImpParcM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplEvalImpParcD extends Dao {

    public List<EvalImpParcM> lstSesionFase(String DniPer) throws Exception {
        List<EvalImpParcM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_SES_IMP_PARC WHERE DNIPER = ? AND ESTFASE LIKE 'I' AND ESTPROG LIKE 'A' AND TIPSES = 'S' ORDER BY NOMSES";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, DniPer);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EvalImpParcM ses;
            while (rs.next()) {
                ses = new EvalImpParcM();
                ses.setDECSES(rs.getString("DECSES"));
                ses.setALUMNO(rs.getString("ALUMNO"));
                ses.setDNIPER(rs.getString("DNIPER"));
                ses.setCODPROG(rs.getString("CODPROG"));
                ses.setNOMPROG(rs.getString("NOMPROG"));
                ses.setESTPROG(rs.getString("ESTPROG"));
                ses.setCODDETPROG(rs.getString("CODDETPROG"));
                ses.setESTDETPROG(rs.getString("ESTDETPROG"));
                ses.setCODFASE(rs.getString("CODFASE"));
                ses.setESTFASE(rs.getString("ESTFASE"));
                ses.setNOMFASE(rs.getString("NOMFASE"));
                ses.setCODSES(rs.getString("CODSES"));
                ses.setNOMSES(rs.getString("NOMSES"));
                lista.add(ses);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<EvalImpParcM> lstProfes(String DniPer) throws Exception {
        List<EvalImpParcM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PROF_IMP_PARC WHERE DNIPER = ? AND ESTFASE LIKE 'I' AND ESTPROG LIKE 'A' AND TIPSES = 'S' ORDER BY NOMSES";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, DniPer);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EvalImpParcM profs;
            while (rs.next()) {
                profs = new EvalImpParcM();
                profs.setPROFESOR(rs.getString("PROFESOR"));
                profs.setCODPROG_PROF(rs.getString("CODPROG"));
                profs.setNOMPROG_PROF(rs.getString("NOMPROG"));
                profs.setESTFASE_PROF(rs.getString("ESTPROG"));
                profs.setCODFASE_PROF(rs.getString("CODFASE"));
                profs.setNOMFASE_PROF(rs.getString("NOMFASE"));
                profs.setESTFASE_PROF(rs.getString("ESTFASE"));
                profs.setCODPROF(rs.getString("CODPROF"));
                profs.setALUMNO_PROF(rs.getString("ALUMNO"));
                profs.setDNIPER_PROF(rs.getString("DNIPER"));
                profs.setDECSES(rs.getString("DECSES"));
                profs.setCODSES(rs.getString("CODSES"));
                lista.add(profs);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<EvalImpParcM> lstDirectores(String DniPer) throws Exception {
        List<EvalImpParcM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT CODPER, DIRECTOR FROM VW_DIREC_IMP_PARC WHERE DNIPER = ? AND ESTFASE = 'I' AND ESTDETPROG = 'A'";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, DniPer);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EvalImpParcM direc;
            while (rs.next()) {
                direc = new EvalImpParcM();
                direc.setCODDIRECTOR(rs.getString("CODPER"));
                direc.setDIRECTOR(rs.getString("DIRECTOR"));
                lista.add(direc);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void addEvalImpacParc(EvalImpParcM imParc, String DniPer, String CodFase) throws Exception {
        this.Conexion();
        try {
            if (imParc.getRPTA07_1() == null) {
                imParc.setRPTA07_1("-");
            }
            String sql = "INSERT INTO F_IMP_PARC (RPTA01,RPTA02,RPTA03,RPTA07,RPTA07_1,RPTA08,RPTA06_1,RPTA06_2,RPTA06_3,RPTA06_4,RPTA06_5,RPTA04_1, DNIPER, CODFASE,FECEVAIMP) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, imParc.getRPTA01());
            ps.setString(2, imParc.getRPTA02());
            ps.setString(3, imParc.getRPTA03());
            ps.setString(4, imParc.getRPTA07());
            ps.setString(5, imParc.getRPTA07_1());
            ps.setString(6, imParc.getRPTA08());
            ps.setString(7, imParc.getRPTA06_1());
            ps.setString(8, imParc.getRPTA06_2());
            ps.setString(9, imParc.getRPTA06_3());
            ps.setString(10, imParc.getRPTA06_4());
            ps.setString(11, imParc.getRPTA06_5());
            ps.setString(12, imParc.getRPTA04_1());
            ps.setString(13, DniPer);
            ps.setString(14, CodFase);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void addEvalImpacParcProf(List<EvalImpParcM> lstProfs, String CODPREG) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO F_IMP_PARC_PROF (RPTA, CODPER, CODIMPPARC, PREGUNTA, CODFASE, DNIPER, CODSES) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps;
            for (EvalImpParcM lstProf : lstProfs) {
                ps = this.getCn().prepareStatement(sql);
                ps.setString(1, lstProf.getRPTA04());
                ps.setString(2, lstProf.getCODPROF());
                ps.setString(3, CODPREG);
                ps.setString(4, "P04");
                ps.setString(5, lstProf.getCODFASE_PROF());
                ps.setString(6, lstProf.getDNIPER_PROF());
                ps.setString(7, lstProf.getCODSES());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void addEvalImpacParcSes(List<EvalImpParcM> lstSes, String CODPREG) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO F_IMP_PARC_SES (RPTA, CODSES, CODIMPPARC, PREGUNTA,CODFASE, DNIPER) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps;
            for (EvalImpParcM lstSession : lstSes) {
                ps = this.getCn().prepareStatement(sql);
                ps.setString(1, lstSession.getRPTA05());
                ps.setString(2, lstSession.getCODSES());
                ps.setString(3, CODPREG);
                ps.setString(4, "P5");
                ps.setString(5, lstSession.getCODFASE());
                ps.setString(6, lstSession.getDNIPER());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public String leerImpParcCreado(String RPTA03, String RPTA07_1, String RPTA08) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODIMPPARC FROM F_IMP_PARC WHERE RPTA03 LIKE ? AND RPTA07_1 LIKE ? AND RPTA08 LIKE ? ORDER BY CODIMPPARC";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, RPTA03);
            ps.setString(2, RPTA07_1);
            ps.setString(3, RPTA08);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODIMPPARC");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public boolean validarImpcParc(String DniPer, String CodFase) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT COUNT(*) AS CANTIDAD FROM F_IMP_PARC WHERE DNIPER = ? AND CODFASE = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, DniPer);
            ps.setString(2, CodFase);
            rs = ps.executeQuery();
            int numero = 0;
            if (rs.next()) {
                numero = rs.getInt("CANTIDAD");
            }
            return numero == 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<EvalImpParcM> estadoImpacParc(String CODDETPROG) throws Exception {
        List<EvalImpParcM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM  VW_IMP_PARC_INFORME WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODDETPROG);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EvalImpParcM estEvaPar;
            while (rs.next()) {
                estEvaPar = new EvalImpParcM();
                estEvaPar.setCODDETPROG_EST(rs.getString("CODDETPROG"));
                estEvaPar.setCODFASE_EST(rs.getString("CODFASE"));
                estEvaPar.setNOMFASE_EST(rs.getString("NOMFASE"));
                estEvaPar.setPERSONA_EST(rs.getString("PERSONA"));
                estEvaPar.setDNIPER_EST(rs.getString("DNIPER"));
                estEvaPar.setESTADO_EST(rs.getString("ESTADO"));
                estEvaPar.setFECEVAIMP(rs.getString("FECHA"));
                lista.add(estEvaPar);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
