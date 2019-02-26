package com.came.dao;

import com.came.interfaces.FaseI;
import com.came.model.FaseM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplFaseD extends Dao implements FaseI {

    @Override
    public void agregarFase(FaseM fase) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO FASE (CODDETPROG,NOMFASE,ESTFASE) VALUES (?,?,'A')";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, fase.getCODDETPROG());
            ps.setString(2, fase.getNOMFASE());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void agregarConfigPesos(FaseM fase) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO CONFIG_PESOS (CODFASE) VALUES (?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, fase.getCODFASE());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public String leerSesionCreada(String NomFase, String CodDetProg) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "select CodFase from fase where nomfase like ? and CODDETPROG like ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, NomFase);
            ps.setString(2, CodDetProg);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODFASE");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<FaseM> listarFase(String CODDETPROG) throws Exception {
        List<FaseM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM FASE WHERE CODDETPROG LIKE ? ORDER BY CODFASE ASC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODDETPROG);
            rs = ps.executeQuery();
            lista = new ArrayList();
            FaseM fM;
            while (rs.next()) {
                fM = new FaseM();
                fM.setCODFASE(rs.getString("CODFASE"));
                fM.setNOMFASE(rs.getString("NOMFASE"));
                lista.add(fM);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void cerrarModulo(String codMod) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE FASE SET ESTFASE = 'I' WHERE CODFASE = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codMod);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    public void cerrarEncuesta(String codMod) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE FASE SET ESTFASE = 'E' WHERE CODFASE = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codMod);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminarFase(String Codfase) throws Exception {
        this.Conexion();
        try {
            String sql = "DELETE FROM FASE WHERE CODFASE = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1,Codfase);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void activarFase(String Codfase) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE FASE SET ESTFASE = 'A' WHERE CODFASE = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1,Codfase);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
