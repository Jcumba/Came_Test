package com.came.dao;

import com.came.interfaces.FParticipanteI;
import com.came.model.EResultadoM;
import com.came.model.FParticipanteM;
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

public class ImplFParticipanteD extends Dao implements FParticipanteI {

    @Override
    public void agregarEncuestaParticipante(FParticipanteM fpartitipante) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO FPARTICIPANTE (ProfRpt,ProfSug,TemRpt,TemSug,SeCoRpt,SeCoRpt1,SeCoRpt2,CasoRpt1,CasoRpt2,CasoRpt3,AutRpt1,AutRpt2,ServRpt1,Sugerencias,Quejas,CODSES,CODASIG) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, fpartitipante.getProfRpt());
            ps.setString(2, fpartitipante.getProfSug());
            ps.setString(3, fpartitipante.getTemRpt());
            ps.setString(4, fpartitipante.getTemSug());
            ps.setString(5, fpartitipante.getSeCoRpt());
            ps.setString(6, fpartitipante.getSeCoRpt1());
            ps.setString(7, fpartitipante.getSeCoRpt2());
            ps.setString(8, fpartitipante.getCasoRpt1());
            ps.setString(9, fpartitipante.getCasoRpt2());
            ps.setString(10, fpartitipante.getCasoRpt2());
            ps.setString(11, fpartitipante.getCasoRpt3());
            ps.setString(12, fpartitipante.getAutRpt1());
            ps.setString(13, fpartitipante.getAutRpt2());
            ps.setString(14, fpartitipante.getSugerencias());
            ps.setString(15, fpartitipante.getQuejas());
            ps.setString(16, fpartitipante.getCodSes());
            ps.setString(17, fpartitipante.getCodAsig());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public boolean validacionParticipante(String dni) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT COUNT(CODPER) AS CONTADOR FROM PERSONA WHERE DNIPER = ? AND TIPOPER LIKE 'ES'";
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
    public boolean verificarUrlFParticipante(String codSes) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT COUNT(CODSES) AS CONTADOR FROM SESION WHERE CODSES = ? AND LINK_PART = ?";
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
    public void traerCodAsig(FParticipanteM fpartitipante, String dni) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODASIG FROM ASIGNACION INNER JOIN PERSONA ON ASIGNACION.CODPER = PERSONA.CODPER WHERE PERSONA.DNIPER = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            rs.next();
            fpartitipante.setCodAsig(rs.getString("CODASIG"));
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean traerEstForm(FParticipanteM fpartitipante) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT COUNT(CODSES) AS CONTADOR FROM FPARTICIPANTE WHERE CODSES = ? AND CODASIG = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, fpartitipante.getCodSes());
            ps.setString(2, fpartitipante.getCodAsig());
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
    public List<FParticipanteM> listRespFParticipante(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM FPARTICIPANTE WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fpart;
            while (rs.next()) {
                fpart = new FParticipanteM();
                fpart.setProfRpt(rs.getString("PROFRPT"));
                fpart.setProfSug(rs.getString("PROFSUG"));
                fpart.setTemRpt(rs.getString("TEMRPT"));
                fpart.setTemSug(rs.getString("TEMSUG"));
                fpart.setSeCoRpt(rs.getString("SECORPT"));
                fpart.setSeCoRpt1(rs.getString("SECORPT1"));
                fpart.setSeCoRpt2(rs.getString("SECORPT2"));
                fpart.setCasoRpt1(rs.getString("CASORPT1"));
                fpart.setCasoRpt2(rs.getString("CASORPT2"));
                fpart.setCasoRpt3(rs.getString("CASORPT3"));
                fpart.setAutRpt1(rs.getString("AUTRPT1"));
                fpart.setAutRpt2(rs.getString("AUTRPT2"));
                fpart.setServRpt1(rs.getString("SERVRPT1"));
                fpart.setSugerencias(rs.getString("SUGERENCIAS"));
                fpart.setQuejas(rs.getString("QUEJAS"));
                lista.add(fpart);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

    @Override
    public List<FParticipanteM> lstRFPAR_CA_I(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFPAR_CA WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fprofe;
            while (rs.next()) {
                fprofe = new FParticipanteM();
                fprofe.setRFPAR_CA_I(rs.getString("ITEM"));
                fprofe.setRFPAR_CA_RS(lstRFPAR_CA_R(fprofe.getRFPAR_CA_I(), codSes));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<EResultadoM> lstRFPAR_CA_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFPAR_CA WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFPAR_CA_Opcion(rs.getString("OPCION"));
                fprofe.setRFPAR_CA_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<FParticipanteM> lstRFPAR_CB_I(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFPAR_CB WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fprofe;
            while (rs.next()) {
                fprofe = new FParticipanteM();
                fprofe.setRFPAR_CB_I(rs.getString("ITEM"));
                fprofe.setRFPAR_CB_RS(lstRFPAR_CB_R(fprofe.getRFPAR_CB_I(), codSes));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<EResultadoM> lstRFPAR_CB_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFPAR_CB WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFPAR_CB_Opcion(rs.getString("OPCION"));
                fprofe.setRFPAR_CB_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<FParticipanteM> lstRFPAR_P_I(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFPAR_P WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fprofe;
            while (rs.next()) {
                fprofe = new FParticipanteM();
                fprofe.setRFPAR_P_I(rs.getString("ITEM"));
                fprofe.setRFPAR_P_RS(lstRFPAR_P_R(fprofe.getRFPAR_P_I(), codSes));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<EResultadoM> lstRFPAR_P_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFPAR_P WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFPAR_P_Opcion(rs.getString("OPCION"));
                fprofe.setRFPAR_P_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<FParticipanteM> lstRFPAR_PART_I(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFPAR_PART WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fprofe;
            while (rs.next()) {
                fprofe = new FParticipanteM();
                fprofe.setRFPAR_PART_I(rs.getString("ITEM"));
                fprofe.setRFPAR_PART_RS(lstRFPAR_PART_R(fprofe.getRFPAR_PART_I(), codSes));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<EResultadoM> lstRFPAR_PART_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFPAR_PART WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFPAR_PART_Opcion(rs.getString("OPCION"));
                fprofe.setRFPAR_PART_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<FParticipanteM> lstRFPAR_PUNT_I(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFPAR_PUNT WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fprofe;
            while (rs.next()) {
                fprofe = new FParticipanteM();
                fprofe.setRFPAR_PUNT_I(rs.getString("ITEM"));
                fprofe.setRFPAR_PUNT_RS(lstRFPAR_PUNT_R(fprofe.getRFPAR_PUNT_I(), codSes));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<EResultadoM> lstRFPAR_PUNT_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFPAR_PUNT WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFPAR_PUNT_Opcion(rs.getString("OPCION"));
                fprofe.setRFPAR_PUNT_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<FParticipanteM> lstRFPAR_SE_I(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFPAR_SE WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fprofe;
            while (rs.next()) {
                fprofe = new FParticipanteM();
                fprofe.setRFPAR_SE_I(rs.getString("ITEM"));
                fprofe.setRFPAR_SE_RS(lstRFPAR_SE_R(fprofe.getRFPAR_SE_I(), codSes));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<EResultadoM> lstRFPAR_SE_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFPAR_SE WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFPAR_SE_Opcion(rs.getString("OPCION"));
                fprofe.setRFPAR_SE_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<FParticipanteM> lstRFPAR_T_I(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFPAR_T WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fprofe;
            while (rs.next()) {
                fprofe = new FParticipanteM();
                fprofe.setRFPAR_T_I(rs.getString("ITEM"));
                fprofe.setRFPAR_T_RS(lstRFPAR_T_R(fprofe.getRFPAR_T_I(), codSes));
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
    public List<EResultadoM> lstRFPAR_T_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFPAR_T WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFPAR_T_Opcion(rs.getString("OPCION"));
                fprofe.setRFPAR_T_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }

    @Override
    public List<FParticipanteM> lstRFPAR_TD_I(String codSes) throws Exception {
        List<FParticipanteM> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DISTINCT ITEM FROM VW_RFPAR_TD WHERE CODSES = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            FParticipanteM fprofe;
            while (rs.next()) {
                fprofe = new FParticipanteM();
                fprofe.setRFPAR_TD_I(rs.getString("ITEM"));
                fprofe.setRFPAR_TD_RS(lstRFPAR_TD_R(fprofe.getRFPAR_TD_I(), codSes));
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
    public List<EResultadoM> lstRFPAR_TD_R(String item, String codSes) throws Exception {
        List<EResultadoM> lista;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_RFPAR_TD WHERE ITEM LIKE ? AND CODSES = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, item);
            ps.setString(2, codSes);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            EResultadoM fprofe;
            while (rs.next()) {
                fprofe = new EResultadoM();
                fprofe.setRFPAR_TD_Opcion(rs.getString("OPCION"));
                fprofe.setRFPAR_TD_Resultado(rs.getInt("RESULTADO"));
                lista.add(fprofe);
            }
        } catch (SQLException e) {
            throw e;
        }
        return lista;
    }

    @Override
    public void exportarPDFParticipante(Map parameters) throws JRException, IOException, Exception {
        this.Conexion();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/RFParticipante.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, this.getCn());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=FParticipante.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

}
