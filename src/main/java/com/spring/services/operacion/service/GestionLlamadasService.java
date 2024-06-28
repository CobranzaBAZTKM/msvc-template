package com.spring.services.operacion.service;

import com.spring.services.operacion.logic.GestionLlamadasLogic;
import com.spring.services.operacion.model.GestionLlamadasModel;
import com.spring.services.operacion.service.impl.GestionLlamadasImpl;
import com.spring.utils.RestResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GestionLlamadasService implements GestionLlamadasImpl {
    GestionLlamadasLogic gestLog=new GestionLlamadasLogic();
    public GestionLlamadasService() {
        //Vacio
    }


    @Override
    public RestResponse<ArrayList<GestionLlamadasModel>> consultarGestionLlamadas() {
        return gestLog.consultarGestionLlamadas();
    }

    @Override
    public RestResponse<String> insertarGestionLlamadas(GestionLlamadasModel gestLlam) {
        return gestLog.insertarGestionLlamadas(gestLlam);
    }

    @Override
    public RestResponse<String> actualizarGestionLlamadas(GestionLlamadasModel gestLlam) {
        return gestLog.actualizarGestionLlamadas(gestLlam);
    }

    @Override
    public RestResponse<String> borrarGestionLlamadas(String idGestion, String idSupervisor) {
        ArrayList<String> idGest=new ArrayList<>();
        idGest.add(idGestion);
        return gestLog.borrarGestionLlamadas(idGest,idSupervisor);
    }
}
