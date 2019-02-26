package com.came.interfaces;

import com.came.model.FMonitorM;
import java.util.List;

public interface FMonitorI {

    List<FMonitorM> listarPersonasPorMonitorear(String codDetProg) throws Exception;

    void agregarMonitoreo(FMonitorM fmon, String codSes) throws Exception;

}
