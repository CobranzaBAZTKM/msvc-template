package com.spring.services.cartera.logic;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.services.constantes.Constantes;
import com.spring.services.notificaciones.logic.NotificacionesLogic;
import com.spring.services.notificaciones.model.CuerpoCorreo;
import com.spring.utils.RestResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;


@Component
public class ObtenerClientesCartera {

    private static final Logger LOGGER = LogManager.getLogger("ObtenerClientesCartera");
    private static final String conUnSaldo=", con un saldo total de ";

    @Autowired
    private CarteraCompletaLogic carteraLogic;

    CarteraCompletaLogic carteraLogic2=new CarteraCompletaLogic();

    @Autowired
    private CarteraSetearDatosLogic setarDatosLogic;

    @Autowired
    private NotificacionesLogic notifiLogic;

    public RestResponse<ArrayList<ClienteModel>> carteraCompleta(ExtrasModel cokkie) {
//    public RestResponse<String> carteraCompleta(ExtrasModel cokkie) {
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
//        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic2.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){
            respuesta=carteraLogic2.clientesCompletos(segmentosCartera.getData(),56162821,cokkie,1);
//            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),561628,cokkie);
//            respuesta.setCode(1);
//            respuesta.setMessage("Se generaron "+datosClientes.getData().toArray().length+" clientes de la cartera completa");
//            respuesta.setData(setarDatosLogic.generarExcelCartera(datosClientes.getData(),cokkie.getNombreArchivo()));
//            respuesta.setError(false);
        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;
    }

    public RestResponse<ArrayList<ClienteModel>>carteraSeg5(ExtrasModel cokkie){

        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){
            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),5,cokkie,1);

            ArrayList<ClienteModel> datosAcomodados=datosClientes.getData();
            //Collections.sort(datosAcomodados,((o1, o2) -> o2.getSALDO_TOTAL().compareTo(o1.getSALDO_TOTAL())));

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Se generaron " + datosAcomodados.toArray().length + " cuentas del Segmento 5");
            respuesta.setData(datosAcomodados);

        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;

    }

    public RestResponse<ArrayList<ClienteModel>> carteraSeg6(ExtrasModel cokkie) {

        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){

            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),6,cokkie,1);

            respuesta.setCode(1);
            respuesta.setMessage("Se generaron "+datosClientes.getData().toArray().length+" clientes del Segmento 6");
            respuesta.setData(datosClientes.getData());
            respuesta.setError(false);

        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;

    }

    public RestResponse<ArrayList<ClienteModel>> carteraSeg6SNNITC(ExtrasModel cokkie) {
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){
            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),6,cokkie,1);
            ArrayList<ClienteModel> clientesSegSNPNITC=setarDatosLogic.discriminacionPNITC(datosClientes.getData());

            Collections.sort(clientesSegSNPNITC,((o1, o2) -> o2.getSALDO_TOTAL().compareTo(o1.getSALDO_TOTAL())));

            respuesta.setCode(1);
            respuesta.setMessage("Se generaron "+clientesSegSNPNITC.toArray().length+" clientes del Segmento 6 sin Normalidad, Italika, TOR y CDT");
            respuesta.setError(false);
            respuesta.setData(clientesSegSNPNITC);

        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;
    }


    public RestResponse<ArrayList<ClienteModel>> carteraSeg16(ExtrasModel cokkie) {

        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){

            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),16,cokkie,1);

            respuesta.setCode(1);
            respuesta.setMessage("Se generaron "+datosClientes.getData().toArray().length+" clientes del Segmento 16");
            respuesta.setData(datosClientes.getData());
            respuesta.setError(false);

        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;
    }

    public RestResponse<ArrayList<ClienteModel>> carteraSeg16SNNITC(ExtrasModel cokkie) {
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){
            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),16,cokkie,1);
            ArrayList<ClienteModel> clientesSegSNPNITC=setarDatosLogic.discriminacionPNITC(datosClientes.getData());

            Collections.sort(clientesSegSNPNITC,((o1, o2) -> o2.getSALDO_TOTAL().compareTo(o1.getSALDO_TOTAL())));

            respuesta.setCode(1);
            respuesta.setMessage("Se generaron "+clientesSegSNPNITC.toArray().length+" clientes del Segmento 16 sin Preventa, Normalidad, Italika, TOR y CDT");
            respuesta.setError(false);
            respuesta.setData(clientesSegSNPNITC);

        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;
    }



    public RestResponse<ArrayList<ClienteModel>> carteraSeg28(ExtrasModel cokkie) {

        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){

            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),28,cokkie,1);

            ArrayList<ClienteModel> datosAcomodados=datosClientes.getData();
            //Collections.sort(datosAcomodados,((o1, o2) -> o2.getSALDO_TOTAL().compareTo(o1.getSALDO_TOTAL())));

            String respuestaMessage="Se generaron "+datosClientes.getData().toArray().length+" clientes del Segmento 28";
            respuesta.setCode(1);
            respuesta.setMessage(respuestaMessage);
            respuesta.setData(datosAcomodados);
            respuesta.setError(false);


        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;
    }

    public RestResponse<ArrayList<ClienteModel>> carteraSegTipo(ExtrasModel cokkie ) {
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>clientesCompleto=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){
            if("Preventa".equals(cokkie.getTipoArchivo())||"MAZ".equals(cokkie.getTipoArchivo())){
                clientesCompleto=carteraLogic.clientesCompletos(segmentosCartera.getData(),16,cokkie,1);
            }
            else{
                clientesCompleto=carteraLogic.clientesCompletos(segmentosCartera.getData(),5616,cokkie,1);
            }

            ArrayList<ClienteModel> clientesSegmentos=setarDatosLogic.setearDatosSegTipo(clientesCompleto.getData(),cokkie.getTipoArchivo());

            Collections.sort(clientesSegmentos,((o1, o2) -> o2.getSALDO_TOTAL().compareTo(o1.getSALDO_TOTAL())));

            respuesta.setCode(1);
            respuesta.setMessage("Se generaron "+clientesSegmentos.toArray().length+" clientes de la cartera "+cokkie.getTipoArchivo());
            respuesta.setError(false);
            respuesta.setData(clientesSegmentos);

        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;
    }


    public RestResponse<ArrayList<ClienteModel>> carteraSaldos(ExtrasModel cokkie){

        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();
        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>clientesCompleto=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){

            RestResponse<ArrayList<ClienteModel>>datosClientes=new RestResponse<>();
//            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),Integer.parseInt(segmento),cokkie);
            switch (cokkie.getTipoArchivo()){
                case "5":
                    datosClientes=carteraSeg5(cokkie);
                    break;
                case "6" :
                    datosClientes=carteraSeg6(cokkie);
                    break;
                case "6SNNITC" :
                    datosClientes=carteraSeg6SNNITC(cokkie);
                    break;
                case "16" :
                    datosClientes=carteraSeg16(cokkie);
                    break;
                case "16SNNITC" :
                    datosClientes=carteraSeg16SNNITC(cokkie);
                    break;
                case "28" :
                    datosClientes=carteraSeg28(cokkie);
                    break;
                case "Preventa":
                case "Normalidad":
                case "ITALIKA":
                case "CDT":
                case "TOR":
                case "MAZ":
                    datosClientes=carteraSegTipo(cokkie);
                    break;

            }


            ArrayList<ClienteModel> clientesSaldos=setarDatosLogic.clientesSaldos(datosClientes.getData(),cokkie.getTipoFuncion());

            Collections.sort(clientesSaldos,((o1, o2) -> o2.getSALDO_TOTAL().compareTo(o1.getSALDO_TOTAL())));

//            if("BAJO".equals(cokkie.getTipoFuncion())){
//                setarDatosLogic.generarExcelSMS(clientesSaldos, cokkie.getNombreArchivo2(), cokkie.getMensajeSMS());
//            }

            respuesta.setCode(1);
            respuesta.setMessage("Se generaron "+clientesSaldos.toArray().length+" clientes del Segmento "+cokkie.getTipoArchivo()+" de saldo "+cokkie.getTipoFuncion());
            respuesta.setError(false);
            respuesta.setData(clientesSaldos);


        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }

        return respuesta;

    }


    public RestResponse<ArrayList<ClienteModel>> carteraSeg21(ExtrasModel cokkie) {
        RestResponse<ArrayList<ClienteModel>>respuesta=new RestResponse<>();

        RestResponse<JSONObject> segmentosCartera=new RestResponse<>();
        int sumaIntentos=0;
        do{
            segmentosCartera=carteraLogic.obtenerSegmentosCartera(cokkie,1);
            sumaIntentos++;
        }while(segmentosCartera.getCode()!=1&&sumaIntentos!=10);

        if(segmentosCartera.getCode()==1){
            RestResponse<ArrayList<ClienteModel>>datosClientes=carteraLogic.clientesCompletos(segmentosCartera.getData(),21,cokkie,1);
            String respuestaMessage="Se generaron "+datosClientes.getData().toArray().length+" clientes del Segmento 21";
            respuesta.setCode(1);
            respuesta.setMessage(respuestaMessage);
            respuesta.setData(datosClientes.getData());
            respuesta.setError(false);
        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se pudieron obtener las campañas");
            respuesta.setError(false);
        }
        return respuesta;
    }
}
