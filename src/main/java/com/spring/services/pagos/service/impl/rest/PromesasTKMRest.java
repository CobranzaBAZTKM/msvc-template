package com.spring.services.pagos.service.impl.rest;

import com.spring.services.pagos.model.PromesasModel;
import com.spring.services.pagos.service.impl.PromesasTKMImpl;
import com.spring.utils.ConexionBD;
import com.spring.utils.RestResponse;
import com.spring.utils.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/service/promesas")
@CrossOrigin("*")
public class PromesasTKMRest {
    @Autowired
    private PromesasTKMImpl promTKMImp;

    public PromesasTKMRest() {
        //Vacio
    }

    @GetMapping(value = {"/consultarPromesasPP"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<PromesasModel>> consultarPromesas(){
        return promTKMImp.consultarPromesas();
    }

    @PostMapping(value = {"/consultarPromesasPPconGestiones"},
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<ArrayList<PromesasModel>> consultarPromesasCGestion(@RequestBody final String cookie){
        return promTKMImp.consultarPromesasCGestion(cookie);
    }

    @PostMapping(value={"/insertarPromesas"},
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> insertarPromesas(@RequestBody final PromesasModel promesa){
        return promTKMImp.insertarPromesas(promesa);
    }

    @PostMapping(value={"/actualizarPromesas"},
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> actualizarPromesas(@RequestBody final PromesasModel promesa){
        return promTKMImp.actualizarPromesas(promesa);
    }

    @GetMapping(value = {"/borrarPromesasPP/{idPromesa}/{idAdmin}"},
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> borrarPromesas(@PathVariable String idPromesa,@PathVariable String idAdmin){
        return promTKMImp.borrarPromesas(idPromesa,idAdmin);
    }

    @PostMapping(value={"/actualizarPromesasEstPag"},
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String> actualizarPromesasEstPag(@RequestBody final PromesasModel promesa){
        return promTKMImp.actualizarPromesasEstPag(promesa);
    }

    @PostMapping(value={"/actualizarMontoPromesa"},
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public RestResponse<String>actualizarMontoPromesa(@RequestBody final ArrayList<PromesasModel> montos){
        return promTKMImp.actualizarMontoPromesa(montos);
    }

}
