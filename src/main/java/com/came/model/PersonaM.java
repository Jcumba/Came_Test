package com.came.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PersonaM implements Serializable {

    private String IniPer;
    private String CodPer;
    private String NomPer;
    private String ApePer;
    private String DniPer;
    private String ImgPer;
    private String EdadPer;
    private String EmailPer;
    private String SexPer;
    private String CelPer;
    private String DirPer;
    private String[] RolesPer;
    private String TipoPer;
    private String TelfPer;
    private String CorreoPer;
    private String RucPer;
    private String NacnlPer;
    private String EstPer;
    private String UserPer;
    private String PassPer;
    private String CodUbi;
    private String NomUbi;

    private String FecNac;
    private String CargPer;
    private String Empresa;
    private String LugNac;
    private String EstCivil;
    private String ProfPer;

    //Datos calculados
    private String Inasistencias;
    private String Tardanzas;
    private String PromedioGeneral;

    //Relaciones
    private ProgDetM ProgDetalle;
    private AsignacionM Asignacion;
    private HijoM Hijo;
    private EmpresaM EmpresaM;
    private PersonaDetM PersonaDet;

    public PersonaM() {
        ProgDetalle = new ProgDetM();
        Asignacion = new AsignacionM();
        EmpresaM = new EmpresaM();
        PersonaDet = new PersonaDetM();
    }

    public String getTipoPerPintado() {
        if (TipoPer != null) {
            switch (TipoPer) {
                case "RE":
                    return "Retirado";
                case "ES":
                    return "Participante";
                case "AD":
                    return "Administrador";
                case "DI":
                    return "Directores";
                case "DP":
                    return "Director de Programa";
                case "OP":
                    return "Operaciones";
                case "PR":
                    return "Profesor";
                case "MO":
                    return "Monitor";
                case "CO":
                    return "Coach";
                case "EN":
                    return "Entrevistador";
                case "AM":
                    return "Administración";
                case "PM":
                    return "Promoción";
                default:
                    return null;
            }
        }
        return null;
    }

    public String getDescTipoPer(String tipo) {
        switch (tipo) {
            case "RE":
                return "Retirado";
            case "ES":
                return "Participante";
            case "AD":
                return "Administrador";
            case "DI":
                return "Director General";
            case "DP":
                return "Director de Programa";
            case "OP":
                return "Operador";
            case "PR":
                return "Profesor";
            case "MO":
                return "Monitor";
            case "CO":
                return "Coach";
            case "EN":
                return "Entrevistador";
            case "AM":
                return "Administración";
            case "PM":
                return "Promoción";
            default:
                return null;
        }
    }

    public List<roles> getListRoles() {
        List<roles> lista = new ArrayList();
        if (RolesPer != null) {
            for (String string : RolesPer) {
                lista.add(new roles(string, getDescTipoPer(string)));
            }
        }
        return lista;
    }

}
