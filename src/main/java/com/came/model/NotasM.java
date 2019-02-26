package com.came.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class NotasM implements Serializable {

    private String CodDetSes;
    private String CodPer;
    private String CodAsig;
    private String NomPer, ApePer;
    private int Divisor;
    private String Generacion;
    private String NomFase;
    private String TipoSes;
    private String Asistencia, Plenaria, Caso, Caso2, Control, ExamParc, ExamFinal, Trabajo;
    private int NotaPlenaria = 0, NotaCaso = 0, NotaCaso2 = 0, NotaControl = 0, NotaExamParc = 0, NotaExamFinal = 0, NotaTrabajo = 0;
    private String NotaAsis = "1";
    private String ConvertAsis;
    private double promedio;

    private String CODPROG, GENDETPROG, NOMPROG, PERSONA, PROMEDIOS, TIPSES, CODFASE, CODASIG, TRABAJO, EXAMPARC,
            PROM_CASO, PROM_EXPARC, PROM_TRA, PROM_EXFINAL, PROM_FASE, PROM_PROG, NOMFASE, PROM_PARTICIPACION;

}
