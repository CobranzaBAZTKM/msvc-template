package com.spring.services.operacion.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class GestionLlamadasModel {

    private Integer idGestionLlam;
    private String clienteUnico;
    private String telefono;
    private Integer idTipificacion;
    private Integer idGestorTkm;
    private String comentario;
    private Integer actualizo;
    private String fechaInserto;
    private Integer cantidadInsert;
    private String horaInserto;
    private String tipoCarteraTKM;


    public GestionLlamadasModel() {
        //Vacio
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
