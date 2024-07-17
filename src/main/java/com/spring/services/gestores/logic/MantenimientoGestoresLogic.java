package com.spring.services.gestores.logic;

import com.spring.services.gestores.model.GestoresModel;
import com.spring.utils.RestResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObject;

@Component
public class MantenimientoGestoresLogic {

    @Value("${gestores.obtenerGestores}")
    private String urlObtenerGestores;

    @Value("${gestores.asignaCartera}")
    private String urlAsignaCartera;

    @Value("${gestores.obtenerGestionesSCL}")
    private String urlGestiones;


    public MantenimientoGestoresLogic() {
        //Vacio
    }

    public RestResponse<JSONObject> obtenerGestoresSCL(GestoresModel clientes){
        RestResponse<JSONObject> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);
        CloseableHttpResponse serviceResponse = null;
        try{
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpGet serviceRequest = new HttpGet(urlObtenerGestores);
            serviceRequest.addHeader("Cookie", clientes.getCokkie());
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

    public RestResponse<JSONObject> asignarClientesGes(GestoresModel cookie,String cu, String idGestor,String tipoCarteraTKM){
        RestResponse<JSONObject> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);
        CloseableHttpResponse serviceResponse = null;
        try{
            String usuario=null;
            switch(tipoCarteraTKM){
                case "1":
                    usuario="LT48022924";
                    break;
                case "2":
                    usuario="LT48023012";
                    break;
                case "3":
                    usuario="LT48023015";
                    break;
                case "4":
                    usuario="LT48023029";
                    break;
                default:
                    //Vacio
                    break;
            }

//            if("1".equals(tipoCarteraTKM)){
//                usuario="LT48022924";
//            }
//            else{
//                usuario="LT48023012";
//            }
            String[] cuPreparado=cu.split("-");
            JSONObject jsonRequest=new JSONObject();
            jsonRequest.put("opcion",1);
            jsonRequest.put("idGestor",idGestor);
//            jsonRequest.put("usuario","LT48022041");
            jsonRequest.put("usuario",usuario);
            jsonRequest.put("pais", cuPreparado[0]);
            jsonRequest.put("canal",cuPreparado[1]);
            jsonRequest.put("sucursal",cuPreparado[2]);
            jsonRequest.put("folio",cuPreparado[3]);
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpPost serviceRequest = new HttpPost(urlAsignaCartera);
            StringEntity post = new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
            serviceRequest.setEntity((HttpEntity)post);
            serviceRequest.addHeader("Cookie", cookie.getCokkie());
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

    public RestResponse<JSONObject> consultarGestiones(String cookie,String cu){
        RestResponse<JSONObject> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);
        CloseableHttpResponse serviceResponse = null;
        try{
            JSONObject jsonRequest=new JSONObject();
            jsonRequest.put("cu",cu);
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpPost serviceRequest = new HttpPost("https://www.sclpcj.com.mx:7071/CyCRest/scl-gestiones/consultar");
            StringEntity post = new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
            serviceRequest.setEntity((HttpEntity)post);
            serviceRequest.addHeader("Cookie", cookie);
            serviceResponse = client.execute((HttpUriRequest)serviceRequest);
            int respStatus=serviceResponse.getStatusLine().getStatusCode();
            if(respStatus==200||respStatus==201){
                JsonObject serviceObject = Json.createReader(serviceResponse.getEntity().getContent()).readObject();

                JSONObject jsonGenerado = new JSONObject(serviceObject.toString());
                respuesta.setCode(1);
                respuesta.setMessage("Gestiones Obtenidas");
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



}
