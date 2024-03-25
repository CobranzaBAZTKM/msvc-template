package com.spring.services.operacion.service;

import com.spring.services.operacion.logic.TipificacionesLogic;
import com.spring.services.operacion.model.TipificacionesModel;
import com.spring.services.operacion.service.impl.TipificacionesImpl;
import com.spring.utils.RestResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TipificacionesService implements TipificacionesImpl {
    TipificacionesLogic tipLogic=new TipificacionesLogic();
    public TipificacionesService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<TipificacionesModel>> consultarTipificaciones() {
        return tipLogic.consultarTipificaciones();
    }

    @Override
    public RestResponse<String> insertarTipificaciones(TipificacionesModel tipif) {
        return tipLogic.insertarTipificaciones(tipif);
    }

    @Override
    public RestResponse<String> actualizarTipificaciones(TipificacionesModel tipif) {
        return tipLogic.actualizarTipificaciones(tipif);
    }

    @Override
    public RestResponse<String> borrarTipificaciones(String idTipificacion, String idSupervisor) {
        return tipLogic.borrarTipificaciones(idTipificacion, idSupervisor);
    }
}
