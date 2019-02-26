package com.came.dao;

import com.came.interfaces.ParticipanteI;
import com.came.model.EmpresaM;
import com.came.model.PersonaDetM;
import com.came.model.PersonaM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplParticipanteD extends Dao implements ParticipanteI {

    @Override
    public void guardar(PersonaM par) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO PERSONA(NOMPER,APEPER,DNIPER,EMAILPER,SEXPER,CELPER,DIRPER,TIPOPER,ESTPER,USERPER,PASSPER,CODUBI,FECHPER,"
                    + "IMGPER,CARGOPER,CODEMP,LUGNAC,ESTCIV,PROFPER) VALUES (?,?,?,?,?,?,?,'PO','A',?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, par.getNomPer());
            ps.setString(2, par.getApePer());
            ps.setString(3, par.getDniPer());
            ps.setString(4, par.getEmailPer());
            ps.setString(5, par.getSexPer());
            ps.setString(6, par.getCelPer());
            ps.setString(7, par.getDirPer());
            ps.setString(8, par.getDniPer());
            ps.setString(9, par.getDniPer());
            ps.setString(10, par.getCodUbi());
            ps.setString(11, par.getFecNac());
            ps.setString(12, par.getImgPer());
            ps.setString(13, par.getCargPer());
            ps.setString(14, par.getEmpresa());
            ps.setString(15, par.getLugNac());
            ps.setString(16, par.getEstCivil());
            ps.setString(17, par.getProfPer());
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void guardarDocente(PersonaM par) throws Exception {
        this.Conexion();
        try {
            String sql = "INSERT INTO PERSONA(NOMPER,APEPER,DNIPER,EMAILPER,SEXPER,CELPER,DIRPER,TIPOPER,ESTPER,USERPER,PASSPER,CODUBI,FECHPER,"
                    + "IMGPER,CARGOPER,CODEMP,LUGNAC,ESTCIV,PROFPER,NACNLPER,INIPER) VALUES (?,?,?,?,?,?,?,'PR','A',?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, par.getNomPer());
            ps.setString(2, par.getApePer());
            ps.setString(3, par.getDniPer());
            ps.setString(4, par.getEmailPer());
            ps.setString(5, par.getSexPer());
            ps.setString(6, par.getCelPer());
            ps.setString(7, par.getDirPer());
            ps.setString(8, par.getDniPer());
            ps.setString(9, par.getDniPer());
            ps.setString(10, par.getCodUbi());
            ps.setString(11, par.getFecNac());
            ps.setString(12, par.getImgPer());
            ps.setString(13, par.getCargPer());
            ps.setString(14, par.getEmpresa());
            ps.setString(15, par.getLugNac());
            ps.setString(16, par.getEstCivil());
            ps.setString(17, par.getProfPer());
            ps.setString(18, par.getNacnlPer());
            ps.setString(19, par.getIniPer());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public String traecodpar(String a) throws Exception {
        this.Conexion();
        try {
            ResultSet rs;
            String sql = "SELECT CODPER FROM PERSONA WHERE DNIPER LIKE ? ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODPER");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void cambioEstadoES(String CODPER) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA SET TIPOPER = 'ES' WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CODPER);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<PersonaM> listar(String CODPROG) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<PersonaM> lista;
        String sql;
        try {
            sql = "SELECT * FROM VW_PARTICIPANTE WHERE TIPOPER LIKE 'PO' AND PROCESO LIKE 'I' AND ESTADO LIKE '1' AND PROGRAMA = ? ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CODPROG);
            rs = ps.executeQuery();
            lista = new ArrayList();
            PersonaM per;
            EmpresaM em;
            while (rs.next()) {
                per = new PersonaM();
                em = new EmpresaM();
                per.setCodPer(rs.getString("CODIGO"));
                per.setDniPer(rs.getString("DNI"));
                per.setNomPer(rs.getString("NOMBRE"));
                per.setApePer(rs.getString("APELLIDO"));
                per.setEmailPer(rs.getString("EMAIL"));
                per.setCelPer(rs.getString("CELULAR"));
                per.setDirPer(rs.getString("DIRECCION"));
                per.setFecNac(rs.getString("NACIMIENTO"));
                per.setEmpresa(rs.getString("CODEMPRESA"));
                per.setLugNac(rs.getString("LUGARNACIMIENTO"));
                per.setEstCivil(rs.getString("ESTADOCIVIL"));
                per.getPersonaDet().setCODPERDET(rs.getString("CODIGODET"));
                per.getPersonaDet().setCONYUGE(rs.getString("CONYUGE"));
                per.getPersonaDet().setPROFCONY(rs.getString("CONYUGEPROF"));
                per.getPersonaDet().setTELFTRAB(rs.getString("TELEFTRAB"));
                per.getPersonaDet().setEMAILTRAB(rs.getString("EMAILTRAB"));
                per.getPersonaDet().setAREATRAB(rs.getString("AREATRAB"));
                per.getPersonaDet().setYEARPUESTO(rs.getString("YEARPUESTO"));
                per.getPersonaDet().setNUMPERCARGO(rs.getString("NUMPERCARGO"));
                per.getPersonaDet().setJEFEMP(rs.getString("JEFE"));
                per.getPersonaDet().setJEFCARGO(rs.getString("JEFECARGO"));
                per.getPersonaDet().setJEFTELF(rs.getString("JEFETELF"));
                per.getPersonaDet().setJEFEMAIL(rs.getString("JEFEMAIL"));
                per.getPersonaDet().setACTPER(rs.getString("ACTIVIDADPER"));
                per.getPersonaDet().setAUTOPER(rs.getString("TIENEAUTO"));
                per.getPersonaDet().setMARCAUT(rs.getString("MARCAAUTO"));
                per.getPersonaDet().setPLACAUT(rs.getString("PLACAAUTO"));
                per.getPersonaDet().setGRAPER(rs.getString("GRADOPER"));
                per.getPersonaDet().setUNIPER(rs.getString("UNIPER"));
                per.getPersonaDet().setESPPER(rs.getString("ESPPER"));
                per.getPersonaDet().setPROACA1(rs.getString("PROACA1"));
                per.getPersonaDet().setDURACA1(rs.getString("DURACA1"));
                per.getPersonaDet().setINSACA1(rs.getString("INSACA1"));
                per.getPersonaDet().setPROACA2(rs.getString("PROACA2"));
                per.getPersonaDet().setDURACA2(rs.getString("DURACA2"));
                per.getPersonaDet().setINSACA2(rs.getString("INSACA2"));
                per.getPersonaDet().setPROACA3(rs.getString("PROACA3"));
                per.getPersonaDet().setDURACA3(rs.getString("DURACA3"));
                per.getPersonaDet().setINSACA3(rs.getString("INSACA3"));
                per.getPersonaDet().setSEGURO(rs.getString("SEGURO"));
                per.getPersonaDet().setTELFSEGURO(rs.getString("TELFSEGURO"));
                per.getPersonaDet().setEMERNOMB(rs.getString("EMERGENCIA"));
                per.getPersonaDet().setEMERTELF(rs.getString("EMERGENCIATELF"));
                per.getPersonaDet().setESTENT(rs.getString("ESTADO"));
                em.setNOMBEMP(rs.getString("EMPRESA"));
                per.setEmpresaM(em);
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
    public void listarDatosParticipantes(PersonaDetM per, String CODPER) throws Exception {
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PARTICIPANTER WHERE CODIGO = ? ";
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
                per.setASUMEPAG(rs.getString("ASUMEPAG"));
                per.setESTADOPAG(rs.getString("ESTADOPAG"));
                per.setCONDIPAG(rs.getString("CONDIPAG"));
                per.setOBSERPAG(rs.getString("OBSERPAG"));
                per.setENCAPAG(rs.getString("ENCAPAG"));
                per.setENCEMAIL(rs.getString("ENCEMAIL"));
                per.setENCTELF(rs.getString("ENCTELF"));
                per.setFECHAREGA(rs.getString("FECHAREGA"));
                per.setPREGUNTA1(rs.getString("PREGUNTA1"));
                per.setPREGUNTA2(rs.getString("PREGUNTA2"));
                per.setCOMENTARIOS(rs.getString("COMENTARIOS"));
                per.setFECHAREGE(rs.getString("FECHAREGE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void cambioEstadoS(String CODPER) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE ASIGNACION SET ESTREG = 'S' WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CODPER);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void actualizar(PersonaM par) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA SET NOMPER = ?, APEPER = ?, EMAILPER = ?, CELPER = ?, DIRPER = ?,"
                    + " FECHPER = to_date(?,'dd/MM/yyyy'), CODEMP = ?, LUGNAC = ?, ESTCIV = ? WHERE CODPER = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, par.getNomPer());
            ps.setString(2, par.getApePer());
            ps.setString(3, par.getEmailPer());
            ps.setString(4, par.getCelPer());
            ps.setString(5, par.getDirPer());
            ps.setString(6, par.getFecNac());
            ps.setString(7, par.getEmpresa());
            ps.setString(8, par.getLugNac());
            ps.setString(9, par.getEstCivil());
            ps.setString(10, par.getCodPer());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<PersonaM> listarInscritos(String CODPROG) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<PersonaM> lista;
        String sql;
        try {
            sql = "SELECT * FROM VW_PARTICIPANTE WHERE (TIPOPER LIKE 'ES' OR TIPOPER LIKE 'RE')AND PROCESO LIKE 'S' AND ESTADO = '1' AND PROGRAMA = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CODPROG);
            rs = ps.executeQuery();
            lista = new ArrayList();
            PersonaM per;
            EmpresaM em;
            while (rs.next()) {
                per = new PersonaM();
                em = new EmpresaM();
                per.setCodPer(rs.getString("CODIGO"));
                per.setDniPer(rs.getString("DNI"));
                per.setImgPer(rs.getString("IMGPER"));
                per.setNomPer(rs.getString("NOMBRE"));
                per.setApePer(rs.getString("APELLIDO"));
                per.setEmailPer(rs.getString("EMAIL"));
                per.setCelPer(rs.getString("CELULAR"));
                per.setDirPer(rs.getString("DIRECCION"));
                per.setFecNac(rs.getString("NACIMIENTO"));
                per.setEmpresa(rs.getString("CODEMPRESA"));
                per.setLugNac(rs.getString("LUGARNACIMIENTO"));
                per.setEstCivil(rs.getString("ESTADOCIVIL"));
                per.setTipoPer(rs.getString("TIPOPER"));
                per.getPersonaDet().setCODPERDET(rs.getString("CODIGODET"));
                per.getPersonaDet().setCONYUGE(rs.getString("CONYUGE"));
                per.getPersonaDet().setPROFCONY(rs.getString("CONYUGEPROF"));
                per.getPersonaDet().setTELFTRAB(rs.getString("TELEFTRAB"));
                per.getPersonaDet().setEMAILTRAB(rs.getString("EMAILTRAB"));
                per.getPersonaDet().setAREATRAB(rs.getString("AREATRAB"));
                per.getPersonaDet().setYEARPUESTO(rs.getString("YEARPUESTO"));
                per.getPersonaDet().setNUMPERCARGO(rs.getString("NUMPERCARGO"));
                per.getPersonaDet().setJEFEMP(rs.getString("JEFE"));
                per.getPersonaDet().setJEFCARGO(rs.getString("JEFECARGO"));
                per.getPersonaDet().setJEFTELF(rs.getString("JEFETELF"));
                per.getPersonaDet().setJEFEMAIL(rs.getString("JEFEMAIL"));
                per.getPersonaDet().setACTPER(rs.getString("ACTIVIDADPER"));
                per.getPersonaDet().setAUTOPER(rs.getString("TIENEAUTO"));
                per.getPersonaDet().setMARCAUT(rs.getString("MARCAAUTO"));
                per.getPersonaDet().setPLACAUT(rs.getString("PLACAAUTO"));
                per.getPersonaDet().setGRAPER(rs.getString("GRADOPER"));
                per.getPersonaDet().setUNIPER(rs.getString("UNIPER"));
                per.getPersonaDet().setESPPER(rs.getString("ESPPER"));
                per.getPersonaDet().setPROACA1(rs.getString("PROACA1"));
                per.getPersonaDet().setDURACA1(rs.getString("DURACA1"));
                per.getPersonaDet().setINSACA1(rs.getString("INSACA1"));
                per.getPersonaDet().setPROACA2(rs.getString("PROACA2"));
                per.getPersonaDet().setDURACA2(rs.getString("DURACA2"));
                per.getPersonaDet().setINSACA2(rs.getString("INSACA2"));
                per.getPersonaDet().setPROACA3(rs.getString("PROACA3"));
                per.getPersonaDet().setDURACA3(rs.getString("DURACA3"));
                per.getPersonaDet().setINSACA3(rs.getString("INSACA3"));
                per.getPersonaDet().setSEGURO(rs.getString("SEGURO"));
                per.getPersonaDet().setTELFSEGURO(rs.getString("TELFSEGURO"));
                per.getPersonaDet().setEMERNOMB(rs.getString("EMERGENCIA"));
                per.getPersonaDet().setEMERTELF(rs.getString("EMERGENCIATELF"));
                per.getPersonaDet().setESTENT(rs.getString("ESTADO"));
                em.setNOMBEMP(rs.getString("EMPRESA"));
                per.setEmpresaM(em);
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
    public void retirarParticipante(String codPer) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA SET TIPOPER = 'RE' WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codPer);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void restablecerParticipante(String codPer) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA SET TIPOPER = 'ES' WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codPer);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }

    }

    @Override
    public List<PersonaM> listarPartiNoAdmitidos(String CODPROG) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<PersonaM> lista;
        String sql;
        try {
            sql = "SELECT * FROM VW_PARTICIPANTE WHERE (PROCESO LIKE 'A' OR PROCESO LIKE 'I') AND ESTADO = '0' AND PROGRAMA = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CODPROG);
            rs = ps.executeQuery();
            lista = new ArrayList();
            PersonaM per;
            EmpresaM em;
            while (rs.next()) {
                per = new PersonaM();
                em = new EmpresaM();
                per.setCodPer(rs.getString("CODIGO"));
                per.setDniPer(rs.getString("DNI"));
                per.setNomPer(rs.getString("NOMBRE"));
                per.setApePer(rs.getString("APELLIDO"));
                per.setEmailPer(rs.getString("EMAIL"));
                per.setCelPer(rs.getString("CELULAR"));
                per.setDirPer(rs.getString("DIRECCION"));
                per.setFecNac(rs.getString("NACIMIENTO"));
                per.setEmpresa(rs.getString("CODEMPRESA"));
                per.setLugNac(rs.getString("LUGARNACIMIENTO"));
                per.setEstCivil(rs.getString("ESTADOCIVIL"));
                per.setTipoPer(rs.getString("TIPOPER"));
                per.getPersonaDet().setCODPERDET(rs.getString("CODIGODET"));
                per.getPersonaDet().setCONYUGE(rs.getString("CONYUGE"));
                per.getPersonaDet().setPROFCONY(rs.getString("CONYUGEPROF"));
                per.getPersonaDet().setTELFTRAB(rs.getString("TELEFTRAB"));
                per.getPersonaDet().setEMAILTRAB(rs.getString("EMAILTRAB"));
                per.getPersonaDet().setAREATRAB(rs.getString("AREATRAB"));
                per.getPersonaDet().setYEARPUESTO(rs.getString("YEARPUESTO"));
                per.getPersonaDet().setNUMPERCARGO(rs.getString("NUMPERCARGO"));
                per.getPersonaDet().setJEFEMP(rs.getString("JEFE"));
                per.getPersonaDet().setJEFCARGO(rs.getString("JEFECARGO"));
                per.getPersonaDet().setJEFTELF(rs.getString("JEFETELF"));
                per.getPersonaDet().setJEFEMAIL(rs.getString("JEFEMAIL"));
                per.getPersonaDet().setACTPER(rs.getString("ACTIVIDADPER"));
                per.getPersonaDet().setAUTOPER(rs.getString("TIENEAUTO"));
                per.getPersonaDet().setMARCAUT(rs.getString("MARCAAUTO"));
                per.getPersonaDet().setPLACAUT(rs.getString("PLACAAUTO"));
                per.getPersonaDet().setGRAPER(rs.getString("GRADOPER"));
                per.getPersonaDet().setUNIPER(rs.getString("UNIPER"));
                per.getPersonaDet().setESPPER(rs.getString("ESPPER"));
                per.getPersonaDet().setPROACA1(rs.getString("PROACA1"));
                per.getPersonaDet().setDURACA1(rs.getString("DURACA1"));
                per.getPersonaDet().setINSACA1(rs.getString("INSACA1"));
                per.getPersonaDet().setPROACA2(rs.getString("PROACA2"));
                per.getPersonaDet().setDURACA2(rs.getString("DURACA2"));
                per.getPersonaDet().setINSACA2(rs.getString("INSACA2"));
                per.getPersonaDet().setPROACA3(rs.getString("PROACA3"));
                per.getPersonaDet().setDURACA3(rs.getString("DURACA3"));
                per.getPersonaDet().setINSACA3(rs.getString("INSACA3"));
                per.getPersonaDet().setSEGURO(rs.getString("SEGURO"));
                per.getPersonaDet().setTELFSEGURO(rs.getString("TELFSEGURO"));
                per.getPersonaDet().setEMERNOMB(rs.getString("EMERGENCIA"));
                per.getPersonaDet().setEMERTELF(rs.getString("EMERGENCIATELF"));
                per.getPersonaDet().setESTENT(rs.getString("ESTADO"));
                em.setNOMBEMP(rs.getString("EMPRESA"));
                per.setEmpresaM(em);
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
    public void listarTipoPrograma(PersonaDetM per, String CODPROG) throws Exception {
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "select tipoprog.CODTIPPG,programa.codemp from prog_det inner join programa on prog_det.codprog = programa.codprog\n"
                    + "inner join tipoprog on programa.codtippg = tipoprog.CODTIPPG where prog_det.CODDETPROG = ? ";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODPROG);
            rs = ps.executeQuery();
            while (rs.next()) {
                per.setTipoProg(rs.getString("CODTIPPG"));
                per.setEMPRESA(rs.getString("CODEMP"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public PersonaM getDatosParticipante(String CodPersona) throws Exception {
        this.Conexion();
        ResultSet rs;
        PersonaM per;
        String sql;
        try {
            sql = "SELECT * FROM VW_PARTICIPANTE WHERE codigo like ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CodPersona);
            rs = ps.executeQuery();
            per = new PersonaM();
            if (rs.next()) {
                per.setCodPer(rs.getString("CODIGO"));
                per.setDniPer(rs.getString("DNI"));
                per.setNomPer(rs.getString("NOMBRE"));
                per.setApePer(rs.getString("APELLIDO"));
                per.setEmailPer(rs.getString("EMAIL"));
                per.setCelPer(rs.getString("CELULAR"));
                per.setDirPer(rs.getString("DIRECCION"));
                per.setFecNac(rs.getString("NACIMIENTO"));
                per.setEmpresa(rs.getString("CODEMPRESA"));
                per.setLugNac(rs.getString("LUGARNACIMIENTO"));
                per.setEstCivil(rs.getString("ESTADOCIVIL"));
                per.getPersonaDet().setCODPERDET(rs.getString("CODIGODET"));
                per.getPersonaDet().setCONYUGE(rs.getString("CONYUGE"));
                per.getPersonaDet().setPROFCONY(rs.getString("CONYUGEPROF"));
                per.getPersonaDet().setTELFTRAB(rs.getString("TELEFTRAB"));
                per.getPersonaDet().setEMAILTRAB(rs.getString("EMAILTRAB"));
                per.getPersonaDet().setAREATRAB(rs.getString("AREATRAB"));
                per.getPersonaDet().setYEARPUESTO(rs.getString("YEARPUESTO"));
                per.getPersonaDet().setNUMPERCARGO(rs.getString("NUMPERCARGO"));
                per.getPersonaDet().setJEFEMP(rs.getString("JEFE"));
                per.getPersonaDet().setJEFCARGO(rs.getString("JEFECARGO"));
                per.getPersonaDet().setJEFTELF(rs.getString("JEFETELF"));
                per.getPersonaDet().setJEFEMAIL(rs.getString("JEFEMAIL"));
                per.getPersonaDet().setACTPER(rs.getString("ACTIVIDADPER"));
                per.getPersonaDet().setAUTOPER(rs.getString("TIENEAUTO"));
                per.getPersonaDet().setMARCAUT(rs.getString("MARCAAUTO"));
                per.getPersonaDet().setPLACAUT(rs.getString("PLACAAUTO"));
                per.getPersonaDet().setGRAPER(rs.getString("GRADOPER"));
                per.getPersonaDet().setUNIPER(rs.getString("UNIPER"));
                per.getPersonaDet().setESPPER(rs.getString("ESPPER"));
                per.getPersonaDet().setPROACA1(rs.getString("PROACA1"));
                per.getPersonaDet().setDURACA1(rs.getString("DURACA1"));
                per.getPersonaDet().setINSACA1(rs.getString("INSACA1"));
                per.getPersonaDet().setPROACA2(rs.getString("PROACA2"));
                per.getPersonaDet().setDURACA2(rs.getString("DURACA2"));
                per.getPersonaDet().setINSACA2(rs.getString("INSACA2"));
                per.getPersonaDet().setPROACA3(rs.getString("PROACA3"));
                per.getPersonaDet().setDURACA3(rs.getString("DURACA3"));
                per.getPersonaDet().setINSACA3(rs.getString("INSACA3"));
                per.getPersonaDet().setSEGURO(rs.getString("SEGURO"));
                per.getPersonaDet().setTELFSEGURO(rs.getString("TELFSEGURO"));
                per.getPersonaDet().setEMERNOMB(rs.getString("EMERGENCIA"));
                per.getPersonaDet().setEMERTELF(rs.getString("EMERGENCIATELF"));
                per.getPersonaDet().setESTENT(rs.getString("ESTADO"));
                per.getEmpresaM().setNOMBEMP(rs.getString("EMPRESA"));
            }
            return per;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
