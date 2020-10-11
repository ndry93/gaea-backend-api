/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.repository;

import com.gaea.gaeabackend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 *
 * @author ndry93
 */
public interface CustomerRepo extends JpaRepository<Customer, Integer>,QuerydslPredicateExecutor<Customer>  {
    
}
