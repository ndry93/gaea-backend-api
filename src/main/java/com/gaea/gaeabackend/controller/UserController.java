/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.controller;

import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.dto.ResponseDTO;
import com.gaea.gaeabackend.dto.UserDTO;
import com.gaea.gaeabackend.request.UserRequest;
import com.gaea.gaeabackend.service.UserService;
import com.gaea.gaeabackend.util.CustomResponseUtil;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ndry93
 */
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public ResponseEntity<ResponseDTO> getUserList() {
      return CustomResponseUtil
          .wrapList(() -> userService.getUserList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getUserById(
            @PathVariable("id") @NotBlank Integer id) {
      return CustomResponseUtil
          .wrap(() -> userService.getUserById(id));
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<ResponseDTO> getUserByEmail(
            @PathVariable("email") @NotBlank String email) {
      return CustomResponseUtil
          .wrap(() -> userService.getUserByEmail(email));
    }
    
    @PostMapping("")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody @Valid UserRequest userRequest) {
      try{
          if(userRequest.getEmail() == null || userRequest.getPassword() == null){
              throw new RuntimeException(GaeaConstants.ERR_EMAIL_PASSWORD_EMPTY);
          }
          if(userRequest.getPassword().length() < 8){
              throw new RuntimeException(GaeaConstants.ERR_PASSWORD_MUST_8_DIGITS);
          }
          if(userRequest.getPassword().equals(userRequest.getConfirmPassword())){
              throw new RuntimeException(GaeaConstants.ERR_PASSWORD_CONFIRM_MISMATCH);
          }
          String createdBy = GaeaConstants.CREATED_BY;
          
          UserDTO newUser = new UserDTO();
          newUser.setFirstName(userRequest.getFirstName());
          newUser.setLastName(userRequest.getLastName());
          newUser.setEmail(userRequest.getEmail());
          newUser.setCreatedBy(createdBy);
          newUser.setCreatedDate(new Date());
          newUser.setEnable(1);
          
          userService.createAndUpdateUsr(newUser);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateUser
        (@PathVariable("id") @NotBlank Integer id, @RequestBody @Valid UserRequest userRequest) {
      try{
          System.out.println("----- update");
          UserDTO user = userService.getUserById(id);
          if(user == null) throw new RuntimeException(GaeaConstants.ERR_USER_NOTFOUND);
          
          String updatedBy = GaeaConstants.CREATED_BY;
          user.setFirstName(userRequest.getFirstName());
          user.setLastName(userRequest.getLastName());
          user.setEmail(userRequest.getEmail());
          user.setEnable(userRequest.getEnable());
          user.setUpdatedBy(updatedBy);
          user.setUpdatedDate(new Date());
          
          userService.createAndUpdateUsr(user);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
        
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUser
        (@PathVariable("id") @NotBlank Integer id) {
      try{
          userService.deleteUser(id);
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
