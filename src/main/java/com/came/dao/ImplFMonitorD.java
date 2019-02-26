package com.came.dao;

import com.came.interfaces.FMonitorI;
import com.came.model.FMonitorM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplFMonitorD extends Dao implements FMonitorI {

    @Override
    public List<FMonitorM> listarPersonasPorMonitorear(String codDetProg) throws Exception {
        List<FMonitorM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT CODPER,NOMPER,CODFMON,ASISTENCIA,ENTCASO,USSEC,PUNTUALIDAD,PARTICIPACION,OBSERVACION FROM VW_FMONITOR WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codDetProg);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FMonitorM fmon;
            while (rs.next()) {
                fmon = new FMonitorM();
                fmon.setCodPer(rs.getString("CODPER"));
                fmon.setNomPer(rs.getString("NOMPER"));
                fmon.setCodFmon(rs.getString("CODFMON"));
                fmon.setAsistencia(rs.getString("ASISTENCIA"));
                fmon.setEntCaso(rs.getString("ENTCASO"));
                fmon.setUssec(rs.getString("USSEC"));
                fmon.setPuntualidad(rs.getString("PUNTUALIDAD"));
                fmon.setParticipacion(rs.getString("PARTICIPACION"));
                fmon.setObservacion(rs.getString("OBSERVACION"));
                lista.add(fmon);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

    @Override
    public void agregarMonitoreo(FMonitorM fmon, String codSes) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO FMONITOR (ASISTENCIA,ENTCASO,USSEC,PUNTUALIDAD,PARTICIPACION,OBSERVACION,CODPER,CODSES) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, fmon.getAsistencia());
            ps.setString(2, fmon.getEntCaso());
            ps.setString(3, fmon.getUssec());
            ps.setString(4, fmon.getPuntualidad());
            ps.setString(5, fmon.getParticipacion());
            ps.setString(6, fmon.getObservacion());
            ps.setString(7, fmon.getCodPer());
            ps.setString(8, codSes);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
