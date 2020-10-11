/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.service;

import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.dto.UserDTO;
import com.gaea.gaeabackend.entity.Role;
import com.gaea.gaeabackend.entity.User;
import com.gaea.gaeabackend.entity.UserActivitiesLog;
import com.gaea.gaeabackend.repository.RoleRepo;
import com.gaea.gaeabackend.repository.UserActivitiesLogRepo;
import com.gaea.gaeabackend.repository.UserRepo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ndry93
 */
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserActivitiesLogRepo userActivitiesLogRepo;
    @Autowired
    private ModelMapper modelMapper;
    
    
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnable(1);
        Role userRole = roleRepo.findByRole(GaeaConstants.ROLE_USER);
        System.out.println("-----userRole "+userRole);
        user.setRoleList(Arrays.asList(userRole));
        userRepo.save(user);
    }
    
    public void logActivity(){
        UserActivitiesLog ual = new UserActivitiesLog();
        
        userActivitiesLogRepo.save(ual);
    }
    
    public List<UserDTO> getUserList(){
        return this.convertToDto(userRepo.findAll());
    }
    
    public UserDTO getUserById(Integer id){
        Optional<User> resp = userRepo.findById(id);
        if(resp.isPresent()) throw new RuntimeException(GaeaConstants.ERR_USER_NOTFOUND); 
        return this.convertToDto(resp.get());
    }
    
    public UserDTO getUserByEmail(String email){
        User user = userRepo.findByEmail(email);
        if(user == null) throw new RuntimeException(GaeaConstants.ERR_USER_NOTFOUND); 
        return this.convertToDto(user);
    }
    
    public void createAndUpdateUsr(UserDTO userDTO){
        userRepo.save(this.convertToEntity(userDTO));
    }
    
    public void deleteUser(Integer id){
        userRepo.deleteById(id);
    }
    
    private UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
    
    private List<UserDTO> convertToDto(List<User> users){
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User u : users){
            UserDTO userDTO = convertToDto(u);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }
    
    private User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }
    
    private List<User> convertToEntity(List<UserDTO> userDTOs) {
        List<User> users = new ArrayList<>();
        for(UserDTO dto : userDTOs){
            User user = convertToEntity(dto);
            users.add(user);
        }
        return users;
    }
}
