package com.spring.services.carteralocal.logic;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.carteralocal.dao.CarteraLocalDAO;
import com.spring.utils.RestResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CarteraLocalLogic2 {

    private CarteraLocalDAO carteraDAO=new CarteraLocalDAO();
    public CarteraLocalLogic2() {
        //Vacio
    }

    public RestResponse<ArrayList<ClienteModel>> buscarTitularesNumeros(String numero){
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        ArrayList<ClienteModel> base=carteraDAO.consultarCarteraCompletaPorCartera("0").getData();
        ArrayList<ClienteModel> datosEncontrados=new ArrayList<>();

        if(base.size()>0){
            for(int i=0;i< base.size();i++){
                if(numero.equals(base.get(i).getTELEFONO1())){
                    datosEncontrados.add(base.get(i));
                }else if(numero.equals(base.get(i).getTELEFONO2())){
                    datosEncontrados.add(base.get(i));
                }else if(numero.equals(base.get(i).getTELEFONO3())){
                    datosEncontrados.add(base.get(i));
                }else if(numero.equals(base.get(i).getTELEFONO4())){
                    datosEncontrados.add(base.get(i));
                }
            }

            respuesta.setCode(1);
            respuesta.setMessage("Resultados de la busqueda");
            respuesta.setData(datosEncontrados);
        }else{
            respuesta.setMessage("No se encontraron base completa");
        }

        return respuesta;
    }
}
