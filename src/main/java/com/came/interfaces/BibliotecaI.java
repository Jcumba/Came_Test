package com.came.interfaces;

import com.came.model.BibliotecaM;
import java.util.List;

public interface BibliotecaI {

    void registrar(BibliotecaM bi) throws Exception;
    
    void registrarVideo(BibliotecaM bi) throws Exception;
    
    void actualizar(BibliotecaM bi) throws Exception;
    
    void actualizarVideo(BibliotecaM bi) throws Exception;

    List<BibliotecaM> listar(String doc) throws Exception;
    
    List<BibliotecaM> listarVideos(String vid) throws Exception;

    List<BibliotecaM> listarDocSes(String CODSES) throws Exception;
    
    List<BibliotecaM> listarDocumentos(String CODTIPPG) throws Exception;
    
    String traerCodtippg(String CODDETPROG) throws Exception;

    void eliminarAnexoDoc(String CODSESANE) throws Exception; 
}
