package com.spring.services.notificaciones.service.impl;

import com.spring.services.notificaciones.model.CuerpoCorreo;
import com.spring.utils.RestResponse;

public interface NotificacionesImpl {
    RestResponse<String> enviarCorreo(CuerpoCorreo correo);
    RestResponse<String> enviarCorreoGenerico(CuerpoCorreo correo);
}
