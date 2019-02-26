package com.came.dao;

import com.came.model.AsigCoachM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplAsigCoachD extends Dao {

    public List<AsigCoachM> listarAsigCoach() throws Exception {
        ResultSet rs;
        List<AsigCoachM> lista;
        this.Conexion();
        try {
            String sql = "select Codasicoa,Codcoach,Coddetprog,Codper from AsigCoach";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            AsigCoachM AsiM;
            while (rs.next()) {
                AsiM = new AsigCoachM();
                AsiM.setCODASICOA(rs.getString("CODASICOA"));
                AsiM.setCODCOACH(rs.getString("CODCOACH"));
                AsiM.setCODPER(rs.getString("CODPER"));
                AsiM.setCODDETPROG(rs.getString("CODDETPROG"));
                lista.add(AsiM);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void insertAsigCoach(AsigCoachM am) throws Exception {
        try {
            this.Conexion();
            String sql = "insert into Asigcoach (CODCOACH,CODPER,CODDETPROG) values (?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, am.getCODCOACH());
            ps.setString(2, am.getCODPER());
            ps.setString(3, am.getCODDETPROG());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void updateAsigCoach(AsigCoachM am) throws Exception {
        try {
            this.Conexion();
            String sql = "UPDATE Asigcoach SET Codcoach=?, Codper=?, Coddetprog=? Where Codasicoa=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, am.getCODCOACH());
            ps.setString(2, am.getCODPER());
            ps.setString(3, am.getCODDETPROG());
            ps.setString(4, am.getCODASICOA());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
