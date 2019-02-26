package com.came.dao;

import com.came.interfaces.PagosI;
import com.came.model.InscripcionM;
import com.came.model.PagosDetM;
import com.came.model.PagosM;
import com.came.model.PersonaDetM;
import com.came.model.PersonaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplPagosD extends Dao implements PagosI {

    @Override
    public void guardarPago(PagosM pa) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO PAGOS(CODPER,ASUMPAG,ESTPAG,CANTDES,CONDPAG,OBSEPAG,ENCPAG,EMAILENC,TELFENC,"
                    + "ADMINISTRADOR,FECHAREG) VALUES (?,?,?,?,?,?,?,?,?,?,sysdate)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, pa.getCODPER());
            ps.setString(2, pa.getASUMPAG());
            ps.setString(3, pa.getESTPAG());
            ps.setString(4, pa.getCANTDES());
            ps.setString(5, pa.getCONDPAG());
            ps.setString(6, pa.getOBSEPAG());
            ps.setString(7, pa.getENCPAG());
            ps.setString(8, pa.getEMAILENC());
            ps.setString(9, pa.getTELFENC());
            ps.setString(10, pa.getADMINISTRADOR());
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void cambioEstadoE(InscripcionM ins) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE ASIGNACION SET ESTREG = 'I' WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, ins.getCODPER());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<PersonaM> postulantesProgramaA(String CODPROG) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<PersonaM> lista;
        String sql;
        try {
            sql = "SELECT * FROM VW_PARXPRO WHERE PROGRAMA = ? AND ESTADO = 'A' ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CODPROG);
            rs = ps.executeQuery();
            lista = new ArrayList();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setNomPer(rs.getString("NOMBRE"));
                per.setApePer(rs.getString("APELLIDO"));
                per.setDniPer(rs.getString("DNI"));
                lista.add(per);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void listaradministracion(PersonaDetM per, String CODPER) throws Exception {
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PARTICIPANTEA WHERE CODIGO = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODPER);
            rs = ps.executeQuery();
            while (rs.next()) {
                per.setFOTO(rs.getString("FOTO"));
                per.setDNI(rs.getString("DNI"));
                per.setNOMBRE(rs.getString("NOMBRE"));
                per.setAPELLIDO(rs.getString("APELLIDO"));
                per.setNACIMIENTO(rs.getString("NACIMIENTO"));
                per.setLUGARNAC(rs.getString("LUGARNAC"));
                per.setESTADOCIVIL(rs.getString("ESTADOCIVIL"));
                per.setSEXO(rs.getString("SEXO"));
                per.setCELULAR(rs.getString("CELULAR"));
                per.setDOMICILIO(rs.getString("DOMICILIO"));
                per.setDISTRITO(rs.getString("DISTRITO"));
                per.setCONYUGE(rs.getString("CONYUGE"));
                per.setPROFCONYUGE(rs.getString("PROFCONYUGE"));
                per.setEMPRESA(rs.getString("EMPRESA"));
                per.setRUC(rs.getString("RUC"));
                per.setDOMICILIOFISCAL(rs.getString("DOMICILIOFISCAL"));
                per.setTELETRAB(rs.getString("TELETRAB"));
                per.setEMAILTRABA(rs.getString("EMAILTRABA"));
                per.setACTIEMP(rs.getString("ACTIEMP"));
                per.setAREA(rs.getString("AREA"));
                per.setPUESTO(rs.getString("PUESTO"));
                per.setYEARPUES(rs.getString("YEARPUES"));
                per.setNPERSONAS(rs.getString("NPERSONAS"));
                per.setJEFE(rs.getString("JEFE"));
                per.setCARGOJEFE(rs.getString("CARGOJEFE"));
                per.setTELFJEFE(rs.getString("TELFJEFE"));
                per.setEMAILJEFE(rs.getString("EMAILJEFE"));
                per.setACTIVIDADPER(rs.getString("ACTIVIDADPER"));
                per.setAUTO(rs.getString("AUTO"));
                per.setMARCA(rs.getString("MARCA"));
                per.setPLACA(rs.getString("PLACA"));
                per.setORGANIGRAMA(rs.getString("ORGANIGRAMA"));
                per.setGRADOACA(rs.getString("GRADOACA"));
                per.setUNIVACA(rs.getString("UNIVACA"));
                per.setESPACA(rs.getString("ESPACA"));
                per.setPRO1(rs.getString("PRO1"));
                per.setPRO2(rs.getString("PRO2"));
                per.setPRO3(rs.getString("PRO3"));
                per.setDURA1(rs.getString("DURA1"));
                per.setDURA2(rs.getString("DURA2"));
                per.setDURA3(rs.getString("DURA3"));
                per.setINST1(rs.getString("INST1"));
                per.setINST2(rs.getString("INST2"));
                per.setINST3(rs.getString("INST3"));
                per.setCON1(rs.getString("CON1"));
                per.setCON2(rs.getString("CON2"));
                per.setCON3(rs.getString("CON3"));
                per.setCON4(rs.getString("CON4"));
                per.setCON5(rs.getString("CON5"));
                per.setINTE1(rs.getString("INTE1"));
                per.setINTE2(rs.getString("INTE2"));
                per.setINTE3(rs.getString("INTE3"));
                per.setINTE4(rs.getString("INTE4"));
                per.setINTE5(rs.getString("INTE5"));
                per.setSEGUROPER(rs.getString("SEGUROPER"));
                per.setTELEFONOSEG(rs.getString("TELEFONOSEG"));
                per.setPEREMER(rs.getString("PEREMER"));
                per.setTELFEMER(rs.getString("TELFEMER"));
                per.setPREGUNTA1(rs.getString("PRE1"));
                per.setPREGUNTA2(rs.getString("PRE2"));
                per.setCOMENTARIOS(rs.getString("COMENT"));
                per.setFECHAREGE(rs.getString("FECENT"));
                per.setESTENT(rs.getString("ESTENT"));
                per.setENTREVISTADOR("ENTREVISTADOR");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void guardarPagosDet(PagosM pa) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO PAGOS_DET (CODPAG,FECPAG,MONTPAG) VALUES (?,to_date(?,'dd/MM/yyyy'),?)";
            PreparedStatement ps;
            for (PagosDetM det : pa.getNumpagos()) {
                ps = this.getCn().prepareStatement(sql);
                ps.setString(1, pa.getCODPAG());
                ps.setString(2, det.getFECPAG());
                ps.setDouble(3, det.getMONTPAG());
                ps.execute();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public String traeCodPag(String a) throws Exception {
        this.Conexion();
        try {
            ResultSet rs;
            String sql = "SELECT CODPAG FROM PAGOS WHERE CODPER LIKE ? ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODPAG");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void updatePago(PagosM pa) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PAGOS SET ASUMPAG = ?, ESTPAG = ?, CANTDES = ?, CONDPAG = ?, OBSEPAG = ?, ENCPAG = ?,"
                    + "EMAILENC = ?, TELFENC = ? WHERE CODPAG = ? ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, pa.getASUMPAG());
            ps.setString(2, pa.getESTPAG());
            ps.setString(3, pa.getCANTDES());
            ps.setString(4, pa.getCONDPAG());
            ps.setString(5, pa.getOBSEPAG());
            ps.setString(6, pa.getENCPAG());
            ps.setString(7, pa.getEMAILENC());
            ps.setString(8, pa.getTELFENC());
            ps.setString(9, pa.getCODPAG());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<PagosM> listarPagos() throws Exception {
        this.Conexion();
        ResultSet rs;
        List<PagosM> lista;
        String sql;
        try {
            sql = "SELECT * FROM VW_PAGOS";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            rs = ps.executeQuery();
            lista = new ArrayList();
            PagosM pa;
            while (rs.next()) {
                pa = new PagosM();
                pa.setCODPAG(rs.getString("CODIGO"));
                pa.setCODPER(rs.getString("PARTICIPANTE"));
                pa.setASUMPAG(rs.getString("ASUME"));
                pa.setESTPAG(rs.getString("ESTADO"));
                pa.setCANTDES(rs.getString("DESCUENTO"));
                pa.setCONDPAG(rs.getString("CONDICION"));
                pa.setOBSEPAG(rs.getString("OBSERVACIONES"));
                pa.setENCPAG(rs.getString("ENCARGADO"));
                pa.setEMAILENC(rs.getString("EMAIL"));
                pa.setTELFENC(rs.getString("TELEFONO"));
                pa.setFECHAREG(rs.getString("REGISTRO"));
                lista.add(pa);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
