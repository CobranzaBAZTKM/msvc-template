package com.spring.services.pagos.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;

@Data
public class datosEntradaPagosPlanes {
    private String cookiePlanes;
    private String cookieGestores;
    private ArrayList<String> cu;
    private String cuFinal;
    private String montoPrometido;
    private String montoDepositado;
    private String status;
    private String idPlanActivo;
    private String gestor;
    private String diaPago;


    public datosEntradaPagosPlanes() {
        //Vacio
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
