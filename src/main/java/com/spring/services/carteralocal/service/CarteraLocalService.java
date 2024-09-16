package com.spring.services.carteralocal.service;

import com.spring.services.cartera.logic.CarteraLogic;
import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.carteralocal.logic.CarteraLocalLogic;
import com.spring.services.carteralocal.logic.CarteraLocalLogic2;
import com.spring.services.carteralocal.service.impl.CarteraLocalImpl;
import com.spring.utils.RestResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarteraLocalService implements CarteraLocalImpl {

    CarteraLocalLogic carteraLocLog=new CarteraLocalLogic();
    CarteraLocalLogic2 carteraLocLog2=new CarteraLocalLogic2();
    public CarteraLocalService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(ExtrasModel cokkie, String tipoCarteraTKM) {
        return carteraLocLog.carteraCompletaGuardar(cokkie,tipoCarteraTKM);
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
    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte(String tipoCartera) {
        return carteraLocLog.consultarCarteraDescarte(tipoCartera);
    }

    @Override
    public RestResponse<ClienteModel> consultarCUCarteraCompleta(String cu){
        return carteraLocLog.consultarCUCarteraCompleta(cu);
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> consultarCarteraConPromesa(String tipoCarteraTKM){
        return carteraLocLog.consultarCarteraConPromesa(tipoCarteraTKM);
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> consultarCuentasSinContacto(String tipoCarteraTKM) {
        return carteraLocLog.consultarCuentasSinContacto(tipoCarteraTKM);
    }

    @Override
    public RestResponse<String> insertarCarteraLocal(String clientes) {
        return carteraLocLog.insertarCarteraLocal(clientes);
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarteDiaCompleta(){
        return carteraLocLog.consultarCarteraDescarteDiaCompleta();
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> consultarBaseCompletaPorCartera(String tipoCarteraTKM){
        return carteraLocLog.consultarBaseCompletaPorCartera(tipoCarteraTKM);
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> buscarTitularesNumeros(String numero){
        return carteraLocLog2.buscarTitularesNumeros(numero);
    }
}
