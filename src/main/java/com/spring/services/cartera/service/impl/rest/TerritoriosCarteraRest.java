package com.spring.services.cartera.service.impl.rest;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.TerritoriosCarteraImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/service/carteraTerritorios")
public class TerritoriosCarteraRest {

    @Autowired
    private TerritoriosCarteraImpl terrCarImpl;
    public TerritoriosCarteraRest() {
        //Vacio
    }

    @PostMapping(value = "/SCL/carteraCompleta",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> TerriSCLcarteraCompleta(@RequestBody final ExtrasModel cokkie) {
        return terrCarImpl.TerriSCLcarteraCompleta(cokkie);
    }
}
