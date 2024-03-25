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
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo.getRemitente()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("axel.rodriguezn@elektra.com.mx"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo.getDestinatario()));
            message.setSubject(correo.getAsunto());
            message.setText(correo.getMensaje());
            Transport t = session.getTransport("smtp");
            t.connect(correo.getRemitente(), correo.getPasswordRemitente());
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Correo enviado correctamente");
            respuesta.setData("Revisa tu buzon");

        }
        catch (Exception e){
            respuesta.setMessage("Algo ocurrio "+e);
        }

        return respuesta;
    }


    public RestResponse<String>  enviarCorreoGenerico(CuerpoCorreo correo){
//        CuerpoCorreo datosCorreo=new CuerpoCorreo();
        correo.setRemitente(Constantes.correoRemitente);
        correo.setPasswordRemitente(Constantes.passwordRemitente);
        correo.setDestinatario(Constantes.correoEncargada);
//        datosCorreo.setAsunto(asunto);
//        datosCorreo.setMensaje(mensaje);
        LOGGER.log(Level.INFO, () -> "REQUEST Envio correo"+correo);
        RestResponse<String> respuesta=this.enviarCorreo(correo);
        LOGGER.log(Level.INFO, () -> "RESPONSE Envio correo "+respuesta);
        return respuesta;
    }

}
