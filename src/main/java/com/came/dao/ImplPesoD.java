package com.came.dao;

import com.came.interfaces.PesoI;
import com.came.model.FaseM;
import com.came.model.PesoM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplPesoD extends Dao implements PesoI {

    @Override
    public void editar(PesoM pM) throws Exception {
        try {
            this.Conexion();
            String sql = "UPDATE CONFIG_PESOS SET PESOPART = ?,PESOTRA = ?,PESOPARC = ?,PESOFINAL = ?,PESOCASO = ? WHERE CODCONFPES= ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, pM.getPESOPART());
            ps.setString(2, pM.getPESOTRA());
            ps.setString(3, pM.getPESOPARC());
            ps.setString(4, pM.getPESOFINAL());
            ps.setString(5, pM.getPESOCASO());
            ps.setString(6, pM.getCODCONFPES());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<PesoM> listarFase(String codprog) throws Exception {
        List<PesoM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PESOS_FASE WHERE CODDETPROG LIKE ? ORDER BY CODFASE ASC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codprog);
            rs = ps.executeQuery();
            lista = new ArrayList();
            PesoM pM;
            FaseM fs;
            while (rs.next()) {
                pM = new PesoM();
                fs = new FaseM();
                fs.setCODDETPROG(rs.getString("CODDETPROG"));
                fs.setCODFASE(rs.getString("CODFASE"));
                fs.setNOMFASE(rs.getString("NOMFASE"));
                fs.setESTFASE(rs.getString("ESTFASE"));
                pM.setCODCONFPES(rs.getString("CODCONFPES"));
                pM.setPESOPART(rs.getString("PESOPART"));
                pM.setPESOTRA(rs.getString("PESOTRA"));
                pM.setPESOPARC(rs.getString("PESOPARC"));
                pM.setPESOFINAL(rs.getString("PESOFINAL"));
                pM.setPESOCASO(rs.getString("PESOCASO"));
                pM.setFase(fs);
                lista.add(pM);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminarPeso(String codPeso) throws Exception {
        this.Conexion();
        try {
            String sql = "DELETE FROM CONFIG_PESOS WHERE CODCONFPES = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codPeso);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
