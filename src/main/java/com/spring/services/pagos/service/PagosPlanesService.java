package com.spring.services.pagos.service;

import com.spring.services.pagos.logic.PagosPlanesLogic;
import com.spring.services.pagos.model.datosEntradaPagosPlanes;
import com.spring.services.pagos.service.impl.PagosPlanesImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
