package com.came.dao;

import com.came.interfaces.InscripcionI;
import com.came.model.InscripcionM;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImplInscripcionD extends Dao implements InscripcionI {

    @Override
    public void guardar(InscripcionM ins) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO ASIGNACION (CODPER,CODDETPROG,FECREG,ESTREG) VALUES (?,?,to_date(sysdate,'dd/MM/yyyy'),'E')";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, ins.getCODPER());
            ps.setString(2, ins.getCODPROG());
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
