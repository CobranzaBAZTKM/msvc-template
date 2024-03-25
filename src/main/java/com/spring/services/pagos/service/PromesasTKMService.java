package com.spring.services.pagos.service;

import com.spring.services.pagos.logic.PromesasTKMLogic;
import com.spring.services.pagos.model.PromesasModel;
import com.spring.services.pagos.service.impl.PromesasTKMImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PromesasTKMService implements PromesasTKMImpl {

    @Autowired
    private PromesasTKMLogic ppLogic;

    public PromesasTKMService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<PromesasModel>> consultarPromesas() {
        return ppLogic.consultarPromesas();
    }

    @Override
    public RestResponse<ArrayList<PromesasModel>> consultarPromesasCGestion(String cookie) {
        return ppLogic.consultarPromesasCGestion(cookie);
    }

    @Override
    public RestResponse<String> insertarPromesas(PromesasModel promesa) {
        return ppLogic.insertarPromesas(promesa);
    }

    @Override
    public RestResponse<String> actualizarPromesas(PromesasModel promesa) {
        return ppLogic.actualizarPromesas(promesa);
    }

    @Override
    public RestResponse<String> borrarPromesas(String idPromesa,String idAdmin) {
        return ppLogic.borrarPromesas(idPromesa,idAdmin);
    }

    @Override
    public RestResponse<String> actualizarPromesasEstPag(PromesasModel promesa) {
        return ppLogic.actualizarPromesasEstPag(promesa);
    }



}
