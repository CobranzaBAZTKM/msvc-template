package com.spring.services.operacion.logic;

import com.spring.services.operacion.dao.GestionLlamadasDAO;
import com.spring.services.operacion.model.GestionLlamadasModel;
import com.spring.utils.RestResponse;
import com.spring.utils.UtilService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GestionLlamadasLogic {

    GestionLlamadasDAO gestLlamDAO=new GestionLlamadasDAO();
    UtilService util=new UtilService();
    public GestionLlamadasLogic() {
        //Vacio
    }

    public RestResponse<ArrayList<GestionLlamadasModel>> consultarGestionLlamadas() {
        return gestLlamDAO.consultarGestionLlamadas();
    }

    public RestResponse<String> insertarGestionLlamadas(GestionLlamadasModel gestLlam) {
        String[] fecha=util.obtenerFechaActual().split(" ");
        gestLlam.setFechaInserto(fecha[0]);
        gestLlam.setHoraInserto(fecha[1]);
        return gestLlamDAO.insertarGestionLlamadas(gestLlam);
    }

    public RestResponse<String> actualizarGestionLlamadas(GestionLlamadasModel gestLlam) {
        return gestLlamDAO.actualizarGestionLlamadas(gestLlam);
    }

    public RestResponse<String> borrarGestionLlamadas(String idGestion, String idSupervisor) {
        return gestLlamDAO.borrarGestionLlamadas(idGestion,idSupervisor);
    }
}
