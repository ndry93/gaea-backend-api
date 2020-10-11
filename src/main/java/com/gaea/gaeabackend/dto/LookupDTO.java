/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ndry93
 */
@Data
@NoArgsConstructor
public class LookupDTO {
    private Integer id;
    private String lookupName;
    private int enable;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private List<LookupDetailDTO> lookupDetailList;
}
