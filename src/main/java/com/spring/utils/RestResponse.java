package com.spring.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

//@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestResponse<T> {

  private String transactionID;
  private int code;
  private String message;
  private boolean error;
  private String timestamp;
  private T data;

  public RestResponse() {
    //Vacio
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
