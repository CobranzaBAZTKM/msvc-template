package com.spring.services.gestores.service.impl;

import com.spring.services.gestores.model.GestoresModel;
import com.spring.utils.RestResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

public interface GestoresImpl {

    RestResponse<ArrayList<GestoresModel>> consultarGestoresSCL(GestoresModel clientes);
    RestResponse<String>asignarClientesSCL(GestoresModel clientes,String tipoCarteraTKM);
    RestResponse<ArrayList<GestoresModel>> consultarGestoresTKM();
    RestResponse<String> insertarGestoresTKM(GestoresModel empleado);
    RestResponse<String> actualizarGestoresTKM(GestoresModel empleado);
    RestResponse<String> eliminarGestoresTKM(String idEmpleado);
    RestResponse<String>asignarClientesSCLGestores(String clientes,String tipoCarteraTKM);
}
