package com.spring.services.gestores.service.impl.rest;

import com.spring.services.gestores.model.GestoresModel;
import com.spring.services.gestores.service.impl.GestoresImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/service/gestores")
@CrossOrigin("*")
public class GestoresRest {

    @Autowired
    private GestoresImpl gesImp;

    public GestoresRest() {
        //Vacio
    }

    @PostMapping(value = {"/buscarGestoresSCL"},
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<GestoresModel>> consultarGestoresSCL(@RequestBody final GestoresModel clientes){
        return gesImp.consultarGestoresSCL(clientes);
    }

    @PostMapping(value = "/asignarClientesUnGestor",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>insertarClientesSCL(@RequestBody final GestoresModel clientes){
        return gesImp.asignarClientesSCL(clientes);
    }

    @PostMapping(value = "/asignarClientesAGestores",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>asignarClientesSCLGestores(@RequestBody final String clientes){
        return gesImp.asignarClientesSCLGestores(clientes);
    }


    @GetMapping(value={"/consultarGestoresTKM"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<GestoresModel>> consultarGestoresTKM(){
        return gesImp.consultarGestoresTKM();
    }

    @PostMapping(value = "/insertarGestorTKM",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> insertarGestoresTKM(@RequestBody final GestoresModel empleado){
        return gesImp.insertarGestoresTKM(empleado);
    }

    @PostMapping(value = "/actualizarGestorTKM",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> actualizarGestoresTKM(@RequestBody final GestoresModel empleado){
        return gesImp.actualizarGestoresTKM(empleado);
    }

    @GetMapping(value = "/borrarGestorTKM/{idEmpleado}",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> eliminarGestoresTKM(@PathVariable String idEmpleado){
        return gesImp.eliminarGestoresTKM(idEmpleado);
    }



}
