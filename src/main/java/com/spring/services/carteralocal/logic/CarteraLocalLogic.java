package com.spring.services.carteralocal.logic;

import com.spring.services.cartera.logic.ObtenerClientesCartera;
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
import org.springframework.stereotype.Component;

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



    public CarteraLocalLogic() {
        //Vacio
    }

    public RestResponse<ArrayList<ClienteModel>> carteraCompletaGuardar(ExtrasModel cokkie,String tipoCarteraTKM)  {
        LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Comienza proceso");
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        RestResponse<ArrayList<ClienteModel>>carteraCompleta=new RestResponse<>();

        if("1".equals(tipoCarteraTKM)){
            carteraCompleta=obtenerCartera.carteraCompleta(cokkie);
        }
        else{
            carteraCompleta=obtenerCartVIP.VIPcarteraCompleta(cokkie);
        }

        if(carteraCompleta.getCode()==1){

            ArrayList<ClienteModel> cuentasConPromesa=carteraDAO.consultarCuentasConPromesa(tipoCarteraTKM).getData();
            ArrayList<ClienteModel> cuentasSinContacto=carteraDAO.consultarCuentasSinContactoTT(tipoCarteraTKM).getData();

            String fecha=utilService.FechaDiaAnteriorPosterior(-1);
            Integer cantidadCuentasLog=carteraCompleta.getData().size();
            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Se obtuvieron "+cantidadCuentasLog);
            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Comenzando validacion de cuentas con gestion");
            ArrayList<ClienteModel>cuentas=this.revisarGestiones(fecha,carteraCompleta.getData(),tipoCarteraTKM).getData();

            LOGGER.log(Level.INFO, () -> "carteraCompletaGuardar: Termina validacion de cuentas con gestion");

//            this.guardarNuevas(cuentas,tipoCarteraTKM);

            carteraDAO.borrarCarteraCompleta(tipoCarteraTKM);
            carteraDAO2.borrarCarteraDescarte(tipoCarteraTKM);


            RestResponse<ArrayList<ClienteModel>>descartarPromesas=this.guardarConPromesa(cuentas,cuentasConPromesa,tipoCarteraTKM);
            RestResponse<ArrayList<ClienteModel>>descartarNoContacto=this.descartarNoCCTT(descartarPromesas.getData(),cuentasSinContacto,tipoCarteraTKM);
            ArrayList<ClienteModel>sinCumplidosVigente=descartarCumplidosVigente(descartarNoContacto.getData());

//            this.guardarCarteraCompletaDia(cuentas);
            this.guardarCarteraCompletaDia(carteraCompleta.getData());
            this.guardarCarteraDescarte(sinCumplidosVigente,tipoCarteraTKM);

            respuesta.setCode(1);
            respuesta.setMessage("Cartera Obtenida correctamente");
            respuesta.setData(sinCumplidosVigente);


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
                    LOGGER.log(Level.INFO, () -> "guardarConPromesa: Cliente Unico: "+CU);
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
////                String tipoCartera=gestiones.get(l).getTipoCarteraTKM();
////                if("Normalidad".equals(tipoCartera)&&"1".equals(tipoCarteraTKM)){
//                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
                    gestionesDia.add(gestiones.get(l));
//                }
//                else if("VIP".equals(tipoCartera)&&"2".equals(tipoCarteraTKM)){
//                    LOGGER.log(Level.INFO, () -> "revisarGestiones: "+tipoCartera+" "+tipoCarteraTKM);
//                    gestionesDia.add(gestiones.get(l));
//                }

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

    public RestResponse<ArrayList<ClienteModel>> consultarCarteraConPromesa(){
        return carteraDAO.consultarCuentasConPromesa("1");
    }

    public RestResponse<String> actualizarMontoCuentaConPromesa(ArrayList<String> registro){
        return carteraDAO.actualizarMontoCuentaConPromesa(registro);
    }

    public RestResponse<String>eliminarCuentasConPromesa(ArrayList<String> registros){
        return carteraDAO.eliminarCuentasConPromesa(registros);
    }
}
