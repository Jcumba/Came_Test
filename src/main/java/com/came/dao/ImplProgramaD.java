package com.came.dao;

import com.came.interfaces.ProgramaI;
import com.came.model.ProgramaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplProgramaD extends Dao implements ProgramaI {

    public void init() throws Exception {
        listarCbTipoProg();
    }

    @Override
    public void agregarPrograma(ProgramaM programa) throws Exception {
        this.Conexion();
        try {
            if (programa.getCodEmp() == null) {
                programa.setCodEmp("1"); //Producción
//                programa.setCodEmp("1361"); //Testing
            }
            String sql = "INSERT INTO Programa(NomProg, EstProg, CodTipPg, iniProg, CodEmp) VALUES(UPPER(?),?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, programa.getNomProg());
            ps.setString(2, "A");
            ps.setString(3, programa.getCodTipPg());
            ps.setString(4, programa.getIniProg());
            ps.setString(5, programa.getCodEmp());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<ProgramaM> listarProgramas() throws Exception {
        List<ProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PROGRAMA ORDER BY CODPROG DESC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            ProgramaM prog;
            while (rs.next()) {
                prog = new ProgramaM();
                prog.setCodProg(rs.getString("CODPROG"));
                prog.setNomProg(rs.getString("NOMPROG"));
                prog.setEstProg(rs.getString("ESTPROG"));
                prog.setCodTipPg(rs.getString("CODTIPPG"));
                prog.setNomTipPg(rs.getString("NOMTIPOPG"));
                prog.setIniProg(rs.getString("INIPROG"));
                prog.setCodEmp(rs.getString("CODEMP"));
                prog.setNombEmp(rs.getString("NOMBEMP"));
                lista.add(prog);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public ArrayList<ProgramaM> listarCbProgramas() throws Exception {
        try {
            ArrayList<ProgramaM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT CODPROG,NOMPROG FROM PROGRAMA";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgramaM prog = new ProgramaM();
                prog.setCodProg(rs.getString("CODPROG"));
                prog.setNomProg(rs.getString("NOMPROG"));
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
    public ArrayList<ProgramaM> listarCbTipoProg() throws Exception {
        try {
            ArrayList<ProgramaM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT * FROM TipoProg ORDER BY NOMTIPOPG ASC";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgramaM tpg = new ProgramaM();
                tpg.setCodTipPg(rs.getString("CodTipPg"));
                tpg.setNomTipPg(rs.getString("NomTipoPg"));
                lst.add(tpg);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void agregarTipoPg(ProgramaM programa) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO TipoProg(NOMTIPOPG, ESTTIPOPG) VALUES(?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, programa.getNomTipPg());
            ps.setString(2, "A");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<ProgramaM> listarTipoPg() throws Exception {
        List<ProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM TipoProg where ESTTIPOPG = 'A' order by CODTIPPG desc";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            ProgramaM prog;
            while (rs.next()) {
                prog = new ProgramaM();
                prog.setCodTipPg(rs.getString("CODTIPPG"));
                prog.setNomTipPg(rs.getString("NOMTIPOPG"));
                prog.setEstTipPg(rs.getString("ESTTIPOPG"));
                lista.add(prog);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<ProgramaM> listarProgramasyGen() throws Exception {
        try {
            ArrayList<ProgramaM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT * FROM VW_ASIGNACION";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgramaM prog = new ProgramaM();
                prog.setCodProg(rs.getString("CODDETPROG"));
                prog.setNomProg(rs.getString("PROGRAMA"));
                lst.add(prog);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void cerrarPrograma(String prog) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PROG_DET SET ESTDETPROG = 'I' WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, prog);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public String traerCodDetProg(String codProg, String GenProg) throws SQLException {
        try {
            this.Conexion();
            ResultSet rs;
            String sql = "SELECT CODDETPROG FROM PROG_DET WHERE CODPROG = ? AND GENDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codProg);
            ps.setString(2, GenProg);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODDETPROG");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public ArrayList<ProgramaM> listarCbAreas() throws Exception {
        try {
            ArrayList<ProgramaM> lst = new ArrayList<>();
            ResultSet rs;
            this.Conexion();
            String Sql = "SELECT CODARE,NOMARE,ABRARE FROM AREA";
            PreparedStatement ps = this.getCn().prepareStatement(Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgramaM prog = new ProgramaM();
                prog.setCodArea(rs.getString("CODARE"));
                prog.setNomArea(rs.getString("NOMARE"));
                prog.setAbreArea(rs.getString("ABRARE"));
                lst.add(prog);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void updatePrograma(ProgramaM prog) throws SQLException {
        this.Conexion();
        try {
            if (prog.getCodEmp() == null) {
                prog.setCodEmp("1"); //Producción
//                prog.setCodEmp("136"); //Testing
            }
            String sql = "UPDATE PROGRAMA SET NOMPROG = ? , CODTIPPG = ? , INIPROG = ?, CODEMP = ? WHERE CODPROG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, prog.getNomProg());
            ps.setString(2, prog.getCodTipPg());
            ps.setString(3, prog.getIniProg());
            ps.setString(4, prog.getCodEmp());
            ps.setString(5, prog.getCodProg());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void updateTipoPrograma(ProgramaM prog) throws SQLException {
        this.Conexion();
        try {
            String sql = "UPDATE TIPOPROG SET NOMTIPOPG = ? WHERE CODTIPPG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, prog.getNomTipPg());
            ps.setString(2, prog.getCodTipPg());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void deletePrograma(ProgramaM programa) throws Exception {
        this.Conexion();
        try {
            String sql = "DELETE FROM PROGRAMA WHERE CODPROG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, programa.getCodProg());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void deleteTipoProg(ProgramaM programa) throws Exception {
        this.Conexion();
        try {
            String sql = "DELETE FROM TIPOPROG WHERE CODTIPPG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, programa.getCodTipPg());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<String> queryAutoCompleteEmpre(String a) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<String> lista;
        String sql;
        try {
            sql = "SELECT CODEMP, NOMBEMP AS EMPRESA FROM EMPRESA WHERE UPPER(NOMBEMP) LIKE UPPER(?)";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + a + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString("EMPRESA"));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public String leerEmpre(String a) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODEMP FROM EMPRESA WHERE NOMBEMP = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODEMP");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
