package com.spring.services.batch;

import com.spring.services.cartera.logic.BlastersSMSLogic;
import com.spring.services.constantes.Constantes;
import com.spring.services.notificaciones.logic.NotificacionesLogic;
import com.spring.services.notificaciones.model.CuerpoCorreo;
import com.spring.services.pagos.logic.PromesasTKMLogic;
import com.spring.services.pagos.model.PromesasModel;
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
    public Batchs() {
        //Vacio
    }

    UtilService util=new UtilService();


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
                if(promesasCompletas.get(i).getPagoFinal()==0){
                    promesapagoHoy.add(promesasCompletas.get(i).getTelefono());
                }
            }
        }
        promesapagoHoy.add("5539252342");
        promesapagoHoy.add("5625247459");
        promesapagoHoy.add("5543773233");
        String mensaje="Cliente Banco Azteca. No hemos recibido su pago, realice el deposito hoy, no pierda de sus beneficios";
        LOGGER.log(Level.INFO, () -> "mandarBlasterRecordatorios: Se envian "+promesapagoHoy.size()+" para blaster");
        blaster.enviarBlaster(promesapagoHoy,mensaje,"RamcesFDz4","R4mdz.tkm");
        LOGGER.log(Level.INFO, () -> "mandarBlasterRecordatorios: Termina batch envio de recordatorios");

    }

    @Scheduled(cron = "0 0 12,22 * * *")
    public void avisoElimacionPromesasMes(){
        LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: Comienza batch de avisoElminacion de Promesas");
        ArrayList<PromesasModel> promesasCompletas=promesas.consultarPromesas().getData();

        String[] fechaHora=util.obtenerFechaActual().split(" ");
        String[] horaCompleta=fechaHora[1].split(":");
        String hora=horaCompleta[0];
        String[] fechaPF=fechaHora[0].split("-");
        String fechaFinal=fechaPF[0]+"/"+fechaPF[1]+"/"+fechaPF[2];
        ArrayList<PromesasModel> promesaRetiroDia=new ArrayList<>();
        CuerpoCorreo correo=new CuerpoCorreo();

        for(int i=0;i<promesasCompletas.size();i++){
            if(fechaFinal.equals(promesasCompletas.get(i).getFechInser())){
                promesaRetiroDia.add(promesasCompletas.get(i));
            }
        }


        if("22".equals(hora)){
            for(int j=0; j<promesaRetiroDia.size();j++){
                String CUElimacion=promesaRetiroDia.get(j).getClienteUnico();
                String gestor=promesaRetiroDia.get(j).getNombreGestor();
                LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: Promesa CLIENTE UNICO: "+CUElimacion+", GESTOR: "+gestor);
                promesas.borrarPromesas(String.valueOf(promesaRetiroDia.get(j).getId()),"3");
            }
            String mensaje="Se elimaron "+promesaRetiroDia.size()+ " Promesas";
            correo.setRemitente(Constantes.correoRemitente);
            correo.setPasswordRemitente(Constantes.passwordRemitente);
            correo.setDestinatario(Constantes.correoEncargada);
            correo.setAsunto("ELIMINACION DE PROMESAS");
            correo.setMensaje(mensaje);
            LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: "+mensaje);
        }
        else{

            correo.setRemitente(Constantes.correoRemitente);
            correo.setPasswordRemitente(Constantes.passwordRemitente);
            correo.setDestinatario(Constantes.correoEncargada);
            correo.setAsunto("AVISO, ELIMINACION DE PROMESAS");
            correo.setMensaje("Se elimanaran "+promesaRetiroDia.size()+" Promesas a las 10 de la noche de hace un mes");
            LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: Envio de correo de aviso "+correo);
        }
        notificaciones.enviarCorreo(correo);
        LOGGER.log(Level.INFO, () -> "avisoElimacionPromesasMes: Termina batch de avisoElminacion de Promesas");

    }

    //Batch con rango de tiempo en horas cada ciertos minutos
//    @Scheduled(cron = "0 0/30 12-16 * * *")
//    public void pruebaBatch(){
//        String fecha=util.obtenerFechaActual();
//        LOGGER.log(Level.INFO, () -> "pruebaBatch Se prueba Batch2"+fecha);
//    }

    //Batch con hora especifica
//    @Scheduled(cron = "0 15 14 * * *")
//    public void pruebaBatchs(){
//        String fecha = util.obtenerFechaActual();
//        LOGGER.log(Level.INFO, () -> "pruebaBatch Se prueba Batch3" + fecha);
//    }





}