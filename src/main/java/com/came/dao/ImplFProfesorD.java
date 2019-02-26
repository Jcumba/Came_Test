package com.came.dao;

import com.came.interfaces.FProfesorI;
import com.came.model.EResultadoM;
import com.came.model.FProfesorM;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JasperFillManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ImplFProfesorD extends Dao implements FProfesorI {

    @Override
    public void agregarEncuestaProfesor(FProfesorM fprofesor) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO FPROFESOR (DIREPROG1,DIREPROG2,DIREPROG3,DIREPROG4,DIREPROG5,PART1,PART2,TEM1,TEM2,SUGERENCIA,CODSES,CODPER) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, fprofesor.getDireProg1());
            ps.setString(2, fprofesor.getDireProg2());
            ps.setString(3, fprofesor.getDireProg3());
            ps.setString(4, fprofesor.getDireProg4());
            ps.setString(5, fprofesor.getDireProg5());
            ps.setString(6, fprofesor.getPart1());
            ps.setString(7, fprofesor.getPart2());
            ps.setString(8, fprofesor.getTem1());
            ps.setString(9, fprofesor.getTem2());
            ps.setString(10, fprofesor.getSugerencia());
            ps.setString(11, fprofesor.getCodSes());
            ps.setString(12, fprofesor.getCodFProfesor());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public boolean validacionProfesor(String dni) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT COUNT(CODPER) AS CONTADOR FROM PERSONA WHERE DNIPER = ? AND TIPOPER LIKE '%PR%'";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, dni);
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
    public boolean verificarUrlFProfesor(String codSes) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT COUNT(CODSES) AS CONTADOR FROM SESION WHERE CODSES = ? AND LINK_PROF = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codSes);
            ps.setString(2, codSes);
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
    public void traerCodPer(FProfesorM fprofesor, String dni) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODPER FROM PERSONA WHERE DNIPER = ? AND TIPOPER LIKE '%PR%'";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            rs.next();
            fprofesor.setCodFProfesor(rs.getString("CODPER"));
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean traerEstForm(FProfesorM fprofesor) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT COUNT(CODSES) AS CONTADOR FROM FPROFESOR WHERE CODSES = ? AND CODPER = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, fprofesor.getCodSes());
            ps.setString(2, fprofesor.getCodFProfesor());
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
    public List<FProfesorM> listarRespFProfesor(String codSes) throws Exception {
        List<FProfesorM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM FPROFESOR WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FProfesorM fprofe;
            while (rs.next()) {
                fprofe = new FProfesorM();
                fprofe.setDireProg1(rs.getString("DIREPROG1"));
                fprofe.setDireProg2(rs.getString("DIREPROG2"));
                fprofe.setDireProg3(rs.getString("DIREPROG3"));
                fprofe.setDireProg4(rs.getString("DIREPROG4"));
                fprofe.setDireProg5(rs.getString("DIREPROG5"));
                fprofe.setPart1(rs.getString("PART1"));
                fprofe.setPart2(rs.getString("PART2"));
                fprofe.setTem1(rs.getString("TEM1"));
                fprofe.setTem2(rs.getString("TEM2"));
                fprofe.setSugerencia(rs.getString("SUGERENCIA"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

    @Override
    public List<FProfesorM> lstRFP_DP_I(String codSes) throws Exception {
        List<FProfesorM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFP_DP WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FProfesorM fprofe;
            while (rs.next()) {
                fprofe = new FProfesorM();
                fprofe.setRFP_DP_I(rs.getString("ITEM"));
                fprofe.setRFP_DP_RS(lstRFP_DP_R(fprofe.getRFP_DP_I(), codSes));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

    @Override
    public List<EResultadoM> lstRFP_DP_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFP_DP WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFP_DP_Opcion(rs.getString("OPCION"));
                fprofe.setRFP_DP_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }

    @Override
    public List<FProfesorM> lstRFP_P_I(String codSes) throws Exception {
        List<FProfesorM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFP_P WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FProfesorM fprofe;
            while (rs.next()) {
                fprofe = new FProfesorM();
                fprofe.setRFP_P_I(rs.getString("ITEM"));
                fprofe.setRFP_P_RS(lstRFP_P_R(fprofe.getRFP_P_I(), codSes));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

    @Override
    public List<EResultadoM> lstRFP_P_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFP_P WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFP_P_Opcion(rs.getString("OPCION"));
                fprofe.setRFP_P_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }

    @Override
    public List<FProfesorM> lstRFP_T_I(String codSes) throws Exception {
        List<FProfesorM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFP_T WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FProfesorM fprofe;
            while (rs.next()) {
                fprofe = new FProfesorM();
                fprofe.setRFP_T_I(rs.getString("ITEM"));
                fprofe.setRFP_T_RS(lstRFP_T_R(fprofe.getRFP_T_I(), codSes));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

    @Override
    public List<EResultadoM> lstRFP_T_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFP_T WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFP_T_Opcion(rs.getString("OPCION"));
                fprofe.setRFP_T_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }

    @Override
    public void exportarPDFProfesor(Map parameters) throws JRException, IOException, Exception {
        this.Conexion();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/RFProfesor.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, this.getCn());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=FProfesor.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

}
