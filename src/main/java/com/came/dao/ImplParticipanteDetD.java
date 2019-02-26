package com.came.dao;

import com.came.interfaces.ParticipanteDetI;
import com.came.model.HijoM;
import com.came.model.PersonaDetM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplParticipanteDetD extends Dao implements ParticipanteDetI {

    @Override
    public void guardar(PersonaDetM det) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO PERSONA_DET(CODPER,CONYUGE,PROFCONY,TELFTRAB,EMAILTRAB,AREATRAB,YEARPUESTO,"
                    + "NUMPERCARGO,JEFEMP,JEFCARGO,JEFTELF,JEFEMAIL,ACTPER,AUTOPER,MARCAUT,PLACAUT,SEGURO,TELFSEGURO,"
                    + "EMERNOMB,EMERTELF,IMGORG,GRAPER,UNIPER,ESPPER,PROACA1,DURACA1,INSACA1,PROACA2,DURACA2,INSACA2,"
                    + "PROACA3,DURACA3,INSACA3,CO1,CO2,CO3,CO4,CO5,INT1,INT2,INT3,INT4,INT5,ENVIODATOS,GERENCIA,JEFATURA,SUPERVISOR,"
                    + "AUTORIZACION,DOCCV,NIVJER,RESCUV) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, det.getCODPER());
            ps.setString(2, det.getCONYUGE());
            ps.setString(3, det.getPROFCONY());
            ps.setString(4, det.getTELFTRAB());
            ps.setString(5, det.getEMAILTRAB());
            ps.setString(6, det.getAREATRAB());
            ps.setString(7, det.getYEARPUESTO());
            ps.setString(8, det.getNUMPERCARGO());
            ps.setString(9, det.getJEFEMP());
            ps.setString(10, det.getJEFCARGO());
            ps.setString(11, det.getJEFTELF());
            ps.setString(12, det.getJEFEMAIL());
            ps.setString(13, det.getACTPER());
            ps.setString(14, det.getAUTOPER());
            ps.setString(15, det.getMARCAUT());
            ps.setString(16, det.getPLACAUT());
            ps.setString(17, det.getSEGURO());
            ps.setString(18, det.getTELFSEGURO());
            ps.setString(19, det.getEMERNOMB());
            ps.setString(20, det.getEMERTELF());
            ps.setString(21, det.getIMGORG());
            ps.setString(22, det.getGRAPER());
            ps.setString(23, det.getUNIPER());
            ps.setString(24, det.getESPPER());
            ps.setString(25, det.getPROACA1());
            ps.setString(26, det.getDURACA1());
            ps.setString(27, det.getINSACA1());
            ps.setString(28, det.getPROACA2());
            ps.setString(29, det.getDURACA2());
            ps.setString(30, det.getINSACA2());
            ps.setString(31, det.getPROACA3());
            ps.setString(32, det.getDURACA3());
            ps.setString(33, det.getINSACA3());
            ps.setString(34, det.getCO1());
            ps.setString(35, det.getCO2());
            ps.setString(36, det.getCO3());
            ps.setString(37, det.getCO4());
            ps.setString(38, det.getCO5());
            ps.setString(39, det.getINT1());
            ps.setString(40, det.getINT2());
            ps.setString(41, det.getINT3());
            ps.setString(42, det.getINT4());
            ps.setString(43, det.getINT5());
            ps.setBoolean(44, det.isENVIODATOS());
            ps.setString(45, det.getGERENCIA());
            ps.setString(46, det.getJEFATURA());
            ps.setString(47, det.getSUPERVISOR());
            ps.setString(48, det.getAUTORIZACION());
            ps.setString(49, det.getDOCCV());
            ps.setString(50, det.getNIVEJER());
            ps.setString(51, det.getRESCUV());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public String traeCodDet(String a) throws Exception {
        this.Conexion();
        try {
            ResultSet rs;
            String sql = "SELECT CODPERDET FROM PERSONA_DET WHERE CODPER LIKE ? ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODPERDET");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void guardarhijo(PersonaDetM par) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO PERSONA_HIJOS (CODPERDET,SEXHIJ,FECNACHI) VALUES (?,?,to_date(?,'dd/MM/yyyy'))";
            PreparedStatement ps;
            for (HijoM hijo : par.getHijos()) {
                ps = this.getCn().prepareStatement(sql);
                ps.setString(1, par.getCODPERDET());
                ps.setString(2, hijo.getSEXHIJ());
                ps.setString(3, hijo.getFECNACHIJ());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void actualizar(PersonaDetM det) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA_DET SET CONYUGE = ?, PROFCONY = ?, TELFTRAB = ?, EMAILTRAB = ?,"
                    + "AREATRAB = ?, YEARPUESTO = ?, NUMPERCARGO = ?, JEFEMP = ?, JEFCARGO = ?, JEFTELF = ?,"
                    + " JEFEMAIL = ?, ACTPER = ?, AUTOPER = ?, MARCAUT = ?, PLACAUT = ?, GRAPER = ?, UNIPER = ?,"
                    + "ESPPER = ?, PROACA1 = ?, DURACA1 = ?, INSACA1 = ?, PROACA2 = ?, DURACA2 = ?, INSACA2 = ?,"
                    + "PROACA3 = ?, DURACA3 = ?, INSACA3 = ?, SEGURO = ?, TELFSEGURO = ?, EMERNOMB = ?,"
                    + "EMERTELF = ? WHERE CODPERDET = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, det.getCONYUGE());
            ps.setString(2, det.getPROFCONY());
            ps.setString(3, det.getTELFTRAB());
            ps.setString(4, det.getEMAILTRAB());
            ps.setString(5, det.getAREATRAB());
            ps.setString(6, det.getYEARPUESTO());
            ps.setString(7, det.getNUMPERCARGO());
            ps.setString(8, det.getJEFEMP());
            ps.setString(9, det.getJEFCARGO());
            ps.setString(10, det.getJEFTELF());
            ps.setString(11, det.getJEFEMAIL());
            ps.setString(12, det.getACTPER());
            ps.setString(13, det.getAUTOPER());
            ps.setString(14, det.getMARCAUT());
            ps.setString(15, det.getPLACAUT());
            ps.setString(16, det.getGRAPER());
            ps.setString(17, det.getUNIPER());
            ps.setString(18, det.getESPPER());
            ps.setString(19, det.getPROACA1());
            ps.setString(20, det.getDURACA1());
            ps.setString(21, det.getINSACA1());
            ps.setString(22, det.getPROACA2());
            ps.setString(23, det.getDURACA2());
            ps.setString(24, det.getINSACA2());
            ps.setString(25, det.getPROACA3());
            ps.setString(26, det.getDURACA3());
            ps.setString(27, det.getINSACA3());
            ps.setString(28, det.getSEGURO());
            ps.setString(29, det.getTELFSEGURO());
            ps.setString(30, det.getEMERNOMB());
            ps.setString(31, det.getEMERTELF());
            ps.setString(32, det.getCODPERDET());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
