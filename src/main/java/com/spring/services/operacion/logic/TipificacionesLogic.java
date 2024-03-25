package com.spring.services.operacion.logic;

import com.spring.services.operacion.dao.TipificacionesDAO;
import com.spring.services.operacion.model.TipificacionesModel;
import com.spring.utils.RestResponse;
import com.spring.utils.UtilService;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TipificacionesLogic {
    TipificacionesDAO tipDAO=new TipificacionesDAO();
    UtilService util=new UtilService();
    public TipificacionesLogic() {
        //Vacio
    }

    public RestResponse<ArrayList<TipificacionesModel>> consultarTipificaciones() {
        return tipDAO.consultarTipificaciones();
    }

    public RestResponse<String> insertarTipificaciones(TipificacionesModel tipif) {

        String[] fecha=util.obtenerFechaActual().split(" ");
        tipif.setFechaInserto(fecha[0]);
        return tipDAO.insertarTipificaciones(tipif);
    }

    public RestResponse<String> actualizarTipificaciones(TipificacionesModel tipif) {
        return tipDAO.actualizarTipificaciones(tipif);
    }

    public RestResponse<String> borrarTipificaciones(String idTipificacion, String idSupervisor) {
        return tipDAO.borrarTipificaciones(idTipificacion,idSupervisor);
    }
}
