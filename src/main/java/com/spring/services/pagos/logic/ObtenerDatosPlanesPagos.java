package com.spring.services.pagos.logic;

import com.spring.services.pagos.model.datosEntradaPagosPlanes;
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
public class ObtenerDatosPlanesPagos {

    private static String cookie;

    @Value("${pagos.planes}")
    private String urlPagosPlanes;

    @Value("${cartera.segmentos.clientegestor}")
    private String urlClienteGestor;

    @Value("${pagos.edocuentas}")
    private String urlEdoCuentas;

    @Value("${pagos.edocuentasabonos}")
    private String urlAbonosEdoCuentas;

    public ObtenerDatosPlanesPagos() {
        //Vacio
    }

    public RestResponse<JSONObject> obtenerPlanesCU(String cu, datosEntradaPagosPlanes cokkie){
        cookie=cokkie.getCookiePlanes();
        RestResponse<JSONObject> respuesta = new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);

        CloseableHttpResponse serviceResponse = null;
        try {
            String[] cuSeparado = cu.split("-");
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpGet serviceRequest = new HttpGet(urlPagosPlanes+"/"+cuSeparado[0]+"/"+cuSeparado[1]+"/"+cuSeparado[2]+"/"+cuSeparado[3]+"/planes");
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

    public RestResponse<JSONObject> obtenerPrometidos(String idPlan, String cu, datosEntradaPagosPlanes cokkie){
        cookie=cokkie.getCookiePlanes();
        RestResponse<JSONObject> respuesta = new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);

        CloseableHttpResponse serviceResponse = null;
        try {
            String[] cuSeparado = cu.split("-");
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpGet serviceRequest = new HttpGet(urlPagosPlanes+"/"+cuSeparado[0]+"/"+cuSeparado[1]+"/"+cuSeparado[2]+"/"+cuSeparado[3]+"/planes/"+idPlan+"/pagos/prometidos");
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

    public RestResponse<JSONObject> obtenerReales(String idPlan, String cu, datosEntradaPagosPlanes cokkie){
        cookie=cokkie.getCookiePlanes();
        RestResponse<JSONObject> respuesta = new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);

        CloseableHttpResponse serviceResponse = null;
        try {
            String[] cuSeparado = cu.split("-");
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpGet serviceRequest = new HttpGet(urlPagosPlanes+"/"+cuSeparado[0]+"/"+cuSeparado[1]+"/"+cuSeparado[2]+"/"+cuSeparado[3]+"/planes/"+idPlan+"/pagos/reales");
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

    public String obtenerGestorCliente(String cu, datosEntradaPagosPlanes cookie2){
        String respuesta="NSOG";
        CloseableHttpResponse serviceResponse = null;
        try {
            String[] cuSeparado = cu.split("-");
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpGet serviceRequest = new HttpGet(urlClienteGestor+"/1/"+cuSeparado[1]+"/"+cuSeparado[2]+"/"+cuSeparado[3]);
            serviceRequest.addHeader("Cookie", cookie2.getCookieGestores());
            serviceResponse = client.execute((HttpUriRequest)serviceRequest);
            int respStatus=serviceResponse.getStatusLine().getStatusCode();
            if(respStatus==200||respStatus==201){
                JsonObject serviceObject = Json.createReader(serviceResponse.getEntity().getContent()).readObject();
                JSONObject jsonGenerado = new JSONObject(serviceObject.toString());
                respuesta= (String) jsonGenerado.getJSONObject("respuesta").get("gestor");
            }


        }
        catch(Exception e){
            respuesta="Algo fallo "+e;

        }

        return respuesta;

    }

    public RestResponse<JSONObject> obtenerEstadoCuenta(String cu, datosEntradaPagosPlanes cokkie){
        RestResponse<JSONObject> respuesta = new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);

        CloseableHttpResponse serviceResponse = null;

        try{
            String[] cuSeparado = cu.split("-");
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpGet serviceRequest = new HttpGet(urlEdoCuentas+"/"+cuSeparado[0]+"/"+cuSeparado[1]+"/"+cuSeparado[2]+"/"+cuSeparado[3]+"/LT48022924");
//            HttpGet serviceRequest = new HttpGet(urlEdoCuentas+"/"+cuSeparado[0]+"/"+cuSeparado[1]+"/"+cuSeparado[2]+"/"+cuSeparado[3]+"/LT48022041");
            serviceRequest.addHeader("Cookie", cokkie.getCookieGestores());
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


    public RestResponse<JSONObject> obtenerAbonosEdoCuenta(String cu, datosEntradaPagosPlanes cokkie){
        RestResponse<JSONObject> respuesta = new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);

        CloseableHttpResponse serviceResponse = null;

        try {
            String[] cuSeparado = cu.split("-");
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
//            HttpGet serviceRequest = new HttpGet(urlAbonosEdoCuentas+"/"+cuSeparado[0]+"/"+cuSeparado[1]+"/"+cuSeparado[2]+"/"+cuSeparado[3]);
            HttpGet serviceRequest = new HttpGet("https://www.sclpcj.com.mx:7071/CyCRest/edoCuentaCU/abonosCliente/"+cuSeparado[0]+"/"+cuSeparado[1]+"/"+cuSeparado[2]+"/"+cuSeparado[3]);
            serviceRequest.addHeader("Cookie", cokkie.getCookieGestores());
            serviceResponse = client.execute((HttpUriRequest)serviceRequest);
            int respStatus=serviceResponse.getStatusLine().getStatusCode();
            JsonObject serviceObject = Json.createReader(serviceResponse.getEntity().getContent()).readObject();
            JSONObject jsonGenerado = new JSONObject(serviceObject.toString());
            if(respStatus==200||respStatus==201){

                respuesta.setCode(1);
                respuesta.setMessage("Valores Obtenidos");
                respuesta.setData(jsonGenerado);
                respuesta.setError(false);
            }
            else if(respStatus==404&&jsonGenerado.getJSONObject("respuesta").getJSONArray("errores").getString(0).contains("cliente fallida")){
                respuesta.setCode(1);
                respuesta.setMessage("Valores Obtenidos");
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

    public RestResponse<JSONObject> obtenerPagosReporteSCl(String cookie, String json){
        RestResponse<JSONObject> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);

        CloseableHttpResponse serviceResponse = null;
        try{
            JSONObject jsonRequest=new JSONObject(json);
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpPost serviceRequest = new HttpPost("https://www.sclpcj.com.mx:7071/CyCRest/cobranza-despachos/pagos");
            StringEntity post = new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
            serviceRequest.setEntity((HttpEntity)post);
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



}
