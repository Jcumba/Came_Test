package com.came.dao;

import com.came.interfaces.NotasI;
import com.came.model.NotasM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplNotasD extends Dao implements NotasI {

    @Override
    public List<NotasM> listarNuevasNotas(String detProg, String codSes) throws Exception {
        List<NotasM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT CODASIG,CODDETPROG,CODPER,NOMBRE,GENDETPROG FROM VW_NOTAS WHERE CODDETPROG = ? AND CODSES = ? AND TIPOPER='ES' AND ESTPER='A'";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, detProg);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            NotasM nota;
            while (rs.next()) {
                nota = new NotasM();
                nota.setCodAsig(rs.getString("CODASIG"));
                nota.setCodPer(rs.getString("CODPER"));
                nota.setNomPer(rs.getString("NOMBRE"));
                nota.setGeneracion(rs.getString("GENDETPROG"));
                lista.add(nota);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void traerConfSesion(NotasM nota, String codSes) throws Exception {
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_SESCAL WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            rs.next();
            nota.setTipoSes(rs.getString("TIPSES"));
            nota.setDivisor(rs.getInt("CONTADOR"));
            nota.setAsistencia(rs.getString("ASISTENCIA"));
            nota.setPlenaria(rs.getString("PARTICIPACION"));
            nota.setCaso(rs.getString("CASO"));
            nota.setCaso2(rs.getString("CASO2"));
            nota.setControl(rs.getString("CONTROL"));
            nota.setExamParc(rs.getString("EXAMPARC"));
            nota.setExamFinal(rs.getString("EXAMFINAL"));
            nota.setTrabajo(rs.getString("TRABAJO"));
            nota.setNomFase(rs.getString("NOMFASE"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void agregarNuevasNotas(NotasM nota, String codSes) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO SESION_DET(CODSES,ASISTENCIA,PARTICIPACION,CASO,CONTROL,EXAMPARC,EXAMFINAL,TRABAJO,CODASIG,PROMEDIOS,CASO2) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            ps.setString(2, nota.getNotaAsis());
            ps.setInt(3, nota.getNotaPlenaria());
            ps.setInt(4, nota.getNotaCaso());
            ps.setInt(5, nota.getNotaControl());
            ps.setInt(6, nota.getNotaExamParc());
            ps.setInt(7, nota.getNotaExamFinal());
            ps.setInt(8, nota.getNotaTrabajo());
            ps.setString(9, nota.getCodAsig());
            ps.setDouble(10, nota.getPromedio());
            ps.setDouble(11, nota.getNotaCaso2());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void editarNotas(NotasM nota) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE SESION_DET SET ASISTENCIA=?,PARTICIPACION=?,CASO=?,CASO2=?,CONTROL=?,EXAMPARC=?,EXAMFINAL=?,TRABAJO=? WHERE CODDETSES=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, nota.getNotaAsis());
            ps.setInt(2, nota.getNotaPlenaria());
            ps.setInt(3, nota.getNotaCaso());
            ps.setDouble(4, nota.getNotaCaso2());
            ps.setInt(5, nota.getNotaControl());
            ps.setInt(6, nota.getNotaExamParc());
            ps.setInt(7, nota.getNotaExamFinal());
            ps.setInt(8, nota.getNotaTrabajo());
            ps.setString(9, nota.getCodDetSes());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public boolean validacionRegistros(String codSes) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT COUNT(CODDETSES) AS CONTADOR FROM SESION_DET WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            rs.next();
            int valor = rs.getInt("CONTADOR");
            return valor == 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<NotasM> listarRegistroNotas(String codSes) throws Exception {
        List<NotasM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_NOTA_PROM WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            NotasM nota;
            while (rs.next()) {
                nota = new NotasM();
                nota.setCodDetSes(rs.getString("CODDETSES"));
                nota.setNomPer(rs.getString("NOMBRE"));
                nota.setNotaAsis(rs.getString("ASISTENCIA"));
                nota.setNotaPlenaria(rs.getInt("PARTICIPACION"));
                nota.setNotaCaso(rs.getInt("CASO"));
                nota.setNotaCaso2(rs.getInt("CASO2"));
                nota.setNotaControl(rs.getInt("CONTROL"));
                nota.setNotaExamParc(rs.getInt("EXAMPARC"));
                nota.setNotaExamFinal(rs.getInt("EXAMFINAL"));
                nota.setNotaTrabajo(rs.getInt("TRABAJO"));
                nota.setPromedio(rs.getDouble("PROMEDIOS"));
                lista.add(nota);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<NotasM> promedioSesiones(String genProg) throws Exception {
        List<NotasM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PROMEDIO_DE_FASES WHERE CODDETPROG = ? ORDER BY PERSONA ASC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, genProg);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            NotasM nota;
            while (rs.next()) {
                nota = new NotasM();
                nota.setPERSONA(rs.getString("PERSONA"));
                nota.setGENDETPROG(rs.getString("GENDETPROG"));
                nota.setCODPROG(rs.getString("CODPROG"));
                nota.setCODFASE(rs.getString("CODFASE"));
                nota.setNomFase(rs.getString("NOMFASE"));
                nota.setPROM_CASO(rs.getString("PROM_CASO"));
                nota.setPROM_PARTICIPACION(rs.getString("PROM_PARTICIPACION"));
                nota.setPROM_TRA(rs.getString("PROM_TRABAJO"));
                nota.setPROM_EXPARC(rs.getString("PROM_PARCIAL"));
                nota.setPROM_EXFINAL(rs.getString("PROM_FINAL"));
                nota.setPROM_FASE(rs.getString("PROMEDIO_FASES_F"));
                lista.add(nota);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<NotasM> promedioPrograma(String genProg) throws Exception {
        List<NotasM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PROMFINAL_PROGRAMA WHERE TIPOPER != 'RE' AND CODDETPROG = ? ORDER BY PROMEDIOFINAL DESC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, genProg);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            NotasM nota;
            while (rs.next()) {
                nota = new NotasM();
                nota.setCodAsig(rs.getString("CODASIG"));
                nota.setNomPer(rs.getString("NOMPER"));
                nota.setApePer(rs.getString("APEPER"));
                nota.setCodDetSes(rs.getString("CODDETPROG"));
                nota.setGENDETPROG(rs.getString("GENDETPROG"));
                nota.setPROM_PROG(rs.getString("PROMEDIOFINAL"));
                lista.add(nota);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void agregarFaseDet(String codDetProg) throws Exception {
        try {
            this.Conexion();
            String sql = "BEGIN SP_INSERT_PROM_FASESv2(?); END;";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codDetProg);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
