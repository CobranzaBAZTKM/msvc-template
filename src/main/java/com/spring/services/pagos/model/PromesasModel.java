package com.spring.services.pagos.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.ss.formula.functions.T;

@Data
public class PromesasModel {

    private Integer id;
    private String fechaIngesoPP;
    private String fechaPago;
    private String fechaVencimientoPP;
    private String folio;
    private String montoPago;
    private String nombreCliente;
    private String clienteUnico;
    private String telefono;
    private String idGestorSCL;
    private String nombreGestor;
    private String observaciones;
    private Integer asignado;
    private Integer whatsApp;
    private String nota;
    private String edito;
    private Integer idGestorTKM;
    private Integer inserto;
    private String gestion1;
    private String gestion2;
    private String gestion3;
    private String tipoLlamada;
    private String fechInser;
    private Integer pagoFinal;
    private String turnoGestor;
    private Integer idAutorizo;
    private String tipoCartera;
    private String idGestorSCLVIP;
    private String recurrencia;
    private String montoInicial;
    private String montoSemanal;
    public PromesasModel() {
        //Vacio
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
