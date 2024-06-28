package com.spring.services.cartera.logic;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.carteralocal.logic.CarteraLocalLogic;
import com.spring.utils.RestResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VIPCarteraLogic {

    CarteraCompletaLogic carteraLogic=new CarteraCompletaLogic();

    public VIPCarteraLogic() {
        //Vacio
    }

    public RestResponse<ArrayList<ClienteModel>> VIPcarteraCompleta(ExtrasModel cokkie) {
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,2);
            sumaIntentos++;
        }
        while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){
            respuesta=carteraLogic.clientesCompletos(segmentosCartera.getData(),5628,cokkie,2);
        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;
    }
}
