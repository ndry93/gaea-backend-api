/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.controller;

import com.gaea.gaeabackend.dto.ResponseDTO;
import com.gaea.gaeabackend.service.LookupService;
import com.gaea.gaeabackend.util.CustomResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ndry93
 */
@RestController
@RequestMapping(value = "/lookups", produces = MediaType.APPLICATION_JSON_VALUE)
public class LookupsController {
    
    @Autowired
    private LookupService lookupService;
    
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAll(){
        return CustomResponseUtil
          .wrap(() -> lookupService.getAllLookups());
    }
    
    @GetMapping("/cities")
    public ResponseEntity<ResponseDTO> getCities() {
      return CustomResponseUtil
          .wrap(() -> lookupService.getCities());
    }
    
    @GetMapping("/currencies")
    public ResponseEntity<ResponseDTO> getCurrencies() {
      return CustomResponseUtil
          .wrap(() -> lookupService.getCurrencies());
    }
    
    @GetMapping("/positions")
    public ResponseEntity<ResponseDTO> getEmployeePositions() {
      return CustomResponseUtil
          .wrap(() -> lookupService.getEmployeePositions());
    }
    
    @GetMapping("/product/types")
    public ResponseEntity<ResponseDTO> getProductTypes() {
      return CustomResponseUtil
          .wrap(() -> lookupService.getProductTypes());
    }
    
    @GetMapping("/sales/types")
    public ResponseEntity<ResponseDTO> getSalesTypes() {
      return CustomResponseUtil
          .wrap(() -> lookupService.getSalesTypes());
    }
    
    @GetMapping("/uoms")
    public ResponseEntity<ResponseDTO> getUOMs() {
      return CustomResponseUtil
          .wrap(() -> lookupService.getUOMs());
    }
    
    @GetMapping("/attendance/types")
    public ResponseEntity<ResponseDTO> getAttendanceTypes() {
      return CustomResponseUtil
          .wrap(() -> lookupService.getAttendanceTypes());
    }
    
    @GetMapping("/salary/types")
    public ResponseEntity<ResponseDTO> getSalaryTypes() {
      return CustomResponseUtil
          .wrap(() -> lookupService.getSalaryTypes());
    }
}
