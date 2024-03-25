package com.spring.services.operacion.service.impl.rest;

import com.spring.services.operacion.model.TipificacionesModel;
import com.spring.services.operacion.service.TipificacionesService;
import com.spring.services.operacion.service.impl.TipificacionesImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/service/operacion/tipificaciones")
public class TipificacionesRest {


    @Autowired
    private TipificacionesImpl tipImpl;

    public TipificacionesRest() {
        //Vacio
    }

    @GetMapping(value={"/consultarTipificaciones"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<TipificacionesModel>> consultarTipificaciones(){
        return tipImpl.consultarTipificaciones();
    }

    @PostMapping(value = "/insertarTipificaciones",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<String> insertarTipificaciones(@RequestBody final TipificacionesModel tipif){
            return tipImpl.insertarTipificaciones(tipif);
    }

    @PostMapping(value = "/actualizarTipificaciones",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<String> actualizarTipificaciones(@RequestBody final TipificacionesModel tipif){
        return tipImpl.actualizarTipificaciones(tipif);
    }

    @GetMapping(value = "/borrarTipificaciones/{idTipificacion}/{idSupervisor}",
            produces = {"application/json"})
    public RestResponse<String> borrarTipificaciones(@PathVariable String idTipificacion, @PathVariable String idSupervisor){
        return tipImpl.borrarTipificaciones(idTipificacion,idSupervisor);
    }


}
