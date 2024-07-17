package com.spring.services.cartera.service;

import com.spring.services.cartera.logic.DiezYearCarteraLogic;
import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.DiezYearCarteraImpl;
import com.spring.utils.RestResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class DiezYearCarteraService implements DiezYearCarteraImpl {

    private DiezYearCarteraLogic diezYearLog=new DiezYearCarteraLogic();

    public DiezYearCarteraService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> DiezYearSCLCarteraCompleta(ExtrasModel cokkie) {
        return diezYearLog.DiezYearSCLCarteraCompleta(cokkie);
    }
}
