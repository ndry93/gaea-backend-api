/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ndry93
 */
@Data
@NoArgsConstructor
public class LookupDetailDTO {
    private Integer Id;
    private Integer lookupId;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private int enable;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
}
