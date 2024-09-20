package com.spring.services.operacion.service.impl;

import com.spring.services.operacion.model.GestionLlamadasModel;
import com.spring.utils.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

public interface GestionLlamadasImpl {
    RestResponse<ArrayList<GestionLlamadasModel>>consultarGestionLlamadas();
    RestResponse<String> insertarGestionLlamadas(GestionLlamadasModel gestLlam);
    RestResponse<String> actualizarGestionLlamadas(GestionLlamadasModel gestLlam);
    RestResponse<String> borrarGestionLlamadas(String idGestion, String idSupervisor);
    RestResponse<ArrayList<GestionLlamadasModel>> consultarGestionLlamadasNumero(String numero);
}
