package com.spring.services.cartera.service.impl;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.utils.RestResponse;

import java.util.ArrayList;

public interface VIPCarteraImpl {
    RestResponse<ArrayList<ClienteModel>> VIPcarteraCompleta(ExtrasModel cokkie);
}
