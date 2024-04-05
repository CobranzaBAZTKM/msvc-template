package com.spring.services.carteralocal.service.impl;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.utils.RestResponse;

import java.util.ArrayList;

public interface CarteraLocalImpl {
    RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(ExtrasModel cokkie);
    RestResponse<ArrayList<ClienteModel>> carteraCompletaDia();
    RestResponse<ArrayList<ClienteModel>> consultarNuevasCuentasDia();
    RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte();
    RestResponse<String> carteraCompletaGuardarLocalPuente(String cartera);
    RestResponse<String>guardarCarteraDescarte(ArrayList<ClienteModel> cuentas);
}
