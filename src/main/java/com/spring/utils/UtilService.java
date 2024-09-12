package com.spring.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class UtilService {
  public UtilService() {
    //Vacio
  }

  //  public ResponseEntity<RestResponse<Object>> armarRespuesta (int code, String message, boolean error, Object data,
//          HttpStatus status) {
//    return new ResponseEntity<>(
//            RestResponse.builder()
//                    .transactionID(UUID.randomUUID().toString())
//                    .code(code)
//                    .message(message)
//                    .error(error)
//                    .data(data)
//                    .timestamp(this.obtenerFechaActual())
//                    .build(),
//            status);
//  }

  public String obtenerFechaActual () {
    LocalDateTime fechaHoraActual = LocalDateTime.now();
    DateTimeFormatter fechaHoraFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    return fechaHoraActual.format(fechaHoraFormat);
  }

  public String FechaDiaAnteriorPosterior(int intervalo){
    LocalDateTime fechaHoraActual = LocalDateTime.now();
    LocalDateTime fechaAntPost=fechaHoraActual.plusDays(intervalo);
    DateTimeFormatter fechaHoraFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return fechaAntPost.format(fechaHoraFormat);
  }

  public String FechaMesAnteriorPosterior(int intervalo){
    LocalDateTime fechaHoraActual = LocalDateTime.now();
    LocalDateTime fechaAntPost=fechaHoraActual.plusMonths(intervalo);
    DateTimeFormatter fechaHoraFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return fechaAntPost.format(fechaHoraFormat);
  }

  public String CuPaisCanalJuntosSucSep(String cu){
    String[] cuSeparado= cu.split("-");

    String pais="0"+cuSeparado[0];
    String canal=cuSeparado[1].length()==2?cuSeparado[1]:"0"+cuSeparado[1];
    String sucursal=cuSeparado[2].length()==3?"00"+cuSeparado[2]:"0"+cuSeparado[2];
    String[] folio =cuSeparado[3].split("");
    String folio1="";
    String folio2="";
    String folioFinal="";
    String cuFinal="";

    if(cuSeparado[3].length()<=4){
      folio1=cuSeparado[3];
    }
    else{
      for(int j=0; j<4;j++){
        folio1= folio1+folio[j];
      }
      for(int k=4; k<folio.length;k++){
        folio2= folio2+folio[k];
      }
    }

    if(("").equals(folio2)){
      folioFinal=folio1;
    }
    else{
      folioFinal=folio1+"-"+folio2;
    }

    return pais+canal+"-"+sucursal+"-"+folioFinal;
  }
}
