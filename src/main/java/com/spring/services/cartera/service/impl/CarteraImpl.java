package com.spring.services.cartera.service.impl;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.utils.RestResponse;


import java.util.ArrayList;

public interface CarteraImpl {
    RestResponse<ArrayList<ClienteModel>> carteraCompleta(ExtrasModel cookie);
//    RestResponse<ArrayList<ClienteModel>> carteraSeg5(ExtrasModel cookie);
//    RestResponse<ArrayList<ClienteModel>> carteraSeg6(ExtrasModel cokkie);
//    RestResponse<ArrayList<ClienteModel>> carteraSeg16(ExtrasModel cokkie);
//    RestResponse<ArrayList<ClienteModel>> carteraSeg28(ExtrasModel cokkie);
//    RestResponse<ArrayList<ClienteModel>> carteraSegTipo(ExtrasModel cokkie);

//    RestResponse<String> carteraCompleta(ExtrasModel cookie);
    RestResponse<String> carteraSeg5(ExtrasModel cookie);
    RestResponse<String> carteraSeg6(ExtrasModel cokkie);
    RestResponse<String> carteraSeg6SNNITC (ExtrasModel cokkie);
    RestResponse<String> carteraSeg16(ExtrasModel cokkie);
    RestResponse<String> carteraSeg16SNNITC (ExtrasModel cokkie);
    RestResponse<String> carteraSeg28(ExtrasModel cokkie);
    RestResponse<String> carteraSegTipo(ExtrasModel cokkie);
    RestResponse<String> carteraSaldos(ExtrasModel cokkie);
    RestResponse<String> blasterSegmento(ExtrasModel cokkie);
    RestResponse<ArrayList<ClienteModel>>carteraSegmentoEleg(ExtrasModel cokkie);
    RestResponse<ArrayList<ClienteModel>> numerosListasSegmento(ExtrasModel cokkie);
}
