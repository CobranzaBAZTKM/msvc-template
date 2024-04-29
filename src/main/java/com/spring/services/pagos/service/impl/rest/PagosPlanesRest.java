package com.spring.services.pagos.service.impl.rest;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.pagos.model.PromesasModel;
import com.spring.services.pagos.model.datosEntradaPagosPlanes;
import com.spring.services.pagos.service.impl.PagosPlanesImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/service/pagos")
@CrossOrigin("*")
public class PagosPlanesRest {
    @Autowired
    private PagosPlanesImpl ppImpl;

    public PagosPlanesRest() {
        //Vacio
    }

    @PostMapping(value = "/planesPagoCUDiario",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> obtenerPlanesPago(@RequestBody final datosEntradaPagosPlanes cu){
        return ppImpl.obtenerPlanesPago(cu);
    }

    @PostMapping(value = "/planesPagoCUSemanal",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> obtenerPlanesPagoSemanal(@RequestBody final datosEntradaPagosPlanes cu){
        return ppImpl.obtenerPlanesPagoSemanal(cu);
    }

    @PostMapping(value = "/pagosDia",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<PromesasModel>> obtenerPagosDia(@RequestBody final datosEntradaPagosPlanes cu){
        return ppImpl.obtenerPagosDia(cu);
    }

    @PostMapping(value = "/validacionPromesasLocal",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<ClienteModel>> validarPromesasPago2semanas(@RequestBody final String json){
        return ppImpl.validarPromesasPago2semanas(json);
    }



}


