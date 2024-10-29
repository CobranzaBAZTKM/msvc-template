package com.spring.services.batch;

import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/service/batchs")
public class BatchsManual {
    @Autowired
    private Batchs batchs;

    public BatchsManual() {
        //Vacio
    }

    @GetMapping(value={"/mandarBlasterRecordatorios"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>mandarBlasterRecordatorios(){
        batchs.mandarBlasterRecordatorios();
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(1);
        respuesta.setMessage("Se corrio el batch mandarBlasterRecordatorios");
        respuesta.setData("Se corrio el batch mandarBlasterRecordatorios");
        return respuesta;
    }

    @GetMapping(value={"/mandarSMSRecordatorios"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>mandarSMSRecordatorio(){
        batchs.mandarSMSRecordatorio();
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(1);
        respuesta.setMessage("Se corrio el batch mandarBlasterRecordatorios");
        respuesta.setData("Se corrio el batch mandarBlasterRecordatorios");
        return respuesta;
    }

    @GetMapping(value={"/recordatorioValidarPromesas"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>recordatorioValidarPromesas(){
        batchs.recordatorioValidarPromesas();
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(1);
        respuesta.setMessage("Se corrio el batch recordatorioValidarPromesas");
        respuesta.setData("Se corrio el batch recordatorioValidarPromesas");
        return respuesta;
    }

    @GetMapping(value={"/eliminacionDeCarteraDiaria"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>eliminacionDeCarteraDiaria(){
        batchs.eliminacionDeCarteraDiaria();
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(1);
        respuesta.setMessage("Se corrio el batch eliminacionDeCarteraDiaria");
        respuesta.setData("Se corrio el batch eliminacionDeCarteraDiaria");
        return respuesta;
    }

    @GetMapping(value={"/resetMontoPagoPromesasPagoDia"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>resetMontoPagoPromesasPagoDia(){
        batchs.resetMontoPagoPromesasPagoDia();
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(1);
        respuesta.setMessage("Se corrio el batch resetMontoPagoPromesasPagoDia");
        respuesta.setData("Se corrio el batch resetMontoPagoPromesasPagoDia");
        return respuesta;
    }

    @GetMapping(value={"/eliminarGestiones7meses"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>eliminarGestiones7meses(){
        batchs.eliminarGestiones7meses();
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(1);
        respuesta.setMessage("Se corrio el batch eliminarGestiones7meses");
        respuesta.setData("Se corrio el batch eliminarGestiones7meses");
        return respuesta;
    }

    @GetMapping(value={"/correrProcesoDescarte"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>correrProcesoDescarte(){
        batchs.correrProcesoDescarte();
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(1);
        respuesta.setMessage("Se corrio el batch correrProcesoDescarte");
        respuesta.setData("Se corrio el batch correrProcesoDescarte");
        return respuesta;
    }

}
