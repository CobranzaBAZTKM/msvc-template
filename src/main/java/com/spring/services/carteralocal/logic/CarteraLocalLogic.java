package com.spring.services.carteralocal.logic;

import com.spring.services.cartera.logic.ObtenerClientesCartera;
import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.carteralocal.dao.CarteraLocalDAO;
import com.spring.services.carteralocal.dao.CarteraLocalDAO2;
import com.spring.services.operacion.logic.GestionLlamadasLogic;
import com.spring.services.operacion.logic.TipificacionesLogic;
import com.spring.services.operacion.model.GestionLlamadasModel;
import com.spring.services.operacion.model.TipificacionesModel;
import com.spring.utils.RestResponse;
import com.spring.utils.UtilService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

@Component
public class CarteraLocalLogic {
    private static final Logger LOGGER = LogManager.getLogger("CarteraLocalLogic");
    ObtenerClientesCartera obtenerCartera=new ObtenerClientesCartera();
    GestionLlamadasLogic gestionLlam=new GestionLlamadasLogic();
    TipificacionesLogic tipificaciones=new TipificacionesLogic();
    UtilService utilService=new UtilService();
    CarteraLocalDAO carteraDAO=new CarteraLocalDAO();
    CarteraLocalDAO2 carteraDAO2=new CarteraLocalDAO2();


    public CarteraLocalLogic() {
        //Vacio
    }

    public RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(ExtrasModel cokkie)  {
        LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Comienza proceso");
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>carteraCompleta=obtenerCartera.carteraCompleta(cokkie);
        if(carteraCompleta.getCode()==1){

            ArrayList<ClienteModel> cuentasConPromesa=carteraDAO.consultarCuentasConPromesa().getData();
            ArrayList<ClienteModel> cuentasSinContacto=carteraDAO.consultarCuentasSinContactoTT().getData();

            String fecha=utilService.FechaDiaAnteriorPosterior(-1);
            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Se obtuvieron "+carteraCompleta.getData().size());
            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Comenzando validacion de cuentas con gestion");
            ArrayList<ClienteModel>cuentas=this.revisarGestiones(fecha,carteraCompleta.getData()).getData();

            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Termina validacion de cuentas con gestion");

            this.guardarNuevas(cuentas);


            RestResponse<ArrayList<ClienteModel>>descartarPromesas=this.guardarConPromesa(cuentas,cuentasConPromesa);
            RestResponse<ArrayList<ClienteModel>>descartarNoContacto=this.descartarNoCCTT(descartarPromesas.getData(),cuentasSinContacto);

//            this.guardarCarteraCompletaDia(cuentas);
//            this.guardarCarteraDescarte(descartarNoContacto.getData());

            RestResponse<String>guardarCarteraCompleta=this.puenteServidorExtraGuardarCarteraCompleta(cuentas);
            RestResponse<String>guardarCarteraDescarte=this.puenteServidorExtraGuardarCarteraDescarte(descartarNoContacto.getData());


            respuesta.setCode(1);
            respuesta.setMessage("Cartera Obtenida correctamente");
            respuesta.setData(descartarNoContacto.getData());


        }
        else{
            respuesta=carteraCompleta;
        }

        LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Termina proceso");

        return respuesta;
    }


    public RestResponse<ArrayList<ClienteModel>> nuevasCuentasDia() {
//        RestResponse<ArrayList<ClienteModel>>carteraCompleta=obtCliLog.carteraCompleta(cokkie);
        return carteraDAO.consultarCuentasNuevasDia();
    }


    public RestResponse<ArrayList<ClienteModel>> consultarCarteraCompletaDia() {
        return carteraDAO.consultarCarteraCompleta();
    }


    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte() {
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        String[] fechaHora=utilService.obtenerFechaActual().split(" ");
        String fecha=fechaHora[0];
        ArrayList<ClienteModel> cuentasConPromesa=carteraDAO.consultarCuentasConPromesa().getData();
        ArrayList<ClienteModel> cuentasSinContacto=carteraDAO.consultarCuentasSinContactoTT().getData();
        ArrayList<ClienteModel> cuentasDescarte=carteraDAO2.consultarCarteraDescarte().getData();
        ArrayList<ClienteModel> cuentasGestion=this.revisarGestiones(fecha,cuentasDescarte).getData();


        RestResponse<ArrayList<ClienteModel>>descartarPromesas=this.guardarConPromesa(cuentasGestion,cuentasConPromesa);
        RestResponse<ArrayList<ClienteModel>>descartarNoContacto=this.descartarNoCCTT(descartarPromesas.getData(),cuentasSinContacto);

        respuesta.setCode(1);
        respuesta.setMessage("Proceso terminado correctamente, se obtienen cuentas");
        respuesta.setData(descartarNoContacto.getData());

        return respuesta;
    }









    private RestResponse<String> guardarNuevas(ArrayList<ClienteModel> cartera){
        LOGGER.log(Level.INFO, () -> "guardarNuevas: Comienza guardado de cuentas nuevas");
        RestResponse<String> respuesta=new RestResponse<>();
        ArrayList<ClienteModel>diaAnterior=carteraDAO.consultarCarteraCompleta().getData();
        carteraDAO.borrarNuevasDia();
        ArrayList<ClienteModel> cuentasNuevas=new ArrayList<>();
        for(int i=0;i<cartera.size();i++){
            int valor=0;
            for(int j=0;j<diaAnterior.size();j++){
                if(cartera.get(i).getCLIENTE_UNICO().equals(diaAnterior.get(j).getCLIENTE_UNICO())){
                    valor=1;
                }
            }

            if(valor==0){
                cuentasNuevas.add(cartera.get(i));
            }
        }


        if(cuentasNuevas.size()>0){
            LOGGER.log(Level.INFO, () -> "guardarNuevas: Se insertan "+cuentasNuevas.size()+" cuentasNuevas");
            for(int k=0;k<cuentasNuevas.size();k++){
                carteraDAO.insertarCuentasNuevasDia(cuentasNuevas.get(k));
            }
        }
        carteraDAO.borrarCarteraCompleta();
        carteraDAO2.borrarCarteraDescarte();
        respuesta.setCode(1);
        respuesta.setMessage("Cuenta nuevas ingresadas");
        respuesta.setData("Cuenta nuevas ingresadas");
        LOGGER.log(Level.INFO, () -> "guardarNuevas: Termina guardado de cuentas nuevas");

        return respuesta;

    }

    public RestResponse<String> guardarCarteraCompletaDia(ArrayList<ClienteModel> cartera){
        LOGGER.log(Level.INFO, () -> "guardarCarteraCompletaDia: Comienza guardado de cartera Completa");
        RestResponse<String> respuesta=new RestResponse<>();


        for(int i=0;i<cartera.size();i++){
            carteraDAO.insertarCarteraCompleta(cartera.get(i));
        }

        respuesta.setCode(1);
        respuesta.setData("Registros insertado correctamente");

        LOGGER.log(Level.INFO, () -> "guardarCarteraCompletaDia: Termina guardado de cartera Completa");

        return respuesta;
    }

    private RestResponse<ArrayList<ClienteModel>>guardarConPromesa(ArrayList<ClienteModel> cartera,ArrayList<ClienteModel> cuentasConPromesa){
        LOGGER.log(Level.INFO, () -> "guardarConPromesa: Comienza guardado de cuentas con Promesa con Gestion");
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        ArrayList<ClienteModel>conPromesa=new ArrayList<>();
        ArrayList<ClienteModel>sinPromesaDectada=new ArrayList<>();
        ArrayList<ClienteModel>carteraSinPromesa=new ArrayList<>();

        for(int i=0;i<cartera.size();i++){
            if("6".equals(cartera.get(i).getTIPOTEL1())){
                conPromesa.add(cartera.get(i));
            }
            else if("6".equals(cartera.get(i).getTIPOTEL2())){
                conPromesa.add(cartera.get(i));
            }
            else if("6".equals(cartera.get(i).getTIPOTEL3())){
                conPromesa.add(cartera.get(i));
            }
            else if("6".equals(cartera.get(i).getTIPOTEL4())){
                conPromesa.add(cartera.get(i));
            }else{
                sinPromesaDectada.add(cartera.get(i));
            }
        }


        if(conPromesa.size()>0){
            String[] fechaCompleta=utilService.obtenerFechaActual().split(" ");
            String fecha=fechaCompleta[0];

            for(int j=0;j<conPromesa.size();j++){
                int valor=0;
                for(int k=0;k<cuentasConPromesa.size();k++){
                    if(conPromesa.get(j).getCLIENTE_UNICO().equals(cuentasConPromesa.get(k).getCLIENTE_UNICO())){
                        valor=1;
                    }
                }

                if(valor==0){
                    carteraDAO.insertarCuentaConPromesa(conPromesa.get(j),fecha);
                }

            }
        }

        for(int l=0;l<sinPromesaDectada.size();l++){
            int valor2=0;
            for(int m=0;m<cuentasConPromesa.size();m++){
                if(sinPromesaDectada.get(l).getCLIENTE_UNICO().equals(cuentasConPromesa.get(m).getCLIENTE_UNICO())){
                    valor2=1;
                }
            }
            if(valor2==0){
                carteraSinPromesa.add(sinPromesaDectada.get(l));
            }
        }

        respuesta.setCode(1);
        respuesta.setMessage("Proceso Terminado Correctamente");
        respuesta.setData(carteraSinPromesa);
        LOGGER.log(Level.INFO, () -> "guardarConPromesa: Termina guardado de cuentas con Promesa con Gestion");

        return respuesta;

    }

    private RestResponse<ArrayList<ClienteModel>>descartarNoCCTT(ArrayList<ClienteModel> cuentas,ArrayList<ClienteModel>cuentasSinContacto){
        LOGGER.log(Level.INFO, () -> "descartarNoCCTT: Comienza guardado de cuentas sin Contacto");
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        ArrayList<ClienteModel>cuentasDescartadas=new ArrayList<>();
        ArrayList<ClienteModel>cuentasContacto=new ArrayList<>();
        ArrayList<ClienteModel>carteraContactoFinal=new ArrayList<>();

        for(int i=0; i<cuentas.size();i++){
            if("9".equals(cuentas.get(i).getTIPOTEL1())){
                cuentas.get(i).setTELEFONO1("N/A");
            }
            if("9".equals(cuentas.get(i).getTIPOTEL2())){
                cuentas.get(i).setTELEFONO2("N/A");
            }
            if("9".equals(cuentas.get(i).getTIPOTEL3())){
                cuentas.get(i).setTELEFONO3("N/A");
            }
            if("9".equals(cuentas.get(i).getTIPOTEL4())){
                cuentas.get(i).setTELEFONO4("N/A");
            }


            if("N/A".equals(cuentas.get(i).getTELEFONO1())&&"N/A".equals(cuentas.get(i).getTELEFONO2())&&"N/A".equals(cuentas.get(i).getTELEFONO3())&&"N/A".equals(cuentas.get(i).getTELEFONO4())){
                cuentasDescartadas.add(cuentas.get(i));
            }else{
                cuentasContacto.add(cuentas.get(i));
            }
        }

        if(cuentasDescartadas.size()>0){
            String[] fechaCompleta=utilService.obtenerFechaActual().split(" ");
            String fecha=fechaCompleta[0];
            for(int j=0;j<cuentasDescartadas.size();j++){
                int valor=0;

                for(int k=0;k<cuentasSinContacto.size();k++){
                    if(cuentasDescartadas.get(j).getCLIENTE_UNICO().equals(cuentasSinContacto.get(k).getCLIENTE_UNICO())){
                        valor=1;
                    }
                }

                if(valor==0){
                    carteraDAO.insertarCuentaSinContactoTT(cuentasDescartadas.get(j),fecha);
                }

            }

        }

        for(int l=0;l<cuentasContacto.size();l++){
            int valor2=0;
            for(int m=0;m<cuentasSinContacto.size();m++){
                if(cuentasContacto.get(l).getCLIENTE_UNICO().equals(cuentasSinContacto.get(m).getCLIENTE_UNICO())){
                    valor2=1;
                }
            }

            if(valor2==0){
                carteraContactoFinal.add(cuentasContacto.get(l));
            }
        }


        respuesta.setCode(1);
        respuesta.setMessage("Proceso Terminado Correctamente");
        respuesta.setData(carteraContactoFinal);

        LOGGER.log(Level.INFO, () -> "descartarNoCCTT: Termina guardado de cuentas sin Contacto");

        return respuesta;
    }

    public RestResponse<String>guardarCarteraDescarte(ArrayList<ClienteModel> cuentas){
        LOGGER.log(Level.INFO, () -> "guardarCarteraDescarte: Comienza guardado de cartera con Descarte");
        RestResponse<String> respuesta=new RestResponse<>();
        Collections.sort(cuentas,((o1, o2)-> o1.getSEGMENTO().compareTo(o2.getSEGMENTO())));

        for(int i=0;i<cuentas.size();i++){
            carteraDAO2.insertarCarteraDescarte(cuentas.get(i));
        }

        respuesta.setCode(1);
        respuesta.setData("Registros insertado correctamente");

        LOGGER.log(Level.INFO, () -> "guardarCarteraDescarte: Termina guardado de cartera con Descarte");

        return respuesta;

    }

    private RestResponse<ArrayList<ClienteModel>> revisarGestiones(String fecha,ArrayList<ClienteModel> cartera){
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        ArrayList<GestionLlamadasModel>gestiones=gestionLlam.consultarGestionLlamadas().getData();
        ArrayList<TipificacionesModel>tipificacion=tipificaciones.consultarTipificaciones().getData();
        ArrayList<ClienteModel>cuentas=new ArrayList<>();
        ArrayList<GestionLlamadasModel>gestionesDia=new ArrayList<>();
        for(int l=0;l<gestiones.size();l++){
            if(fecha.equals(gestiones.get(l).getFechaInserto())){
                gestionesDia.add(gestiones.get(l));
            }
        }

        if(gestionesDia.size()>0) {
            for (int i = 0; i < cartera.size(); i++) {
                for (int j = 0; j < gestionesDia.size(); j++) {
                    int valor = 0;
                    for (int k = 0; k < tipificacion.size(); k++) {
                        if (gestionesDia.get(j).getIdTipificacion() == tipificacion.get(k).getId()) {
                            valor = tipificacion.get(k).getValor();
                        }

                    }

                    if (gestionesDia.get(j).getTelefono().equals(cartera.get(i).getTELEFONO1())) {
                        cartera.get(i).setTIPOTEL1("" + valor);
                    } else if (gestionesDia.get(j).getTelefono().equals(cartera.get(i).getTELEFONO2())) {
                        cartera.get(i).setTIPOTEL2("" + valor);
                    } else if (gestionesDia.get(j).getTelefono().equals(cartera.get(i).getTELEFONO3())) {
                        cartera.get(i).setTIPOTEL3("" + valor);
                    } else if (gestionesDia.get(j).getTelefono().equals(cartera.get(i).getTELEFONO4())) {
                        cartera.get(i).setTIPOTEL4("" + valor);
                    }
                }

                cuentas.add(cartera.get(i));
            }
        }
        else{
            cuentas.addAll(cartera);
        }

        respuesta.setCode(1);
        respuesta.setData(cuentas);
        respuesta.setMessage("Proces terminado");

        return respuesta;
    }



    private RestResponse<String> puenteServidorExtraGuardarCarteraCompleta(ArrayList<ClienteModel> cartera){
        RestResponse<String>respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);
        CloseableHttpResponse serviceResponse = null;
        try{
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpPost serviceRequest = new HttpPost("http://172.16.201.6:8080/api/v1/msvc-template/service/carteraLocal/carteraCompletaGuardarLocal");
            StringEntity post = new StringEntity(String.valueOf(cartera), ContentType.APPLICATION_JSON);
            serviceRequest.setEntity((HttpEntity)post);
            serviceResponse = client.execute((HttpUriRequest)serviceRequest);
            int respStatus=serviceResponse.getStatusLine().getStatusCode();
            if(respStatus==200||respStatus==201){
                respuesta.setCode(1);
            }
        }
        catch(Exception e){
            respuesta.setMessage("Algo fallo "+ e);
            respuesta.setError(true);
        }

        return respuesta;

    }

    private RestResponse<String> puenteServidorExtraGuardarCarteraDescarte(ArrayList<ClienteModel> cartera){
        RestResponse<String>respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);
        respuesta.setError(true);
        CloseableHttpResponse serviceResponse = null;
        try{
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpPost serviceRequest = new HttpPost("http://172.16.201.6:8080/api/v1/msvc-template/service/carteraLocal/carteraDescarteGuardarLocal");
            StringEntity post = new StringEntity(String.valueOf(cartera), ContentType.APPLICATION_JSON);
            serviceRequest.setEntity((HttpEntity)post);
            serviceResponse = client.execute((HttpUriRequest)serviceRequest);
            int respStatus=serviceResponse.getStatusLine().getStatusCode();
            if(respStatus==200||respStatus==201){
                respuesta.setCode(1);
            }
        }
        catch(Exception e){
            respuesta.setMessage("Algo fallo "+ e);
            respuesta.setError(true);
        }

        return respuesta;

    }


}
