package com.spring.services.cartera.service.impl.rest;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.CarteraImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
//@CrossOrigin(origins = "http://172.16.201.28:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/service/cartera")

//@CrossOrigin(maxAge = 3600)

public class CarteraRest {
    @Autowired
    private CarteraImpl carteraImpl;

    public CarteraRest() {
        //Vacio
    }

    @PostMapping(value = "/carteraCompleta",
            consumes = {"application/json"},
            produces = {"application/json"})
//            consumes = { MediaType.APPLICATION_JSON_VALUE },
//            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<ClienteModel>> carteraCompleta(@RequestBody final ExtrasModel cokkie){
//    public RestResponse<String> carteraCompleta(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraCompleta(cokkie);
    }

    @PostMapping(value = "/carteraSegmento5",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> carteraSeg5(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSeg5(cokkie);
    }
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg5(@RequestBody final ExtrasModel cokkie){
//        return carteraImpl.carteraSeg5(cokkie);
//    }

    @PostMapping(value = "/carteraSegmento6Completa",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg6(@RequestBody final ExtrasModel cokkie){
    public RestResponse<String> carteraSeg6(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSeg6(cokkie);
    }

    @PostMapping(value = "/carteraSegmento6SNNITC",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg16(@RequestBody final ExtrasModel cokkie){
    public RestResponse<String> carteraSeg6SNNITC(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSeg6SNNITC(cokkie);
    }

    @PostMapping(value = "/carteraSegmento16Completa",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg16(@RequestBody final ExtrasModel cokkie){
    public RestResponse<String> carteraSeg16(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSeg16(cokkie);
    }

    @PostMapping(value = "/carteraSegmento16SNNITC",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg16(@RequestBody final ExtrasModel cokkie){
    public RestResponse<String> carteraSeg16SNNITC(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSeg16SNNITC(cokkie);
    }


    @PostMapping(value = "/carteraSegmento28",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg28(@RequestBody final ExtrasModel cokkie){
    public RestResponse<String> carteraSeg28(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSeg28(cokkie);
    }

    @PostMapping(value = "/carteraSaldos",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg28(@RequestBody final ExtrasModel cokkie){
    public RestResponse<String> carteraSaldos(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSaldos(cokkie);
    }

    @PostMapping(value = "/carteraSegmentoTipo",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
//    public RestResponse<ArrayList<ClienteModel>> carteraSegTipo(@RequestBody final ExtrasModel cokkie){
    public RestResponse<String> carteraSegTipo(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSegTipo(cokkie);
    }
    @PostMapping(value = "/blasterSegmento",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> blasterSegmento(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.blasterSegmento(cokkie);
    }

    @PostMapping(value = "/carteraSegmentoEleg",
            consumes = {"application/json"},
            produces = {"application/json"})
//            consumes = { MediaType.APPLICATION_JSON_VALUE },
//            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<ClienteModel>>carteraSegmentoEleg(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.carteraSegmentoEleg(cokkie);
    }

    @PostMapping(value = "/numerosListasSegmento",
            consumes = {"application/json"},
            produces = {"application/json"})
//            consumes = { MediaType.APPLICATION_JSON_VALUE },
//            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<ClienteModel>> smsListasSegmento(@RequestBody final ExtrasModel cokkie){
        return carteraImpl.numerosListasSegmento(cokkie);
    }
}


