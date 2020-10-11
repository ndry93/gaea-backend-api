/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.dto;


import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.common.GaeaUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ndry93
 */
@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String employeeNum;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String cityOfBirth;
    private String currentAddress;
    private String currentCity;
    private Date startDate;
    private Date endDate;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    private String phone;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private List<AttendanceDTO> employeeAttendanceList = new ArrayList<>();
    private String position;
    private int totalWorkThisMonth;
    private int totalCutiThisMonth;
    private int totalLiburThisMonth;
    private int totalLemburThisMonth;
    private List<AttendanceReportDTO> monthlyAttendance = new ArrayList<>();
    private List<EmployeeSalaryDTO> employeeSalaryList = new ArrayList<>();
}
