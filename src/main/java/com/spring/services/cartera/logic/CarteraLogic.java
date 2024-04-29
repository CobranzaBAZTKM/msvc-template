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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class CarteraLogic {
    private static final Logger LOGGER = LogManager.getLogger("CarteraLogic");
    private static final String conUnSaldo=", con un saldo total de ";

    @Autowired
    private ObtenerClientesCartera obteneClienLogic;

    @Autowired
    private CarteraSetearDatosLogic setarDatosLogic;

    @Autowired
    private NotificacionesLogic notifiLogic;

    public CarteraLogic() {
        //Vacio
    }

    public RestResponse<String> carteraSeg5(ExtrasModel cokkie) {

        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>> segmento5=obteneClienLogic.carteraSeg5(cokkie);

        respuesta.setCode(segmento5.getCode());
        respuesta.setMessage(segmento5.getMessage());
        respuesta.setError(segmento5.isError());

        if(segmento5.getCode()==1) {
            respuesta.setData(setarDatosLogic.generarExcelCartera(segmento5.getData(), cokkie.getNombreArchivo()));

            RestResponse<String> envioCorreo = this.mandarCorreoCarteras("Informacion Segmento 5 Puro", segmento5.getMessage(), segmento5.getData());
            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        return respuesta;
    }

    public RestResponse<String> carteraSeg6(ExtrasModel cokkie) {
        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>> segmento6=obteneClienLogic.carteraSeg6(cokkie);
        respuesta.setCode(segmento6.getCode());
        respuesta.setMessage(segmento6.getMessage());
        respuesta.setError(segmento6.isError());
        if(segmento6.getCode()==1) {
            respuesta.setData(setarDatosLogic.generarExcelCartera(segmento6.getData(), cokkie.getNombreArchivo()));

            RestResponse<String> envioCorreo = this.mandarCorreoCarteras("Informacion Segmento 6 Puro", segmento6.getMessage(), segmento6.getData());
            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        return respuesta;
    }

    public RestResponse<String> carteraSeg6SNNITC(ExtrasModel cokkie) {
        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>segmento6SNNITC=obteneClienLogic.carteraSeg6SNNITC(cokkie);
        respuesta.setCode(segmento6SNNITC.getCode());
        respuesta.setMessage(segmento6SNNITC.getMessage());
        respuesta.setError(segmento6SNNITC.isError());
        if(segmento6SNNITC.getCode()==1) {
            respuesta.setData(setarDatosLogic.generarExcelCartera(segmento6SNNITC.getData(), cokkie.getNombreArchivo()));

            RestResponse<String> envioCorreo = this.mandarCorreoCarteras("Informacion Segmento 6 sin Preventa, Normalidad, Italika, TOR y CDT", segmento6SNNITC.getMessage(), segmento6SNNITC.getData());
            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        return respuesta;

    }




    public RestResponse<String> carteraSeg16(ExtrasModel cokkie) {
        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>segmento16=obteneClienLogic.carteraSeg16(cokkie);
        respuesta.setCode(segmento16.getCode());
        respuesta.setMessage(segmento16.getMessage());
        respuesta.setError(segmento16.isError());
        if(segmento16.getCode()==1) {
            respuesta.setData(setarDatosLogic.generarExcelCartera(segmento16.getData(), cokkie.getNombreArchivo()));

            RestResponse<String> envioCorreo = this.mandarCorreoCarteras("Informacion Segmento 16 Puro", segmento16.getMessage(), segmento16.getData());
            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        return respuesta;
    }

    public RestResponse<String> carteraSeg16SNNITC(ExtrasModel cokkie) {
        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>segmento16SNNITC=obteneClienLogic.carteraSeg16SNNITC(cokkie);
        respuesta.setCode(segmento16SNNITC.getCode());
        respuesta.setMessage(segmento16SNNITC.getMessage());
        respuesta.setError(segmento16SNNITC.isError());
        if(segmento16SNNITC.getCode()==1) {
            respuesta.setData(setarDatosLogic.generarExcelCartera(segmento16SNNITC.getData(), cokkie.getNombreArchivo()));

            RestResponse<String> envioCorreo = this.mandarCorreoCarteras("Informacion Segmento 16 sin Preventa, Normalidad, Italika, TOR y CDT", segmento16SNNITC.getMessage(), segmento16SNNITC.getData());
            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        return respuesta;
    }


    public RestResponse<String> carteraSeg28(ExtrasModel cokkie) {
        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>segmento28=obteneClienLogic.carteraSeg28(cokkie);
        respuesta.setCode(segmento28.getCode());
        respuesta.setMessage(segmento28.getMessage());
        respuesta.setError(segmento28.isError());
        if(segmento28.getCode()==1) {
            respuesta.setData(setarDatosLogic.generarExcelCartera(segmento28.getData(), cokkie.getNombreArchivo()));

            RestResponse<String> envioCorreo = this.mandarCorreoCarteras("Informacion Segmento 28 Puro", segmento28.getMessage(), segmento28.getData());
            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        return respuesta;
    }

    public RestResponse<String> carteraSegTipo(ExtrasModel cokkie ) {
        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>segmentoTipo=obteneClienLogic.carteraSegTipo(cokkie);

        respuesta.setCode(segmentoTipo.getCode());
        respuesta.setMessage(segmentoTipo.getMessage());
        respuesta.setError(segmentoTipo.isError());
        if(segmentoTipo.getCode()==1) {
            respuesta.setData(setarDatosLogic.generarExcelCartera(segmentoTipo.getData(), cokkie.getNombreArchivo()));

            RestResponse<String> envioCorreo = this.mandarCorreoCarteras("Informacion Segmento "+cokkie.getTipoArchivo(), segmentoTipo.getMessage(), segmentoTipo.getData());
            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        return respuesta;

    }

    public RestResponse<String> carteraSaldos(ExtrasModel cokkie){
        RestResponse<String>respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>segmentoSegmentada=obteneClienLogic.carteraSaldos(cokkie);
        respuesta.setCode(segmentoSegmentada.getCode());
        respuesta.setMessage(segmentoSegmentada.getMessage());
        respuesta.setError(segmentoSegmentada.isError());
        if(segmentoSegmentada.getCode()==1) {
            respuesta.setData(setarDatosLogic.generarExcelCartera(segmentoSegmentada.getData(), cokkie.getNombreArchivo()));

            RestResponse<String> envioCorreo = this.mandarCorreoCarteras("Informacion Segmento "+cokkie.getTipoArchivo()+" tipo"+cokkie.getTipoFuncion(), segmentoSegmentada.getMessage(), segmentoSegmentada.getData());
            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        return respuesta;
    }


    private RestResponse<String> mandarCorreoCarteras(String asunto, String mensaje,ArrayList<ClienteModel> datos){
        ArrayList<String>correos=new ArrayList<>();
        correos.add(Constantes.correoEncargada);
        correos.add("axel.rodriguezn@elektra.com.mx");
        Double obtenerSaldoTotal=setarDatosLogic.obtenerSaldoTotal(datos);
        String saldo= String.valueOf(BigDecimal.valueOf(obtenerSaldoTotal));
        CuerpoCorreo datosCorreo=new CuerpoCorreo();
        datosCorreo.setRemitente(Constantes.correoRemitente);
        datosCorreo.setPasswordRemitente(Constantes.passwordRemitente);
        datosCorreo.setDestinatario(correos);
        datosCorreo.setAsunto(asunto);
        datosCorreo.setMensaje(mensaje+conUnSaldo+obtenerSaldoTotal);

        LOGGER.log(Level.INFO, () -> "REQUEST Envio correo"+datosCorreo);
        return notifiLogic.enviarCorreo(datosCorreo);

    }

//    public RestResponse<String> carteraSegmentoElegClientes(ExtrasModel cokkie){
//
//        RestResponse<String> respuesta=new RestResponse<>();
//        RestResponse<ArrayList<ClienteModel>> clientes=carteraSegmentoEleg(cokkie);
//
//        respuesta.setCode(clientes.getCode());
//        respuesta.setMessage(clientes.getMessage());
//        if(clientes.getCode()==1) {
//            respuesta.setData(setarDatosLogic.generarExcelCartera(clientes.getData(), cokkie.getNombreArchivo()));
//        }
//        respuesta.setError(clientes.isError());
//
//        return respuesta;
//    }
    public RestResponse<ArrayList<ClienteModel>> carteraSegmentoEleg(ExtrasModel cokkie){

        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        String segmentosTotales=cokkie.getTipoArchivo();
        String[] segmentos=cokkie.getTipoArchivo().split("\\|");
        ArrayList<ClienteModel>clientesSegmentos=new ArrayList<>();
        int bandera=1;

        for(int i=0;i< segmentos.length;i++){
            cokkie.setTipoArchivo(segmentos[i]);
            switch (segmentos[i]){
                case "5":
                    if(bandera!=0) {
                        RestResponse<ArrayList<ClienteModel>> segmento5 = obteneClienLogic.carteraSeg5(cokkie);
                        if (segmento5.getCode() == 1) {
                            bandera = 1;
                            clientesSegmentos.addAll(segmento5.getData());
                        }
                        else{
                            bandera=0;
                        }
                    }
                    break;
                case "6":
                    if(bandera!=0) {
                        RestResponse<ArrayList<ClienteModel>> segmento6 = obteneClienLogic.carteraSeg6(cokkie);
                        if(segmento6.getCode()==1){
                            bandera = 1;
                            clientesSegmentos.addAll(segmento6.getData());
                        }
                        else{
                            bandera=0;
                        }
                    }
                    break;
                case "6SNNITC":
                    if(bandera!=0) {
                        RestResponse<ArrayList<ClienteModel>> segmento6SNNITC = obteneClienLogic.carteraSeg6SNNITC(cokkie);
                        if(segmento6SNNITC.getCode()==1){
                            bandera = 1;
                            clientesSegmentos.addAll(segmento6SNNITC.getData());
                        }
                        else{
                            bandera=0;
                        }
                    }
                    break;
                case "16":
                    if(bandera!=0) {
                        RestResponse<ArrayList<ClienteModel>> segmento16 = obteneClienLogic.carteraSeg16(cokkie);
                        if(segmento16.getCode()==1) {
                            bandera = 1;
                            clientesSegmentos.addAll(segmento16.getData());
                        }
                        else{
                            bandera=0;
                        }
                    }
                    break;
                case "16SNNITC":
                    if(bandera!=0) {
                        RestResponse<ArrayList<ClienteModel>> segmento16SNNITC = obteneClienLogic.carteraSeg16SNNITC(cokkie);
                        if(segmento16SNNITC.getCode()==1) {
                            clientesSegmentos.addAll(segmento16SNNITC.getData());
                            bandera = 1;
                        }
                        else{
                            bandera=0;
                        }
                    }
                    break;
                case "28":
                    if(bandera!=0) {
                        RestResponse<ArrayList<ClienteModel>> segmento28 = obteneClienLogic.carteraSeg28(cokkie);
                        if(segmento28.getCode()==1) {
                            clientesSegmentos.addAll(segmento28.getData());
                            bandera = 1;
                        }
                        else{
                            bandera=0;
                        }
                    }
                    break;
                case "21":
                    if(bandera!=0) {
                        RestResponse<ArrayList<ClienteModel>> segmento21 = obteneClienLogic.carteraSeg21(cokkie);
                        if(segmento21.getCode()==1) {
                            clientesSegmentos.addAll(segmento21.getData());
                            bandera = 1;
                        }
                        else{
                            bandera=0;
                        }
                    }
                    break;
                case "Preventa":
                case "Normalidad":
                case "ITALIKA":
                case "CDT":
                case "TOR":
                case "MAZ":
                    if(bandera!=0) {
                        RestResponse<ArrayList<ClienteModel>> segmentoTipo = obteneClienLogic.carteraSegTipo(cokkie);
                        if(segmentoTipo.getCode()==1){
                            clientesSegmentos.addAll(segmentoTipo.getData());
                            bandera = 1;
                        }
                        else{
                            bandera=0;
                        }
                    }
                    break;
                default:
                    //vacio

            }
        }


        respuesta.setError(false);
        if(bandera!=0) {
            respuesta.setCode(1);
            respuesta.setMessage("Se generaron " + clientesSegmentos.toArray().length + " clientes del Segmento " + cokkie.getTipoArchivo());
            respuesta.setData(clientesSegmentos);

//            String mensaje="Se generaron " + clientesSegmentos.toArray().length + " clientes del Segmento(s) " + segmentosTotales;
//            RestResponse<String> envioCorreo =mandarCorreoCarteras("Informacion Segmento(s)"+segmentosTotales,mensaje,clientesSegmentos);
//            LOGGER.log(Level.INFO, () -> envioCorreo);
        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("No se obtuvieron campañas, favor de volver a intentar");
        }

        return respuesta;

    }

}
