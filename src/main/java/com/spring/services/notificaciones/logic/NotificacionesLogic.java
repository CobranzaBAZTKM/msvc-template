package com.spring.services.notificaciones.logic;

import com.spring.services.constantes.Constantes;
import com.spring.services.notificaciones.model.CuerpoCorreo;
import com.spring.utils.RestResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

@Component
public class NotificacionesLogic {
    private static final Logger LOGGER = LogManager.getLogger("NotificacionesLogic");
    public NotificacionesLogic() {
        //Vacio
    }


    public RestResponse<String> enviarCorreo(CuerpoCorreo correo) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setData(null);


        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);

        try{
            LOGGER.log(Level.INFO, () -> "REQUEST enviarCorreo"+correo);
            String correos="";
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo.getRemitente()));
            for(int i=0;i<correo.getDestinatario().size();i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo.getDestinatario().get(i)));
                correos=correos+" "+correo.getDestinatario().get(i)+",";
            }
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("axel.rodriguezn@elektra.com.mx"));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo.getDestinatario()));
            message.setSubject(correo.getAsunto());
            message.setText(correo.getMensaje());
            Transport t = session.getTransport("smtp");
            t.connect(correo.getRemitente(), correo.getPasswordRemitente());
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Correo(s) enviado(s) correctamente "+correos);
            respuesta.setData("Revisa tu buzon de enviados");
            LOGGER.log(Level.INFO, () -> "REQUEST enviarCorreo"+respuesta);

        }
        catch (Exception e){
            respuesta.setMessage("Algo ocurrio "+e);
        }

        return respuesta;
    }


    public RestResponse<String>  enviarCorreoGenerico(CuerpoCorreo correo){
//        CuerpoCorreo datosCorreo=new CuerpoCorreo();
        ArrayList<String> correos=new ArrayList<>();
        correos.add(Constantes.correoEncargada);
//        correos.add("axel.rodriguezn@elektra.com.mx");
        correo.setRemitente(Constantes.correoRemitente);
        correo.setPasswordRemitente(Constantes.passwordRemitente);
        correo.setDestinatario(correos);
//        datosCorreo.setAsunto(asunto);
//        datosCorreo.setMensaje(mensaje);
        LOGGER.log(Level.INFO, () -> "REQUEST Envio Correo Generico"+correo);
        RestResponse<String> respuesta=this.enviarCorreo(correo);
        LOGGER.log(Level.INFO, () -> "RESPONSE Envio Correo Generico"+respuesta);
        return respuesta;
    }

}
