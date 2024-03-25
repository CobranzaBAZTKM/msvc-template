package com.spring.exceptions;

import com.spring.utils.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

//  @ExceptionHandler(value = {ResponseStatusException.class})
//  public ResponseEntity<Object> handleResponseStatusException (ResponseStatusException ex) {
//    HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
//
//    return new ResponseEntity<>(
//            RestResponse.builder()
//                    .code(status.value())
//                    .message(ex.getReason())
//                    .error(true)
//                    .data(null)
//                    .build(),
//            status);
//  }
//
//  @ExceptionHandler(value = {Exception.class})
//  public ResponseEntity<RestResponse<Object>> handleExceptionOwn (Exception e, WebRequest request) {
//    return new ResponseEntity<>(
//            RestResponse.builder()
//                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .message(e.getMessage())
//                    .error(true)
//                    .data(null)
//                    .build(),
//            HttpStatus.INTERNAL_SERVER_ERROR);
//  }
}
