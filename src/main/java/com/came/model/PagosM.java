package com.came.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PagosM {

    private String CODPAG, CODPER, ASUMPAG, ESTPAG, CANTDES, CONDPAG, OBSEPAG, FECHAPAG, ENCPAG,
            EMAILENC, TELFENC, ADMINISTRADOR, FECHAREG;

    private List<PagosDetM> numpagos;

    public PagosM() {
        numpagos = new ArrayList();
    }
}
