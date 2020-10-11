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
public class AttendanceDTO {
    private Integer id;
    private Integer employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private Date attendanceDate;
    private String attendanceType;
    private String attendanceTypeLabel;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
}
