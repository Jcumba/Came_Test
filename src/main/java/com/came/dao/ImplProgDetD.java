package com.came.dao;

import com.came.interfaces.ProgDetI;
import com.came.model.ProgDetM;
import com.came.services.SessionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplProgDetD extends Dao implements ProgDetI {

    @Override
    public void agregarProDet(ProgDetM prodet) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO PROG_DET (FECHINI,FECHFIN,GENDETPROG,CODPER,CODPROG,FRECDETPROG,HORINIFIN) VALUES(TO_DATE(?,'dd/mm/yyyy'),TO_DATE(?,'dd/mm/yyyy'),?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, prodet.getFechIni());
            ps.setString(2, prodet.getFechFin());
            ps.setString(3, prodet.getGenDetProg());
            ps.setString(4, prodet.getCodPer());
            ps.setString(5, prodet.getCodProg());
            ps.setString(6, SessionService.arrayToString(prodet.getFrecDetProg()));
            ps.setString(7, prodet.getHorIniFin());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<ProgDetM> listarProDet() throws Exception {
        List<ProgDetM> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_LISTDETPROG ORDER BY CODDETPROG DESC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            ProgDetM prodet;
            while (rs.next()) {
                prodet = new ProgDetM();
                prodet.setCodDetProg(rs.getString("CODDETPROG"));
                prodet.setNomProg(rs.getString("NOMPROG"));
                prodet.setNomPer(rs.getString("DIRECTOR"));
                prodet.setFechIni(rs.getString("FECHINI"));
                prodet.setFechFin(rs.getString("FECHFIN"));
                prodet.setGenDetProg(rs.getString("GENDETPROG"));
                prodet.setFrecDetProg(SessionService.stringToArray(rs.getString("FRECDETPROG")));
                prodet.setHorIniFin(rs.getString("HORINIFIN"));
                prodet.setEstDetProg(rs.getString("ESTDETPROG"));
                list.add(prodet);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return list;
    }

    @Override
    public ArrayList<ProgDetM> listarCbGeneracion() throws Exception {
        try {
            ArrayList<ProgDetM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT * FROM VW_CBGEN_PROG WHERE  ESTDETPROG = 'A' ORDER BY CODDETPROG DESC";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgDetM prog = new ProgDetM();
                prog.setNomProg(rs.getString("PROGRAMA"));
                prog.setCodDetProg(rs.getString("CODDETPROG"));
                prog.setGenDetProg(rs.getString("GENDETPROG"));
                lst.add(prog);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public ArrayList<ProgDetM> listarCbGeneracion(String CodDirectorPrograma) throws Exception {
        try {
            ArrayList<ProgDetM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT * FROM VW_CBGEN_PROG WHERE ESTDETPROG = 'A' AND codper = ? ORDER BY CODDETPROG DESC";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            ps.setString(1, CodDirectorPrograma);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgDetM prog = new ProgDetM();
                prog.setNomProg(rs.getString("PROGRAMA"));
                prog.setCodDetProg(rs.getString("CODDETPROG"));
                prog.setGenDetProg(rs.getString("GENDETPROG"));
                lst.add(prog);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public ArrayList<ProgDetM> listarCbGeneracionPostulante() throws Exception {
        try {
            ArrayList<ProgDetM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT * FROM VW_CBGEN_PROG WHERE ESTDETPROG = 'A' OR ESTDETPROG = 'E' ORDER BY CODDETPROG DESC";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgDetM prog = new ProgDetM();
                prog.setNomProg(rs.getString("PROGRAMA"));
                prog.setCodDetProg(rs.getString("CODDETPROG"));
                prog.setGenDetProg(rs.getString("GENDETPROG"));
                prog.setEstDetProg(rs.getString("ESTDETPROG"));
                lst.add(prog);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public boolean validarProgramaPostulante(String CodProgDet) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String Sql = "SELECT COUNT(*) AS CANTIDAD FROM PROG_DET WHERE CODDETPROG LIKE ? AND ESTDETPROG LIKE 'A'";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            ps.setString(1, CodProgDet);
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

    @Override
    public ArrayList<ProgDetM> listarCbProgenUser(String codPer) throws Exception {
        try {
            ArrayList<ProgDetM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT CODDETPROG,GENDETPROG,PROGRAMA FROM VW_CBGEN_PROG_USER WHERE CODPER = ? ORDER BY CODDETPROG DESC";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            ps.setString(1, codPer);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgDetM prog = new ProgDetM();
                prog.setNomProg(rs.getString("PROGRAMA"));
                prog.setCodDetProg(rs.getString("CODDETPROG"));
                prog.setGenDetProg(rs.getString("GENDETPROG"));
                lst.add(prog);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public ArrayList<ProgDetM> listarCbSesiones(String detProg) throws Exception {
        try {
            ArrayList<ProgDetM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT CODSES,NOMSES FROM SESION INNER JOIN FASE ON SESION.CODFASE = FASE.CODFASE WHERE FASE.CODDETPROG = ? ORDER BY TO_NUMBER(NOMSES) DESC";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            ps.setString(1, detProg);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgDetM prog = new ProgDetM();
                prog.setCodSes(rs.getString("CODSES"));
                prog.setNomSes(rs.getString("NOMSES"));
                lst.add(prog);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void deleteProgDet(ProgDetM progDetM) throws Exception {
        this.Conexion();
        try {
            String sql = "DELETE FROM PROG_DET WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, progDetM.getCodDetProg());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void updateProgDet(ProgDetM progDet) throws SQLException {
        this.Conexion();
        try {
            String sql = "UPDATE PROG_DET SET FECHINI = TO_DATE(?,'DD/MM/YYYY') , FECHFIN = TO_DATE(?,'DD/MM/YYYY') , GENDETPROG = ? , CODPER = ?, FRECDETPROG = ?, HORINIFIN = ? WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, progDet.getFechIni());
            ps.setString(2, progDet.getFechFin());
            ps.setString(3, progDet.getGenDetProg());
            ps.setString(4, progDet.getCodPer());
            ps.setString(5, SessionService.arrayToString(progDet.getFrecDetProg()));
            ps.setString(6, progDet.getHorIniFin());
            ps.setString(7, progDet.getCodDetProg());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void iniciarProgDet(String codDetProg) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PROG_DET SET ESTDETPROG = 'A' WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codDetProg);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
