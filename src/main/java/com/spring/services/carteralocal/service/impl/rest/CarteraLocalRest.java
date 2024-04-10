package com.spring.services.carteralocal.service.impl.rest;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.carteralocal.service.impl.CarteraLocalImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/service/carteraLocal")
public class CarteraLocalRest {

    @Autowired
    private CarteraLocalImpl carteraLoImpl;
    public CarteraLocalRest() {
        //Vacio
    }

    @PostMapping(value = "/carteraCompletaSCLGuardar",
            consumes = {"application/json"},
            produces = {"application/json"})
//            consumes = { MediaType.APPLICATION_JSON_VALUE },
//            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(@RequestBody final ExtrasModel cokkie){
//    public RestResponse<String> carteraCompleta(@RequestBody final ExtrasModel cokkie){
        return carteraLoImpl.carteraCompletaGuardar(cokkie);
    }


    @PostMapping(value = "/carteraCompletaDia",
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> carteraCompletaDia(){
//    public RestResponse<String> carteraCompleta(@RequestBody final ExtrasModel cokkie){
        return carteraLoImpl.carteraCompletaDia();
    }

    @GetMapping(value = "/consultarNuevasCuentasDia",
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> consultarNuevasCuentasDia(){
//    public RestResponse<String> carteraCompleta(@RequestBody final ExtrasModel cokkie){
        return carteraLoImpl.consultarNuevasCuentasDia();
    }

    @PostMapping(value = "/carteraConDescarte",
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte() {
        return carteraLoImpl.consultarCarteraDescarte();
    }

    @PostMapping(value = "/carteraCompletaGuardarLocal",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<String> carteraCompletaGuardarLocalPuente(@RequestBody final String cartera){
        return carteraLoImpl.carteraCompletaGuardarLocalPuente(cartera);
    }


    @PostMapping(value = "/carteraDescarteGuardarLocal",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<String>carteraDescarteGuardarLocalPuente(@RequestBody final String cuentas){
        return carteraLoImpl.carteraDescarteGuardarLocalPuente(cuentas);
    }

    @GetMapping(value = "/consultarClienteUnico/{cu}",
            produces = {"application/json"})
    public RestResponse<ClienteModel> consultarCUCarteraCompleta(@PathVariable String cu){
        return carteraLoImpl.consultarCUCarteraCompleta(cu);
    }
}
