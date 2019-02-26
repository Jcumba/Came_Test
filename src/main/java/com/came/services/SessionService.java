package com.came.services;

import com.came.model.PersonaM;
import lombok.Data;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import javax.faces.context.FacesContext;

@Data
@Named(value = "sessionService")
@SessionScoped
public class SessionService implements Serializable {

    /* Variables Generales */
    public static final String Repositorio = "RepositorioCAME";
    public static final String Proyecto = "Came";
    public static final String rutaDoc = "/home/glassfish4/glassfish/domains/domain1/docroot";
//    public static final String rutaDoc = "C:/Servers/glassfish4/glassfish/domains/domain1/docroot";
    public static final String Dominio = "http://developers.vallegrande.edu.pe";
//    public static final String Dominio = "http://localhost:9090";
//    public static final String Dominio = "http://35.231.16.77";

    /* SERVICIOS */
    public static String quitarUltimaCarpeta(String ruta) {
        String[] lista = ruta.split("/");
        int nc = lista[lista.length - 1].length() + 1;
        return ruta.substring(0, ruta.length() - nc);
    }

    public String getDominio() {
        return Dominio;
    }

    public String getDominioBib() {
        String link = getLinkProyecto();
        String[] lista = link.split("/");
        int nc = lista[lista.length - 1].length() + 1;
        link = link.substring(0, link.length() - nc);
        lista = link.split("/");
        nc = lista[lista.length - 1].length() + 1;
        return link.substring(0, link.length() - nc);
    }

    public static String arrayToString(String[] cadena) {
        String save = Arrays.toString(cadena).substring(1);
        return save.substring(0, save.length() - 1).replace(" ", "");
    }

    public static String[] stringToArray(String cadena) {
        return cadena != null ? cadena.split(",") : null;
    }

    /* VALIDACIONES - JAVA */
    public static boolean _isLoginStart() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        return persona != null;
    }

    public static boolean _isEstudiante() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("ES");
        } else {
            return false;
        }
    }

    public static boolean _isRetirado() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("RE");
        } else {
            return false;
        }
    }

    public static boolean _isAdministrador() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("AD");
        } else {
            return false;
        }
    }

    public static boolean _isDirectores() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("DI");
        } else {
            return false;
        }
    }

    public static boolean _isDirectorPrograma() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("DP");
        } else {
            return false;
        }
    }

    public static boolean _isOperaciones() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("OP");
        } else {
            return false;
        }
    }

    public static boolean _isProfesor() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("PR");
        } else {
            return false;
        }
    }

    public static boolean _isMonitor() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("MO");
        } else {
            return false;
        }
    }

    public static boolean _isCoach() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("CO");
        } else {
            return false;
        }
    }

    public static boolean _isEntrevistador() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("EN");
        } else {
            return false;
        }
    }

    public static boolean _isAdministracion() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("AM");
        } else {
            return false;
        }
    }

    public static boolean _isPromocion() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("PM");
        } else {
            return false;
        }
    }

    public static boolean _isTypeNotNull() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null;
        } else {
            return false;
        }
    }

    /* VALIDACIONES - XHTML */
    public boolean isEstudiante() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("ES");
        } else {
            return false;
        }
    }

    public boolean isRetirado() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("RE");
        } else {
            return false;
        }
    }

    public boolean isAdministrador() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("AD");
        } else {
            return false;
        }
    }

    public boolean isDirectores() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("DI");
        } else {
            return false;
        }
    }

    public boolean isDirectorPrograma() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("DP");
        } else {
            return false;
        }
    }

    public boolean isOperaciones() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("OP");
        } else {
            return false;
        }
    }

    public boolean isProfesor() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("PR");
        } else {
            return false;
        }
    }

    public boolean isMonitor() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("MO");
        } else {
            return false;
        }
    }

    public boolean isCoach() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("CO");
        } else {
            return false;
        }
    }

    public boolean isEntrevistador() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("EN");
        } else {
            return false;
        }
    }

    public boolean isAdministracion() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("AM");
        } else {
            return false;
        }
    }

    public boolean isPromocion() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getTipoPer() != null && persona.getTipoPer().equals("PM");
        } else {
            return false;
        }
    }

    /* SESIONES */
    public static String getCodEstuadiante() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        return persona != null ? persona.getCelPer() : null;
    }

    public static String getAsigEstuadiante() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        return persona != null ? persona.getAsignacion().getCODASIG() : null;
    }

    public static PersonaM getPersona() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        return persona != null ? persona : null;
    }

    public static String getCodigoPersona() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getCodPer();
        } else {
            return null;
        }
    }

    public static String getDNIPersona() {
        PersonaM persona = (PersonaM) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");
        if (persona != null) {
            return persona.getDniPer();
        } else {
            return null;
        }
    }

    /* Repositorio */
    public String getLinkRepositorio() {
        return Dominio + "/" + Repositorio;
    }

    public String getLinkRepositorioImg() {
        return Dominio + "/" + Repositorio + "/img";
    }

    public String getRutaDoc() {
        return rutaDoc;
    }

    public String getLinkProyecto() {
        return Dominio + "/" + Proyecto + "/faces";
    }

    public String getLinkProyectoPretty() {
        return Dominio + "/" + Proyecto;
    }

}
