package com.spring.services.cartera.service.impl.rest;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.VIPCarteraImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/service/carteraVIP")
public class VIPCarteraVIP {

    @Autowired
    private VIPCarteraImpl vipCarteraImpl ;

    public VIPCarteraVIP() {
        //Vacia
    }

    @PostMapping(value = "/VIPcarteraCompleta",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> VIPcarteraCompleta(@RequestBody final ExtrasModel cokkie) {
        return vipCarteraImpl.VIPcarteraCompleta(cokkie);
    }
}
