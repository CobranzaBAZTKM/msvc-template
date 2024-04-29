package com.spring.services.notificaciones.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;

@Data
public class CuerpoCorreo {
    private String remitente;
    private String passwordRemitente;
    private ArrayList<String> destinatario;
    private String asunto;
    private String mensaje;
    private String copia;

    public CuerpoCorreo() {
        //Vacio
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
