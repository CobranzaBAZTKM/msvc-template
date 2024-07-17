package com.spring.services.cartera.service;

import com.spring.services.cartera.logic.TerritoriosCarteraLogic;
import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.TerritoriosCarteraImpl;
import com.spring.utils.RestResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class TerritoriosCarteraService implements TerritoriosCarteraImpl {

    private TerritoriosCarteraLogic terrCartLog=new TerritoriosCarteraLogic();
    public TerritoriosCarteraService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> TerriSCLcarteraCompleta(ExtrasModel cokkie) {
        return terrCartLog.TerriSCLcarteraCompleta(cokkie);
    }
}
