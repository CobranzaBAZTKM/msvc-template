package com.spring.services.operacion.service.impl.rest;

import com.spring.services.operacion.model.GestionLlamadasModel;
import com.spring.services.operacion.service.impl.GestionLlamadasImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/service/operacion/gestionllamadas")
public class GestionLlamadasRest {
    @Autowired
    private GestionLlamadasImpl gestLlamIm;

    public GestionLlamadasRest() {
        //Vacio
    }

    @GetMapping(value={"/consultarGestionLlamadas"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<GestionLlamadasModel>>consultarGestionLlamadas(){
        return gestLlamIm.consultarGestionLlamadas();
    }

    @PostMapping(value = "/insertarGestionLlamadas",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<String> insertarGestionLlamadas(@RequestBody final GestionLlamadasModel gestLlam){
        return gestLlamIm.insertarGestionLlamadas(gestLlam);
    }

    @PostMapping(value = "/actualizarGestionLlamadas",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<String> actualizarGestionLlamadas(@RequestBody final GestionLlamadasModel gestLlam){
        return gestLlamIm.actualizarGestionLlamadas(gestLlam);
    }

    @GetMapping(value = "/borrarGestionLlamadas/{idGestion}/{idSupervisor}",
            produces = {"application/json"})
    public RestResponse<String> borrarGestionLlamadas(@PathVariable String idGestion, @PathVariable String idSupervisor) {
        return gestLlamIm.borrarGestionLlamadas(idGestion, idSupervisor);
    }

    @GetMapping(value = "/consultarGestionLlamadasNumero/{numero}",
            produces = {"application/json"})
    public RestResponse<ArrayList<GestionLlamadasModel>> consultarGestionLlamadasNumero(@PathVariable String numero){
        return gestLlamIm.consultarGestionLlamadasNumero(numero);
    }

}
