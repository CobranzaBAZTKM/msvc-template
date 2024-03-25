package com.spring.services.operacion.service.impl;

import com.spring.services.operacion.model.TipificacionesModel;
import com.spring.utils.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

public interface TipificacionesImpl {

    RestResponse<ArrayList<TipificacionesModel>> consultarTipificaciones();
    RestResponse<String> insertarTipificaciones(TipificacionesModel tipif);
    RestResponse<String> actualizarTipificaciones(TipificacionesModel tipif);
    RestResponse<String> borrarTipificaciones(String idTipificacion, String idSupervisor);
}
