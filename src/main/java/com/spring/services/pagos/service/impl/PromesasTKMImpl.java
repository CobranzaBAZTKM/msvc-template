package com.spring.services.pagos.service.impl;

import com.spring.services.pagos.model.PromesasModel;
import com.spring.utils.RestResponse;

import java.util.ArrayList;

public interface PromesasTKMImpl {
    RestResponse<ArrayList<PromesasModel>> consultarPromesas();
    RestResponse<ArrayList<PromesasModel>> consultarPromesasCGestion(String cookie);
    RestResponse<String> insertarPromesas(PromesasModel promesa);
    RestResponse<String> actualizarPromesas(PromesasModel promesa);
    RestResponse<String> borrarPromesas(String idPromesa,String idAdmin);
    RestResponse<String> actualizarPromesasEstPag(PromesasModel promesa);

}
