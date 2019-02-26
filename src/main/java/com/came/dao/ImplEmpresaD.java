package com.came.dao;

import com.came.interfaces.EmpresaI;
import com.came.model.EmpresaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplEmpresaD extends Dao implements EmpresaI {

    @Override
    public void guardar(EmpresaM emp) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO EMPRESA(RUCEMP,NOMBEMP,CODUBI,DIREMP,ACTEMP) VALUES (?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, emp.getRUCEMP());
            ps.setString(2, emp.getNOMBEMP());
            ps.setString(3, emp.getCODUBI());
            ps.setString(4, emp.getDIREMP());
            ps.setString(5, emp.getACTEMP());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<String> queryAutoCompleteUbi(String a) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<String> lista;
        String sql;
        try {

            sql = "SELECT CODUBI,CONCAT(CONCAT(CONCAT(CONCAT(DEPUBI,','),PROVUBI),','),DISTUBI) AS NOMBRE FROM UBIGEO WHERE DISTUBI LIKE UPPER(?)";

            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + a + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString("NOMBRE"));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public String leerUbi(String a) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODUBI FROM UBIGEO WHERE CONCAT(CONCAT(CONCAT(CONCAT(DEPUBI,','),PROVUBI),','),DISTUBI) = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODUBI");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public String traeCodigo(String a) throws Exception {
        this.Conexion();
        try {
            ResultSet rs;
            String sql = "SELECT CODEMP FROM EMPRESA WHERE RUCEMP LIKE ?";
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

    public List<EmpresaM> listarEmpresa() throws Exception {
        List<EmpresaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_EMPRESA_MANTENIMIENTO ORDER BY CODEMP DESC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EmpresaM emp;
            while (rs.next()) {
                emp = new EmpresaM();
                emp.setCODEMP(rs.getString("CODEMP"));
                emp.setNOMBEMP(rs.getString("NOMBEMP"));
                emp.setRUCEMP(rs.getString("RUCEMP"));
                emp.setACTEMP(rs.getString("ACTEMP"));
                emp.setDIREMP(rs.getString("DIREMP"));
                emp.setUBIGEO(rs.getString("UBIGEO"));
                lista.add(emp);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void updateEmpresa(EmpresaM emp) throws SQLException {
        this.Conexion();
        try {
            String sql = "UPDATE EMPRESA SET RUCEMP = ? , NOMBEMP = ? , CODUBI = ?, DIREMP = ?, ACTEMP = ? WHERE CODEMP = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, emp.getRUCEMP());
            ps.setString(2, emp.getNOMBEMP());
            ps.setString(3, emp.getCODUBI());
            ps.setString(4, emp.getDIREMP());
            ps.setString(5, emp.getACTEMP());
            ps.setString(6, emp.getCODEMP());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void deleteEmpresa(EmpresaM empresa) throws Exception {
        this.Conexion();
        try {
            String sql = "DELETE FROM EMPRESA WHERE CODEMP = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, empresa.getCODEMP());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<String> queryAutoCompleteEmpresa(String a) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<String> lista;
        String sql;
        try {

            sql = "SELECT CODEMP, NOMBEMP AS EMPRESA FROM EMPRESA WHERE NOMBEMP LIKE UPPER(?)";

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

    @Override
    public String leerEmpresa(String a) throws Exception {
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

    @Override
    public String buscarRucEmpresa(String rucEmp) throws Exception {
        this.Conexion();
        try {
            ResultSet rs;
            String sql = "SELECT RUCEMP FROM EMPRESA WHERE RUCEMP LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, rucEmp);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("RUCEMP");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
