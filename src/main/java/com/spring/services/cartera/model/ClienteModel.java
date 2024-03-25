package com.spring.services.cartera.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Data
public class ClienteModel {
    private String CLIENTE_UNICO;
    private String NOMBRE_CTE;
    private String RFC_CTE;
    private String GENERO_CLIENTE;
    private String EDAD_CLIENTE;
    private String OCUPACION;
    private String CORREO_ELECTRONICO;
    private String DIRECCION_CTE;
    private String NUM_EXT_CTE;
    private String NUM_INT_CTE;
    private String CP_CTE;
    private String COLONIA_CTE;
    private String POBLACION_CTE;
    private String ESTADO_CTE;
    private String TERRITORIO;
    private String TERRITORIAL;
    private String ZONA;
    private String ZONAL;
    private String NOMBRE_DESPACHO;
    private String GERENCIA;
    private String FECHA_ASIGNACION;
    private String DIAS_ASIGNACION;
    private String REFERENCIAS_DOMICILIO;
    private String CLASIFICACION_CTE;
    private String DIQUE;
    private String ATRASO_MAXIMO;
    private Integer DIAS_ATRASO;
    private String SALDO;
    private String MORATORIOS;
    private Float SALDO_TOTAL;
    private String SALDO_ATRASADO;
    private String SALDO_REQUERIDO;
    private String PAGO_PUNTUAL;
    private String PAGO_NORMAL;
    private String PRODUCTO;
    private String FECHA_ULTIMO_PAGO;
    private String IMP_ULTIMO_PAGO;
    private String CALLE_EMPLEO;
    private String NUM_EXT_EMPLEO;
    private String NUM_INT_EMPLEO;
    private String COLONIA_EMPLEO;
    private String POBLACION_EMPLEO;
    private String ESTADO_EMPLEO;
    private String NOMBRE_AVAL;
    private String TEL_AVAL;
    private String CALLE_AVAL;
    private String NUM_EXT_AVAL;
    private String COLONIA_AVAL;
    private String CP_AVAL;
    private String POBLACION_AVAL;
    private String ESTADO_AVAL;
    private String CLIENTE_GRUPAL;
    private String FIPAISGEO;
    private String FICUADRANTEGEO;
    private String FIZONAGEO;
    private String FIDIAPAGO;
    private String TELEFONO1;
    private String TELEFONO2;
    private String TELEFONO3;
    private String TELEFONO4;
    private String TIPOTEL1;
    private String TIPOTEL2;
    private String TIPOTEL3;
    private String TIPOTEL4;
    private String LATITUD;
    private String LONGITUD;
    private String DESPACHO_GESTIONO;
    private String ULTIMA_GESTION;
    private String GESTION_DESC;
    private String CAMPANIA_RELAMPAGO;
    private String CAMPANIA;
    private String TIPO_CARTERA;
    private String ID_GRUPO;
    private String GRUPO_MAZ;
    private String CLAVE_SPEI;
    private String PAGOS_CLIENTE;
    private String MONTO_PAGOS;
    private String GESTORES;
    private String FOLIO_PLAN;
    private String SEGMENTO_GENERACION;
    private String ESTATUS_PLAN;
    private String SEMANAS_ATRASO;
    private String ATRASO;
    private String GENERACION_PLAN;
    private String CANCELACION_CUMPLIMIENTO_PLAN;
    private String ULTIMO_ESTATUS;
    private String EMPLEADO;
    private String CANAL;
    private String ABONO_SEMANAL;
    private String PLAZO;
    private String MONTO_ABONADO;
    private String MONTO_PLAN;
    private String ENGANCHE;
    private String PAGOS_RECIBIDOS;
    private String SALDO_ANTES_DEL_PLAN;
    private String SALDO_ATRASADO_ANTES_PLAN;
    private String MORATORIOS_ANTES_PLAN;
    private String ESTATUS_PROMESA_PAGO;
    private String MONTO_PROMESA_PAGO;
    private Integer SEGMENTO;
    private String FECHA_INSER_LOCAL;

    public ClienteModel() {
        //Vacio
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


}
