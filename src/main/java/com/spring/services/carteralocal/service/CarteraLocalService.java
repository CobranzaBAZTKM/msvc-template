package com.spring.services.carteralocal.service;

import com.spring.services.cartera.logic.CarteraLogic;
import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.carteralocal.logic.CarteraLocalLogic;
import com.spring.services.carteralocal.service.impl.CarteraLocalImpl;
import com.spring.utils.RestResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarteraLocalService implements CarteraLocalImpl {

    CarteraLocalLogic carteraLocLog=new CarteraLocalLogic();
    public CarteraLocalService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(ExtrasModel cokkie) {
        return carteraLocLog.carteraCompletaGuardar(cokkie);
    }


    @Override
    public RestResponse<ArrayList<ClienteModel>> carteraCompletaDia() {
        return carteraLocLog.consultarCarteraCompletaDia();
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> consultarNuevasCuentasDia() {
        return carteraLocLog.nuevasCuentasDia();
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte() {
        return carteraLocLog.consultarCarteraDescarte();
    }

    @Override
    public RestResponse<String> carteraCompletaGuardarLocalPuente(String cartera){
        return carteraLocLog.carteraCompletaGuardarLocalPuente(cartera);
    }

    @Override
    public RestResponse<String>guardarCarteraDescarte(ArrayList<ClienteModel> cuentas){
        return carteraLocLog.guardarCarteraDescarte(cuentas);
    }
}
