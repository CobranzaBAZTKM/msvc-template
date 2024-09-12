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

    @PostMapping(value = "/carteraCompletaSCLGuardar/{tipoCarteraTKM}",
            consumes = {"application/json"},
            produces = {"application/json"})
//            consumes = { MediaType.APPLICATION_JSON_VALUE },
//            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(@RequestBody final ExtrasModel cokkie,@PathVariable String tipoCarteraTKM){
//    public RestResponse<String> carteraCompleta(@RequestBody final ExtrasModel cokkie){
        return carteraLoImpl.carteraCompletaGuardar(cokkie,tipoCarteraTKM);
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

    @PostMapping(value = "/carteraConDescarte/{tipoCartera}",
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte(@PathVariable String tipoCartera) {
        return carteraLoImpl.consultarCarteraDescarte(tipoCartera);
    }

    @GetMapping(value = "/consultarClienteUnico/{cu}",
            produces = {"application/json"})
    public RestResponse<ClienteModel> consultarCUCarteraCompleta(@PathVariable String cu){
        return carteraLoImpl.consultarCUCarteraCompleta(cu);
    }

    @GetMapping(value = "/consultarCarteraPromesaHistorico/{tipoCarteraTKM}",
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> consultarCarteraConPromesa(@PathVariable String tipoCarteraTKM){
        return carteraLoImpl.consultarCarteraConPromesa(tipoCarteraTKM);
    }

    @GetMapping(value = "/consultarCuentasSinContactoHistorico/{tipoCarteraTKM}",
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> consultarCuentasSinContacto(@PathVariable String tipoCarteraTKM){
        return carteraLoImpl.consultarCuentasSinContacto(tipoCarteraTKM);
    }

    @PostMapping(value = "/insertarCarteraLocal",
            consumes = {"application/json"},
            produces = {"application/json"})
//            consumes = { MediaType.APPLICATION_JSON_VALUE },
//            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> insertarCarteraLocal(@RequestBody final String clientes){
//    public RestResponse<String> carteraCompleta(@RequestBody final ExtrasModel cokkie){
        return carteraLoImpl.insertarCarteraLocal(clientes);
    }

    @PostMapping(value = "/carteraConDescarteCompleta",
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarteDiaCompleta(){
        return carteraLoImpl.consultarCarteraDescarteDiaCompleta();
    }
}
