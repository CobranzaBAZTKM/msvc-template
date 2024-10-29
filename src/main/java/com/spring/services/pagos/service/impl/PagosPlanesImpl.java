package com.spring.services.pagos.service.impl;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.pagos.model.PromesasModel;
import com.spring.services.pagos.model.datosEntradaPagosPlanes;
import com.spring.utils.RestResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

public interface PagosPlanesImpl {
    RestResponse<String> obtenerPlanesPago(datosEntradaPagosPlanes cu);
    RestResponse<String> obtenerPlanesPagoSemanal(datosEntradaPagosPlanes cu);
    RestResponse<ArrayList<PromesasModel>> obtenerPagosDia(datosEntradaPagosPlanes cu,String tipoCarteraTKM);
    RestResponse<ArrayList<ClienteModel>> validarPromesasPago2semanas(String json,String tipoCarteraTKM);
    RestResponse<String>layoutSemanal(String layout);
}
