package com.spring.services.cartera.service;

import com.spring.services.cartera.logic.BlastersSMSLogic;
import com.spring.services.cartera.logic.CarteraLogic;
import com.spring.services.cartera.logic.ObtenerClientesCartera;
import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.CarteraImpl;
import com.spring.utils.RestResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarteraService implements CarteraImpl {

    @Autowired
    private ObtenerClientesCartera ccLogic;

    @Autowired
    private CarteraLogic carteraLogic;

    @Autowired
    private BlastersSMSLogic blastersSMSLogic;


    public CarteraService() {
        //Vacio
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> carteraCompleta(ExtrasModel cokkie){
//    public RestResponse<String> carteraCompleta(ExtrasModel cokkie){
        return ccLogic.carteraCompleta(cokkie);
    }

    @Override
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg5(ExtrasModel cokkie) {
    public RestResponse<String> carteraSeg5(ExtrasModel cokkie) {
        return carteraLogic.carteraSeg5(cokkie);
    }

    @Override
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg6(ExtrasModel cokkie) {
    public RestResponse<String> carteraSeg6(ExtrasModel cokkie) {
        return carteraLogic.carteraSeg6(cokkie);
    }

    @Override
    public RestResponse<String> carteraSeg6SNNITC(ExtrasModel cokkie) {
        return carteraLogic.carteraSeg6SNNITC(cokkie);
    }

    @Override
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg16(ExtrasModel cokkie) {
    public RestResponse<String> carteraSeg16(ExtrasModel cokkie) {
        return carteraLogic.carteraSeg16(cokkie);
    }

    @Override
    public RestResponse<String> carteraSeg16SNNITC(ExtrasModel cokkie) { return carteraLogic.carteraSeg16SNNITC(cokkie); }

    @Override
//    public RestResponse<ArrayList<ClienteModel>> carteraSeg28(ExtrasModel cokkie) {
    public RestResponse<String> carteraSeg28(ExtrasModel cokkie) {
        return carteraLogic.carteraSeg28(cokkie);
    }

    @Override
//    public RestResponse<ArrayList<ClienteModel>> carteraSegTipo(ExtrasModel cokkie) {
    public RestResponse<String> carteraSegTipo(ExtrasModel cokkie) {
        return carteraLogic.carteraSegTipo(cokkie);
    }

    @Override
    public RestResponse<String> carteraSaldos(ExtrasModel cokkie){ return carteraLogic.carteraSaldos(cokkie);}

    @Override
    public RestResponse<String> blasterSegmento(ExtrasModel cokkie){ return blastersSMSLogic.blasterSegmento(cokkie);}

    @Override
    public RestResponse<ArrayList<ClienteModel>>carteraSegmentoEleg(ExtrasModel cokkie){
        return carteraLogic.carteraSegmentoEleg(cokkie);
    }

    @Override
    public RestResponse<ArrayList<ClienteModel>> numerosListasSegmento(ExtrasModel cokkie){
        return blastersSMSLogic.numerosListasSegmento(cokkie);
    }

}
