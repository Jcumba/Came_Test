package com.came.controller;

import com.came.dao.ImplEmpresaD;
import com.came.dao.ImplInscripcionD;
import com.came.dao.ImplParticipanteD;
import com.came.dao.ImplParticipanteDetD;
import com.came.dao.ImplPersonaD;
import com.came.dao.ImplProgDetD;
import com.came.model.EmpresaM;
import com.came.model.HijoM;
import com.came.model.InscripcionM;
import com.came.model.PersonaDetM;
import com.came.model.PersonaM;
import com.came.model.ProgDetM;
import com.came.model.ProgramaM;
import com.came.services.GenerateReport;
import com.came.services.SessionService;
import com.came.services.UploadFile;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Data;
import org.primefaces.PrimeFaces;
import org.primefaces.model.UploadedFile;

@Named(value = "participanteC")
@SessionScoped
@Data
public class ParticipanteC implements Serializable {

    private EmpresaM emp = new EmpresaM();
    private ImplEmpresaD empd = new ImplEmpresaD();
    private PersonaM par = new PersonaM();
    private ImplParticipanteD pard = new ImplParticipanteD();
    private PersonaDetM pardet = new PersonaDetM();
    private ImplParticipanteDetD pardetd = new ImplParticipanteDetD();
    private InscripcionM ins = new InscripcionM();
    private ImplInscripcionD insd = new ImplInscripcionD();

    private UploadedFile foto;
    private UploadedFile organigrama;
    private UploadedFile cv;
    private int numhijos;
    private List<ProgramaM> listCbProg;
    private String Celular, Telefono, Dni, Ruc;

    //    Lista de participantes por inscribir
    private List<PersonaM> listParticipantes;
    //    Lista de participante inscritos
    private List<PersonaM> listParticipantesI;

    //lista de participantes que no fueron admitidos en el proceso de entrevista
    private List<PersonaM> listParticipantesNoAd;

    private List<ProgDetM> listaCbGen;

    //Lista de Combo Box para Postulante
    private List<ProgDetM> listaCbGenPostulante;

    private PersonaDetM listabloqins = new PersonaDetM();
    private PersonaDetM listatipoProg = new PersonaDetM();
    //    Variable para los programas en los participantes por inscribir
    private String CODPROG;
    //    Variable para los programas en los participantes inscritos
    private String CODPROGI, CODPROGNOAD;

    private String CODPER;
    private boolean verificar;
    private PersonaM selectPer;

    //Matrícula
    private List<PersonaM> lstselectPar;

    @PostConstruct
    public void init() {
        try {
            verificar = false;
            listarCbGeneracion();
            listarCbGeneracionPostulante();
            listarPostulantes();
        } catch (Exception ex) {
            Logger.getLogger(ParticipanteC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void descargarPDFInscripcion() throws Exception {
        GenerateReport report;
        try {
            report = new GenerateReport();
            Map<String, Object> parameters = new HashMap();
            parameters.put("CodPerFicIns", ins.getCODPER());
            report.exportFichaInscripcionInicial(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarCbGeneracion() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            listaCbGen = dao.listarCbGeneracion();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarCbGeneracionPostulante() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            listaCbGenPostulante = dao.listarCbGeneracionPostulante();
        } catch (Exception e) {
            throw e;
        }
    }

    public void validarPrograma() throws Exception {
        ImplProgDetD dao;
        try {
            dao = new ImplProgDetD();
            listarTipoPrograma();
            if (!dao.validarProgramaPostulante(ins.getCODPROG())) {
                PrimeFaces.current().executeScript("PF('DialogAdvertencia').show();");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarTipoPrograma() throws Exception {
        try {
            pard.listarTipoPrograma(listatipoProg, ins.getCODPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarDatosParticipante(String CODPER) throws Exception {
        try {
            pard.listarDatosParticipantes(listabloqins, pard.traecodpar(CODPER));
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarParticipantesI() throws Exception {
        try {
            listParticipantesI = pard.listarInscritos(getCODPROGI());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarParticipantesNoAdmitidos() throws Exception {
        try {
            listParticipantesNoAd = pard.listarPartiNoAdmitidos(getCODPROGNOAD());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarPostulantes() throws Exception {
        try {
            listParticipantes = pard.listar(getCODPROG());
        } catch (Exception e) {
            throw e;
        }
    }

    public void listahijos() {
        try {
            HijoM hijo;
            List<HijoM> lsthijo = new ArrayList();
            for (int i = 0; i < numhijos; i++) {
                hijo = new HijoM();
                lsthijo.add(hijo);
            }
            pardet.setHijos(lsthijo);
        } catch (Exception e) {
        }
    }

    public void actualizar() throws Exception {
        try {
            selectPer.setEmpresa(empd.leerEmpresa(selectPer.getEmpresaM().getNOMBEMP()));
            pard.actualizar(selectPer);
            pardetd.actualizar(selectPer.getPersonaDet());
            pardet.setCODPERDET(pardetd.traeCodDet(selectPer.getCodPer()));
            pardetd.guardarhijo(pardet);
            setNumhijos(0);
            selectPer = new PersonaM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Participante"));
        } catch (Exception e) {
            throw e;
        }
    }

    public void retirarParticipante(String dniPer) throws Exception {
        try {
            pard.retirarParticipante(pard.traecodpar(dniPer));
            listarParticipantesI();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Participante", "Retirado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al retirar", e.getMessage()));
        }

    }

    public void restablecerParticipante(String dniPer) throws Exception {
        try {
            pard.restablecerParticipante(pard.traecodpar(dniPer));
            listarParticipantesI();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Participante", "Restablecido"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al restablecer", e.getMessage()));
        }

    }

    public void matricular() throws Exception {
        try {
            if (lstselectPar.size() > 0) {
                for (PersonaM participante : lstselectPar) {
                    pard.cambioEstadoES(pard.traecodpar(participante.getDniPer()));
                    pard.cambioEstadoS(pard.traecodpar(participante.getDniPer()));
                    Thread.sleep(80);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione", "Participantes"));
            }
            listarPostulantes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Matriculado", "Asignado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se pudo Asignar", e.getMessage()));
        }
    }

    public void limpiar() {
        emp = new EmpresaM();
        par = new PersonaM();
        pardet = new PersonaDetM();
        setNumhijos(0);
        setCelular("");
        setTelefono("");
    }

    public void addInscripcion() throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            this.setDni(dao.buscarDniPersona(par.getDniPer()));
            if (par.getDniPer().equals(this.Dni)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ya existe", "Ya ha sido registrado anteriormente"));
            } else {
                addHijos();
                ins.setCODPER(pard.traecodpar(par.getDniPer()));
                insd.guardar(ins);
                limpiar();
                verificar = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Formulario", "Se envió correctamente"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo registrar"));
            throw e;
        }
    }

    public void addInscripcionDocente() throws Exception {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            this.setDni(dao.buscarDniPersona(par.getDniPer()));
            if (par.getDniPer().equals(this.Dni)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ya existe", "Ya ah sido registrado anteriormente"));
            } else {
                addHijosDocente();
                emp = new EmpresaM();
                par = new PersonaM();
                pardet = new PersonaDetM();
                ins = new InscripcionM();
                setNumhijos(0);
                setCODPROG(null);
                setCelular("");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingresado", "Se envio correctamente"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void addHijos() throws Exception {

        try {
            addParticipanteDet();
            pardet.setCODPERDET(pardetd.traeCodDet(pardet.getCODPER()));
            pardetd.guardarhijo(pardet);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo registrar"));
            throw e;
        }
    }

    public void addHijosDocente() throws Exception {

        try {
            addParticipanteDetDocente();
            pardet.setCODPERDET(pardetd.traeCodDet(pardet.getCODPER()));
            pardetd.guardarhijo(pardet);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addParticipanteDetDocente() throws Exception {
        String FileCV;
        try {
            addDocente();
            FileCV = uploadCV();
            pardet.setCODPER(pard.traecodpar(par.getDniPer()));
            pardet.setDOCCV(FileCV);
            pardetd.guardar(pardet);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addParticipanteDet() throws Exception {
        String FileOrg;
        try {
            addParticipante();
            FileOrg = uploadOrg();
            pardet.setCODPER(pard.traecodpar(par.getDniPer()));
            pardet.setIMGORG(FileOrg);
            pardetd.guardar(pardet);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo registrar"));
            throw e;
        }
    }

    public void addDocente() throws Exception {
        String FileName;
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            addEmpresa();
            FileName = uploadPhoto();
            par.setCodUbi(dao.leerUbigeo(par.getCodUbi()));
            par.setEmpresa(empd.traeCodigo(emp.getRUCEMP()));
            par.setImgPer(FileName);
            par.setCelPer(Celular + "/" + Telefono);
            pard.guardarDocente(par);

        } catch (Exception e) {
            throw e;
        }
    }

    public void addParticipante() throws Exception {
        String FileName;
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            addEmpresa();
            FileName = uploadPhoto();
            par.setCodUbi(dao.leerUbigeo(par.getCodUbi()));
            par.setEmpresa(empd.traeCodigo(emp.getRUCEMP()));
            par.setImgPer(FileName);
            par.setCelPer(Celular + "/" + Telefono);
            pard.guardar(par);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo registrar"));
            throw e;
        }
    }

    public String uploadPhoto() throws Exception {
        UploadFile service;
        String FileName;
        try {
            service = new UploadFile();
            if (foto != null) {
                FileName = service.savePhoto(foto, par.getDniPer());
                if (FileName == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", "El archivo no se guardo"));
                    return null;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", "Seleccione arhivo"));
                return null;
            }
            return FileName;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String uploadOrg() throws Exception {
        UploadFile service;
        String FileName;
        try {
            service = new UploadFile();
            if (organigrama != null) {
                FileName = service.saveOrg(organigrama, par.getDniPer());
                if (FileName == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", "La arhivo no se guardo"));
                    return null;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", "Seleccione archivo"));
                return null;
            }
            return FileName;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String uploadCV() throws Exception {
        UploadFile service;
        String FileName;
        try {
            service = new UploadFile();
            if (cv != null) {
                FileName = service.saveCV(cv, par.getDniPer());
                if (FileName == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Archivo no aceptado"));
                    return null;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Seleccione Archivo"));
                return null;
            }
            return FileName;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public void addEmpresa() throws Exception {
        try {
            this.setRuc(empd.buscarRucEmpresa(emp.getRUCEMP()));
            if (emp.getRUCEMP().equals(this.Ruc)) {
                System.out.println("El ruc ya existe");
            } else {
                emp.setCODUBI(empd.leerUbi(emp.getCODUBI()));
                empd.guardar(emp);
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se pudo agregar"));
            throw e;
        }
    }

    //ACTUALIZAR CONTRASEÑA DE LA PERSONA
    public void updatePassword() throws SQLException {
        ImplPersonaD dao;
        try {
            dao = new ImplPersonaD();
            dao.updatePassword(par);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña", "Actualizada"));
        } catch (SQLException e) {
            throw e;
        }
    }

    //ACTUALIZAR FOTO DE LA PERSONA
    public void modificarFotoPersona() throws Exception {
        ImplPersonaD dao;
        String fotoPersona;
        String organigramaPersona;
        try {
            dao = new ImplPersonaD();
            fotoPersona = uploadPhoto();
            if (fotoPersona != null) {
                par.setImgPer(fotoPersona);
                dao.modificarFotoPersona(par);
            }
            organigramaPersona = uploadOrg();
            if (organigramaPersona != null) {
                pardet.setCODPER(par.getCodPer());
                pardet.setIMGORG(organigramaPersona);
                dao.modificarOrganigramaPersona(pardet);
            }
            PrimeFaces.current().executeScript("PF('modificarFoto').hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se actualizo correctamente"));
        } catch (Exception e) {
            throw e;
        }

    }

    public List<String> completeTextEmpresa(String query) throws Exception {
        return empd.queryAutoCompleteEmpresa(query);
    }

    //Obtener los datos de la persona a editar
    public void obtenerDatosParticipante() throws Exception {
        selectPer = pard.getDatosParticipante(SessionService.getCodigoPersona());
    }
}
