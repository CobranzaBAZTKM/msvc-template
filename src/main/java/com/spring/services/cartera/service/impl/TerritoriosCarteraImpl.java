package com.spring.services.cartera.service.impl;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.utils.RestResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

public interface TerritoriosCarteraImpl {

    RestResponse<ArrayList<ClienteModel>> TerriSCLcarteraCompleta(ExtrasModel cokkie);
}
