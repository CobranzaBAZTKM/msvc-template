package com.spring.services.gestores.service;

import com.spring.services.gestores.logic.GestoresLogic;
import com.spring.services.gestores.model.GestoresModel;
import com.spring.services.gestores.service.impl.GestoresImpl;
import com.spring.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class GestoresService  implements GestoresImpl {

    @Autowired
    private GestoresLogic gesLogic;

    public GestoresService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<GestoresModel>> consultarGestoresSCL(GestoresModel clientes) {
        return gesLogic.consultarGestoresSCL(clientes);
    }

    @Override
    public RestResponse<String> asignarClientesSCL(GestoresModel clientes) {
        return gesLogic.asignarClientesSCL(clientes);
    }

    @Override
    public RestResponse<ArrayList<GestoresModel>> consultarGestoresTKM() {
        return gesLogic.consultarGestoresTKM();
    }

    @Override
    public RestResponse<String> insertarGestoresTKM(GestoresModel empleado){
        return gesLogic.insertarGestoresTKM(empleado);
    }

    @Override
    public RestResponse<String> actualizarGestoresTKM(GestoresModel empleado){
        return gesLogic.actualizarGestoresTKM(empleado);
    }

    @Override
    public RestResponse<String> eliminarGestoresTKM(String idEmpleado){
        return gesLogic.eliminarGestoresTKM(idEmpleado);
    }

    @Override
    public RestResponse<String> asignarClientesSCLGestores(String clientes) {
        return gesLogic.asignarClientesSCLGestores(clientes);
    }


}
