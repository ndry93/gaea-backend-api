/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.controller;

import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.dto.CustomerDTO;
import com.gaea.gaeabackend.dto.ResponseDTO;
import com.gaea.gaeabackend.request.CustomerRequest;
import com.gaea.gaeabackend.service.CustomerService;
import com.gaea.gaeabackend.util.CustomResponseUtil;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ndry93
 */
@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping("")
    public ResponseEntity<ResponseDTO> getCustomerList(
            @RequestParam( value = "filter", required = false) String filter,
            @RequestParam( value = "page") int page) {
      return CustomResponseUtil
          .wrapList(() -> customerService.getCustomerList(filter,page));
    }
    
    @GetMapping("/{customerId}")
    public ResponseEntity<ResponseDTO> getCustomerById(
            @PathVariable("customerId") @NotBlank Integer id) {
      return CustomResponseUtil
          .wrap(() -> customerService.getCustomerById(id));
    }
    
    @PostMapping("")
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
      try{
          String createdBy = GaeaConstants.CREATED_BY;
          
          CustomerDTO newCustomer = new CustomerDTO();
          newCustomer.setFirstName(customerRequest.getFirstName());
          newCustomer.setLastName(customerRequest.getLastName());
          newCustomer.setPhone(customerRequest.getPhone());
          newCustomer.setEmail(customerRequest.getEmail());
          newCustomer.setCityOfBirth(customerRequest.getCityOfBirth());
          newCustomer.setCreatedBy(createdBy);
          newCustomer.setCreatedDate(new Date());
          newCustomer.setEnable(1);
          
          customerService.createAndUpdateCustomer(newCustomer);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
    
    @PutMapping("/{customerId}")
    public ResponseEntity<ResponseDTO> updateCustomer
        (@PathVariable("customerId") @NotBlank Integer customerId, @RequestBody @Valid CustomerRequest customerRequest) {
      try{
          System.out.println("----- update");
          CustomerDTO customer = customerService.getCustomerById(customerId);
          if(customer == null) throw new RuntimeException(GaeaConstants.ERR_CUSTOMER_NOTFOUND);
          
          String updatedBy = GaeaConstants.CREATED_BY;
          customer.setFirstName(customerRequest.getFirstName());
          customer.setLastName(customerRequest.getLastName());
          customer.setPhone(customerRequest.getPhone());
          customer.setEmail(customerRequest.getEmail());
          customer.setCityOfBirth(customerRequest.getCityOfBirth());
          customer.setEnable(customerRequest.getEnable());
          customer.setUpdatedBy(updatedBy);
          customer.setUpdatedDate(new Date());
          
          customerService.createAndUpdateCustomer(customer);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
        
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ResponseDTO> deleteCustomer
        (@PathVariable("customerId") @NotBlank Integer customerId) {
      try{
          customerService.deleteCustomer(customerId);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }catch(EmptyResultDataAccessException empty){
          empty.getMessage();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", "Data not found", "[]"));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
    
}
