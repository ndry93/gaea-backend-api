/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.dto;

import lombok.Data;

/**
 *
 * @author ndry93
 */
@Data
public class ResponseDTO {

    private String errorCode;
    private String errorMessage;
    private String data;
    
    public ResponseDTO(){
        super();
    }

    public ResponseDTO(String errorCode, String errorMessage, String data) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }
    
}
