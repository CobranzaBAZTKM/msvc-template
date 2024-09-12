package com.spring.services.carteralocal.service.impl;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.utils.RestResponse;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

public interface CarteraLocalImpl {
    RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(ExtrasModel cokkie, String tipoCarteraTKM);
    RestResponse<ArrayList<ClienteModel>> carteraCompletaDia();
    RestResponse<ArrayList<ClienteModel>> consultarNuevasCuentasDia();
    RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte(String tipoCarteraTKM);
    RestResponse<ClienteModel> consultarCUCarteraCompleta(String cu);
    RestResponse<ArrayList<ClienteModel>> consultarCarteraConPromesa(String tipoCarteraTKM);
    RestResponse<ArrayList<ClienteModel>> consultarCuentasSinContacto(String tipoCarteraTKM);
    RestResponse<String> insertarCarteraLocal(String clientes);
    RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarteDiaCompleta();
}
