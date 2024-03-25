package com.spring.services.notificaciones.service.impl.rest;

import com.spring.services.notificaciones.model.CuerpoCorreo;
import com.spring.services.notificaciones.service.impl.NotificacionesImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/notificaciones")
@CrossOrigin("*")
public class NotificacionesRest {
    @Autowired
    private NotificacionesImpl notiImpl;
    public NotificacionesRest() {
        //Vacio
    }

    @PostMapping(value = "/enviarCorreo",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> enviarCorreo(@RequestBody final CuerpoCorreo correo){
        return notiImpl.enviarCorreo(correo);
    }

    @PostMapping(value = "/enviarCorreoGenerico",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> enviarCorreoGenerico(@RequestBody final CuerpoCorreo correo){
        return notiImpl.enviarCorreoGenerico(correo);
    }


}
