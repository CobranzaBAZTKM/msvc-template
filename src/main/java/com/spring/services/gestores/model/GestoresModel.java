package com.spring.services.gestores.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;

@Data
public class GestoresModel {

    private String idGestor;
    private String nombreGestor;
    private String password;
    private String cokkie;
    private ArrayList<String> cuClientes;
    private Integer idTkm;
    private Integer puesto;
    private Integer idRegistro;
    private Integer idActualizo;
    private String turno;
    private Integer estado;

    public GestoresModel() {
        //Vacio
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
