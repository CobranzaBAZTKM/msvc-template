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
}
