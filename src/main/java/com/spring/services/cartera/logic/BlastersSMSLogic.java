package com.spring.services.cartera.logic;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.cartera.model.ExtrasModel;
import com.spring.utils.RestResponse;
import com.spring.utils.UtilService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Component
public class BlastersSMSLogic {

    private static final Logger LOGGER = LogManager.getLogger("BlastersSMSLogic");

    @Autowired
    private ObtenerClientesCartera carteraLogic;
    @Autowired
    private CarteraLogic carteraLog;
    @Autowired
    private CarteraSetearDatosLogic setearDatosLogic;

    private UtilService util=new UtilService();


    @Value("${blasters.envioMasivoTTS}")
    private String urlBlastersTTS;


    public BlastersSMSLogic() {
        //Vacio
    }

    public RestResponse<String> blasterSegmento(ExtrasModel cokkie){
        RestResponse<String> respuesta=new RestResponse<>();
        ArrayList<String>numero=new ArrayList<>();
        numero.add("5530387145");
//        numero.add("5539252342");
        RestResponse<ArrayList<ClienteModel>> clientesSegmento= new RestResponse<>();
        switch (cokkie.getTipoArchivo()){
            case "5":
                clientesSegmento=carteraLogic.carteraSeg5(cokkie);
                break;
            case "6":
                clientesSegmento=carteraLogic.carteraSeg6(cokkie);
                break;
            case "6SSNNITC":
                clientesSegmento=carteraLogic.carteraSeg6SNNITC(cokkie);
                break;
            case "16":
                clientesSegmento=carteraLogic.carteraSeg16(cokkie);
                break;
            case "16SSNNITC":
                clientesSegmento=carteraLogic.carteraSeg16SNNITC(cokkie);
                break;
            case "28":
                clientesSegmento=carteraLogic.carteraSeg28(cokkie);
                break;
            case "Preventa":
            case "Normalidad":
            case "ITALIKA":
            case "CDT":
            case "TOR":
            case "MAZ":
                clientesSegmento=carteraLogic.carteraSegTipo(cokkie);
                break;

        }

        if(clientesSegmento.getCode()==1) {
            if (clientesSegmento.getData().toArray().length < 1000) {
                for (int i = 0; i < clientesSegmento.getData().toArray().length; i++) {
                    if (!"N/A".equals(clientesSegmento.getData().get(i).getTELEFONO1())) {
                        numero.add(clientesSegmento.getData().get(i).getTELEFONO1());
                    }
                }
            }
            else {
                for (int j = 0; j <= 999; j++) {
                    if (!"N/A".equals(clientesSegmento.getData().get(j).getTELEFONO1())) {
                        numero.add(clientesSegmento.getData().get(j).getTELEFONO1());
                    }
                }
            }


            LOGGER.log(Level.INFO, () -> "Se enviaran " + numero.toArray().length + " del segmento: " + cokkie.getTipoArchivo() + " con el mensaje " + cokkie.getMensajeSMS());
            respuesta.setCode(1);
            respuesta.setMessage("Envio Blasters");
            respuesta.setData(this.enviarBlaster(numero, cokkie.getMensajeSMS(), cokkie.getUsuarioBlasters(), cokkie.getPasswordBlasters()));
        }
        else{
            respuesta.setCode(1);
            respuesta.setMessage("Favor de reintentar, cookie no funciono");
            respuesta.setData(null);
        }
        return respuesta;
    }


    public String enviarBlaster(ArrayList<String> numero,String mensaje,String usuario, String password){
        String respuesta="";
        try{
            String requestBlaster=this.formatoXMLBlaster(numero, mensaje, usuario, password);

            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type","text/xml");
            headers.add("SOAPAction","http://tempuri.org/IServicioBlaster/EnvioBlasterMasivoTTS");
            HttpEntity<String> integralEntity = new HttpEntity(requestBlaster, (MultiValueMap)headers);
            LOGGER.log(Level.INFO, () -> "REQUEST Envio Blasters "+integralEntity);
                        respuesta=rest.postForObject("https://wsblaster.marcatel.com.mx/ServicioBlaster.svc/mex",integralEntity,String.class,new Object[0]);
            String respLog=respuesta;
            LOGGER.log(Level.INFO, () -> "RESPONSE Envio Blasters Parametros "+respLog);
        }
        catch(Exception e){
            respuesta="Algo fallo "+ e;
            LOGGER.log(Level.INFO, () -> "ISSUE enviarBlaster: Ocurrio algo Inesperado:  "+e);

        }
        return respuesta;
    }

    private String formatoXMLBlaster(ArrayList<String> numero,String mensaje,String usuario, String password){

        String[] fechaHora=util.obtenerFechaActual().split(" ");
        String[] horaCompleta=fechaHora[1].split(":");
        String hora=horaCompleta[0]+":"+horaCompleta[1];
        String[] fechaCompleta=fechaHora[0].split("-");
        String fecha=fechaCompleta[2]+"-"+fechaCompleta[1]+"-"+fechaCompleta[0];
        String primeraParteFormato="<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <Body>\n" +
                "        <EnvioBlasterMasivoTTS xmlns=\"http://tempuri.org/\">\n" +
                "            <Usuario>"+usuario+"</Usuario>\n" +
                "            <Password>"+password+"</Password>\n" +
                "            <Telefonos>\n";
        String parteFinalFormato="</Telefonos>\n" +
                "            <FechaInicial>"+fecha+" "+hora+"</FechaInicial>\n" +
                "            <FechaFinal>"+fecha+" 20:55</FechaFinal>\n" +
                "            <MensajeTTS>"+mensaje+"</MensajeTTS>\n" +
                "            <TipodeVoz>M</TipodeVoz>\n" +
                "            <Buzon>0</Buzon>\n" +
                "            <NoRepeticion>0</NoRepeticion>\n" +
                "            <Campania>0</Campania>\n" +
                "            <IdTransaccion>0</IdTransaccion>\n" +
                "            <Aux>0</Aux>\n" +
                "        </EnvioBlasterMasivoTTS>\n" +
                "    </Body>\n" +
                "</Envelope>";

        String parteMedia="";
        for(int i=0;i<numero.toArray().length;i++){
            String telefono= numero.get(i);
            parteMedia=parteMedia+
                    "                <ServicioBlaster.TelefonoBlaster xmlns=\"http://schemas.datacontract.org/2004/07/WCFBlaster\">\n" +
                    "                    <Telefono>"+telefono+"</Telefono>\n" +
                    "                </ServicioBlaster.TelefonoBlaster>\n";
        }
        return primeraParteFormato+parteMedia+parteFinalFormato;
    }


    public RestResponse<ArrayList<ClienteModel>> numerosListasSegmento(ExtrasModel cokkie){
        RestResponse<ArrayList<ClienteModel>> respuesta= new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>obtenerCartera=carteraLog.carteraSegmentoEleg(cokkie);


        if(obtenerCartera.getCode()==1){
            respuesta.setCode(1);
            respuesta.setMessage("Numeros y clientes obtenidos correctamente");
            respuesta.setData(discrimarNumerosTel(obtenerCartera.getData(),cokkie.getTipoFuncion()));
        }
        else{
            respuesta.setCode(obtenerCartera.getCode());
            respuesta.setMessage(obtenerCartera.getMessage());
        }


        return respuesta;
    }

    private ArrayList<ClienteModel> discrimarNumerosTel(ArrayList<ClienteModel> clientes, String turno){
        ArrayList<ClienteModel> clientesConTel=new ArrayList<>();
        for(int i=0;i<clientes.toArray().length;i++){
            if(!"0".equals(clientes.get(i).getTELEFONO1())&&!"N/A".equals(clientes.get(i).getTELEFONO1())){
                clientesConTel.add(clientes.get(i));
            }
        }

        return dividirTurnoCrearArchivo(clientesConTel,turno);
    }

    private ArrayList<ClienteModel> dividirTurnoCrearArchivo(ArrayList<ClienteModel> clientes, String turno){
        ArrayList<ClienteModel> segmento5=new ArrayList<>();
        ArrayList<ClienteModel> segmento28=new ArrayList<>();
        ArrayList<ClienteModel> segmento6=new ArrayList<>();
        ArrayList<ClienteModel> segmento16=new ArrayList<>();
        ArrayList<ClienteModel> respuesta=new ArrayList<>();

        for (int i=0;i<clientes.toArray().length;i++){
            if("5".equals(clientes.get(i).getSEGMENTO())){
                segmento5.add(clientes.get(i));
            }
            else if("28".equals(clientes.get(i).getSEGMENTO())){
                segmento28.add(clientes.get(i));
            }
            else if("6".equals(clientes.get(i).getSEGMENTO())){
                segmento6.add(clientes.get(i));
            }
            else if("16".equals(clientes.get(i).getSEGMENTO())){
                segmento16.add(clientes.get(i));
            }
        }

        float segmento5PIArr=segmento5.toArray().length%2;
        float segmento28PIArr=segmento28.toArray().length%2;
        float segmento6PIArr=segmento6.toArray().length%2;
        float segmento16PIArr=segmento16.toArray().length%2;

        int segmento5Array=Integer.parseInt(String.valueOf(segmento5PIArr==0?segmento5.toArray().length/2 : (float) ((segmento5.toArray().length / 2) + .5)));
        int segmento28Array= Integer.parseInt(String.valueOf(segmento28PIArr==0?segmento28.toArray().length/2: (float) ((segmento28.toArray().length / 2) + .5)));
        int segmento6Array= Integer.parseInt(String.valueOf(segmento6PIArr==0?segmento6.toArray().length/2: (float) (segmento6.toArray().length / 2 + .5)));
        int segmento16Array= Integer.parseInt(String.valueOf(segmento16PIArr==0?segmento16.toArray().length/2: (float) (segmento16.toArray().length / 2 + .5)));


        if("M".equals(turno)){
            for(int a=0;a<segmento5Array;a++){
                respuesta.add(segmento5.get(a));
            }
            for(int b=0;b<segmento28Array;b++){
                respuesta.add(segmento28.get(b));
            }
            for(int c=0;c<segmento6Array;c++){
                respuesta.add(segmento6.get(c));
            }
            for(int d=0;d<segmento16Array;d++){
                respuesta.add(segmento16.get(d));
            }
        }
        else{
            for(int e=segmento5Array;e<segmento5.toArray().length;e++){
                respuesta.add(segmento5.get(e));
            }
            for(int f=segmento28Array;f<segmento28.toArray().length;f++){
                respuesta.add(segmento28.get(f));
            }
            for(int g=segmento6Array;g<segmento6.toArray().length;g++){
                respuesta.add(segmento6.get(g));
            }
            for(int h=segmento16Array;h<segmento16.toArray().length;h++){
                respuesta.add(segmento16.get(h));
            }
        }


        return respuesta;
    }

}
