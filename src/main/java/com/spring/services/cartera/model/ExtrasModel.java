package com.spring.services.cartera.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class ExtrasModel {
    private String cookie;
    private String tipoArchivo;
    private String nombreArchivo;
    private String nombreArchivo2;
    private String mensajeSMS;
    private String tipoFuncion;
    private String usuarioBlasters;
    private String passwordBlasters;

    public ExtrasModel() {
        //Vacio
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
