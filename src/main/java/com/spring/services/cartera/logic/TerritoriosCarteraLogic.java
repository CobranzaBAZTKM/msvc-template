package com.spring.services.cartera.logic;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.cartera.service.impl.TerritoriosCarteraImpl;
import com.spring.utils.RestResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
public class TerritoriosCarteraLogic{

    public TerritoriosCarteraLogic() {
        //Vacio
    }

    CarteraCompletaLogic carteraLogic=new CarteraCompletaLogic();
    public RestResponse<ArrayList<ClienteModel>> TerriSCLcarteraCompleta(ExtrasModel cokkie) {
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();

        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,3);
            sumaIntentos++;
        }
        while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){
            String seg="";
            JSONArray seg5=segmentosCartera.getData().getJSONObject("respuesta").getJSONArray("segmento5");
            JSONArray seg6=segmentosCartera.getData().getJSONObject("respuesta").getJSONArray("segmento6");
            JSONArray seg16=segmentosCartera.getData().getJSONObject("respuesta").getJSONArray("segmento16");
            JSONArray seg28=segmentosCartera.getData().getJSONObject("respuesta").getJSONArray("segmento28");
            JSONArray seg21=segmentosCartera.getData().getJSONObject("respuesta").getJSONArray("segmento21");

            if(seg5.length()>0){
               seg=seg+"5";
            }

            if(seg6.length()>0){
                seg=seg+"6";
            }

            if(seg16.length()>0){
                seg=seg+"16";
            }

            if(seg28.length()>0){
                seg=seg+"28";
            }

            if(seg21.length()>0){
                seg=seg+"21";
            }

            respuesta=carteraLogic.clientesCompletos(segmentosCartera.getData(),Integer.parseInt(seg),cokkie,3);

        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }

        return respuesta;
    }

}
