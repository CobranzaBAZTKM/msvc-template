package com.spring.services.notificaciones.service;

import com.spring.services.notificaciones.logic.NotificacionesLogic;
import com.spring.services.notificaciones.model.CuerpoCorreo;
import com.spring.services.notificaciones.service.impl.NotificacionesImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionesService implements NotificacionesImpl {

    @Autowired
    private NotificacionesLogic notiLogic;

    public NotificacionesService() {
        //Vacio
    }

    @Override
    public RestResponse<String> enviarCorreo(CuerpoCorreo correo) {
        return notiLogic.enviarCorreo(correo);
    }

    @Override
    public RestResponse<String> enviarCorreoGenerico(CuerpoCorreo correo){
        return notiLogic.enviarCorreoGenerico(correo);
    }
}
