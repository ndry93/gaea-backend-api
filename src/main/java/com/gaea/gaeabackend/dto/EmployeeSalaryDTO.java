/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.dto;

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
public class EmployeeSalaryDTO {
    private Integer id;
    private String salaryType;
    private double salary;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private Integer employeeId;
}
