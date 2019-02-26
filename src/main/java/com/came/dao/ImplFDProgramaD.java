package com.came.dao;

import com.came.interfaces.FDProgramaI;
import com.came.model.EResultadoM;
import com.came.model.FDProgramaM;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ImplFDProgramaD extends Dao implements FDProgramaI {

    @Override
    public void agregarEncuestaDPrograma(FDProgramaM fdprograma) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO FDPROGRAMA (ProfRpt1,ProfRpt2,ProfRpt3,ProfRpt4,PuntRpt1,PuntRpt2,AreaOporProf,DircProgRpt1,DircProgRpt2,TemRpt1,TemRpt2,TemSug,PartRpt1,PartRpt2,SesRpt,ServRpt1,ServRpt2,Sugerencia,CODSES) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, fdprograma.getProfRpt1());
            ps.setString(2, fdprograma.getProfRpt2());
            ps.setString(3, fdprograma.getProfRpt3());
            ps.setString(4, fdprograma.getProfRpt4());
            ps.setString(5, fdprograma.getPuntRpt1());
            ps.setString(6, fdprograma.getPuntRpt2());
            ps.setString(7, fdprograma.getAreaOporProf());
            ps.setString(8, fdprograma.getDircProgRpt1());
            ps.setString(9, fdprograma.getDircProgRpt2());
            ps.setString(10, fdprograma.getTemRpt1());
            ps.setString(11, fdprograma.getTemRpt2());
            ps.setString(12, fdprograma.getTemSug());
            ps.setString(13, fdprograma.getPartRpt1());
            ps.setString(14, fdprograma.getPartRpt2());
            ps.setString(15, fdprograma.getSesRpt());
            ps.setString(16, fdprograma.getServRpt1());
            ps.setString(17, fdprograma.getServRpt2());
            ps.setString(18, fdprograma.getSugerencia());
            ps.setString(19, fdprograma.getCodSes());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<FDProgramaM> listRespFDPrograma(String codSes) throws Exception {
        List<FDProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM FDPROGRAMA WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FDProgramaM fdpro;
            while (rs.next()) {
                fdpro = new FDProgramaM();
                fdpro.setProfRpt1(rs.getString("PROFRPT1"));
                fdpro.setProfRpt2(rs.getString("PROFRPT2"));
                fdpro.setProfRpt3(rs.getString("PROFRPT3"));
                fdpro.setProfRpt4(rs.getString("PROFRPT4"));
                fdpro.setPuntRpt1(rs.getString("PUNTRPT1"));
                fdpro.setPuntRpt2(rs.getString("PUNTRPT2"));
                fdpro.setAreaOporProf(rs.getString("AREAOPORPROF"));
                fdpro.setDircProgRpt1(rs.getString("DIRCPROGRPT1"));
                fdpro.setDircProgRpt2(rs.getString("DIRCPROGRPT2"));
                fdpro.setTemRpt1(rs.getString("TEMRPT1"));
                fdpro.setTemRpt2(rs.getString("TEMRPT2"));
                fdpro.setTemSug(rs.getString("TEMSUG"));
                fdpro.setPartRpt1(rs.getString("PARTRPT1"));
                fdpro.setPartRpt2(rs.getString("PARTRPT2"));
                fdpro.setSesRpt(rs.getString("SESRPT"));
                fdpro.setServRpt1(rs.getString("SERVRPT1"));
                fdpro.setServRpt2(rs.getString("SERVRPT2"));
                fdpro.setSugerencia(rs.getString("SUGERENCIA"));
                lista.add(fdpro);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }
    
    
    @Override
    public List<FDProgramaM> lstRFDP_DP_I(String codSes) throws Exception {
        List<FDProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFDP_DP WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FDProgramaM fprofe;
            while (rs.next()) {
                fprofe = new FDProgramaM();
                fprofe.setRFDP_DP_I(rs.getString("ITEM"));
                fprofe.setRFDP_DP_RS(lstRFDP_DP_R(fprofe.getRFDP_DP_I(), codSes));
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
    public List<EResultadoM> lstRFDP_DP_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFDP_DP WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFDP_DP_Opcion(rs.getString("OPCION"));
                fprofe.setRFDP_DP_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }

    @Override
    public List<FDProgramaM> lstRFDP_P_I(String codSes) throws Exception {
        List<FDProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFDP_P WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FDProgramaM fprofe;
            while (rs.next()) {
                fprofe = new FDProgramaM();
                fprofe.setRFDP_P_I(rs.getString("ITEM"));
                fprofe.setRFDP_P_RS(lstRFDP_P_R(fprofe.getRFDP_P_I(), codSes));
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
    public List<EResultadoM> lstRFDP_P_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFDP_P WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFDP_P_Opcion(rs.getString("OPCION"));
                fprofe.setRFDP_P_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<FDProgramaM> lstRFDP_PA_I(String codSes) throws Exception {
        List<FDProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFDP_PA WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FDProgramaM fprofe;
            while (rs.next()) {
                fprofe = new FDProgramaM();
                fprofe.setRFDP_PA_I(rs.getString("ITEM"));
                fprofe.setRFDP_PA_RS(lstRFDP_PA_R(fprofe.getRFDP_PA_I(), codSes));
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
    public List<EResultadoM> lstRFDP_PA_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFDP_PA WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFDP_PA_Opcion(rs.getString("OPCION"));
                fprofe.setRFDP_PA_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<FDProgramaM> lstRFDP_PU_I(String codSes) throws Exception {
        List<FDProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFDP_PU WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FDProgramaM fprofe;
            while (rs.next()) {
                fprofe = new FDProgramaM();
                fprofe.setRFDP_PU_I(rs.getString("ITEM"));
                fprofe.setRFDP_PU_RS(lstRFDP_PU_R(fprofe.getRFDP_PU_I(), codSes));
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
    public List<EResultadoM> lstRFDP_PU_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFDP_PU WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFDP_PU_Opcion(rs.getString("OPCION"));
                fprofe.setRFDP_PU_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }
    
    @Override   
    public List<FDProgramaM> lstRFDP_SE_I(String codSes) throws Exception {
        List<FDProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFDP_SE WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FDProgramaM fprofe;
            while (rs.next()) {
                fprofe = new FDProgramaM();
                fprofe.setRFDP_SE_I(rs.getString("ITEM"));
                fprofe.setRFDP_SE_RS(lstRFDP_SE_R(fprofe.getRFDP_SE_I(), codSes));
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
    public List<EResultadoM> lstRFDP_SE_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFDP_SE WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFDP_SE_Opcion(rs.getString("OPCION"));
                fprofe.setRFDP_SE_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<FDProgramaM> lstRFDP_SER_I(String codSes) throws Exception {
        List<FDProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFDP_SER WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FDProgramaM fprofe;
            while (rs.next()) {
                fprofe = new FDProgramaM();
                fprofe.setRFDP_SER_I(rs.getString("ITEM"));
                fprofe.setRFDP_SER_RS(lstRFDP_SER_R(fprofe.getRFDP_SER_I(), codSes));
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
    public List<EResultadoM> lstRFDP_SER_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFDP_SER WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFDP_SER_Opcion(rs.getString("OPCION"));
                fprofe.setRFDP_SER_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<FDProgramaM> lstRFDP_T_I(String codSes) throws Exception {
        List<FDProgramaM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFDP_T WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FDProgramaM fprofe;
            while (rs.next()) {
                fprofe = new FDProgramaM();
                fprofe.setRFDP_T_I(rs.getString("ITEM"));
                fprofe.setRFDP_T_RS(lstRFDP_T_R(fprofe.getRFDP_T_I(), codSes));
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
    public List<EResultadoM> lstRFDP_T_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFDP_T WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFDP_T_Opcion(rs.getString("OPCION"));
                fprofe.setRFDP_T_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }
    
    @Override
    public void exportarPDFPrograma(Map parameters) throws JRException, IOException, Exception {
        this.Conexion();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/RFDPrograma.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, this.getCn());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=FDPrograma.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
}
