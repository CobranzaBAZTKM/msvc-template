package com.spring.services.carteralocal.logic;

import com.spring.services.cartera.logic.ObtenerClientesCartera;
import com.spring.services.cartera.logic.TerritoriosCarteraLogic;
import com.spring.services.cartera.logic.VIPCarteraLogic;
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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;


@Component
public class CarteraLocalLogic {
    private static final Logger LOGGER = LogManager.getLogger("CarteraLocalLogic");
    ObtenerClientesCartera obtenerCartera=new ObtenerClientesCartera();
    GestionLlamadasLogic gestionLlam=new GestionLlamadasLogic();
    TipificacionesLogic tipificaciones=new TipificacionesLogic();
    UtilService utilService=new UtilService();
    CarteraLocalDAO carteraDAO=new CarteraLocalDAO();
    CarteraLocalDAO2 carteraDAO2=new CarteraLocalDAO2();
    VIPCarteraLogic obtenerCartVIP=new VIPCarteraLogic();
    TerritoriosCarteraLogic obtenerCarTer=new TerritoriosCarteraLogic();



    public CarteraLocalLogic() {
        //Vacio
    }

    public RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(ExtrasModel cokkie,String tipoCarteraTKM)  {
        LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Comienza proceso, tipo Cartera: "+tipoCarteraTKM);
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>carteraCompleta=carteraDAO.consultarCarteraCompletaPorCartera(tipoCarteraTKM);

//        switch (tipoCarteraTKM){
//            case "1":
//                carteraCompleta=obtenerCartera.carteraCompleta(cokkie);
//                break;
//            case "2":
//                carteraCompleta=obtenerCartVIP.VIPcarteraCompleta(cokkie);
//                break;
//            case "3":
//                carteraCompleta=obtenerCarTer.TerriSCLcarteraCompleta(cokkie);
//                break;
//            default:
//                //Vacio
//                break;
//        }
//        if("1".equals(tipoCarteraTKM)){
//            carteraCompleta=obtenerCartera.carteraCompleta(cokkie);
//        }
//        else{
//            carteraCompleta=obtenerCartVIP.VIPcarteraCompleta(cokkie);
//        }

//        if(carteraCompleta.getCode()==1){

//            carteraDAO.borrarCarteraCompleta(tipoCarteraTKM);
//            this.guardarCarteraCompletaDia(carteraCompleta.getData());

            ArrayList<ClienteModel> cuentasConPromesa=carteraDAO.consultarCuentasConPromesa(tipoCarteraTKM).getData();
            ArrayList<ClienteModel> cuentasSinContacto=carteraDAO.consultarCuentasSinContactoTT(tipoCarteraTKM).getData();

            String fecha=utilService.FechaDiaAnteriorPosterior(-1);
            Integer cantidadCuentasLog=carteraCompleta.getData().size();
            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Se obtuvieron "+cantidadCuentasLog);
            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Comenzando validacion de cuentas con gestion");
            ArrayList<ClienteModel>cuentas=this.revisarGestiones(fecha,carteraCompleta.getData(),tipoCarteraTKM).getData();

            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Termina validacion de cuentas con gestion");

//            this.guardarNuevas(cuentas,tipoCarteraTKM)
            carteraDAO2.borrarCarteraDescarte(tipoCarteraTKM);

            RestResponse<ArrayList<ClienteModel>>descartarPromesas=this.guardarConPromesa(cuentas,cuentasConPromesa,tipoCarteraTKM);
            RestResponse<ArrayList<ClienteModel>>obtenerGestionesGral=this.revisarGestionesGenerales(descartarPromesas.getData(),tipoCarteraTKM);
            RestResponse<ArrayList<ClienteModel>>descartarNoContacto=this.descartarNoCCTT(obtenerGestionesGral.getData(),cuentasSinContacto,tipoCarteraTKM);
//            RestResponse<ArrayList<ClienteModel>>descartarNoContacto=this.descartarNoCCTT(descartarPromesas.getData(),cuentasSinContacto,tipoCarteraTKM);
            ArrayList<ClienteModel>sinCumplidosVigente=descartarCumplidosVigente(descartarNoContacto.getData());

            this.guardarCarteraDescarte(sinCumplidosVigente,tipoCarteraTKM);

            respuesta.setCode(1);
            respuesta.setMessage("Cartera Obtenida correctamente");
            respuesta.setData(sinCumplidosVigente);


//        }
//        else{
//            respuesta=carteraCompleta;
//        }

        LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Termina proceso");

        return respuesta;
    }


    public RestResponse<ArrayList<ClienteModel>> nuevasCuentasDia() {
//        RestResponse<ArrayList<ClienteModel>>carteraCompleta=obtCliLog.carteraCompleta(cokkie);
        return carteraDAO.consultarCuentasNuevasDia();
    }

    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte(String tipoCarteraTKM) {
        LOGGER.log(Level.INFO, () -> "consultarCarteraDescarte: Comenzando consulta de la cartera con Descarte");
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        String[] fechaHora=utilService.obtenerFechaActual().split(" ");
        String fecha=fechaHora[0];
        ArrayList<ClienteModel> cuentasConPromesa=carteraDAO.consultarCuentasConPromesa(tipoCarteraTKM).getData();
        ArrayList<ClienteModel> cuentasSinContacto=carteraDAO.consultarCuentasSinContactoTT(tipoCarteraTKM).getData();
        ArrayList<ClienteModel> cuentasDescarte=carteraDAO2.consultarCarteraDescarte(tipoCarteraTKM).getData();
        LOGGER.log(Level.INFO, () -> "consultarCarteraDescarte: Se obtuvieron "+cuentasDescarte.size()+" de la cartera con Descarte");
        ArrayList<ClienteModel> cuentasGestion=this.revisarGestiones(fecha,cuentasDescarte,tipoCarteraTKM).getData();
        LOGGER.log(Level.INFO, () -> "consultarCarteraDescarte: Total de  Revision de Gestiones"+cuentasGestion.size());
        Collections.sort(cuentasGestion,((o1, o2)-> o1.getSEGMENTO().compareTo(o2.getSEGMENTO())));

        RestResponse<ArrayList<ClienteModel>>descartarPromesas=this.guardarConPromesa(cuentasGestion,cuentasConPromesa,tipoCarteraTKM);
        RestResponse<ArrayList<ClienteModel>>descartarNoContacto=this.descartarNoCCTT(descartarPromesas.getData(),cuentasSinContacto,tipoCarteraTKM);

        respuesta.setCode(1);
        respuesta.setMessage("Proceso terminado correctamente, se obtienen cuentas");
        respuesta.setData(descartarNoContacto.getData());
        LOGGER.log(Level.INFO, () -> "consultarCarteraDescarte: Termina consulta de la cartera con Descarte");
        return respuesta;
    }


//    private RestResponse<String> guardarNuevas(ArrayList<ClienteModel> cartera){
//        LOGGER.log(Level.INFO, () -> "guardarNuevas: Comienza guardado de cuentas nuevas");
//        RestResponse<String> respuesta=new RestResponse<>();
//        ArrayList<ClienteModel>diaAnterior=carteraDAO.consultarCarteraCompleta().getData();
//        carteraDAO.borrarNuevasDia();
//        ArrayList<ClienteModel> cuentasNuevas=new ArrayList<>();
//        for(int i=0;i<cartera.size();i++){
//            int valor=0;
//            for(int j=0;j<diaAnterior.size();j++){
//                if(cartera.get(i).getCLIENTE_UNICO().equals(diaAnterior.get(j).getCLIENTE_UNICO())){
//                    valor=1;
//                }
//            }
//
//            if(valor==0){
//                cuentasNuevas.add(cartera.get(i));
//            }
//        }
//
//
//        if(cuentasNuevas.size()>0){
//            LOGGER.log(Level.INFO, () -> "guardarNuevas: Se insertan "+cuentasNuevas.size()+" cuentasNuevas");
//            carteraDAO.insertarCuentasNuevasDia(cuentasNuevas);
//        }
//        carteraDAO.borrarCarteraCompleta();
//        carteraDAO2.borrarCarteraDescarte();
//        respuesta.setCode(1);
//        respuesta.setMessage("Cuenta nuevas ingresadas");
//        respuesta.setData("Cuenta nuevas ingresadas");
//        LOGGER.log(Level.INFO, () -> "guardarNuevas: Termina guardado de cuentas nuevas");
//
//        return respuesta;
//
//    }


    public RestResponse<String> guardarCarteraCompletaDia(ArrayList<ClienteModel> cartera){
        LOGGER.log(Level.INFO, () -> "guardarCarteraCompletaDia: Comienza guardado de cartera Completa");
        RestResponse<String> respuesta=carteraDAO.insertarCarteraCompleta(cartera);
        LOGGER.log(Level.INFO, () -> "guardarCarteraCompletaDia: Termina guardado de cartera Completa");

        return respuesta;
    }

    private RestResponse<ArrayList<ClienteModel>>guardarConPromesa(ArrayList<ClienteModel> cartera,ArrayList<ClienteModel> cuentasConPromesa,String tipoCarteraTKM){
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
            LOGGER.log(Level.INFO, () -> "guardarConPromesa: Cuentas con promesa: "+conPromesa.size());
            for(int j=0;j<conPromesa.size();j++){
                int valor=0;
                for(int k=0;k<cuentasConPromesa.size();k++){
                    if(conPromesa.get(j).getCLIENTE_UNICO().equals(cuentasConPromesa.get(k).getCLIENTE_UNICO())){
                        valor=1;
                    }
                }

                if(valor==0){
                    String CU=conPromesa.get(j).getCLIENTE_UNICO()+","+j;
                    LOGGER.log(Level.INFO, () -> "guardarConPromesa: Cliente Unico: "+CU);
                    carteraDAO.insertarCuentaConPromesa(conPromesa.get(j),fecha,tipoCarteraTKM);
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

    private RestResponse<ArrayList<ClienteModel>>descartarNoCCTT(ArrayList<ClienteModel> cuentas,ArrayList<ClienteModel>cuentasSinContacto,String tipoCarteraTKM){
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
            LOGGER.log(Level.INFO, () -> "descartarNoCCTT: Cuentas sin contacto: "+cuentasDescartadas.size());
            for(int j=0;j<cuentasDescartadas.size();j++){
                int valor=0;

                for(int k=0;k<cuentasSinContacto.size();k++){
                    if(cuentasDescartadas.get(j).getCLIENTE_UNICO().equals(cuentasSinContacto.get(k).getCLIENTE_UNICO())){
                        valor=1;
                    }
                }

                if(valor==0){
                    String CU=cuentasDescartadas.get(j).getCLIENTE_UNICO()+","+j;
                    LOGGER.log(Level.INFO, () -> "guardarSinContacto: Cliente Unico: "+CU);
                    carteraDAO.insertarCuentaSinContactoTT(cuentasDescartadas.get(j),fecha,tipoCarteraTKM);
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

    public RestResponse<String>guardarCarteraDescarte(ArrayList<ClienteModel> cuentas,String tipoCarteraTKM){
        LOGGER.log(Level.INFO, () -> "guardarCarteraDescarte: Comienza guardado de cartera con Descarte");
//        Collections.sort(cuentas,((o1, o2)-> o1.getSEGMENTO().compareTo(o2.getSEGMENTO())));
        LOGGER.log(Level.INFO, () -> "guardarCarteraDescarte: Se insertan "+cuentas.size());
        RestResponse<String> respuesta=carteraDAO2.insertarCarteraDescarte(cuentas,tipoCarteraTKM);
        LOGGER.log(Level.INFO, () -> "guardarCarteraDescarte: Termina guardado de cartera con Descarte");

        return respuesta;

    }

    private RestResponse<ArrayList<ClienteModel>> revisarGestiones(String fecha,ArrayList<ClienteModel> cartera,String tipoCarteraTKM){
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        ArrayList<GestionLlamadasModel>gestiones=gestionLlam.consultarGestionLlamadas().getData();
        ArrayList<TipificacionesModel>tipificacion=tipificaciones.consultarTipificaciones().getData();
        ArrayList<ClienteModel>cuentas=new ArrayList<>();
        ArrayList<GestionLlamadasModel>gestionesDia=new ArrayList<>();
        for(int l=0;l<gestiones.size();l++){
            if(fecha.equals(gestiones.get(l).getFechaInserto())){
                String tipoCartera=gestiones.get(l).getTipoCarteraTKM();
                if("Normalidad".equals(tipoCartera)&&"1".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }
                else if("VIP".equals(tipoCartera)&&"2".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }
                else if("Territorios".equals(tipoCartera)&&"3".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }
                else if("Abandonados".equals(tipoCartera)&&"5".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }
                else if("Implant".equals(tipoCartera)&&"6".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }
                else if("TAZ".equals(tipoCartera)&&"7".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }
                else if("TOR".equals(tipoCartera)&&"8".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }
                else if("SaldosAltos".equals(tipoCartera)&&"9".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }
                else if("Italika".equals(tipoCartera)&&"10".equals(tipoCarteraTKM)){
                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
                }

            }
        }

        if(gestionesDia.size()>0) {
            for (int i = 0; i < cartera.size(); i++) {
                for (int j = 0; j < gestionesDia.size(); j++) {
                    int valor = 0;
                    String nombreTipificacion=null;
                    for (int k = 0; k < tipificacion.size(); k++) {
                        if (gestionesDia.get(j).getIdTipificacion() == tipificacion.get(k).getId()) {
                            valor = tipificacion.get(k).getValor();
                            nombreTipificacion = tipificacion.get(k).getTipificacion();
                        }

                    }

                    if (gestionesDia.get(j).getTelefono().equals(cartera.get(i).getTELEFONO1())) {
                        cartera.get(i).setTIPOTEL1("" + valor);
                        cartera.get(i).setCLIENTE_GRUPAL("" + nombreTipificacion);
                    } else if (gestionesDia.get(j).getTelefono().equals(cartera.get(i).getTELEFONO2())) {
                        cartera.get(i).setTIPOTEL2("" + valor);
                        cartera.get(i).setFIPAISGEO("" + nombreTipificacion);
                    } else if (gestionesDia.get(j).getTelefono().equals(cartera.get(i).getTELEFONO3())) {
                        cartera.get(i).setTIPOTEL3("" + valor);
                        cartera.get(i).setFICUADRANTEGEO("" + nombreTipificacion);
                    } else if (gestionesDia.get(j).getTelefono().equals(cartera.get(i).getTELEFONO4())) {
                        cartera.get(i).setTIPOTEL4("" + valor);
                        cartera.get(i).setFIZONAGEO("" + nombreTipificacion);
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
        respuesta.setMessage("Proceso terminado");

        return respuesta;
    }

    private RestResponse<ArrayList<ClienteModel>> revisarGestionesGenerales(ArrayList<ClienteModel> cartera,String tipoCarteraTKM){
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        ArrayList<GestionLlamadasModel>gestiones=gestionLlam.consultarGestionLlamadas().getData();
        ArrayList<TipificacionesModel>tipificacion=tipificaciones.consultarTipificaciones().getData();
        ArrayList<String>numeroGest=new ArrayList<>();
        ArrayList<ClienteModel>cuentas=new ArrayList<>();
        String fechaTresMeses= utilService.FechaMesAnteriorPosterior(-2);
        String tipoCarteraSol=null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha1 = LocalDate.parse(fechaTresMeses, formatter);

        switch (tipoCarteraTKM){
            case "1":
                tipoCarteraSol="Normalidad";
                break;
            case "2":
                tipoCarteraSol="VIP";
                break;
            case "3":
                tipoCarteraSol="Territorios";
                break;
            case "5":
                tipoCarteraSol="Abandonados";
                break;
            case "6":
                tipoCarteraSol="Implant";
                break;
            case "7":
                tipoCarteraSol="TAZ";
                break;
            case "8":
                tipoCarteraSol="TOR";
                break;
            case "9":
                tipoCarteraSol="SaldosAltos";
                break;
            case "10":
                tipoCarteraSol="Italika";
                break;
            default:
                //Vacio
                break;
        }

        for (int j = 0; j < gestiones.size(); j++) {

            int valor = 0;
            String nombreTipificacion=null;
            String fechaGestion=gestiones.get(j).getFechaInserto();

            LocalDate fecha2 = LocalDate.parse(fechaGestion, formatter);

            if(fecha2.isEqual(fecha1)||fecha2.isAfter(fecha1)) {
                if(gestiones.get(j).getTipoCarteraTKM()!=null){
                    if(gestiones.get(j).getTipoCarteraTKM().equals(tipoCarteraSol)) {
                        for (int k = 0; k < tipificacion.size(); k++) {
                            if (gestiones.get(j).getIdTipificacion() == tipificacion.get(k).getId()) {
                                valor = tipificacion.get(k).getValor();
                                nombreTipificacion=tipificacion.get(k).getTipificacion();
                            }
                        }
                        if (valor != 6) {
                            numeroGest.add(gestiones.get(j).getTelefono() + "," + valor + "," + gestiones.get(j).getTipoCarteraTKM()+","+nombreTipificacion);
                        }
                    }
                }
            }
        }


        for (int i = 0; i < cartera.size(); i++) {
            for (int l = 0; l < numeroGest.size(); l++) {
                String telefono=numeroGest.get(l).split(",")[0];
                int valor = Integer.parseInt(numeroGest.get(l).split(",")[1]);
                String nomTipi=numeroGest.get(l).split(",")[3];

                if (telefono.equals(cartera.get(i).getTELEFONO1())) {
                    cartera.get(i).setTIPOTEL1("" + valor);
                    cartera.get(i).setCLIENTE_GRUPAL("" + nomTipi);
                } else if (telefono.equals(cartera.get(i).getTELEFONO2())) {
                    cartera.get(i).setTIPOTEL2("" + valor);
                    cartera.get(i).setFIPAISGEO("" + nomTipi);
                } else if (telefono.equals(cartera.get(i).getTELEFONO3())) {
                    cartera.get(i).setTIPOTEL3("" + valor);
                    cartera.get(i).setFICUADRANTEGEO("" + nomTipi);
                } else if (telefono.equals(cartera.get(i).getTELEFONO4())) {
                    cartera.get(i).setTIPOTEL4("" + valor);
                    cartera.get(i).setFIZONAGEO("" + nomTipi);
                }

            }
            cuentas.add(cartera.get(i));
        }

        respuesta.setCode(1);
        respuesta.setMessage("Gestiones Insertadas");
        respuesta.setData(cuentas);
        return respuesta;
    }

    private ArrayList<ClienteModel>descartarCumplidosVigente(ArrayList<ClienteModel> cuentas){
        LOGGER.log(Level.INFO, () -> "descartarCumplidosVigente: Comienza descarte de Cumplidos y Vigentes");
        ArrayList<ClienteModel> respuesta=new ArrayList<>();

        for(int i=0;i<cuentas.size();i++){
            if(!cuentas.get(i).getESTATUS_PLAN().contains("VIGENTE")&&!"CUMPLIDO".equals(cuentas.get(i).getESTATUS_PLAN())){
                if(!"N/A".equals(cuentas.get(i).getTELEFONO1())&&!"0".equals(cuentas.get(i).getTELEFONO1())){
                    respuesta.add(cuentas.get(i));
                }
            }
        }

        for(int j=0;j<respuesta.size();j++){
            int valorArr=j;
            int a=valorArr%2;

            if(a==0){
                respuesta.get(j).setTURNO("M");
            }
            else{
                respuesta.get(j).setTURNO("V");
            }



        }


        LOGGER.log(Level.INFO, () -> "descartarCumplidosVigente: Termina descarte de Cumplidos y Vigentes");
        return respuesta;
    }

    public RestResponse<ClienteModel> consultarCUCarteraCompleta(String cu){
        return carteraDAO.consultarCUCarteraCompleta(cu);
    }

    public RestResponse<ArrayList<ClienteModel>> consultarCarteraConPromesa(String tipoCarteraTKM){
        return carteraDAO.consultarCuentasConPromesa(tipoCarteraTKM);
    }

    public RestResponse<String> actualizarMontoCuentaConPromesa(ArrayList<String> registro,String tipoCarteraTKM){
        return carteraDAO.actualizarMontoCuentaConPromesa(registro,tipoCarteraTKM);
    }

    public RestResponse<String>eliminarCuentasConPromesa(ArrayList<String> registros,String tipoCarteraTKM){
        return carteraDAO.eliminarCuentasConPromesa(registros,tipoCarteraTKM);
    }

    public RestResponse<ArrayList<ClienteModel>> consultarCuentasSinContacto(String tipoCarteraTKM) {
        return carteraDAO.consultarCuentasSinContactoTT(tipoCarteraTKM);
    }


    public RestResponse<String> insertarCarteraLocal(String clientes) {
        JSONObject jsonCliente=new JSONObject(clientes);
        JSONArray jsonArrayClientes=jsonCliente.getJSONArray("cartera");
        ArrayList<ClienteModel> cartera=new ArrayList<>();
        for(int i=0;i< jsonArrayClientes.length();i++){
            String c=String.valueOf(jsonArrayClientes.getJSONObject(i));
            try {
                ClienteModel clientesCar = new ClienteModel();
                clientesCar.setCLIENTE_UNICO(jsonArrayClientes.getJSONObject(i).getString("CLIENTE_UNICO"));
                clientesCar.setNOMBRE_CTE(jsonArrayClientes.getJSONObject(i).getString("NOMBRE_CTE"));
                clientesCar.setGENERO_CLIENTE(jsonArrayClientes.getJSONObject(i).getString("GENERO_CLIENTE"));
                clientesCar.setEDAD_CLIENTE(jsonArrayClientes.getJSONObject(i).getString("EDAD_CLIENTE"));
                clientesCar.setOCUPACION(jsonArrayClientes.getJSONObject(i).getString("OCUPACION"));
                clientesCar.setDIRECCION_CTE(jsonArrayClientes.getJSONObject(i).getString("DIRECCION_CTE"));
                clientesCar.setNUM_EXT_CTE(jsonArrayClientes.getJSONObject(i).getString("NUM_EXT_CTE"));
                clientesCar.setNUM_INT_CTE(jsonArrayClientes.getJSONObject(i).getString("NUM_INT_CTE"));
                clientesCar.setCP_CTE(jsonArrayClientes.getJSONObject(i).getString("CP_CTE"));
                clientesCar.setCOLONIA_CTE(jsonArrayClientes.getJSONObject(i).getString("COLONIA_CTE"));
                clientesCar.setPOBLACION_CTE(jsonArrayClientes.getJSONObject(i).getString("POBLACION_CTE"));
                clientesCar.setESTADO_CTE(jsonArrayClientes.getJSONObject(i).getString("ESTADO_CTE"));
                clientesCar.setTERRITORIO(jsonArrayClientes.getJSONObject(i).getString("TERRITORIO"));
                clientesCar.setTERRITORIAL(jsonArrayClientes.getJSONObject(i).getString("TERRITORIAL"));
                clientesCar.setZONA(jsonArrayClientes.getJSONObject(i).getString("ZONA"));
                clientesCar.setZONAL(jsonArrayClientes.getJSONObject(i).getString("ZONAL"));
                clientesCar.setNOMBRE_DESPACHO(jsonArrayClientes.getJSONObject(i).getString("NOMBRE_DESPACHO"));
                clientesCar.setGERENCIA(jsonArrayClientes.getJSONObject(i).getString("GERENCIA"));
                clientesCar.setFECHA_ASIGNACION(jsonArrayClientes.getJSONObject(i).getString("FECHA_ASIGNACION"));
                clientesCar.setDIAS_ASIGNACION(jsonArrayClientes.getJSONObject(i).getString("DIAS_ASIGNACION"));
                clientesCar.setREFERENCIAS_DOMICILIO(jsonArrayClientes.getJSONObject(i).getString("REFERENCIAS_DOMICILIO"));
                clientesCar.setCLASIFICACION_CTE(jsonArrayClientes.getJSONObject(i).getString("CLASIFICACION_CTE"));
                clientesCar.setDIQUE(jsonArrayClientes.getJSONObject(i).getString("DIQUE"));
                clientesCar.setATRASO_MAXIMO(jsonArrayClientes.getJSONObject(i).getString("ATRASO_MAXIMO"));
                clientesCar.setDIAS_ATRASO(Integer.valueOf(jsonArrayClientes.getJSONObject(i).getString("DIAS_ATRASO")));
                clientesCar.setSALDO(jsonArrayClientes.getJSONObject(i).getString("SALDO"));
                clientesCar.setMORATORIOS(jsonArrayClientes.getJSONObject(i).getString("MORATORIOS"));
                clientesCar.setSALDO_TOTAL(Float.valueOf(jsonArrayClientes.getJSONObject(i).getString("SALDO_TOTAL")));
                clientesCar.setSALDO_ATRASADO(jsonArrayClientes.getJSONObject(i).getString("SALDO_ATRASADO"));
                clientesCar.setSALDO_REQUERIDO(jsonArrayClientes.getJSONObject(i).getString("SALDO_REQUERIDO"));
                clientesCar.setPAGO_NORMAL(jsonArrayClientes.getJSONObject(i).getString("PAGO_NORMAL"));
                clientesCar.setPRODUCTO(jsonArrayClientes.getJSONObject(i).getString("PRODUCTO"));
                clientesCar.setFECHA_ULTIMO_PAGO(jsonArrayClientes.getJSONObject(i).getString("FECHA_ULTIMO_PAGO"));
                clientesCar.setIMP_ULTIMO_PAGO(jsonArrayClientes.getJSONObject(i).getString("IMP_ULTIMO_PAGO"));
                clientesCar.setCALLE_EMPLEO(jsonArrayClientes.getJSONObject(i).getString("CALLE_EMPLEO"));
                clientesCar.setNUM_EXT_EMPLEO(jsonArrayClientes.getJSONObject(i).getString("NUM_EXT_EMPLEO"));
                clientesCar.setNUM_INT_EMPLEO(jsonArrayClientes.getJSONObject(i).getString("NUM_INT_EMPLEO"));
                clientesCar.setCOLONIA_EMPLEO(jsonArrayClientes.getJSONObject(i).getString("COLONIA_EMPLEO"));
                clientesCar.setPOBLACION_EMPLEO(jsonArrayClientes.getJSONObject(i).getString("POBLACION_EMPLEO"));
                clientesCar.setESTADO_EMPLEO(jsonArrayClientes.getJSONObject(i).getString("ESTADO_EMPLEO"));
                clientesCar.setNOMBRE_AVAL(jsonArrayClientes.getJSONObject(i).getString("NOMBRE_AVAL"));
                clientesCar.setTEL_AVAL(jsonArrayClientes.getJSONObject(i).getString("TEL_AVAL"));
                clientesCar.setCALLE_AVAL(jsonArrayClientes.getJSONObject(i).getString("CALLE_AVAL"));
                clientesCar.setNUM_EXT_AVAL(jsonArrayClientes.getJSONObject(i).getString("NUM_EXT_AVAL"));
                clientesCar.setCOLONIA_AVAL(jsonArrayClientes.getJSONObject(i).getString("COLONIA_AVAL"));
                clientesCar.setCP_AVAL(jsonArrayClientes.getJSONObject(i).getString("CP_AVAL"));
                clientesCar.setPOBLACION_AVAL(jsonArrayClientes.getJSONObject(i).getString("POBLACION_AVAL"));
                clientesCar.setESTADO_AVAL(jsonArrayClientes.getJSONObject(i).getString("ESTADO_AVAL"));
                clientesCar.setFIDIAPAGO(jsonArrayClientes.getJSONObject(i).getString("FIDIAPAGO"));
                clientesCar.setTELEFONO1(jsonArrayClientes.getJSONObject(i).getString("TELEFONO1"));
                clientesCar.setTELEFONO2(jsonArrayClientes.getJSONObject(i).getString("TELEFONO2"));
                clientesCar.setTELEFONO3(jsonArrayClientes.getJSONObject(i).getString("TELEFONO3"));
                clientesCar.setTELEFONO4(jsonArrayClientes.getJSONObject(i).getString("TELEFONO4"));
                clientesCar.setTIPOTEL1(jsonArrayClientes.getJSONObject(i).getString("TIPOTEL1"));
                clientesCar.setTIPOTEL2(jsonArrayClientes.getJSONObject(i).getString("TIPOTEL2"));
                clientesCar.setTIPOTEL3(jsonArrayClientes.getJSONObject(i).getString("TIPOTEL3"));
                clientesCar.setTIPOTEL4(jsonArrayClientes.getJSONObject(i).getString("TIPOTEL4"));
                clientesCar.setLATITUD(jsonArrayClientes.getJSONObject(i).getString("LATITUD"));
                clientesCar.setLONGITUD(jsonArrayClientes.getJSONObject(i).getString("LONGITUD"));
                clientesCar.setDESPACHO_GESTIONO(jsonArrayClientes.getJSONObject(i).getString("DESPACHO_GESTIONO"));
                clientesCar.setULTIMA_GESTION(jsonArrayClientes.getJSONObject(i).getString("ULTIMA_GESTION"));
                clientesCar.setGESTION_DESC(jsonArrayClientes.getJSONObject(i).getString("GESTION_DESC"));
                clientesCar.setCAMPANIA_RELAMPAGO(jsonArrayClientes.getJSONObject(i).getString("CAMPANIA_RELAMPAGO"));
                clientesCar.setCAMPANIA(jsonArrayClientes.getJSONObject(i).getString("CAMPANIA"));
                clientesCar.setID_GRUPO(jsonArrayClientes.getJSONObject(i).getString("ID_GRUPO"));
                clientesCar.setGRUPO_MAZ(jsonArrayClientes.getJSONObject(i).getString("GRUPO_MAZ"));
                clientesCar.setCLAVE_SPEI(jsonArrayClientes.getJSONObject(i).getString("CLAVE_SPEI"));
                clientesCar.setPAGOS_CLIENTE(jsonArrayClientes.getJSONObject(i).getString("PAGOS_CLIENTE"));
                clientesCar.setMONTO_PAGOS(jsonArrayClientes.getJSONObject(i).getString("MONTO_PAGOS"));
                clientesCar.setGESTORES(jsonArrayClientes.getJSONObject(i).getString("GESTORES"));
                clientesCar.setFOLIO_PLAN(jsonArrayClientes.getJSONObject(i).getString("FOLIO_PLAN"));
                clientesCar.setSEGMENTO_GENERACION(jsonArrayClientes.getJSONObject(i).getString("SEGMENTO_GENERACION"));
                clientesCar.setESTATUS_PLAN(jsonArrayClientes.getJSONObject(i).getString("ESTATUS_PLAN"));
                clientesCar.setSEMANAS_ATRASO(jsonArrayClientes.getJSONObject(i).getString("SEMANAS_ATRASO"));
                clientesCar.setATRASO(jsonArrayClientes.getJSONObject(i).getString("ATRASO"));
                clientesCar.setGENERACION_PLAN(jsonArrayClientes.getJSONObject(i).getString("GENERACION_PLAN"));
                clientesCar.setCANCELACION_CUMPLIMIENTO_PLAN(jsonArrayClientes.getJSONObject(i).getString("CANCELACION_CUMPLIMIENTO_PLAN"));
                clientesCar.setULTIMO_ESTATUS(jsonArrayClientes.getJSONObject(i).getString("ULTIMO_ESTATUS"));
                clientesCar.setEMPLEADO(jsonArrayClientes.getJSONObject(i).getString("EMPLEADO"));
                clientesCar.setCANAL(jsonArrayClientes.getJSONObject(i).getString("CANAL"));
                clientesCar.setABONO_SEMANAL(jsonArrayClientes.getJSONObject(i).getString("ABONO_SEMANAL"));
                clientesCar.setPLAZO(jsonArrayClientes.getJSONObject(i).getString("PLAZO"));
                clientesCar.setMONTO_ABONADO(jsonArrayClientes.getJSONObject(i).getString("MONTO_ABONADO"));
                clientesCar.setMONTO_PLAN(jsonArrayClientes.getJSONObject(i).getString("MONTO_PLAN"));
                clientesCar.setENGANCHE(jsonArrayClientes.getJSONObject(i).getString("ENGANCHE"));
                clientesCar.setPAGOS_RECIBIDOS(jsonArrayClientes.getJSONObject(i).getString("PAGOS_RECIBIDOS"));
                clientesCar.setSALDO_ANTES_DEL_PLAN(jsonArrayClientes.getJSONObject(i).getString("SALDO_ANTES_DEL_PLAN"));
                clientesCar.setSALDO_ATRASADO_ANTES_PLAN(jsonArrayClientes.getJSONObject(i).getString("SALDO_ATRASADO_ANTES_PLAN"));
                clientesCar.setMORATORIOS_ANTES_PLAN(jsonArrayClientes.getJSONObject(i).getString("MORATORIOS_ANTES_PLAN"));
                clientesCar.setESTATUS_PROMESA_PAGO(jsonArrayClientes.getJSONObject(i).getString("ESTATUS_PROMESA_PAGO"));
                clientesCar.setMONTO_PROMESA_PAGO(jsonArrayClientes.getJSONObject(i).getString("MONTO_PROMESA_PAGO"));
                clientesCar.setSEGMENTO(Integer.valueOf(jsonArrayClientes.getJSONObject(i).getString("SEGMENTO")));
                clientesCar.setTIPOCARTERATKM(jsonArrayClientes.getJSONObject(i).getString("TIPOCARTERATKM"));
                cartera.add(clientesCar);
            }
            catch (Exception e){
                LOGGER.log(Level.INFO, () -> "Ocurrio Algo inesperado "+e);
                LOGGER.log(Level.INFO, () -> "Cuenta "+c);
            }
        }
        return guardarCarteraCompletaDia(cartera);
    }

    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarteDiaCompleta(){
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        ArrayList<ClienteModel> bases=new ArrayList<>();
        bases.addAll(consultarCarteraDescarte("1").getData());
        bases.addAll(consultarCarteraDescarte("2").getData());
        bases.addAll(consultarCarteraDescarte("3").getData());
        bases.addAll(consultarCarteraDescarte("4").getData());
        bases.addAll(consultarCarteraDescarte("5").getData());
        bases.addAll(consultarCarteraDescarte("6").getData());
        bases.addAll(consultarCarteraDescarte("7").getData());
        bases.addAll(consultarCarteraDescarte("8").getData());
        bases.addAll(consultarCarteraDescarte("9").getData());
        bases.addAll(consultarCarteraDescarte("10").getData());
        respuesta.setCode(1);
        respuesta.setMessage("Proceso para obtener Cartera Descarte Dia Completa");
        respuesta.setData(bases);

        return respuesta;
    }

    public RestResponse<ArrayList<ClienteModel>> consultarBaseCompletaPorCartera(String tipoCarteraTKM){
        return carteraDAO.consultarCarteraCompletaPorCartera(tipoCarteraTKM);
    }


    public RestResponse<ArrayList<ClienteModel>>carterasDescarteCompleta() {
        RestResponse<ArrayList<ClienteModel>> respuesta = new RestResponse<>();
        LOGGER.log(Level.INFO, () -> "Comienza proceso de todas las carteras Descarte");
        ArrayList<ClienteModel> cuentasCompletas = new ArrayList<>();
        ExtrasModel cokkie = new ExtrasModel();
        RestResponse<ArrayList<ClienteModel>> carteraNormalidad = this.carteraCompletaGuardar(cokkie, "1");
        RestResponse<ArrayList<ClienteModel>> carteraVIP = this.carteraCompletaGuardar(cokkie, "2");
        RestResponse<ArrayList<ClienteModel>> carteraTerritorios = this.carteraCompletaGuardar(cokkie, "3");
        RestResponse<ArrayList<ClienteModel>> carteraAbandonados = this.carteraCompletaGuardar(cokkie, "5");
        RestResponse<ArrayList<ClienteModel>> carteraImplant = this.carteraCompletaGuardar(cokkie, "6");
        RestResponse<ArrayList<ClienteModel>> carteraTAZ = this.carteraCompletaGuardar(cokkie, "7");
        RestResponse<ArrayList<ClienteModel>> carteraTOR = this.carteraCompletaGuardar(cokkie, "8");
        RestResponse<ArrayList<ClienteModel>> carteraSaldosAltos = this.carteraCompletaGuardar(cokkie, "9");
        RestResponse<ArrayList<ClienteModel>> carteraItalika = this.carteraCompletaGuardar(cokkie, "10");

        cuentasCompletas.addAll(carteraNormalidad.getData());
        cuentasCompletas.addAll(carteraVIP.getData());
        cuentasCompletas.addAll(carteraTerritorios.getData());
        cuentasCompletas.addAll(carteraAbandonados.getData());
        cuentasCompletas.addAll(carteraImplant.getData());
        cuentasCompletas.addAll(carteraTAZ.getData());
        cuentasCompletas.addAll(carteraTOR.getData());
        cuentasCompletas.addAll(carteraSaldosAltos.getData());
        cuentasCompletas.addAll(carteraItalika.getData());

        respuesta.setCode(1);
        respuesta.setMessage("Cartera Completas Descarte");
        respuesta.setData(cuentasCompletas);
        LOGGER.log(Level.INFO, () -> "Termina proceso de todas las carteras Descarte");

        return respuesta;
    }
}
