package com.spring.services.cartera.service;

import com.spring.services.cartera.logic.VIPCarteraLogic;
import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.VIPCarteraImpl;
import com.spring.services.carteralocal.logic.CarteraLocalLogic;
import com.spring.utils.RestResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class VIPCarteraService implements VIPCarteraImpl {
    private VIPCarteraLogic vipCarteraLogic=new VIPCarteraLogic();

    public VIPCarteraService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> VIPcarteraCompleta(ExtrasModel cokkie) {
        RestResponse<ArrayList<ClienteModel>> respuesta=vipCarteraLogic.VIPcarteraCompleta(cokkie);
        return respuesta;
    }
}
