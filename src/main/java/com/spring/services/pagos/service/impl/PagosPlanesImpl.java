package com.spring.services.pagos.service.impl;

import com.spring.services.pagos.model.datosEntradaPagosPlanes;
import com.spring.utils.RestResponse;

public interface PagosPlanesImpl {
    RestResponse<String> obtenerPlanesPago(datosEntradaPagosPlanes cu);
    RestResponse<String> obtenerPlanesPagoSemanal(datosEntradaPagosPlanes cu);
}
