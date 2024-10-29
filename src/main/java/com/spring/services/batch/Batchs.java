package com.spring.services.batch;

import com.spring.services.cartera.logic.BlastersSMSLogic;
import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.carteralocal.dao.CarteraLocalDAO;
import com.spring.services.carteralocal.logic.CarteraLocalLogic;
import com.spring.services.constantes.Constantes;
import com.spring.services.gestores.logic.GestoresLogic;
import com.spring.services.gestores.model.GestoresModel;
import com.spring.services.notificaciones.logic.NotificacionesLogic;
import com.spring.services.notificaciones.model.CuerpoCorreo;
import com.spring.services.operacion.logic.GestionLlamadasLogic;
import com.spring.services.operacion.model.GestionLlamadasModel;
import com.spring.services.pagos.logic.PromesasTKMLogic;
import com.spring.services.pagos.model.PromesasModel;
import com.spring.utils.RestResponse;
import com.spring.utils.UtilService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


@Component
public class Batchs {
    private static final Logger LOGGER = LogManager.getLogger("Batchs");
    private PromesasTKMLogic promesas=new PromesasTKMLogic();
    private BlastersSMSLogic blaster=new BlastersSMSLogic();
    private NotificacionesLogic notificaciones=new NotificacionesLogic();
    private CarteraLocalLogic carteraLog=new CarteraLocalLogic();
    private GestionLlamadasLogic gestionLogic=new GestionLlamadasLogic();
    private UtilService util=new UtilService();
    private CarteraLocalDAO localDAO=new CarteraLocalDAO();
    private GestoresLogic gestoresLogic=new GestoresLogic();
    public Batchs() {
        //Vacio
    }


    @Scheduled(cron = "0 0 09-20 * * *")
    public void mandarBlasterRecordatorios(){
        LOGGER.log(Level.INFO, () -> "mandarBlasterRecordatorios: Comienza batch envio de recordatorios");
        ArrayList<PromesasModel> promesasCompletas=promesas.consultarPromesas().getData();
        ArrayList<String> promesapagoHoy=new ArrayList<>();
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String[] fecha= String.valueOf(date).split(" ");
        String fechaPago=fecha[2]+"/"+fecha[1]+"/"+fecha[5];
        for(int i=0;i<promesasCompletas.size();i++){
            if(promesasCompletas.get(i).getFechaPago().equals(fechaPago)){
                if("0".equals(promesasCompletas.get(i).getPagoFinal())){
                    promesapagoHoy.add(promesasCompletas.get(i).getTelefono());
                }
            }
        }
//        promesapagoHoy.add("5539252342");
//        promesapagoHoy.add("5543773233");
        String mensaje="Cliente Banco Azteca. No hemos recibido su pago, realice el deposito hoy, no pierda de sus beneficios";
        LOGGER.log(Level.INFO, () -> "mandarBlasterRecordatorios: Se envian "+promesapagoHoy.size()+" para blaster");
        blaster.enviarBlaster(promesapagoHoy,mensaje,"RamcesFDz4","CobranzaTKM2024*");
        LOGGER.log(Level.INFO, () -> "mandarBlasterRecordatorios: Termina batch envio de recordatorios");

    }

    @Scheduled(cron = "0 30 09 * * *")
    public void mandarSMSRecordatorio(){
        LOGGER.log(Level.INFO, () -> "mandarSMSRecordatorios: Comienza batch envio de recordatorios");
        ArrayList<PromesasModel> promesasCompletas=promesas.consultarPromesas().getData();
        ArrayList<String> promesapagoHoy=new ArrayList<>();
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String[] fecha= String.valueOf(date).split(" ");
        String fechaPago=fecha[2]+"/"+fecha[1]+"/"+fecha[5];
        for(int i=0;i<promesasCompletas.size();i++){
            if(promesasCompletas.get(i).getFechaPago().equals(fechaPago)){
                promesapagoHoy.add(promesasCompletas.get(i).getTelefono());
            }
        }
        promesapagoHoy.add("5645888697");
        promesapagoHoy.add("5534849196");
        String mensaje="CLIENTE%20B.%20AZTECA,%20RECUERDE%20GENERAR%20SU%20PAGO%20EN%20TIEMPO%20Y%20FORMA,%20ASI%20MISMO%20REPORTARLO%20A%20SU%20LICENCIADO%20EN%20TURNO,%20o%20comunícate%20http://wa.me/5512421451";
        String respuesta=blaster.envioSMS(promesapagoHoy,mensaje,"Cobranza.API","C0brnza4Ap1Baz.");

        ArrayList<String>correos=new ArrayList<>();
        correos.add("rfrutos@tkm.com.mx");
        correos.add("amartinezt@tkm.com.mx");
        correos.add("asalas@tkm.com.mx");

        CuerpoCorreo correo = new CuerpoCorreo();
        correo.setRemitente(Constantes.correoRemitente);
        correo.setPasswordRemitente(Constantes.passwordRemitente);
        correo.setDestinatario(correos);
        correo.setAsunto("ENVIO SMS RECORDATORIO "+fechaPago);
        correo.setMensaje(respuesta);
        RestResponse<String> enviarCorreo=notificaciones.enviarCorreo(correo);

        LOGGER.log(Level.INFO, () -> "mandarSMSRecordatorios: Termina batch envio de recordatorios");
    }

    @Scheduled(cron = "0 30 09 * * MON")
    public void recordatorioValidarPromesas() {
        LOGGER.log(Level.INFO, () -> "Comienza Batch de recordatorioValidarPromesas de cada Lunes");
        Calendar calendar = Calendar.getInstance();
        int numeroSemana = calendar.get(Calendar.WEEK_OF_YEAR);
        if (numeroSemana % 2 != 0) {
            ArrayList<String>correos=new ArrayList<>();
            correos.add("rfrutos@tkm.com.mx");

            CuerpoCorreo correo = new CuerpoCorreo();
            correo.setRemitente(Constantes.correoRemitente);
            correo.setPasswordRemitente(Constantes.passwordRemitente);
            correo.setDestinatario(correos);
            correo.setAsunto("AVISO, CORRER REVISION DE PAGOS DE LA CARTERA CON PROMESA");
            correo.setMensaje("Recuerda correr la revison de pagos de la cartera con promesa");
            RestResponse<String> enviarCorreo=notificaciones.enviarCorreo(correo);
            LOGGER.log(Level.INFO, () -> "Se envia correo para correr proceso de validacion de cuentas con promesas "+enviarCorreo);

            RestResponse<ArrayList<ClienteModel>>obtenerPromesasNormalidad=carteraLog.consultarCarteraConPromesa("1");
            RestResponse<ArrayList<ClienteModel>>obtenerPromesasVIP=carteraLog.consultarCarteraConPromesa("2");
            RestResponse<ArrayList<ClienteModel>>obtenerPromesasTerritorios=carteraLog.consultarCarteraConPromesa("3");
            RestResponse<ArrayList<ClienteModel>>obtenerPromesasAbanderados=carteraLog.consultarCarteraConPromesa("5");
            RestResponse<ArrayList<ClienteModel>>obtenerPromesasImplant=carteraLog.consultarCarteraConPromesa("6");
            RestResponse<ArrayList<ClienteModel>>obtenerPromesasTAZ=carteraLog.consultarCarteraConPromesa("7");
            RestResponse<ArrayList<ClienteModel>>obtenerPromesasTOR=carteraLog.consultarCarteraConPromesa("8");
            RestResponse<ArrayList<ClienteModel>>obtenerPromesasSaldAltos=carteraLog.consultarCarteraConPromesa("9");
            RestResponse<ArrayList<ClienteModel>>obtenerPromesasItalika=carteraLog.consultarCarteraConPromesa("10");


            ArrayList<ClienteModel>obtenerPromesas=new ArrayList<>();
            obtenerPromesas.addAll(obtenerPromesasNormalidad.getData());
            obtenerPromesas.addAll(obtenerPromesasVIP.getData());
            obtenerPromesas.addAll(obtenerPromesasTerritorios.getData());
            obtenerPromesas.addAll(obtenerPromesasAbanderados.getData());
            obtenerPromesas.addAll(obtenerPromesasImplant.getData());
            obtenerPromesas.addAll(obtenerPromesasTAZ.getData());
            obtenerPromesas.addAll(obtenerPromesasTOR.getData());
            obtenerPromesas.addAll(obtenerPromesasSaldAltos.getData());
            obtenerPromesas.addAll(obtenerPromesasItalika.getData());

            ArrayList<String> actualizarMontoPromesaNormalidad=new ArrayList<>();
            ArrayList<String> actualizarMontoPromesaVIP=new ArrayList<>();
            ArrayList<String> actualizarMontoPromesaTerritorios=new ArrayList<>();
            ArrayList<String> actualizarMontoPromesaAbandonados=new ArrayList<>();
            ArrayList<String> actualizarMontoPromesaImplant=new ArrayList<>();
            ArrayList<String> actualizarMontoPromesaTAZ=new ArrayList<>();
            ArrayList<String> actualizarMontoPromesaTOR=new ArrayList<>();
            ArrayList<String> actualizarMontoPromesaSaldosAltos=new ArrayList<>();
            ArrayList<String> actualizarMontoPromesaItalika=new ArrayList<>();

            for(int i=0;i<obtenerPromesas.size();i++){
                String tipoCarteraArr=obtenerPromesas.get(i).getTIPOCARTERATKM();
                String actualizar = "0," + obtenerPromesas.get(i).getCLIENTE_UNICO();

                switch (tipoCarteraArr){
                    case "1":
                        actualizarMontoPromesaNormalidad.add(actualizar);
                        break;
                    case "2":
                        actualizarMontoPromesaVIP.add(actualizar);
                        break;
                    case "3":
                        actualizarMontoPromesaTerritorios.add(actualizar);
                        break;
                    case "5":
                        actualizarMontoPromesaAbandonados.add(actualizar);
                        break;
                    case "6":
                        actualizarMontoPromesaImplant.add(actualizar);
                        break;
                    case "7":
                        actualizarMontoPromesaTAZ.add(actualizar);
                        break;
                    case "8":
                        actualizarMontoPromesaTOR.add(actualizar);
                        break;
                    case "9":
                        actualizarMontoPromesaSaldosAltos.add(actualizar);
                        break;
                    case "10":
                        actualizarMontoPromesaItalika.add(actualizar);
                        break;
                    default:
                        //Vacio
                        break;
                }
            }

            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaNormalidad,"1");
            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaVIP,"2");
            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaTerritorios,"3");
            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaAbandonados,"5");
            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaImplant,"6");
            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaTAZ,"7");
            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaTOR,"8");
            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaSaldosAltos,"9");
            this.carteraLog.actualizarMontoCuentaConPromesa(actualizarMontoPromesaItalika,"10");
        }
        else{
            LOGGER.log(Level.INFO, () -> "Batch recordatorioValidarPromesas no han pasado las dos semanas");
        }

        LOGGER.log(Level.INFO, () -> "Termina Batch de recordatorioValidarPromesas de cada Lunes");


    }

    @Scheduled(cron = "0 0 10 * * *")
    public void eliminacionDeCarteraDiaria() {
        LOGGER.log(Level.INFO, () -> "Comienza Batch de eliminacion de cartera Diaria");
        RestResponse<String> borrar=localDAO.borrarCarteraCompleta("0");
        LOGGER.log(Level.INFO, () -> "Resultado Batch de eliminacion de cartera Diaria: "+borrar);
        ArrayList<String>correos=new ArrayList<>();
        correos.add("rfrutos@tkm.com.mx");
        correos.add("amartinezt@tkm.com.mx");
        CuerpoCorreo correo = new CuerpoCorreo();
        correo.setRemitente(Constantes.correoRemitente);
        correo.setPasswordRemitente(Constantes.passwordRemitente);
        correo.setDestinatario(correos);
        correo.setAsunto("AVISO, SE ELIMINO LA CARTERA");
        correo.setMensaje("Se elimino la cartera de la Base de Datos");
        RestResponse<String> enviarCorreo=notificaciones.enviarCorreo(correo);
        LOGGER.log(Level.INFO, () -> "Se envia correo de aviso de eliminacion de la cartera "+enviarCorreo);
        LOGGER.log(Level.INFO, () -> "Termina Batch de eliminacion de cartera Diaria: "+borrar);
    }

    @Scheduled(cron = "0 45 07 * * *")
    public void resetMontoPagoPromesasPagoDia() {
        LOGGER.log(Level.INFO, () -> "Comienza Batch de resetMontoPagoPromesasPagoDia");
        ArrayList<PromesasModel> promesasCompletas=promesas.consultarPromesas().getData();
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String[] fecha= String.valueOf(date).split(" ");
        String fechaPago=fecha[2]+"/"+fecha[1]+"/"+fecha[5];
        for(int i=0;i<promesasCompletas.size();i++){
            if(promesasCompletas.get(i).getFechaPago().equals(fechaPago)){
                    promesasCompletas.get(i).setPagoFinal("0");
                    promesasCompletas.get(i).setFechaVencimientoPP(promesasCompletas.get(i).getFechaPago());
                    promesas.actualizarPromesasEstPag(promesasCompletas.get(i));
            }
        }


        LOGGER.log(Level.INFO, () -> "Termina Batch de resetMontoPagoPromesasPagoDia");
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void eliminarGestiones7meses() {
        LOGGER.log(Level.INFO, () -> "eliminarGestiones3meses: Comienza batch eliminacion de Gestiones de 7 meses");

        String fecha7Meses=util.FechaMesAnteriorPosterior(-7);
        CuerpoCorreo correo=new CuerpoCorreo();
        ArrayList<String>correos=new ArrayList<>();
        correos.add(Constantes.correoEncargada);
        correos.add("rfrutos@tkm.com.mx");
        correo.setRemitente(Constantes.correoRemitente);
        correo.setPasswordRemitente(Constantes.passwordRemitente);
        correo.setDestinatario(correos);

        ArrayList<GestionLlamadasModel> gestiones=gestionLogic.consultarGestionLlamadas().getData();
        ArrayList<String> gestionesABorrar=new ArrayList<>();

        for(int i=0; i<gestiones.size();i++){
            if(gestiones.get(i).getFechaInserto().equals(fecha7Meses)){
                gestionesABorrar.add(String.valueOf(gestiones.get(i).getIdGestionLlam()));
            }
        }

        RestResponse<String> borrarPromesas=gestionLogic.borrarGestionLlamadas(gestionesABorrar,"3");

        String mensaje="Se elimaron las gestiones del dia "+fecha7Meses+ ", se eliminan "+gestionesABorrar.size()+" gestiones";
        correo.setAsunto("ELIMINACION DE GESTIONES");
        correo.setMensaje(mensaje);
        LOGGER.log(Level.INFO, () -> "eliminarGestiones7meses: "+mensaje+" ,"+borrarPromesas);

        notificaciones.enviarCorreo(correo);

        LOGGER.log(Level.INFO, () -> "eliminarGestiones3meses: Termina batch eliminacion de Gestiones de 3 meses");
    }


    @Scheduled(cron = "0 30 11 * * SUN")
    public void correrProcesoDescarte(){
        LOGGER.log(Level.INFO, () -> "Cominza proceso de Descarte Fin de Semana");
        RestResponse<ArrayList<ClienteModel>>car=carteraLog.carterasDescarteCompleta();
        LOGGER.log(Level.INFO, () -> "Termina proceso de Descarte Fin de Semana, Codigo: "+car.getCode()+" Mensaje,"+car.getMessage());
    }




//        @Scheduled(cron = "0 0 07 * * *")
    public void resetAsistenciaDia() {
        LOGGER.log(Level.INFO, () -> "Comienza Batch de resetAsistenciaDia");
        ArrayList<GestoresModel> obtenerGestores=gestoresLogic.consultarGestoresTKM().getData();
        ArrayList<String> gestoresAct=new ArrayList<>();
        for(int i=0;i< obtenerGestores.size();i++){
            if(obtenerGestores.get(i).getEstado()==1){
                gestoresAct.add("I|"+obtenerGestores.get(i).getIdTkm());
            }
        }

        RestResponse<String> act=gestoresLogic.actualizarAsisGestoresTKMArr(gestoresAct);
        LOGGER.log(Level.INFO, () -> "Termina Batch de resetAsistenciaDia "+act);
    }

//    @Scheduled(cron = "0 0 12,22 * * *")
//    public void avisoElimacionPromesasMes(){
//        LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: Comienza batch de avisoElminacion de Promesas");
//        ArrayList<PromesasModel> promesasCompletas=promesas.consultarPromesas().getData();
//
//        String[] fechaHora=util.obtenerFechaActual().split(" ");
//        String[] horaCompleta=fechaHora[1].split(":");
//        String hora=horaCompleta[0];
//        String[] fechaPF=fechaHora[0].split("-");
//        String fechaFinal=fechaPF[0]+"/"+fechaPF[1]+"/"+fechaPF[2];
//        ArrayList<PromesasModel> promesaRetiroDia=new ArrayList<>();
//        CuerpoCorreo correo=new CuerpoCorreo();
//        ArrayList<String>correos=new ArrayList<>();
//        correos.add(Constantes.correoEncargada);
//        correos.add("axel.rodriguezn@elektra.com.mx");
//        correos.add("amartinezt@tkm.com.mx");
//        correos.add("aolvera@tkm.com.mx");
//        correo.setRemitente(Constantes.correoRemitente);
//        correo.setPasswordRemitente(Constantes.passwordRemitente);
//        correo.setDestinatario(correos);
//
//
//        for(int i=0;i<promesasCompletas.size();i++){
//            if(fechaFinal.equals(promesasCompletas.get(i).getFechInser())){
//                promesaRetiroDia.add(promesasCompletas.get(i));
//            }
//        }
//
//
//        if("22".equals(hora)){
//            for(int j=0; j<promesaRetiroDia.size();j++){
//                String CUElimacion=promesaRetiroDia.get(j).getClienteUnico();
//                String gestor=promesaRetiroDia.get(j).getNombreGestor();
//                LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: Promesa CLIENTE UNICO: "+CUElimacion+", GESTOR: "+gestor);
//                promesas.borrarPromesas(String.valueOf(promesaRetiroDia.get(j).getId()),"3");
//            }
//            String mensaje="Se elimaron "+promesaRetiroDia.size()+ " Promesas";
//            correo.setAsunto("ELIMINACION DE PROMESAS");
//            correo.setMensaje(mensaje);
//            LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: "+mensaje);
//        }
//        else{
//            correo.setAsunto("AVISO, ELIMINACION DE PROMESAS");
//            correo.setMensaje("Se elimanaran "+promesaRetiroDia.size()+" Promesas a las 10 de la noche de hace un mes");
//            LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: Envio de correo de aviso "+correo);
//        }
//        notificaciones.enviarCorreo(correo);
//        LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: Termina batch de avisoElminacion de Promesas");
//
//    }
}

