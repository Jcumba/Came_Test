package com.came.dao;

import com.came.interfaces.SesionI;
import com.came.model.BibliotecaM;
import com.came.model.CompetenciaM;
import com.came.model.FaseM;
import com.came.model.PesoM;
import com.came.model.SesionM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplSesionD extends Dao implements SesionI {

    //    DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
    @Override
    public void modificarConfiguracion(SesionM sesion) throws Exception {
        try {
            this.Conexion();
            String sql = "UPDATE SESION SET PARTICIPACION = ?,CASO = ?,CASO2 = ?,CONTROL = ?,EXAMPARC = ?,EXAMFINAL = ?,TRABAJO = ?,TIPSES=? WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, sesion.isPARTICIPACION() == true ? "1" : "0");
            ps.setString(2, sesion.isCASO() == true ? "1" : "0");
            ps.setString(3, sesion.isCASO2() == true ? "1" : "0");
            ps.setString(4, sesion.isCONTROL() == true ? "1" : "0");
            ps.setString(5, sesion.isEXAMPARC() == true ? "1" : "0");
            ps.setString(6, sesion.isEXAMFINAL() == true ? "1" : "0");
            ps.setString(7, sesion.isTRABAJO() == true ? "1" : "0");
            ps.setString(8, sesion.getTIPSES());
            ps.setString(9, sesion.getCODSES());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<SesionM> filSesion(String CODDETPROG) throws Exception {
        List<SesionM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "select * from vw_sesion where CODDETPROG LIKE ? ORDER BY TO_NUMBER(NOMSES) DESC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODDETPROG);
            rs = ps.executeQuery();
            lista = new ArrayList();
            SesionM sm;
            PesoM pes;
            FaseM fs;
            while (rs.next()) {
                sm = new SesionM();
                pes = new PesoM();
                fs = new FaseM();
                sm.setCODPROF(rs.getString("CODPROF"));
                sm.setCODSES(rs.getString("CODSES"));
                sm.setDECSES(rs.getString("DECSES"));
                sm.setNOMSES(rs.getString("NOMSES"));
                sm.setFECHASES(rs.getString("FECHASES"));
                sm.setPARTICIPACION("1".equals(rs.getString("PARTICIPACION")));
                sm.setCASO("1".equals(rs.getString("CASO")));
                sm.setCASO2("1".equals(rs.getString("CASO2")));
                sm.setCONTROL("1".equals(rs.getString("CONTROL")));
                sm.setEXAMPARC("1".equals(rs.getString("EXAMPARC")));
                sm.setEXAMFINAL("1".equals(rs.getString("EXAMFINAL")));
                sm.setTRABAJO("1".equals(rs.getString("TRABAJO")));
                sm.setCANTDOC(rs.getString("CANTDOC"));
                fs.setNOMFASE(rs.getString("NOMFASE"));
                fs.setCODFASE(rs.getString("CODFASE"));
                fs.setESTFASE(rs.getString("ESTFASE"));
                pes.setPESOPART(rs.getString("PESOPART"));
                pes.setPESOTRA(rs.getString("PESOTRA"));
                pes.setPESOPARC(rs.getString("PESOPARC"));
                pes.setPESOFINAL(rs.getString("PESOFINAL"));
                pes.setPESOCASO(rs.getString("PESOCASO"));
                sm.setFase(fs);
                sm.setPesos(pes);
                lista.add(sm);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void agregarSesion(SesionM sesion) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO SESION (NOMSES,DECSES,CODFASE,CODPROF,FECHASES,HORA_INI,HORA_FIN,MODOSES,CODCOM) VALUES (?,?,?,?,TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'HH24-MI'),TO_DATE(?,'HH24-MI'),?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, sesion.getNOMSES());
            ps.setString(2, sesion.getDECSES());
            ps.setString(3, sesion.getFase().getCODFASE());
            ps.setString(4, sesion.getCODPROF());
            ps.setString(5, sesion.getFECHASES());
            ps.setString(6, sesion.getHORA_INI());
            ps.setString(7, sesion.getHORA_FIN());
            ps.setString(8, sesion.getMODOSES());
            ps.setString(9, sesion.getCODCOM());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<SesionM> listarProfesores() throws Exception {
        List<SesionM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT CODPER,ESTPER,CONCAT(CONCAT(NOMPER, ' '), APEPER) AS PROFESORES FROM PERSONA WHERE TIPOPER LIKE '%PR%' AND ESTPER LIKE 'A'";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList();
            SesionM prof;
            while (rs.next()) {
                prof = new SesionM();
                prof.setCODPROF(rs.getString("CODPER"));
                prof.setPROFESORES(rs.getString("PROFESORES"));
                lista.add(prof);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<FaseM> listarFases(String CODGEN) throws Exception {
        List<FaseM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_CBFASE WHERE ESTFASE LIKE 'A' AND CODDETPROG LIKE ? ORDER BY CODFASE DESC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODGEN);
            rs = ps.executeQuery();
            lista = new ArrayList();
            FaseM fase;
            while (rs.next()) {
                fase = new FaseM();
                fase.setCODFASE(rs.getString("CODFASE"));
                fase.setNOMFASE(rs.getString("NOMFASE"));
                lista.add(fase);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<SesionM> listar(String NOMB, String FECH, String CodDetProg) throws Exception {
        List<SesionM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "select NOMSES,nomfase,decses,FECHA,NOMB,FECDOC,TIPCON,TAM,UBIC,PRIV from VW_DOC_PART WHERE CODDETPROG LIKE ? AND DECSES LIKE UPPER(?) AND FECHA LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CodDetProg);
            ps.setString(2, "%" + NOMB + "%");
            ps.setString(3, "%" + FECH + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            SesionM sesion;
            while (rs.next()) {
                sesion = new SesionM();
                sesion.setNOMSES(rs.getString("NOMSES"));
                sesion.setDECSES(rs.getString("decses"));
                sesion.getFase().setNOMFASE(rs.getString("nomfase"));
                sesion.setFECHASES(rs.getString("FECHA"));
                sesion.setBiblioteca(new BibliotecaM(
                        rs.getString("NOMB"),
                        rs.getString("FECDOC"),
                        rs.getString("TIPCON"),
                        rs.getString("TAM"),
                        rs.getString("UBIC"),
                        rs.getString("PRIV")
                ));
                lista.add(sesion);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void agregarsesionanexo(SesionM sesion) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO SESION_ANEXO (CODSES,CODBIB,FECSESANE) VALUES (?,?,sysdate)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, sesion.getCODSES());
            ps.setString(2, sesion.getBiblioteca().getCod());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<CompetenciaM> competencia() throws Exception {
        List<CompetenciaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_CBCOMPETENCIA ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList();
            CompetenciaM comp;
            while (rs.next()) {
                comp = new CompetenciaM();
                comp.setCODCOM(rs.getString("CODCOM"));
                comp.setNOMCOM(rs.getString("COMABRE"));
                comp.setABRARE(rs.getString("ABRARE"));
                lista.add(comp);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void agregarActividades(SesionM sesion) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO SESION_ACT(HORAINI,HORAFIN,CODSES,DESCACT) VALUES(?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, sesion.getIniAct());
            ps.setString(2, sesion.getFinAct());
            ps.setString(3, sesion.getCODSES());
            ps.setString(4, sesion.getDescAct());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminarActividad(String codSesAct) throws Exception {
        this.Conexion();
        try {
            String sql = "DELETE FROM SESION_ACT WHERE CODSESACT = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSesAct);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<SesionM> listarActividades(String codSes) throws Exception {
        List<SesionM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT CODSESACT,HORAINI,HORAFIN,DESCACT FROM SESION_ACT WHERE CODSES = ? ORDER BY HORAINI";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList();
            SesionM act;
            while (rs.next()) {
                act = new SesionM();
                act.setCodSesAct(rs.getString("CODSESACT"));
                act.setIniAct(rs.getString("HORAINI"));
                act.setFinAct(rs.getString("HORAFIN"));
                act.setDescAct(rs.getString("DESCACT"));
                lista.add(act);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminarSesion(String CODSES) throws Exception {
        this.Conexion();
        try {
            String sql = "DELETE FROM SESION WHERE CODSES LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODSES);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public boolean validadCantidadActividades(String CodDetPrograma) throws Exception {
        this.Conexion();
        int cantidad = 0;
        ResultSet rs;
        try {
            String sql = "SELECT count(*) as total\n"
                    + "FROM programa\n"
                    + "inner join prog_det on programa.codprog = prog_det.codprog\n"
                    + "inner join fase on prog_det.coddetprog = fase.coddetprog\n"
                    + "inner join sesion on sesion.codfase = fase.codfase\n"
                    + "WHERE Prog_Det.CODDETPROG like ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CodDetPrograma);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("total");
            }
            return cantidad != 0;
        } catch (Exception e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void actualisarSesion(SesionM sesion) throws Exception {
        try {
            this.Conexion();
            String sql = "UPDATE SESION SET CODPROF = ?,NOMSES = ?,FECHASES = TO_DATE(?,'DD/MM/YYYY')  WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, sesion.getCODPROF());
            ps.setString(2, sesion.getNOMSES());
            ps.setString(3, sesion.getFECHASES());
            ps.setString(4, sesion.getCODSES());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
