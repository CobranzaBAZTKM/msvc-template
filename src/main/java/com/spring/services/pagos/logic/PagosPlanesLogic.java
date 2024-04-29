package com.spring.services.pagos.logic;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.carteralocal.logic.CarteraLocalLogic;
import com.spring.services.pagos.model.PromesasModel;
import com.spring.services.pagos.model.datosEntradaPagosPlanes;
import com.spring.utils.RestResponse;

import com.spring.utils.UtilService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class PagosPlanesLogic {

    private static final Logger LOGGER = LogManager.getLogger("PagosPlanesLogic");

    @Autowired
    private SetearDatosPagoPlanesLog sdPpLogic;

//    @Autowired
    private ObtenerDatosPlanesPagos obDatLogic=new ObtenerDatosPlanesPagos();

    @Autowired
    private UtilService util;

    private PromesasTKMLogic promesas=new PromesasTKMLogic();

    private CarteraLocalLogic carteraLog=new CarteraLocalLogic();


    public PagosPlanesLogic() {
        //Vacio
    }

    public RestResponse<String> obtenerPlanesPago(datosEntradaPagosPlanes cu){
        RestResponse<String> respuesta=new RestResponse<>();
        ArrayList<datosEntradaPagosPlanes> datosPagosFinales=new ArrayList<>();
        try{
            for(int i=0;i<cu.getCu().toArray().length;i++){
                String cuPreparado=sdPpLogic.prepararCU(cu.getCu().get(i));
                RestResponse<JSONObject> obtenerPlanCU=new RestResponse<>();
                int intentoPlanes=1;
                do {
                    obtenerPlanCU=obDatLogic.obtenerPlanesCU(cuPreparado,cu);
                    String intentoLog=String.valueOf(intentoPlanes);
                    LOGGER.log(Level.INFO, () -> "Intentando obtener los planes de pago del cliente CU "+cuPreparado+" . Intento: "+intentoLog);
                    intentoPlanes++;
                }
                while(obtenerPlanCU.getCode()!=1&&intentoPlanes!=500);

                if(obtenerPlanCU.getCode()==1) {
                    datosEntradaPagosPlanes rep = continuacionObtenerPlanesPago(cu, cuPreparado, obtenerPlanCU);
                    datosPagosFinales.add(rep);
                }

            }
            respuesta.setCode(1);
            respuesta.setData("Se obtuvieron "+datosPagosFinales.toArray().length);
            respuesta.setMessage(sdPpLogic.generarExcelPagos(datosPagosFinales));
            respuesta.setError(false);
        }
        catch(Exception e){
            respuesta.setMessage("Algo fallo "+ e);
            respuesta.setError(true);
        }

        return respuesta;
    }

    public datosEntradaPagosPlanes continuacionObtenerPlanesPago(datosEntradaPagosPlanes cu,String cuPreparado, RestResponse<JSONObject> planesCU){
        datosEntradaPagosPlanes respuesta=new datosEntradaPagosPlanes();
        String planIdStatus=sdPpLogic.obtenerActivo(planesCU.getData());

        String gestor=null;
        do{
            gestor=obDatLogic.obtenerGestorCliente(cuPreparado,cu);
            LOGGER.log(Level.INFO, () -> "Intentando obtener el gestor del Cliente "+cuPreparado);
        }
        while("NSOG".equals(gestor));
        respuesta.setGestor(gestor);

        RestResponse<JSONObject> obtenerEdoCuentasMon=this.obtenerEdoCuentas(cuPreparado,cu);

        if(!"SN".equals(planIdStatus)){
            String [] separarIdStatusPlan=planIdStatus.split("\\|");

            RestResponse<JSONObject> obtenerPrometidos=new RestResponse<>();
            do {
                obtenerPrometidos = obDatLogic.obtenerPrometidos(separarIdStatusPlan[0], cuPreparado, cu);
                LOGGER.log(Level.INFO, () -> "Intentando obtener los montos prometidos");
            }while (obtenerPrometidos.getCode()!=1);


            RestResponse<JSONObject> obtenerReales=new RestResponse<>();
            do{
                obtenerReales=obDatLogic.obtenerReales(separarIdStatusPlan[0],cuPreparado,cu);
                LOGGER.log(Level.INFO, () -> "Intentando obtener los montos depositados");
            }while (obtenerReales.getCode()!=1);

            respuesta.setCuFinal(cuPreparado);
            respuesta.setIdPlanActivo(separarIdStatusPlan[0]);
            respuesta.setStatus(separarIdStatusPlan[1]);
            respuesta.setMontoPrometido(String.valueOf(obtenerPrometidos.getData().getJSONArray("resultado").getJSONObject(0).get("montoPrometido")));

            JSONArray deposito=obtenerReales.getData().getJSONArray("resultado");
            if(deposito.length()==0){
                respuesta.setMontoDepositado("0");
                respuesta.setDiaPago("SN");
            }
            else{
                float montoDepo=0;
                String diaPago="";

                for(int i=0;i<deposito.length();i++){
                    Integer importeAbono=(Integer) deposito.getJSONObject(i).get("importeAbono");
                    montoDepo=montoDepo + Float.parseFloat(String.valueOf(importeAbono));
                    String dPago=(String) deposito.getJSONObject(i).get("fechaRecepcion")+"="+importeAbono+", ";
                    diaPago=diaPago+dPago;
                }
//                respuesta.setMontoDepositado(String.valueOf(deposito.getJSONObject(0).get("importeAbono")));
                respuesta.setMontoDepositado(String.valueOf(montoDepo));
                respuesta.setDiaPago(diaPago);
            }


        }
        else if (obtenerEdoCuentasMon.getCode()==1&&obtenerEdoCuentasMon.getData()!=null) {
            respuesta.setCuFinal(cuPreparado);
            respuesta.setMontoPrometido("0");
            respuesta.setIdPlanActivo(planIdStatus);
            respuesta.setMontoDepositado(String.valueOf((Integer) obtenerEdoCuentasMon.getData().get("abono")));
            respuesta.setDiaPago((String) obtenerEdoCuentasMon.getData().get("diasPago"));
            respuesta.setStatus("Sin Plan Activo o Cumplido");
        }
        else{
            respuesta.setCuFinal(cuPreparado);
            respuesta.setMontoPrometido("0");
            respuesta.setMontoDepositado("0");
            respuesta.setIdPlanActivo(planIdStatus);
            respuesta.setDiaPago("SN");
            respuesta.setStatus("Sin Plan Activo o Cumplido");
        }

        return respuesta;
    }

    public RestResponse<String> obtenerPlanesPagoSemanal(datosEntradaPagosPlanes cu){
        RestResponse<String> respuesta=new RestResponse<>();
        ArrayList<datosEntradaPagosPlanes> datosPagosFinales=new ArrayList<>();
        try{

            for(int i=0;i<cu.getCu().toArray().length;i++){
                String[] cuSeparado = cu.getCu().get(i).split("-");
                String canal= cuSeparado[1].length()==2?cuSeparado[1]:"0"+cuSeparado[1];
                String cuPreparado= "0"+cuSeparado[0]+"-"+canal+"-"+cuSeparado[2]+"-"+cuSeparado[3];
                RestResponse<JSONObject> obtenerPlanCU=new RestResponse<>();
                int intentoPlanes=1;
                do {
                    obtenerPlanCU=obDatLogic.obtenerPlanesCU(cuPreparado,cu);
                    String intentoLog=String.valueOf(intentoPlanes);
                    LOGGER.log(Level.INFO, () -> "Intentando obtener los planes de pago del cliente CU "+cuPreparado+" . Intento: "+intentoLog);
                    intentoPlanes++;
                }
                while(obtenerPlanCU.getCode()!=1&&intentoPlanes!=500);

                if(obtenerPlanCU.getCode()==1) {
                    datosEntradaPagosPlanes rep =continuacionPlanesPagoSemanal(cu, cuPreparado, obtenerPlanCU.getData());
                    datosPagosFinales.add(rep);
                }
            }

            respuesta.setCode(1);
            respuesta.setData("Se obtuvieron "+datosPagosFinales.toArray().length);
            respuesta.setMessage(sdPpLogic.generarExcelPagos(datosPagosFinales));
            respuesta.setError(false);

        }
        catch (Exception e){
            respuesta.setMessage("Algo fallo "+ e);
            respuesta.setError(true);
        }

        return respuesta;
    }

    public datosEntradaPagosPlanes continuacionPlanesPagoSemanal(datosEntradaPagosPlanes cu,String cuPreparado,JSONObject planesCU){
        datosEntradaPagosPlanes respuesta=new datosEntradaPagosPlanes();
        String idUltimoPlan=planesCU.getJSONArray("resultado").getJSONObject(0).getString("idPlan");
        String status=planesCU.getJSONArray("resultado").getJSONObject(0).getString("status");
        String gestor=null;
        do{
            gestor=obDatLogic.obtenerGestorCliente(cuPreparado,cu);
            LOGGER.log(Level.INFO, () -> "Intentando obtener el gestor del Cliente "+cuPreparado);
        }
        while("NSOG".equals(gestor));

        respuesta.setGestor(gestor);
        respuesta.setStatus(status);

        RestResponse<JSONObject> obtenerPrometidos=new RestResponse<>();
        RestResponse<JSONObject> obtenerReales=new RestResponse<>();

        do {
            obtenerPrometidos = obDatLogic.obtenerPrometidos(idUltimoPlan, cuPreparado, cu);
            LOGGER.log(Level.INFO, () -> "Intentando obtener los montos prometidos");
        }while (obtenerPrometidos.getCode()!=1);


        do{
            obtenerReales=obDatLogic.obtenerReales(idUltimoPlan,cuPreparado,cu);
            LOGGER.log(Level.INFO, () -> "Intentando obtener los montos depositados");
        }while (obtenerReales.getCode()!=1);

        respuesta.setCuFinal(cuPreparado);
        respuesta.setIdPlanActivo(idUltimoPlan);
        respuesta.setMontoPrometido(String.valueOf(obtenerPrometidos.getData().getJSONArray("resultado").getJSONObject(0).get("montoPrometido")));
        JSONArray deposito=obtenerReales.getData().getJSONArray("resultado");
        if(deposito.length()==0){
            respuesta.setMontoDepositado("0");
        }
        else{
            respuesta.setMontoDepositado(String.valueOf(deposito.getJSONObject(0).get("importeAbono")));
        }


        return respuesta;
    }


    public RestResponse<JSONObject> obtenerEdoCuentas(String cu, datosEntradaPagosPlanes cokkie){
        RestResponse<JSONObject> respuesta= new RestResponse<>();

        RestResponse<JSONObject> obtenerEstadoCuenta= new RestResponse<>();

        do{
            obtenerEstadoCuenta=obDatLogic.obtenerEstadoCuenta(cu, cokkie);
            LOGGER.log(Level.INFO, () -> "Intentando obtener si el cliente tiene estados de cuenta");
        }
        while(obtenerEstadoCuenta.getCode()!=1);

        String respuestaConsulta= (String) obtenerEstadoCuenta.getData().getJSONObject("respuesta").get("mensaje");
        if(!respuestaConsulta.contains("no existe")){
            respuesta=this.obtenerEdoCuentasMontos(cu, cokkie);
        }
        else{
            respuesta.setCode(1);
            respuesta.setMessage("Cuenta no existe");
            respuesta.setError(false);
        }

        return respuesta;
    }

    public RestResponse<JSONObject> obtenerEdoCuentasMontos(String cu, datosEntradaPagosPlanes cokkie){
        RestResponse<JSONObject> respuesta= new RestResponse<>();
        RestResponse<JSONObject> obtenerAbonosEdoCuenta=new RestResponse<>();
        JSONObject abonosEdoCuenta= new JSONObject();
        String diasAbonos="";
        Integer abonosTotal=0;
        int sumaIntentos=1;
        do{
            obtenerAbonosEdoCuenta=obDatLogic.obtenerAbonosEdoCuenta(cu, cokkie);
            int finalSumaIntentos = sumaIntentos;
            LOGGER.log(Level.INFO, () -> "Intentando montos del estado de cuenta del cliente "+ cu+ ", intento "+finalSumaIntentos);
            sumaIntentos++;
        }
        while(obtenerAbonosEdoCuenta.getCode()!=1&&sumaIntentos!=500);

        String[] fechaHora=util.obtenerFechaActual().split(" ");
        String[] fechaCompleta=fechaHora[0].split("-");
        String fechaMesYear=fechaCompleta[1]+"/"+fechaCompleta[2];
        if(obtenerAbonosEdoCuenta.getData()!=null) {
            JSONArray abonos = obtenerAbonosEdoCuenta.getData().getJSONArray("respuesta");

            if (abonos.length() > 0) {
                for (int i = 0; i < abonos.length(); i++) {

                    String fecha = abonos.getJSONObject(i).getString("fecha");
                    String cargoAt= abonos.getJSONObject(i).getString("cargo_automatico");
//                    if (fecha.contains(fechaMesYear)&&"NO".equals(cargoAt)) {
                    if ("NO".equals(cargoAt)) {
                        Integer importeAbono = abonos.getJSONObject(i).getInt("importe");
                        abonosTotal = abonosTotal + importeAbono;
                        diasAbonos = diasAbonos + "," + (String) abonos.getJSONObject(i).get("fecha") + "=" + importeAbono ;
                    }
                }
                abonosEdoCuenta.put("abono", abonosTotal);
                abonosEdoCuenta.put("diasPago", diasAbonos);

                respuesta.setCode(1);
                respuesta.setMessage("Montos colocados correctamente");
                respuesta.setData(abonosEdoCuenta);
                respuesta.setError(false);

            } else {
                respuesta.setCode(1);
                respuesta.setMessage("Sin abonos");
            }
        }
        else{
            respuesta.setCode(1);
            respuesta.setMessage("Sin abonos");
        }

        return respuesta;
    }

    public RestResponse<ArrayList<PromesasModel>> obtenerPagosDia(datosEntradaPagosPlanes cookie) {
        RestResponse<ArrayList<PromesasModel>> respuesta=new RestResponse<>();
        String[] fechaHora=util.obtenerFechaActual().split(" ");
        String[] fechaCompleta=fechaHora[0].split("-");

        ArrayList<PromesasModel> promesasCompletas=promesas.consultarPromesas().getData();
        ArrayList<PromesasModel>promesasDia=new ArrayList<>();
        for(int i=0;i<promesasCompletas.size();i++){
            if(cookie.getDiaPago().equals(promesasCompletas.get(i).getFechaPago())){
                RestResponse<JSONObject>montos=this.obtenerEdoCuentasMontos(promesasCompletas.get(i).getClienteUnico(),cookie);
                if(montos.getData()!=null) {
                    String montosObtenidos = montos.getData().getString("diasPago");
                    if (!"".equals(montosObtenidos)) {
                        String[] montosEnc = montosObtenidos.split(",");
                        int bandera = 0;
                        for (int j = 0; j < montosEnc.length; j++) {
                            if (!"".equals(montosEnc[j])) {
                                String[] separarMonto = montosEnc[j].split("=");
                                if (separarMonto[0].equals(cookie.getIdPlanActivo())) {
                                    Integer monto = Integer.parseInt(separarMonto[1]);
                                    if (monto > 0) {
                                        PromesasModel promesa = new PromesasModel();
                                        promesa.setId(promesasCompletas.get(i).getId());
                                        promesa.setClienteUnico(promesasCompletas.get(i).getClienteUnico());
                                        promesa.setNota(promesasCompletas.get(i).getNota());
                                        promesa.setPagoFinal(monto);
                                        promesa.setNombreCliente(promesasCompletas.get(i).getNombreCliente());
                                        promesa.setNombreGestor(promesasCompletas.get(i).getNombreGestor());
                                        promesa.setMontoPago(promesasCompletas.get(i).getMontoPago());
                                        promesasDia.add(promesa);
                                        promesas.actualizarPromesasEstPag(promesa);
                                        bandera = 1;
                                    }

                                }

                            }
                        }

                        if (bandera == 0) {
                            promesasDia.add(promesasCompletas.get(i));
                        }
                    }
                    else {
                        promesasDia.add(promesasCompletas.get(i));
                    }
                }
                else {
                    promesasDia.add(promesasCompletas.get(i));
                }

            }

        }

        respuesta.setCode(1);
        respuesta.setMessage("Datos obtenidos");
        respuesta.setData(promesasDia);

        return respuesta;
    }

    public RestResponse<ArrayList<ClienteModel>> validarPromesasPago2semanas(String json){
        Calendar calendar = Calendar.getInstance();
        int numeroSemana = calendar.get(Calendar.WEEK_OF_YEAR);
        int numeroSemanaPasada=numeroSemana-1;
        int numeroSemanaAntePasada=numeroSemana-2;
        if(numeroSemana==1){
            numeroSemanaPasada=52;
            numeroSemanaAntePasada=51;
        }

        String json1="{\n" +
                "    \"anio\":2024,\n" +
                "    \"despacho\":\"58921\",\n" +
                "    \"segmento\":0,\n" +
                "    \"semana\":"+numeroSemanaPasada+"\n" +
                "}";

        String json2="{\n" +
                "    \"anio\":2024,\n" +
                "    \"despacho\":\"58921\",\n" +
                "    \"segmento\":0,\n" +
                "    \"semana\":"+numeroSemanaAntePasada+"\n" +
                "}";

        JSONObject jsonCookie=new JSONObject(json);

        RestResponse<JSONObject> reporteSemPas=obDatLogic.obtenerPagosReporteSCl((String) jsonCookie.get("cokkie"),json1);
        RestResponse<JSONObject> reporteSemAntPas=obDatLogic.obtenerPagosReporteSCl((String) jsonCookie.get("cokkie"),json2);

        return this.validarPagosPromesas(reporteSemPas.getData(),reporteSemAntPas.getData());
    }

    private RestResponse<ArrayList<ClienteModel>> validarPagosPromesas(JSONObject reporteSemPas, JSONObject reporteSemAnt) {
        RestResponse<ArrayList<ClienteModel>> respuesta = new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>obtenerPromesas=carteraLog.consultarCarteraConPromesa();
        JSONArray pagosSemPas=reporteSemPas.getJSONArray("respuesta");
        JSONArray pagosSemAntPas=reporteSemAnt.getJSONArray("respuesta");
        ArrayList<ClienteModel> valores=new ArrayList<>();
        ArrayList<String> actualizarMontoPromesa=new ArrayList<>();

        for(int i=0;i<obtenerPromesas.getData().size();i++){
            int bandera=0;
            for(int j=0;j<pagosSemPas.length();j++){
                JSONObject pagos=(JSONObject) pagosSemPas.get(j);
                Float montoPago= (Float) pagos.get("recupporgestion");
                if(montoPago!=0){
                    String cuMonto= (String) pagos.get("clienteUnico");
                    if(obtenerPromesas.getData().get(i).getCLIENTE_UNICO().equals(cuMonto)){
                      bandera=1;
                      obtenerPromesas.getData().get(i).setMONTO_PROMESA_PAGO(String.valueOf(montoPago));
                      String actualizar=montoPago+","+obtenerPromesas.getData().get(i).getCLIENTE_UNICO();
                      actualizarMontoPromesa.add(actualizar);
                    }
                }
            }

            if(bandera==1){
                for(int k=0;k<pagosSemAntPas.length();k++){
                    JSONObject pagos= (JSONObject) pagosSemAntPas.get(k);
                    Float montoPago= (Float) pagos.get("recupporgestion");
                    if(montoPago!=0){
                        String cuMonto= (String) pagos.get("clienteUnico");
                        if(obtenerPromesas.getData().get(i).getCLIENTE_UNICO().equals(cuMonto)){
                            obtenerPromesas.getData().get(i).setMONTO_PROMESA_PAGO(String.valueOf(montoPago));
                            String actualizar=montoPago+","+obtenerPromesas.getData().get(i).getCLIENTE_UNICO();
                            actualizarMontoPromesa.add(actualizar);
                        }
                    }
                }
            }

            valores.add(obtenerPromesas.getData().get(i));
        }

        this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesa);

        respuesta.setCode(1);
        respuesta.setMessage("Proceso terminado correctamente");
        respuesta.setData(valores);



        return respuesta;
    }






}
