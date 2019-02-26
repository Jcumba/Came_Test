package com.came.dao;

import com.came.interfaces.BibliotecaI;
import com.came.model.BibliotecaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImplBibliotecaD extends Dao implements BibliotecaI {

    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void registrar(BibliotecaM bi) throws Exception {
        this.Conexion();
        try {
            String sql = "insert into BIBLIOTECA(NOMB,TAM,FECHA,TIPCON,UBIC,PRIV,CODTIPPG) values (?,?,F_SYSDATE,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, bi.getNomb());
            ps.setString(2, bi.getTamano());
            ps.setString(3, bi.getTipocont());
            ps.setString(4, bi.getUbicacion());
            ps.setString(5, bi.getPrivilegio());
            ps.setString(6, bi.getPrograma());
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<BibliotecaM> listar(String doc) throws Exception {
        ResultSet rs;
        List<BibliotecaM> lista;
        try {
            this.Conexion();
            String sql = "select * from VW_BIBLIOTECA WHERE upper(NOMBRE) LIKE upper(?) and TIPO not like 'Pelicula' and TIPO not LIKE 'Video' order by CODIGO DESC";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + doc + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            BibliotecaM bi;
            while (rs.next()) {
                bi = new BibliotecaM();
                bi.setCod(rs.getString("CODIGO"));
                bi.setNomb(rs.getString("NOMBRE"));
                bi.setTamano(rs.getString("TAMAÑO"));
                bi.setFecha(rs.getString("FECHA"));
                bi.setTipocont(rs.getString("TIPO"));
                bi.setUbicacion(rs.getString("UBICACION"));
                bi.setPrivilegio(rs.getString("PRIVILEGIO"));
                bi.setPrograma(rs.getString("PROGRAMA"));
                lista.add(bi);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<BibliotecaM> listarDocumentos(String CODTIPPG) throws Exception {
        List<BibliotecaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT CODBIB, NOMB ,CASE  WHEN PRIV = 'E' THEN 'EST' ELSE 'PROF' END AS PRIV FROM BIBLIOTECA  WHERE CODTIPPG LIKE ? ORDER BY NOMB";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODTIPPG);
            rs = ps.executeQuery();
            lista = new ArrayList();
            BibliotecaM docs;
            while (rs.next()) {
                docs = new BibliotecaM();
                docs.setCod(rs.getString("CODBIB"));
                docs.setNomb(rs.getString("NOMB"));
                docs.setPrivilegio(rs.getString("PRIV"));
                lista.add(docs);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }

    }

    @Override
    public String traerCodtippg(String CODDETPROG) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT PROGRAMA.CODPROG,PROGRAMA.CODTIPPG,PROG_DET.CODDETPROG FROM PROGRAMA INNER JOIN PROG_DET ON programa.codprog = PROG_DET.CODPROG WHERE PROG_DET.CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CODDETPROG);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODTIPPG");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<BibliotecaM> listarDocSes(String CODSES) throws Exception {
        List<BibliotecaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT SESION_ANEXO.CODSESANE,SESION_ANEXO.CODSES,BIBLIOTECA.NOMB,BIBLIOTECA.UBIC,to_char( FECSESANE, 'DD-MM-YYYY HH:MI AM' ) AS FECHA  FROM SESION_ANEXO INNER JOIN BIBLIOTECA ON sesion_anexo.codbib = BIBLIOTECA.CODBIB WHERE SESION_ANEXO.CODSES LIKE ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODSES);
            rs = ps.executeQuery();
            lista = new ArrayList();
            BibliotecaM docs;
            while (rs.next()) {
                docs = new BibliotecaM();
                docs.setCod(rs.getString("CODSESANE"));
                docs.setCodses(rs.getString("CODSES"));
                docs.setNomb(rs.getString("NOMB"));
                docs.setUbicacion(rs.getString("UBIC"));
                docs.setFecha(rs.getString("FECHA"));
                lista.add(docs);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminarAnexoDoc(String CODSESANE) throws Exception {
        try {
            this.Conexion();
            String sql = "delete from sesion_anexo where CODSESANE LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODSESANE);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void actualizar(BibliotecaM bi) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE BIBLIOTECA SET CODTIPPG = ? WHERE CODBIB = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, bi.getPrograma());
            ps.setString(2, bi.getCod());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void registrarVideo(BibliotecaM bi) throws Exception {
        this.Conexion();
        try {
            String sql = "insert into BIBLIOTECA(NOMB,TAM,FECHA,TIPCON,UBIC,PRIV,CODTIPPG) values (?,?,F_SYSDATE,?,?,'P',?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, bi.getNombVid());
            ps.setString(2, bi.getDurVid());
            ps.setString(3, bi.getTipoVid());
            ps.setString(4, bi.getUbiVid());
            ps.setString(5, bi.getProgVid());
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<BibliotecaM> listarVideos(String vid) throws Exception {
        ResultSet rs;
        List<BibliotecaM> lista;
        try {
            this.Conexion();
            String sql = "select * from VW_BIBLIOTECA WHERE upper(NOMBRE) LIKE upper(?) and (TIPO  like 'Pelicula' OR TIPO  LIKE 'Video') order by CODIGO DESC";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + vid + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            BibliotecaM bi;
            while (rs.next()) {
                bi = new BibliotecaM();
                bi.setCodVid(rs.getString("CODIGO"));
                bi.setNombVid(rs.getString("NOMBRE"));
                bi.setDurVid(rs.getString("TAMAÑO"));
                bi.setFecVid(rs.getString("FECHA"));
                bi.setTipoVid(rs.getString("TIPO"));
                bi.setUbiVid(rs.getString("UBICACION"));
                bi.setPriVid(rs.getString("PRIVILEGIO"));
                bi.setProgVid(rs.getString("PROGRAMA"));
                lista.add(bi);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void actualizarVideo(BibliotecaM bi) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE BIBLIOTECA SET NOMB = ?, TAM = ?  WHERE CODBIB = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, bi.getNombVid());
            ps.setString(2, bi.getDurVid());
            ps.setString(3, bi.getCodVid());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
