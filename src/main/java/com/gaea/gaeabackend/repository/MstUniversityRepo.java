/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.repository;

import com.gaea.gaeabackend.entity.MstUniversity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ndry93
 */
public interface MstUniversityRepo extends JpaRepository<MstUniversity, Integer> {
    
}
