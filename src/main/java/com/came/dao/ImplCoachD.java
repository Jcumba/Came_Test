package com.came.dao;

import com.came.interfaces.CoachI;
import com.came.model.CoachM;
import com.came.model.PersonaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImplCoachD extends Dao implements CoachI {

    @Override
    public List<CoachM> listaPrograma() throws Exception {
        this.Conexion();
        try {
            ArrayList<CoachM> lst = new ArrayList<>();
            ResultSet rs;

            Statement ps = this.getCn().createStatement();
            rs = ps.executeQuery("SELECT * FROM VW_CBGEN_PROG");
            while (rs.next()) {
                CoachM coach = new CoachM();
                coach.setCODDETPROG(rs.getString("CODDETPROG"));
                coach.setPROGRAMA(rs.getString("PROGRAMA"));
                lst.add(coach);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<PersonaM> listaAlum(String CODCOACH, String CODDETPROG) throws Exception {
        this.Conexion();
        try {
            ArrayList<PersonaM> lst = new ArrayList<>();
            ResultSet rs;
            String sql = "SELECT ASIGCOACH.CODASICOA,ASIGCOACH.CODCOACH,ASIGCOACH.CODDETPROG,CONCAT(CONCAT(PERSONA.APEPER,' '),PERSONA.NOMPER) AS NOMBRE,ASIGCOACH.CODPER FROM ASIGCOACH INNER JOIN PERSONA ON persona.codper = ASIGCOACH.CODPER WHERE CODDETPROG LIKE ? AND CODCOACH LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODDETPROG);
            ps.setString(2, CODCOACH);
            rs = ps.executeQuery();
            while (rs.next()) {
                PersonaM alu = new PersonaM();
                alu.setNomPer(rs.getString("NOMBRE"));
                alu.setCodPer(rs.getString("CODASICOA"));
                lst.add(alu);
            }
            return lst;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void addCoach(CoachM coach) throws Exception {
        try {
            this.Conexion();
            String sql = "INSERT INTO COACHING (HORINI,HORFIN,LUGAR,DESCRI,CODASICOA,FECHA) VALUES (?,?,?,?,?,TO_DATE(?,'dd/mm/yyyy'))";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, coach.getHORINI().toUpperCase());
            ps.setString(2, coach.getHORFIN().toUpperCase());
            ps.setString(3, coach.getLUGAR());
            ps.setString(4, coach.getDESCRI());
            ps.setString(5, coach.getCODASICOA());
            ps.setString(6, coach.getFECHA());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<CoachM> listarCoachines(String CODCOACH, String CODDETPROG) throws Exception {
        this.Conexion();
        try {
            ArrayList<CoachM> lista = new ArrayList<>();
            ResultSet rs;
            String sql = "SELECT * FROM VW_COACH WHERE CODCOACH LIKE ? AND CODDETPROG LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, CODCOACH);
            ps.setString(2, CODDETPROG);
            rs = ps.executeQuery();
            PersonaM post;
            CoachM coach;
            while (rs.next()) {
                post = new PersonaM();
                coach = new CoachM();
                coach.setHORINI(rs.getString("HORINI"));
                coach.setHORFIN(rs.getString("HORFIN"));
                coach.setFECHA(rs.getString("FECHA"));
                coach.setDESCRI(rs.getString("DESCRI"));
                post.setNomPer(rs.getString("NOMBRE"));
                coach.setPersona(post);
                lista.add(coach);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public String contarSesionesCoaching(String codPer, String codDetProg) throws Exception {
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "SELECT COUNT(COACHING.CODCOA) AS CONTADOR FROM COACHING INNER JOIN ASIGCOACH ON coaching.codasicoa = ASIGCOACH.CODASICOA WHERE ASIGCOACH.CODPER LIKE ? AND ASIGCOACH.CODDETPROG LIKE ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codPer);
            ps.setString(2, codDetProg);
            rs = ps.executeQuery();
            rs.next();
            return rs.getString("CONTADOR");
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    @Override
    public List<CoachM> listarSesionesCoaching(String codPer) throws Exception {
        this.Conexion();
        try {
            ArrayList<CoachM> lista = new ArrayList<>();
            ResultSet rs;
            String sql = "SELECT FECHA,COACH FROM VW_LIST_SESCOUCH WHERE CODPER = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, codPer);
            rs = ps.executeQuery();
            CoachM coach;
            while (rs.next()) {
                coach = new CoachM();
                coach.setFECHA(rs.getString("FECHA"));
                coach.setCOACH(rs.getString("COACH"));
                lista.add(coach);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
