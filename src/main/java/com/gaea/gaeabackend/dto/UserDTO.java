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
public class UserDTO {
    
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private List<UserActivitiesLogDTO> userActivitiesLogList;
}
