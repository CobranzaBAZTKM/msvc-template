package com.spring.services.operacion.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class TipificacionesModel {

    private Integer id;
    private String tipificacion;
    private Integer valor;
    private Integer inserto;
    private Integer actualizo;
    private String fechaInserto;

    public TipificacionesModel() {
        //Vacio
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
