package com.came.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PersonaDetM {

    private String INIPER;
    private String CODPER;
    private String CONYUGE;
    private String PROFCONY;
    private String TELFTRAB;
    private String EMAILTRAB;
    private String AREATRAB;
    private String YEARPUESTO;
    private String NUMPERCARGO;
    private String JEFEMP;
    private String JEFCARGO;
    private String JEFTELF;
    private String JEFEMAIL;
    private String ACTPER;
    private String AUTOPER;
    private String MARCAUT;
    private String PLACAUT;
    private String SEGURO;
    private String TELFSEGURO;
    private String EMERNOMB;
    private String EMERTELF;
    private String IMGORG;
    private String GRAPER;
    private String UNIPER;
    private String ESPPER;
    private String PROACA1;
    private String DURACA1;
    private String INSACA1;
    private String PROACA2;
    private String DURACA2;
    private String INSACA2;
    private String PROACA3;
    private String DURACA3;
    private String INSACA3;
    private String CO1;
    private String CO2;
    private String CO3;
    private String CO4;
    private String CO5;
    private String INT1;
    private String INT2;
    private String INT3;
    private String INT4;
    private String INT5;
    private String NIVEJER;
    private String PROFESION;
    private String RESCUV;
    private String DOCCV;
    private boolean ENVIODATOS = true;
    private String GERENCIA;
    private String JEFATURA;
    private String SUPERVISOR;
    private String AUTORIZACION;

    private String CODPERDET;

    private List<HijoM> hijos;

    public PersonaDetM() {
        hijos = new ArrayList();
    }

    // VISTA
    private String CODIGO;
    private String FOTO;
    private String DNI;
    private String NOMBRE;
    private String APELLIDO;
    private String NACIMIENTO;
    private String LUGARNAC;
    private String ESTADOCIVIL;
    private String SEXO;
    private String CELULAR;
    private String DOMICILIO;
    private String DISTRITO;
    private String PROFCONYUGE;
    private String EMPRESA;
    private String RUC;
    private String DOMICILIOFISCAL;
    private String TELETRAB;
    private String EMAILTRABA;
    private String ACTIEMP;
    private String AREA;
    private String PUESTO;
    private String YEARPUES;
    private String NPERSONAS;
    private String JEFE;
    private String CARGOJEFE;
    private String TELFJEFE;
    private String EMAILJEFE;
    private String ACTIVIDADPER;
    private String AUTO;
    private String MARCA;
    private String PLACA;
    private String ORGANIGRAMA;
    private String GRADOACA;
    private String UNIVACA;
    private String ESPACA;
    private String PRO1;
    private String PRO2;
    private String PRO3;
    private String DURA1;
    private String DURA2;
    private String DURA3;
    private String INST1;
    private String INST2;
    private String INST3;
    private String CON1;
    private String CON2;
    private String CON3;
    private String CON4;
    private String CON5;
    private String INTE1;
    private String INTE2;
    private String INTE3;
    private String INTE4;
    private String INTE5;
    private String SEGUROPER;
    private String TELEFONOSEG;
    private String PEREMER;
    private String TELFEMER;
    
    private String ASUMEPAG;
    private String ESTADOPAG;
    private String CONDIPAG;
    private String OBSERPAG;
    private String FECPAG;
    private String ENCAPAG;
    private String ENCEMAIL;
    private String ENCTELF;
    private String FECHAREGA;
    
    private String PREGUNTA1;
    private String PREGUNTA2;
    private String COMENTARIOS;
    private String FECHAREGE;
    private String ESTENT;
    private String ENTREVISTADOR;
    
    private String tipoProg;
}
