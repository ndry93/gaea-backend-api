/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.controller;

import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.dto.EmployeeDTO;
import com.gaea.gaeabackend.dto.ResponseDTO;
import com.gaea.gaeabackend.request.AttendanceRequest;
import com.gaea.gaeabackend.request.EmployeeRequest;
import com.gaea.gaeabackend.service.EmployeeService;
import com.gaea.gaeabackend.util.CustomResponseUtil;
import com.gaea.gaeabackend.util.GaeaUtil;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ndry93
 */
@RestController
@RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeesController {

    private static Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping("")
    public ResponseEntity<ResponseDTO> getEmployeeList() {
        return CustomResponseUtil
                .wrapList(() -> employeeService.getEmployeeList());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ResponseDTO> getEmployeeById(
            @PathVariable("employeeId") @NotBlank Integer employeeId) {
        return CustomResponseUtil
                .wrap(() -> employeeService.getEmployeeById(employeeId));
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        try {
            String createdBy = GaeaConstants.CREATED_BY;
            Date createdDate = new Date();
            String employeeNumber = GaeaUtil.generateEmployeeNumber(employeeRequest.getPhone());

            logger.info("-----employeeNumber " + employeeNumber);

            EmployeeDTO newEmployee = new EmployeeDTO();
            newEmployee.setEmployeeNum(employeeNumber);
            newEmployee.setFirstName(employeeRequest.getFirstName());
            newEmployee.setLastName(employeeRequest.getLastName());
            newEmployee.setPhone(employeeRequest.getPhone());
            newEmployee.setEmail(employeeRequest.getEmail());
            newEmployee.setPosition(employeeRequest.getPosition());
            newEmployee.setDateOfBirth(employeeRequest.getDateOfBirth());
            newEmployee.setCityOfBirth(employeeRequest.getCityOfBirth());
            newEmployee.setCurrentCity(employeeRequest.getCurrentCity());
            newEmployee.setCurrentAddress(employeeRequest.getCurrentAddress());
            newEmployee.setStartDate(employeeRequest.getStartDate() == null ? new Date() : employeeRequest.getStartDate());
            newEmployee.setEndDate(employeeRequest.getEndDate());
            newEmployee.setCreatedBy(createdBy);
            newEmployee.setCreatedDate(createdDate);
            newEmployee.setEnable(1);

            employeeService.createAndUpdateEmployee(newEmployee);
            
            return CustomResponseUtil
                .wrapList(() -> employeeService.getEmployeeList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<ResponseDTO> updateEmployee(@PathVariable("employeeId") @NotBlank Integer employeeId, @RequestBody @Valid EmployeeRequest employeeRequest) {
        try {
            System.out.println("----- update");
            EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
            if (employee == null) {
                throw new RuntimeException(GaeaConstants.ERR_EMPLOYEE_NOTFOUND);
            }

            String updatedBy = GaeaConstants.CREATED_BY;
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setPhone(employeeRequest.getPhone());
            employee.setEmail(employeeRequest.getEmail());
            employee.setDateOfBirth(employeeRequest.getDateOfBirth());
            employee.setCityOfBirth(employeeRequest.getCityOfBirth());
            employee.setCurrentCity(employeeRequest.getCurrentCity());
            employee.setCurrentAddress(employeeRequest.getCurrentAddress());
            employee.setPosition(employeeRequest.getPosition());
            employee.setStartDate(employeeRequest.getStartDate());
            employee.setEndDate(employeeRequest.getEndDate());
            employee.setEnable(employeeRequest.getEnable());
            employee.setUpdatedBy(updatedBy);
            employee.setUpdatedDate(new Date());
            employee.getEmployeeSalaryList().clear();
            employee.getEmployeeSalaryList().addAll(employeeRequest.getEmployeeSalaryList());

            employeeService.createAndUpdateEmployee(employee);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ResponseDTO> deleteEmployee(@PathVariable("employeeId") @NotBlank Integer employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
        } catch (EmptyResultDataAccessException empty) {
            empty.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", "Data not found", "[]"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
        }
    }

    @GetMapping("/attendances")
    public ResponseEntity<ResponseDTO> getEmployeesAttendances() {
        return CustomResponseUtil
                .wrapList(() -> employeeService.getEmployeesAttendanceList());
    }

    @PostMapping("/attendances")
    public ResponseEntity<ResponseDTO> addEmployeesToAttendance(@RequestBody @Valid AttendanceRequest attendanceRequest) {
        try {
            if(attendanceRequest== null) 
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("2", "Error request must be provided", "[]"));
            if(attendanceRequest.getEmployeeIdList().size() < 1)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("3", "Error employee must be selected", "[]"));
            if(attendanceRequest.getAttendanceType() == null || "".equals(attendanceRequest.getAttendanceType()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("4", "Attendance type must be provided", "[]"));
            if(attendanceRequest.getAttendanceDate() == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("5", "Attendance date must be provided", "[]"));
            
            String createdBy = GaeaConstants.CREATED_BY;
            employeeService.addEmployeeAttendance(
                    attendanceRequest.getEmployeeIdList(),
                    attendanceRequest.getAttendanceDate(),
                    attendanceRequest.getAttendanceType(),
                    createdBy, new Date());

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "Success", "[]"));
        } catch (EmptyResultDataAccessException empty) {
            empty.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", "Data not found", "[]"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
        }
    }

    @DeleteMapping("/attendances/{attendanceId}")
    public ResponseEntity<ResponseDTO> deleteEmployeeAttendance(@PathVariable("attendanceId") @NotBlank Integer attendanceId) {
        try {
            employeeService.deleteEmployeeAttendance(attendanceId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "Success", "[]"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
        }
    }

}
