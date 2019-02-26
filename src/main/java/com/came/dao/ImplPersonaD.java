package com.came.dao;

import com.came.interfaces.PersonaI;
import com.came.model.*;
import com.came.services.SessionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplPersonaD extends Dao implements PersonaI {

    @Override
    public List<PersonaM> listarPersona() throws SQLException {
        ResultSet rs;
        List<PersonaM> lista;
        this.Conexion();
        try {
            String sql = "SELECT * FROM VW_PERSONA WHERE ESTPER LIKE 'A'";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            PersonaM pM;
            while (rs.next()) {
                pM = new PersonaM();
                pM.setCodPer(rs.getString("CODPER"));
                pM.setNomPer(rs.getString("NOMBRE"));
                pM.setApePer(rs.getString("APELLIDO"));
                pM.setDniPer(rs.getString("DNIPER"));
                pM.setEmailPer(rs.getString("EMAILPER"));
                pM.setSexPer(rs.getString("SEXO"));
                pM.setUserPer(rs.getString("USERPER"));
                pM.setCelPer(rs.getString("CELPER"));
                pM.setDirPer(rs.getString("DIRPER"));
                pM.setRolesPer(SessionService.stringToArray(rs.getString("CODTP")));
                pM.setCodUbi(rs.getString("UBIGEO"));
                lista.add(pM);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //INSERTAR O AGREGAR UNA NUEVA PERSONA
    @Override
    public void agregarPersona(PersonaM persona) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO PERSONA (NomPer,ApePer,DniPer,EmailPer,SexPer,CelPer,DirPer,TipoPer,EstPer,UserPer,PassPer,CodUbi) "
                    + "VALUES(UPPER(?),UPPER(?),?,?,?,?,?,?,?,TRIM(?),?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, persona.getNomPer());
            ps.setString(2, persona.getApePer());
            ps.setString(3, persona.getDniPer());
            ps.setString(4, persona.getEmailPer());
            ps.setString(5, persona.getSexPer());
            ps.setString(6, persona.getCelPer());
            ps.setString(7, persona.getDirPer());
            ps.setString(8, SessionService.arrayToString(persona.getRolesPer()));
            ps.setString(9, "A");
            ps.setString(10, persona.getUserPer());
            ps.setString(11, persona.getPassPer());
            ps.setString(12, persona.getCodUbi());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<String> queryAutoCompleteUbigeo(String a) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        List<String> lista;
        try {
            String sql = "SELECT UBIGEO FROM VW_UBIGEO WHERE UBIGEO LIKE UPPER(?)";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + a + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString("UBIGEO"));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public String leerUbigeo(String a) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODUBI FROM VW_UBIGEO WHERE UBIGEO = ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODUBI");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<String> queryAutoCompleteDirector(String a) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        List<String> lista;
        try {
            String sql = "SELECT NAMES FROM VW_PERSONA WHERE UPPER(NAMES) LIKE UPPER(?) AND CODTP LIKE '%DP%'";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + a + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString("NAMES"));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public String leerDirector(String a) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODPER FROM VW_PERSONA WHERE NAMES = ?";
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

    public String leerVW_PERSONA(String a) throws Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODPER FROM VW_PERSONA where NAMES like ?";
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
    public List<PersonaM> listarAlumno() throws SQLException {
        ResultSet rs;
        List<PersonaM> lista;
        this.Conexion();
        try {
            String sql = "SELECT * FROM PERSONA WHERE TIPOPER LIKE '%ES%'";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setCodPer(rs.getString("CODPER"));
                per.setDniPer(rs.getString("DNIPER"));
                per.setNomPer(rs.getString("NOMPER"));
                per.setCelPer(rs.getString("CELPER"));
                lista.add(per);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<PersonaM> listarAsigCoach(String CodDetPrograma) throws SQLException {
        ResultSet rs;
        List<PersonaM> lista;
        this.Conexion();
        try {
            String sql = "SELECT * FROM VW_PERSONA_COACH WHERE CODDETPROG LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CodDetPrograma);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setCodPer(rs.getString("CODPER"));
                per.setDniPer(rs.getString("DNIPER"));
                per.setNomPer(rs.getString("NOMPER"));
                per.setApePer(rs.getString("NOMPRO"));
                per.setEmailPer(rs.getString("EMAILPER"));
                per.setCelPer(rs.getString("CELPER"));
                lista.add(per);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<PersonaM> listarAsigMonitor(String CodDetPrograma) throws SQLException {
        ResultSet rs;
        List<PersonaM> lista;
        this.Conexion();
        try {
            String sql = "SELECT * FROM VW_PERSONA_MONITOR WHERE CODDETPROG LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CodDetPrograma);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setCodPer(rs.getString("CODPER"));
                per.setDniPer(rs.getString("DNIPER"));
                per.setNomPer(rs.getString("NOMPER"));
                per.setApePer(rs.getString("NOMPRO"));
                per.setEmailPer(rs.getString("EMAILPER"));
                per.setCelPer(rs.getString("CELPER"));
                lista.add(per);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    // LISTAR AUTOCOMPLETE PROFESOR
    public List<String> ListProfesorAutoComplete(String a) throws Exception {
        List<String> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "Select CONCAT(CONCAT(NOMPER, ' / '), APEPER) AS NOMPERS from Persona Where Tipoper LIKE '%PR%' and estper = 'A' AND (UPPER(NOMPER)like UPPER(?) or UPPER(APEPER) LIKE UPPER(?))";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + a + "%");
            ps.setString(2, "%" + a + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString("NOMPERS"));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<String> ListCoachAutoComplete(String a) throws Exception {
        List<String> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT NAMES FROM VW_PERSONA WHERE CODTP LIKE '%CO%' AND ESTPER LIKE 'A' AND (UPPER(NOMBRE)like UPPER(?) OR APELLIDO LIKE UPPER(?))";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + a + "%");
            ps.setString(2, "%" + a + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString("NAMES"));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<String> ListMonitorAutoComplete(String a) throws Exception {
        List<String> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT NAMES FROM VW_PERSONA WHERE CODTP LIKE '%MO%' AND ESTPER LIKE 'A' AND (UPPER(NOMBRE)like UPPER(?) OR APELLIDO LIKE UPPER(?))";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + a + "%");
            ps.setString(2, "%" + a + "%");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString("NAMES"));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public boolean validarUsuario(String usuario, String codigo) throws SQLException {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "Select COUNT(*) as cantidad  from PERSONA WHERE  USERPER like TRIM(?) and CODPER not like ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, usuario);
            ps.setString(2, codigo);
            rs = ps.executeQuery();
            int num = 0;
            if (rs.next()) {
                num = rs.getInt("cantidad");
            }
            return num == 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public boolean validarPassword(String usuario, String password) throws SQLException {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "Select count(*) as cantidad from PERSONA where CODPER like ? and PASSPER like ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            rs = ps.executeQuery();
            int num = 0;
            if (rs.next()) {
                num = rs.getInt("cantidad");
            }
            return num != 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void asignarAlumno(String CODDETPROG, String CODPER) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO ASIGNACION(CODPER,CODDETPROG) VALUES(?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODPER);
            ps.setString(2, CODDETPROG);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void asignarCoach(String CodigoCoach, List<PersonaM> list, String CodDetPrograma) throws Exception {
        this.Conexion();
        try {
            String sql;
            PreparedStatement ps;
            for (PersonaM item : list) {
                limpiarAsigCoach(item.getCodPer());
                sql = "INSERT INTO ASIGCOACH (CODCOACH,CODPER,CODDETPROG) VALUES (?,?,?)";
                ps = this.getCn().prepareStatement(sql);
                ps.setString(1, CodigoCoach);
                ps.setString(2, item.getCodPer());
                ps.setString(3, CodDetPrograma);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void asignarMonitor(String CodigoMonitor, List<PersonaM> list, String CodDetPrograma) throws Exception {
        this.Conexion();
        try {
            String sql;
            PreparedStatement ps;
            for (PersonaM item : list) {
                limpiarAsigMonitor(item.getCodPer());
                sql = "INSERT INTO ASIGMONITOR (CODMONITOR,CODPER,CODDETPROG) VALUES (?,?,?)";
                ps = this.getCn().prepareStatement(sql);
                ps.setString(1, CodigoMonitor);
                ps.setString(2, item.getCodPer());
                ps.setString(3, CodDetPrograma);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //Inactiva todos los registros activos de Coach del Participante
    public void limpiarAsigCoach(String CodPersona) throws SQLException {
        try {
            String sql = "UPDATE ASIGCOACH SET ESTASICOA = 'I' WHERE CODPER LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CodPersona);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    //Inactiva todos los registros activos de Monitor del Participante
    public void limpiarAsigMonitor(String CodPersona) throws SQLException {
        try {
            String sql = "UPDATE ASIGMONITOR SET ESTASIMON = 'I' WHERE CODPER LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CodPersona);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    //ACTUALIZAR DATOS DE LA PERSONA
    public void updatePersona(PersonaM per) throws SQLException {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA SET NOMPER=?, APEPER=?, DNIPER=?, EMAILPER=?, SEXPER=?, CELPER=?, DIRPER=?, TIPOPER=?, CODUBI=?, USERPER = TRIM(?) WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, per.getNomPer());
            ps.setString(2, per.getApePer());
            ps.setString(3, per.getDniPer());
            ps.setString(4, per.getEmailPer());
            ps.setString(5, per.getSexPer());
            ps.setString(6, per.getCelPer());
            ps.setString(7, per.getDirPer());
            ps.setString(8, SessionService.arrayToString(per.getRolesPer()));
            ps.setString(9, per.getCodUbi());
            ps.setString(10, per.getUserPer());
            ps.setString(11, per.getCodPer());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //ACTUALIZAR CONTRASEÑA DE LA PERSONA
    public void updatePassword(PersonaM per) throws SQLException {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA SET PASSPER =? WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, per.getPassPer());
            ps.setString(2, per.getCodPer());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }


    //ELIMINAR UNA PERSONA 
    public void deletePersona(String per) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA set ESTPER = 'I' where CODPER = ? ";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, per);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //LOGIN
    public PersonaM iniciarSesion(String usu, String psw) throws Exception {
        this.Conexion();
        PersonaM user = null;
        AsignacionM asignacion;
        ResultSet rs;
        try {
            String sql = "select * from VW_LOGIN where USERPER like ? and PASSPER like ? ORDER BY CODASIG DESC";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, usu);
            ps.setString(2, psw);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new PersonaM();
                asignacion = new AsignacionM();
                user.setCodPer(rs.getString("CODPER"));
                user.setNomPer(rs.getString("NOMPER"));
                user.setDniPer(rs.getString("DNIPER"));
                user.setApePer(rs.getString("APEPER"));
                user.setRolesPer(SessionService.stringToArray(rs.getString("TIPOPER")));
                user.setTipoPer(rs.getString("TIPOPER").length() > 2 ? null : rs.getString("TIPOPER"));
                user.setInasistencias(rs.getString("Inasistencias"));
                user.setTardanzas(rs.getString("Tardanza"));
                user.setPromedioGeneral(rs.getString("Promedio"));
                user.setUserPer(usu);
                user.setPassPer(psw);
                asignacion.setCODASIG(rs.getString("codasig"));
                asignacion.setCODDETPROG(rs.getString("coddetprog"));
                user.setAsignacion(asignacion);
            }
            return user;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //Listar las fases de los estudiantes
    public List<FaseM> fasesEstudiante(String CodAsignacion, String CodDetPrograma) throws Exception {
        this.Conexion();
        ResultSet rs;
        List<FaseM> lista;
        try {
            String sql = "SELECT * FROM VW_NUM_FASES WHERE CODASIG = ? AND CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CodAsignacion);
            ps.setString(2, CodDetPrograma);
            rs = ps.executeQuery();
            lista = new ArrayList();
            FaseM fase;
            while (rs.next()) {
                fase = new FaseM();
                fase.setNOMFASE(rs.getString("NOMFASE"));
                fase.setCODFASE(rs.getString("CODFASE"));
                fase.setPROMFASE(rs.getString("PROMFASE"));
                fase.setFormula(detalleNotaFase(CodAsignacion, fase.getCODFASE()));
                lista.add(fase);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public FormulaM detalleNotaFase(String CodAsig, String CodFase) throws Exception {
        FormulaM formula = null;
        ResultSet rs;
        try {
            String sql = "SELECT * FROM VW_DET_NOTA WHERE CODASIG = ? AND CODFASE = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CodAsig);
            ps.setString(2, CodFase);
            rs = ps.executeQuery();
            if (rs.next()) {
                formula = new FormulaM();
                formula.setPESOCASO(rs.getString("peso_caso"));
                formula.setPROMCASO(rs.getString("prom_caso"));
                formula.setPESOPART(rs.getString("peso_part"));
                formula.setPROMPART(rs.getString("prom_part"));
                formula.setPESOTRAB(rs.getString("peso_trab"));
                formula.setPROMTRAB(rs.getString("prom_trab"));
                formula.setPESOPARC(rs.getString("peso_parc"));
                formula.setPROMPARC(rs.getString("prom_parc"));
                formula.setPESOFINA(rs.getString("peso_fina"));
                formula.setPROMFINA(rs.getString("prom_fina"));
            }
            return formula;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<PersonaM> listarBirthday(String day) throws SQLException {
        List<PersonaM> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_HAPPY_BIRTHDAY WHERE ESTADO LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, day);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setNomPer(rs.getString("NOMBRE"));
                per.setImgPer(rs.getString("IMAGEN"));
                per.setEdadPer(rs.getString("EDAD"));
                list.add(per);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return list;
    }
    
    @Override
    public List<PersonaM> listarBirthday() throws SQLException {
        List<PersonaM> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT NOMBRE,IMAGEN,FECHPER,EDAD FROM VW_HAPPY_BIRTHDAY WHERE ESTADO LIKE '3' OR ESTADO LIKE '4' OR ESTADO LIKE '5' OR ESTADO LIKE '6'";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setNomPer(rs.getString("NOMBRE"));
                per.setImgPer(rs.getString("IMAGEN"));
                per.setEdadPer(rs.getString("EDAD"));
                per.setFecNac(rs.getString("FECHPER"));
                list.add(per);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return list;
    }

    @Override
    public List<PersonaM> listarBirthdayProgram(String codDetProg, String day) throws SQLException {
        List<PersonaM> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT NOMBRE,IMAGEN,FECHPER,EDAD FROM VW_BIRTHDAY_PROGRAM WHERE CODDETPROG = ? AND ESTADO LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codDetProg);
            ps.setString(2, day);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setNomPer(rs.getString("NOMBRE"));
                per.setImgPer(rs.getString("IMAGEN"));
                per.setEdadPer(rs.getString("EDAD"));
                per.setFecNac(rs.getString("FECHPER"));
                list.add(per);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return list;
    }
    
    @Override
    public List<PersonaM> listarBirthdayProgram(String codDetProg) throws SQLException {
        List<PersonaM> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT NOMBRE,IMAGEN,FECHPER,EDAD FROM VW_BIRTHDAY_PROGRAM WHERE CODDETPROG = ? AND (ESTADO LIKE '3' OR ESTADO LIKE '4' OR ESTADO LIKE '5' OR ESTADO LIKE '6')";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codDetProg);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setNomPer(rs.getString("NOMBRE"));
                per.setImgPer(rs.getString("IMAGEN"));
                per.setEdadPer(rs.getString("EDAD"));
                per.setFecNac(rs.getString("FECHPER"));
                list.add(per);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return list;
    }

    @Override
    public void traerResultados(PersonaM persona, String codPer, String codDetProg) throws Exception {
        this.Conexion();
        ResultSet rs;
        AsignacionM asignacion;
        try {
            String sql = "SELECT INASISTENCIAS,PROMEDIO,CODASIG FROM VW_LOGIN WHERE CODPER LIKE ? AND CODDETPROG LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codPer);
            ps.setString(2, codDetProg);
            rs = ps.executeQuery();
            asignacion = new AsignacionM();
            rs.next();
            persona.setInasistencias(rs.getString("INASISTENCIAS"));
            persona.setPromedioGeneral(rs.getString("PROMEDIO"));
            asignacion.setCODASIG(rs.getString("CODASIG"));
            persona.setAsignacion(asignacion);
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<PersonaM> listarDirectorioParticipante(String codDetProg) throws SQLException {
        List<PersonaM> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT APENOM,NOMBEMP,IMGPER,CARGOPER,EMAILPER,CELPER,FECHPER FROM VW_DIRECTORIO_PART WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codDetProg);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setNomPer(rs.getString("APENOM"));
                per.getEmpresaM().setNOMBEMP(rs.getString("NOMBEMP"));
                per.setImgPer(rs.getString("IMGPER"));
                per.setCargPer(rs.getString("CARGOPER"));
                per.setEmailPer(rs.getString("EMAILPER"));
                per.setCelPer(rs.getString("CELPER"));
                per.setFecNac(rs.getString("FECHPER"));
                list.add(per);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return list;
    }
    
    @Override
    public List<PersonaM> listarDirectorioDirector(String codDetProg) throws SQLException {
        List<PersonaM> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT DIRECTOR,CARGODIRE,NOMBEMPDIRE,CELDIRE,FECHDIRE,EMAILDIRE,FOTDIREC FROM VW_DIRECTORIO_PART WHERE CODDETPROG = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codDetProg);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            PersonaM per;
            while (rs.next()) {
                per = new PersonaM();
                per.setNomPer(rs.getString("DIRECTOR"));
                per.getEmpresaM().setNOMBEMP(rs.getString("NOMBEMPDIRE"));
                per.setImgPer(rs.getString("FOTDIREC"));
                per.setCargPer(rs.getString("CARGODIRE"));
                per.setEmailPer(rs.getString("EMAILDIRE"));
                per.setCelPer(rs.getString("CELDIRE"));
                per.setFecNac(rs.getString("FECHDIRE"));
                list.add(per);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return list;
    }

    @Override
    public String buscarDniPersona(String dniPer) throws Exception {
        this.Conexion();
        try {
            ResultSet rs;
            String sql = "SELECT DNIPER FROM PERSONA WHERE DNIPER LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, dniPer);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("DNIPER");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<roles> listarInasistencias(String CodAsignacion, String CodDetPrograma) throws Exception {
        List<roles> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT nomses,fechases FROM VW_ASISTENCIAS_PARTICIPANTE WHERE asistencia LIKE '0' AND coddetprog LIKE ? AND codasig LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CodDetPrograma);
            ps.setString(2, CodAsignacion);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(new roles(
                        rs.getString("nomses"),
                        rs.getString("fechases")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public List<roles> listarTardanzas(String CodAsignacion, String CodDetPrograma) throws Exception {
        List<roles> list;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT nomses,fechases FROM VW_ASISTENCIAS_PARTICIPANTE WHERE asistencia LIKE '2' AND coddetprog LIKE ? AND codasig LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CodDetPrograma);
            ps.setString(2, CodAsignacion);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(new roles(
                        rs.getString("nomses"),
                        rs.getString("fechases")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void modificarFotoPersona(PersonaM per) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA SET IMGPER =? WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, per.getImgPer());
            ps.setString(2, per.getCodPer());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public ProgDetM listarHorario(String CodDetPrograma) throws Exception {
        ProgDetM modelo;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "Select TO_CHAR(prog_det.fechini,'DD/MM/YYYY') as fechini,TO_CHAR(prog_det.fechfin,'DD/MM/YYYY') AS fechfin,prog_det.frecdetprog,prog_det.horinifin from programa inner join prog_det on programa.codprog = prog_det.codprog where prog_det.coddetprog like ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CodDetPrograma);
            rs = ps.executeQuery();
            modelo = new ProgDetM();
            if (rs.next()) {
                modelo.setFechIni(rs.getString("fechini"));
                modelo.setFechFin(rs.getString("fechfin"));
                modelo.setFrecDetProg(SessionService.stringToArray(rs.getString("frecdetprog")));
                modelo.setHorIniFin(rs.getString("horinifin"));
            }
            return modelo;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    @Override
    public void modificarOrganigramaPersona(PersonaDetM perd) throws Exception {
        this.Conexion();
        try {
            String sql = "UPDATE PERSONA_DET SET IMGORG =? WHERE CODPER=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, perd.getIMGORG());
            ps.setString(2, perd.getCODPER());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
