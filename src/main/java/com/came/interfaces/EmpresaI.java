package com.came.interfaces;

import com.came.model.EmpresaM;
import java.util.List;

public interface EmpresaI {

    void guardar(EmpresaM emp) throws Exception;

    List<String> queryAutoCompleteUbi(String a) throws Exception;

    String leerUbi(String a) throws Exception;

    String traeCodigo(String a) throws Exception;

    List<String> queryAutoCompleteEmpresa(String a) throws Exception;

    String leerEmpresa(String a) throws Exception;

    String buscarRucEmpresa(String rucEmp) throws Exception;

}
