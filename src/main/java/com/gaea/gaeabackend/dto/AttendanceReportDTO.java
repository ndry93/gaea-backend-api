/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.dto;

import com.gaea.gaeabackend.common.GaeaUtils;
import java.text.ParseException;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ndry93
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceReportDTO {
    
    private String mmyy;
    private int totalWork;
    private int totalCuti;
    private int totalLibur;
    private int totalLembur;
    
    public Date getDateMMYY() {
        try{
            return GaeaUtils.getSimpleDateFormatMMYY().parse(mmyy);
        }catch(ParseException e){
            return null;
        }
    }
    
}
