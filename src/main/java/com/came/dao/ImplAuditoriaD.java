package com.came.dao;

import com.came.model.AuditoriaM;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImplAuditoriaD extends Dao {

    //INSERTAR AUDITORIA
    public void insertAuditoria(AuditoriaM Audi) throws SQLException {
        try {
            this.Conexion();
            String sql = "insert into Auditoria (USUARIO,EVENTO,VALOR,CODDETPRO,CODBIB)Values (?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, Audi.getUSUARIO());
            ps.setString(2, Audi.getEVENTO());
            ps.setString(3, Audi.getVALOR());
            ps.setString(4, Audi.getCODDETPRO());
            ps.setString(5, Audi.getCODBIB());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //ACTUALIZAR AUDITORIA
    public void updateAuditoria(AuditoriaM Audi) throws SQLException {
        try {
            String sql = "update Auditoria set USUARIO=?,EVENTO=?,VALOR=?,CODDETPRO=?,CODBIB=? where CODAUD=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, Audi.getUSUARIO());
            ps.setString(2, Audi.getEVENTO());
            ps.setString(3, Audi.getVALOR());
            ps.setString(4, Audi.getCODDETPRO());
            ps.setString(5, Audi.getCODBIB());
            ps.setString(6, Audi.getCODAUD());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
