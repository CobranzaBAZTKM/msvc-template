package com.spring.services.pagos.service;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.pagos.logic.PagosPlanesLogic;
import com.spring.services.pagos.model.PromesasModel;
import com.spring.services.pagos.model.datosEntradaPagosPlanes;
import com.spring.services.pagos.service.impl.PagosPlanesImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PagosPlanesService implements PagosPlanesImpl {

    @Autowired
    private PagosPlanesLogic ppLogic;

    public PagosPlanesService() {
        //Vacio
    }

    @Override
    public RestResponse<String> obtenerPlanesPago(datosEntradaPagosPlanes cu){
        return ppLogic.obtenerPlanesPago(cu);
    }

    @Override
    public RestResponse<String> obtenerPlanesPagoSemanal(datosEntradaPagosPlanes cu){
        return ppLogic.obtenerPlanesPagoSemanal(cu);
    }

    @Override
    public RestResponse<ArrayList<PromesasModel>> obtenerPagosDia(datosEntradaPagosPlanes cu,String tipoCarteraTKM) {
        return ppLogic.obtenerPagosDia(cu,tipoCarteraTKM);
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> validarPromesasPago2semanas(String json,String tipoCarteraTKM){
        return ppLogic.validarPromesasPago2semanas(json,tipoCarteraTKM);
    }

    @Override
    public RestResponse<String>layoutSemanal(String layout) {
        return ppLogic.layoutSemanal(layout);
    }
}
