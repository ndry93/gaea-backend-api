/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.gaea.gaeabackend.dto.ResponseDTO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;


/**
 *
 * @author ndry93
 */
public class CustomResponseUtil {
    
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
//  private final static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  private final static ObjectMapper mapper = new ObjectMapper();
  
  static{
//      mapper.registerModule(new JodaModule());
//      
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
//      mapper.setDateFormat(df);
  }
  
  public static <T> ResponseEntity<ResponseDTO> wrap(Supplier<T> supplier) {
    

    try {
      T dto = supplier.get();

      if (Objects.isNull(dto)){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "data not found", "[]"));
      }
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", mapper.writeValueAsString(dto)));
    } catch (Exception e) {
        e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), ""));
    }
  }

  public static <T> ResponseEntity<ResponseDTO> wrapList(Supplier<List<T>> supplier) {

    try {
      List<T> dtos = supplier.get();

      if (Objects.isNull(dtos) || CollectionUtils.isEmpty(dtos)){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", mapper.writeValueAsString(dtos)));
    } catch (Exception e) {
        e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), ""));
    }
  }

  public static ResponseEntity<ResponseDTO> wrapPayload(Supplier<ResponseDTO> supplier) {
    try {
      ResponseDTO payload = supplier.get();

      if (payload == null) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }
      return ResponseEntity.status(HttpStatus.OK).body(payload);
    } catch (Exception e) {
        e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), ""));
    }
  }
}
