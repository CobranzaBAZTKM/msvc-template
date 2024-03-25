package com.spring.services.cartera.logic;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.utils.RestResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.http.conn.ssl.SSLContexts;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;

@Component
public class CarteraCompletaLogic {

    private static final Logger LOGGER = LogManager.getLogger("CarteraCompletaLogic");
    private static String cookie;

//    @Autowired
//    private CarteraSetearDatosLogic setearDatosLogic;
    CarteraSetearDatosLogic setearDatosLogic=new CarteraSetearDatosLogic();

    @Value("${cartera.segmentos.uno}")
    private String urlCarteraSegmentosUno;

    @Value("${cartera.segmentos.clientes}")
    private String urlCarteraSegmentosClientes;

    public RestResponse<JSONObject> obtenerSegmentosCartera(ExtrasModel cokkie) {
        cookie=cokkie.getCookie();
        RestResponse<JSONObject> respuesta = new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);


        CloseableHttpResponse serviceResponse = null;

        try {

            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpGet serviceRequest = new HttpGet("https://www.sclpcj.com.mx:7071/CyCRest/cartera-download/search/58921");
            serviceRequest.addHeader("Cookie", cookie);
            serviceResponse = client.execute((HttpUriRequest)serviceRequest);

            int respStatus=serviceResponse.getStatusLine().getStatusCode();
            if(respStatus==200||respStatus==201){
                JsonObject serviceObject = Json.createReader(serviceResponse.getEntity().getContent()).readObject();


                JSONObject jsonGenerado = new JSONObject(serviceObject.toString());
                respuesta.setCode(1);
                respuesta.setMessage("Valores Obtenidos");
                respuesta.setData(jsonGenerado);
                respuesta.setError(false);

            }
            else{
                respuesta.setMessage("Algo fallo en la consulta");
                respuesta.setError(false);
            }
        }
        catch(Exception e){
            respuesta.setMessage("Algo fallo "+ e);
            respuesta.setError(true);
        }

        return respuesta;
    }



    public RestResponse<ArrayList<ClienteModel>> clientesCompletos(JSONObject jsonGenerado,int segmento, ExtrasModel cokkie) {
        cookie=cokkie.getCookie();
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        try{
            ArrayList<ClienteModel> resp=new ArrayList<>();

            RestResponse<ArrayList<String>> clienSeg5=new RestResponse<>();
            RestResponse<ArrayList<String>> clienSeg6=new RestResponse<>();
            RestResponse<ArrayList<String>> clienSeg16=new RestResponse<>();
            RestResponse<ArrayList<String>> clienSeg28=new RestResponse<>();
            RestResponse<ArrayList<String>> clienSeg21=new RestResponse<>();

            ArrayList<ClienteModel>datosSeg5=new ArrayList<>();
            ArrayList<ClienteModel>datosSeg6=new ArrayList<>();
            ArrayList<ClienteModel>datosSeg16=new ArrayList<>();
            ArrayList<ClienteModel>datosSeg28=new ArrayList<>();
            ArrayList<ClienteModel>datosSeg21=new ArrayList<>();

            switch(segmento){
                case 5:
                    clienSeg5=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento5"),5);
                    datosSeg5=setearDatosLogic.setearDatos(clienSeg5.getData(),5);
                    resp.addAll(datosSeg5);
                    break;
                case 6:
                    clienSeg6=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento6"),6);
                    datosSeg6=setearDatosLogic.setearDatos(clienSeg6.getData(),6);
                    resp.addAll(datosSeg6);
                    break;

                case 16:
                    clienSeg16=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento16"),16);
                    datosSeg16=setearDatosLogic.setearDatos(clienSeg16.getData(),16);
                    resp.addAll(datosSeg16);
                    break;

                case 28:
                    clienSeg28=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento28"),28);
                    datosSeg28=setearDatosLogic.setearDatos(clienSeg28.getData(),28);
                    resp.addAll(datosSeg28);
                    break;

                case 21:
                    clienSeg21=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento21"),21);
                    datosSeg21=setearDatosLogic.setearDatos(clienSeg21.getData(),21);
                    resp.addAll(datosSeg21);
                    break;

                case 616:
                    clienSeg6=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento6"),6);
                    clienSeg16=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento16"),16);
                    datosSeg6=setearDatosLogic.setearDatos(clienSeg6.getData(),6);
                    datosSeg16=setearDatosLogic.setearDatos(clienSeg16.getData(),16);
                    resp.addAll(datosSeg6);
                    resp.addAll(datosSeg16);
                    break;

                case 5616:
                    clienSeg5=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento5"),5);
                    clienSeg6=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento6"),6);
                    clienSeg16=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento16"),16);
                    datosSeg5=setearDatosLogic.setearDatos(clienSeg5.getData(),5);
                    datosSeg6=setearDatosLogic.setearDatos(clienSeg6.getData(),6);
                    datosSeg16=setearDatosLogic.setearDatos(clienSeg16.getData(),16);
                    resp.addAll(datosSeg5);
                    resp.addAll(datosSeg6);
                    resp.addAll(datosSeg16);
                    break;

                case 561628:
                    clienSeg5=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento5"),5);
                    clienSeg6=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento6"),6);
                    clienSeg16=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento16"),16);
                    clienSeg28=clientesXSegmentos( jsonGenerado.getJSONObject("respuesta").getJSONArray("segmento28"),28);
                    datosSeg5=setearDatosLogic.setearDatos(clienSeg5.getData(),5);
                    datosSeg6=setearDatosLogic.setearDatos(clienSeg6.getData(),6);
                    datosSeg16=setearDatosLogic.setearDatos(clienSeg16.getData(),16);
                    datosSeg28=setearDatosLogic.setearDatos(clienSeg28.getData(),28);
                    resp.addAll(datosSeg5);
                    resp.addAll(datosSeg6);
                    resp.addAll(datosSeg16);
                    resp.addAll(datosSeg28);
                    break;
                default:
                    //vacio


            }

            respuesta.setCode(1);
            respuesta.setMessage("Proceso ejecutado correctamente");
            respuesta.setData(resp);
            respuesta.setError(false);

        }
        catch (Exception e){
            respuesta.setMessage("Algo fallo "+ e);
            respuesta.setError(true);
        }
        return respuesta;
    }

    public RestResponse<ArrayList<String>> clientesXSegmentos(JSONArray jsonGenerado, int segmento){
        RestResponse<ArrayList<String>> respuesta=new RestResponse<>();
        ArrayList<String> clientes=new ArrayList<>();
        Integer suma=0;

        JSONObject jsonRequest=new JSONObject();
        jsonRequest.put("idDespacho",58921);
        jsonRequest.put("idSegmento",segmento);

        for(int i=0;i<jsonGenerado.length();i++){
            JSONArray jsonArray=jsonGenerado.getJSONArray(i);
            jsonRequest.put("gerencias",jsonArray);
            RestResponse<String[]>obtenerClientes=new RestResponse<>();

            do{
                obtenerClientes=obtenerDatosClientes(jsonRequest);
                LOGGER.log(Level.INFO, () -> "Estamos en el do-while posiblemente mandando nuevamente la peticion de una parte del segmento: " + segmento);
            }while(obtenerClientes.getCode()!=1);

            suma=suma+obtenerClientes.getData().length-1;
            for(int j=1;j<obtenerClientes.getData().length;j++){
                clientes.add(obtenerClientes.getData()[j]);
            }
        }

        String mensajeLog= "Se encontraron: " + suma + " clientes del segmento: "+ segmento;
        LOGGER.log(Level.INFO, () -> mensajeLog);

        respuesta.setCode(1);
        respuesta.setMessage("Proceso terminado. " +mensajeLog);
        respuesta.setData(clientes);
        respuesta.setError(false);

        return respuesta;
    }




    public RestResponse<String[]>obtenerDatosClientes(JSONObject jsonRequest){
        RestResponse<String[]> respuesta = new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);
        CloseableHttpResponse serviceResponse = null;
        try{
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpPost serviceRequest = new HttpPost("https://www.sclpcj.com.mx:7071/CyCRest/cartera-download/get");
            StringEntity post = new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
            serviceRequest.setEntity((HttpEntity)post);
            serviceRequest.addHeader("Cookie", cookie);
            serviceResponse = client.execute((HttpUriRequest)serviceRequest);
            int respStatus=serviceResponse.getStatusLine().getStatusCode();

            if(respStatus==200||respStatus==201){
                String clientes= EntityUtils.toString(serviceResponse.getEntity());
                String[] clientesFinales= clientes.split("\n");
//                LOGGER.log(Level.INFO, () -> "REQUEST Servicio: " + clientes);
                respuesta.setCode(1);
                respuesta.setMessage("Clientes Consultados correctamente");
                respuesta.setData(clientesFinales);
                respuesta.setError(false);
            }
            else{
                respuesta.setMessage("Sin datos del servicio");
            }
        }
        catch(Exception e){
            respuesta.setMessage("Algo fallo "+ e);
            respuesta.setError(true);
        }

        return respuesta;
    }

}
