package com.spring.services.cartera.service.impl.rest;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.DiezYearCarteraImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/service/carteraDiezYear")
public class DiezYearCarteraRest {
    @Autowired
    private DiezYearCarteraImpl diezYearCarImpl;

    @PostMapping(value = "/SCL/carteraCompleta",
            consumes = {"application/json"},
            produces = {"application/json"})
    public RestResponse<ArrayList<ClienteModel>> DiezYearSCLCarteraCompleta(@RequestBody final ExtrasModel cokkie) {
        return diezYearCarImpl.DiezYearSCLCarteraCompleta(cokkie);
    }

}
