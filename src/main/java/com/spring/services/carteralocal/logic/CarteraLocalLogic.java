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
import org.json.JSONArray;
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

//            this.guardarNuevas(cuentas);


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

    public RestResponse<String> carteraCompletaGuardarLocalPuente(String json){
        RestResponse<String> respuesta=new RestResponse<>();
        JSONObject jsonRec=new JSONObject(json);
        JSONArray arrayCuentas=jsonRec.getJSONArray("cartera");
        ArrayList<ClienteModel> cartera=new ArrayList<>();

        for(int i=0;i<arrayCuentas.length();i++){
            ClienteModel cuenta=new ClienteModel();
            JSONObject jsonCuenta= (JSONObject) arrayCuentas.get(i);
            cuenta.setCLIENTE_UNICO((String) jsonCuenta.get("CLIENTE_UNICO"));
            cuenta.setNOMBRE_CTE((String)jsonCuenta.get("NOMBRE_CTE"));
            cuenta.setRFC_CTE((String)jsonCuenta.get("RFC_CTE"));
            cuenta.setGENERO_CLIENTE((String)jsonCuenta.get("GENERO_CLIENTE"));
            cuenta.setEDAD_CLIENTE((String)jsonCuenta.get("EDAD_CLIENTE"));
            cuenta.setOCUPACION((String)jsonCuenta.get("OCUPACION"));
            cuenta.setCORREO_ELECTRONICO((String)jsonCuenta.get("CORREO_ELECTRONICO"));
            cuenta.setDIRECCION_CTE((String)jsonCuenta.get("DIRECCION_CTE"));
            cuenta.setNUM_EXT_CTE((String)jsonCuenta.get("NUM_EXT_CTE"));
            cuenta.setNUM_INT_CTE((String)jsonCuenta.get("NUM_INT_CTE"));
            cuenta.setCP_CTE((String)jsonCuenta.get("CP_CTE"));
            cuenta.setCOLONIA_CTE((String)jsonCuenta.get("COLONIA_CTE"));
            cuenta.setPOBLACION_CTE((String)jsonCuenta.get("POBLACION_CTE"));
            cuenta.setESTADO_CTE((String)jsonCuenta.get("ESTADO_CTE"));
            cuenta.setTERRITORIO((String)jsonCuenta.get("TERRITORIO"));
            cuenta.setTERRITORIAL((String)jsonCuenta.get("TERRITORIAL"));
            cuenta.setZONA((String)jsonCuenta.get("ZONA"));
            cuenta.setZONAL((String)jsonCuenta.get("ZONAL"));
            cuenta.setNOMBRE_DESPACHO((String)jsonCuenta.get("NOMBRE_DESPACHO"));
            cuenta.setGERENCIA((String)jsonCuenta.get("GERENCIA"));
            cuenta.setFECHA_ASIGNACION((String)jsonCuenta.get("FECHA_ASIGNACION"));
            cuenta.setDIAS_ASIGNACION((String)jsonCuenta.get("DIAS_ASIGNACION"));
            cuenta.setREFERENCIAS_DOMICILIO((String)jsonCuenta.get("REFERENCIAS_DOMICILIO"));
            cuenta.setCLASIFICACION_CTE((String)jsonCuenta.get("CLASIFICACION_CTE"));
            cuenta.setDIQUE((String)jsonCuenta.get("DIQUE"));
            cuenta.setATRASO_MAXIMO((String)jsonCuenta.get("ATRASO_MAXIMO"));
            cuenta.setDIAS_ATRASO((Integer) jsonCuenta.get(" DIAS_ATRASO"));
            cuenta.setSALDO((String)jsonCuenta.get("SALDO"));
            cuenta.setMORATORIOS((String)jsonCuenta.get("MORATORIOS"));
            cuenta.setSALDO_TOTAL((Float) jsonCuenta.get("SALDO_TOTAL"));
            cuenta.setSALDO_ATRASADO((String)jsonCuenta.get("SALDO_ATRASADO"));
            cuenta.setSALDO_REQUERIDO((String)jsonCuenta.get("SALDO_REQUERIDO"));
            cuenta.setPAGO_PUNTUAL((String)jsonCuenta.get("PAGO_PUNTUAL"));
            cuenta.setPAGO_NORMAL((String)jsonCuenta.get("PAGO_NORMAL"));
            cuenta.setPRODUCTO((String)jsonCuenta.get("PRODUCTO"));
            cuenta.setFECHA_ULTIMO_PAGO((String)jsonCuenta.get("FECHA_ULTIMO_PAGO"));
            cuenta.setIMP_ULTIMO_PAGO((String)jsonCuenta.get("IMP_ULTIMO_PAGO"));
            cuenta.setCALLE_EMPLEO((String)jsonCuenta.get("CALLE_EMPLEO"));
            cuenta.setNUM_EXT_EMPLEO((String)jsonCuenta.get("NUM_EXT_EMPLEO"));
            cuenta.setNUM_INT_EMPLEO((String)jsonCuenta.get("NUM_INT_EMPLEO"));
            cuenta.setCOLONIA_EMPLEO((String)jsonCuenta.get("COLONIA_EMPLEO"));
            cuenta.setPOBLACION_EMPLEO((String)jsonCuenta.get("POBLACION_EMPLEO"));
            cuenta.setESTADO_EMPLEO((String)jsonCuenta.get("ESTADO_EMPLEO"));
            cuenta.setNOMBRE_AVAL((String)jsonCuenta.get("NOMBRE_AVAL"));
            cuenta.setTEL_AVAL((String)jsonCuenta.get("TEL_AVAL"));
            cuenta.setCALLE_AVAL((String)jsonCuenta.get("CALLE_AVAL"));
            cuenta.setNUM_EXT_AVAL((String)jsonCuenta.get("NUM_EXT_AVAL"));
            cuenta.setCOLONIA_AVAL((String)jsonCuenta.get("COLONIA_AVAL"));
            cuenta.setCP_AVAL((String)jsonCuenta.get("CP_AVAL"));
            cuenta.setPOBLACION_AVAL((String)jsonCuenta.get("POBLACION_AVAL"));
            cuenta.setESTADO_AVAL((String)jsonCuenta.get("ESTADO_AVAL"));
            cuenta.setCLIENTE_GRUPAL((String)jsonCuenta.get("CLIENTE_GRUPAL"));
            cuenta.setFIPAISGEO((String)jsonCuenta.get("FIPAISGEO"));
            cuenta.setFICUADRANTEGEO((String)jsonCuenta.get("FICUADRANTEGEO"));
            cuenta.setFIZONAGEO((String)jsonCuenta.get("FIZONAGEO"));
            cuenta.setFIDIAPAGO((String)jsonCuenta.get("FIDIAPAGO"));
            cuenta.setTELEFONO1((String)jsonCuenta.get("TELEFONO1"));
            cuenta.setTELEFONO2((String)jsonCuenta.get("TELEFONO2"));
            cuenta.setTELEFONO3((String)jsonCuenta.get("TELEFONO3"));
            cuenta.setTELEFONO4((String)jsonCuenta.get("TELEFONO4"));
            cuenta.setTIPOTEL1((String)jsonCuenta.get("TIPOTEL1"));
            cuenta.setTIPOTEL2((String)jsonCuenta.get("TIPOTEL2"));
            cuenta.setTIPOTEL3((String)jsonCuenta.get("TIPOTEL3"));
            cuenta.setTIPOTEL4((String)jsonCuenta.get("TIPOTEL4"));
            cuenta.setLATITUD((String)jsonCuenta.get("LATITUD"));
            cuenta.setLONGITUD((String)jsonCuenta.get("LONGITUD"));
            cuenta.setDESPACHO_GESTIONO((String)jsonCuenta.get("DESPACHO_GESTIONO"));
            cuenta.setULTIMA_GESTION((String)jsonCuenta.get("ULTIMA_GESTION"));
            cuenta.setGESTION_DESC((String)jsonCuenta.get("GESTION_DESC"));
            cuenta.setCAMPANIA_RELAMPAGO((String)jsonCuenta.get("CAMPANIA_RELAMPAGO"));
            cuenta.setCAMPANIA((String)jsonCuenta.get("CAMPANIA"));
            cuenta.setTIPO_CARTERA((String)jsonCuenta.get("TIPO_CARTERA"));
            cuenta.setID_GRUPO((String)jsonCuenta.get("ID_GRUPO"));
            cuenta.setGRUPO_MAZ((String)jsonCuenta.get("GRUPO_MAZ"));
            cuenta.setCLAVE_SPEI((String)jsonCuenta.get("CLAVE_SPEI"));
            cuenta.setPAGOS_CLIENTE((String)jsonCuenta.get("PAGOS_CLIENTE"));
            cuenta.setMONTO_PAGOS((String)jsonCuenta.get("MONTO_PAGOS"));
            cuenta.setGESTORES((String)jsonCuenta.get("GESTORES"));
            cuenta.setFOLIO_PLAN((String)jsonCuenta.get("FOLIO_PLAN"));
            cuenta.setSEGMENTO_GENERACION((String)jsonCuenta.get("SEGMENTO_GENERACION"));
            cuenta.setESTATUS_PLAN((String)jsonCuenta.get("ESTATUS_PLAN"));
            cuenta.setSEMANAS_ATRASO((String)jsonCuenta.get("SEMANAS_ATRASO"));
            cuenta.setATRASO((String)jsonCuenta.get("ATRASO"));
            cuenta.setGENERACION_PLAN((String)jsonCuenta.get("GENERACION_PLAN"));
            cuenta.setCANCELACION_CUMPLIMIENTO_PLAN((String)jsonCuenta.get("CANCELACION_CUMPLIMIENTO_PLAN"));
            cuenta.setULTIMO_ESTATUS((String)jsonCuenta.get("ULTIMO_ESTATUS"));
            cuenta.setEMPLEADO((String)jsonCuenta.get("EMPLEADO"));
            cuenta.setCANAL((String)jsonCuenta.get("CANAL"));
            cuenta.setABONO_SEMANAL((String)jsonCuenta.get("ABONO_SEMANAL"));
            cuenta.setPLAZO((String)jsonCuenta.get("PLAZO"));
            cuenta.setMONTO_ABONADO((String)jsonCuenta.get("MONTO_ABONADO"));
            cuenta.setMONTO_PLAN((String)jsonCuenta.get("MONTO_PLAN"));
            cuenta.setENGANCHE((String)jsonCuenta.get("ENGANCHE"));
            cuenta.setPAGOS_RECIBIDOS((String)jsonCuenta.get("PAGOS_RECIBIDOS"));
            cuenta.setSALDO_ANTES_DEL_PLAN((String)jsonCuenta.get("SALDO_ANTES_DEL_PLAN"));
            cuenta.setSALDO_ATRASADO_ANTES_PLAN((String)jsonCuenta.get("SALDO_ATRASADO_ANTES_PLAN"));
            cuenta.setMORATORIOS_ANTES_PLAN((String)jsonCuenta.get("MORATORIOS_ANTES_PLAN"));
            cuenta.setESTATUS_PROMESA_PAGO((String)jsonCuenta.get("ESTATUS_PROMESA_PAGO"));
            cuenta.setMONTO_PROMESA_PAGO((String)jsonCuenta.get("MONTO_PROMESA_PAGO"));
            cuenta.setSEGMENTO((Integer) jsonCuenta.get("SEGMENTO"));
            cartera.add(cuenta);
        }

        return guardarCarteraCompletaDia(cartera);
    }

    private RestResponse<String> guardarCarteraCompletaDia(ArrayList<ClienteModel> cartera){
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
            JSONObject json=prepararDatosPuente(cartera);
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpPost serviceRequest = new HttpPost("http://172.16.201.6:8080/api/v1/msvc-template/service/carteraLocal/carteraCompletaGuardarLocal");
            StringEntity post = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
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
            JSONObject json=prepararDatosPuente(cartera);
//            JSONArray json=prepararDatosPuente(cartera);
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory((LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new TrustSelfSignedStrategy()).build())).build();
            HttpPost serviceRequest = new HttpPost("http://172.16.201.6:8080/api/v1/msvc-template/service/carteraLocal/carteraDescarteGuardarLocal");
            StringEntity post = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
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

    private JSONObject prepararDatosPuente(ArrayList<ClienteModel> cartera){
        JSONObject respuesta=new JSONObject();
        JSONArray datos=new JSONArray();
        for (int i=0;i<cartera.size();i++){
            JSONObject cliente=new JSONObject();
            cliente.put("CLIENTE_UNICO",cartera.get(i).getCLIENTE_UNICO());
            cliente.put("NOMBRE_CTE",cartera.get(i).getNOMBRE_CTE());
            cliente.put("RFC_CTE",cartera.get(i).getRFC_CTE());
            cliente.put("GENERO_CLIENTE",cartera.get(i).getGENERO_CLIENTE());
            cliente.put("EDAD_CLIENTE",cartera.get(i).getEDAD_CLIENTE());
            cliente.put("OCUPACION",cartera.get(i).getOCUPACION());
            cliente.put("CORREO_ELECTRONICO",cartera.get(i).getCORREO_ELECTRONICO());
            cliente.put("DIRECCION_CTE",cartera.get(i).getDIRECCION_CTE());
            cliente.put("NUM_EXT_CTE",cartera.get(i).getNUM_EXT_CTE());
            cliente.put("NUM_INT_CTE",cartera.get(i).getNUM_INT_CTE());
            cliente.put("CP_CTE",cartera.get(i).getCP_CTE());
            cliente.put("COLONIA_CTE",cartera.get(i).getCOLONIA_CTE());
            cliente.put("POBLACION_CTE",cartera.get(i).getPOBLACION_CTE());
            cliente.put("ESTADO_CTE",cartera.get(i).getESTADO_CTE());
            cliente.put("TERRITORIO",cartera.get(i).getTERRITORIO());
            cliente.put("TERRITORIAL",cartera.get(i).getTERRITORIAL());
            cliente.put("ZONA",cartera.get(i).getZONA());
            cliente.put("ZONAL",cartera.get(i).getZONAL());
            cliente.put("NOMBRE_DESPACHO",cartera.get(i).getNOMBRE_DESPACHO());
            cliente.put("GERENCIA",cartera.get(i).getGERENCIA());
            cliente.put("FECHA_ASIGNACION",cartera.get(i).getFECHA_ASIGNACION());
            cliente.put("DIAS_ASIGNACION",cartera.get(i).getDIAS_ASIGNACION());
            cliente.put("REFERENCIAS_DOMICILIO",cartera.get(i).getREFERENCIAS_DOMICILIO());
            cliente.put("CLASIFICACION_CTE",cartera.get(i).getCLASIFICACION_CTE());
            cliente.put("DIQUE",cartera.get(i).getDIQUE());
            cliente.put("ATRASO_MAXIMO",cartera.get(i).getATRASO_MAXIMO());
            cliente.put(" DIAS_ATRASO",cartera.get(i).getDIAS_ATRASO());
            cliente.put("SALDO",cartera.get(i).getSALDO());
            cliente.put("MORATORIOS",cartera.get(i).getMORATORIOS());
            cliente.put("SALDO_TOTAL",cartera.get(i).getSALDO_TOTAL());
            cliente.put("SALDO_ATRASADO",cartera.get(i).getSALDO_ATRASADO());
            cliente.put("SALDO_REQUERIDO",cartera.get(i).getSALDO_REQUERIDO());
            cliente.put("PAGO_PUNTUAL",cartera.get(i).getPAGO_PUNTUAL());
            cliente.put("PAGO_NORMAL",cartera.get(i).getPAGO_NORMAL());
            cliente.put("PRODUCTO",cartera.get(i).getPRODUCTO());
            cliente.put("FECHA_ULTIMO_PAGO",cartera.get(i).getFECHA_ULTIMO_PAGO());
            cliente.put("IMP_ULTIMO_PAGO",cartera.get(i).getIMP_ULTIMO_PAGO());
            cliente.put("CALLE_EMPLEO",cartera.get(i).getCALLE_EMPLEO());
            cliente.put("NUM_EXT_EMPLEO",cartera.get(i).getNUM_EXT_EMPLEO());
            cliente.put("NUM_INT_EMPLEO",cartera.get(i).getNUM_INT_EMPLEO());
            cliente.put("COLONIA_EMPLEO",cartera.get(i).getCOLONIA_EMPLEO());
            cliente.put("POBLACION_EMPLEO",cartera.get(i).getPOBLACION_EMPLEO());
            cliente.put("ESTADO_EMPLEO",cartera.get(i).getESTADO_EMPLEO());
            cliente.put("NOMBRE_AVAL",cartera.get(i).getNOMBRE_AVAL());
            cliente.put("TEL_AVAL",cartera.get(i).getTEL_AVAL());
            cliente.put("CALLE_AVAL",cartera.get(i).getCALLE_AVAL());
            cliente.put("NUM_EXT_AVAL",cartera.get(i).getNUM_EXT_AVAL());
            cliente.put("COLONIA_AVAL",cartera.get(i).getCOLONIA_AVAL());
            cliente.put("CP_AVAL",cartera.get(i).getCP_AVAL());
            cliente.put("POBLACION_AVAL",cartera.get(i).getPOBLACION_AVAL());
            cliente.put("ESTADO_AVAL",cartera.get(i).getESTADO_AVAL());
            cliente.put("CLIENTE_GRUPAL",cartera.get(i).getCLIENTE_GRUPAL());
            cliente.put("FIPAISGEO",cartera.get(i).getFIPAISGEO());
            cliente.put("FICUADRANTEGEO",cartera.get(i).getFICUADRANTEGEO());
            cliente.put("FIZONAGEO",cartera.get(i).getFIZONAGEO());
            cliente.put("FIDIAPAGO",cartera.get(i).getFIDIAPAGO());
            cliente.put("TELEFONO1",cartera.get(i).getTELEFONO1());
            cliente.put("TELEFONO2",cartera.get(i).getTELEFONO2());
            cliente.put("TELEFONO3",cartera.get(i).getTELEFONO3());
            cliente.put("TELEFONO4",cartera.get(i).getTELEFONO4());
            cliente.put("TIPOTEL1",cartera.get(i).getTIPOTEL1());
            cliente.put("TIPOTEL2",cartera.get(i).getTIPOTEL2());
            cliente.put("TIPOTEL3",cartera.get(i).getTIPOTEL3());
            cliente.put("TIPOTEL4",cartera.get(i).getTIPOTEL4());
            cliente.put("LATITUD",cartera.get(i).getLATITUD());
            cliente.put("LONGITUD",cartera.get(i).getLONGITUD());
            cliente.put("DESPACHO_GESTIONO",cartera.get(i).getDESPACHO_GESTIONO());
            cliente.put("ULTIMA_GESTION",cartera.get(i).getULTIMA_GESTION());
            cliente.put("GESTION_DESC",cartera.get(i).getGESTION_DESC());
            cliente.put("CAMPANIA_RELAMPAGO",cartera.get(i).getCAMPANIA_RELAMPAGO());
            cliente.put("CAMPANIA",cartera.get(i).getCAMPANIA());
            cliente.put("TIPO_CARTERA",cartera.get(i).getTIPO_CARTERA());
            cliente.put("ID_GRUPO",cartera.get(i).getID_GRUPO());
            cliente.put("GRUPO_MAZ",cartera.get(i).getGRUPO_MAZ());
            cliente.put("CLAVE_SPEI",cartera.get(i).getCLAVE_SPEI());
            cliente.put("PAGOS_CLIENTE",cartera.get(i).getPAGOS_CLIENTE());
            cliente.put("MONTO_PAGOS",cartera.get(i).getMONTO_PAGOS());
            cliente.put("GESTORES",cartera.get(i).getGESTORES());
            cliente.put("FOLIO_PLAN",cartera.get(i).getFOLIO_PLAN());
            cliente.put("SEGMENTO_GENERACION",cartera.get(i).getSEGMENTO_GENERACION());
            cliente.put("ESTATUS_PLAN",cartera.get(i).getESTATUS_PLAN());
            cliente.put("SEMANAS_ATRASO",cartera.get(i).getSEMANAS_ATRASO());
            cliente.put("ATRASO",cartera.get(i).getATRASO());
            cliente.put("GENERACION_PLAN",cartera.get(i).getGENERACION_PLAN());
            cliente.put("CANCELACION_CUMPLIMIENTO_PLAN",cartera.get(i).getCANCELACION_CUMPLIMIENTO_PLAN());
            cliente.put("ULTIMO_ESTATUS",cartera.get(i).getULTIMO_ESTATUS());
            cliente.put("EMPLEADO",cartera.get(i).getEMPLEADO());
            cliente.put("CANAL",cartera.get(i).getCANAL());
            cliente.put("ABONO_SEMANAL",cartera.get(i).getABONO_SEMANAL());
            cliente.put("PLAZO",cartera.get(i).getPLAZO());
            cliente.put("MONTO_ABONADO",cartera.get(i).getMONTO_ABONADO());
            cliente.put("MONTO_PLAN",cartera.get(i).getMONTO_PLAN());
            cliente.put("ENGANCHE",cartera.get(i).getENGANCHE());
            cliente.put("PAGOS_RECIBIDOS",cartera.get(i).getPAGOS_RECIBIDOS());
            cliente.put("SALDO_ANTES_DEL_PLAN",cartera.get(i).getSALDO_ANTES_DEL_PLAN());
            cliente.put("SALDO_ATRASADO_ANTES_PLAN",cartera.get(i).getSALDO_ATRASADO_ANTES_PLAN());
            cliente.put("MORATORIOS_ANTES_PLAN",cartera.get(i).getMORATORIOS_ANTES_PLAN());
            cliente.put("ESTATUS_PROMESA_PAGO",cartera.get(i).getESTATUS_PROMESA_PAGO());
            cliente.put("MONTO_PROMESA_PAGO",cartera.get(i).getMONTO_PROMESA_PAGO());
            cliente.put("SEGMENTO",cartera.get(i).getSEGMENTO());
            datos.put(cliente);
        }

        respuesta.put("cartera",datos);
        return respuesta;
    }


}
