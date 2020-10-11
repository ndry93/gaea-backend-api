/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.service;

import com.gaea.gaeabackend.repository.MstUniversityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ndry93
 */
@Service
public class MasterService {
    
    @Autowired
    private MstUniversityRepo mstUniversityRepo;
    
    
    
}
