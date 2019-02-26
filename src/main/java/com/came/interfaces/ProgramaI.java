package com.came.interfaces;

import com.came.model.ProgramaM;
import java.util.ArrayList;
import java.util.List;

public interface ProgramaI {

    //Programas
    void agregarPrograma(ProgramaM programa) throws Exception;

    List<ProgramaM> listarProgramas() throws Exception;

    List<ProgramaM> listarProgramasyGen() throws Exception;

    ArrayList<ProgramaM> listarCbProgramas() throws Exception;
    
    ArrayList<ProgramaM> listarCbAreas() throws Exception;

    //Tipo de programas
    void agregarTipoPg(ProgramaM programa) throws Exception;

    List<ProgramaM> listarTipoPg() throws Exception;

    ArrayList<ProgramaM> listarCbTipoProg() throws Exception;
}
