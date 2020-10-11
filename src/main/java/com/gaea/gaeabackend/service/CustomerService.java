/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.service;

import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.dto.CustomerDTO;
import com.gaea.gaeabackend.entity.Customer;
import com.gaea.gaeabackend.entity.QCustomer;
import com.gaea.gaeabackend.repository.CustomerRepo;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author ndry93
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ModelMapper modelMapper;
    
    public List<CustomerDTO> getCustomerList(String filter, int page){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QCustomer customer = QCustomer.customer;
        System.out.println("-----filter "+filter + " page "+page);
        if(filter != null && !filter.isEmpty()){
            booleanBuilder.or(customer.firstName.contains(filter));
            booleanBuilder.or(customer.lastName.contains(filter));
//            booleanBuilder.or(customer.city.contains(filter));
//            booleanBuilder.or(customer.address.contains(filter));
            booleanBuilder.or(customer.phone.contains(filter));
//            booleanBuilder.or(customer.email.contains(filter));
        }
        PageRequest pageable = PageRequest.of(page - 1, GaeaConstants.LIST_FETCH_SIZE,new Sort(Sort.Direction.DESC, "createdDate"));
        return this.convertToDto(Lists.newArrayList(customerRepo.findAll(booleanBuilder.getValue(),pageable)));
//        return this.convertToDto(customerRepo.findAll());
    }
    
    public CustomerDTO getCustomerById(Integer id){
        Optional<Customer> resp = customerRepo.findById(id);
        System.out.println("++++++++++getEmployeeById "+resp.isPresent());
        if(resp.isPresent()) return this.convertToDto(resp.get());
        return null;
    }
    
    public void createAndUpdateCustomer(CustomerDTO customerDTO){
        customerRepo.save(this.convertToEntity(customerDTO));
    }
    
    public void deleteCustomer(Integer id){
        customerRepo.deleteById(id);
    }
    
    private CustomerDTO convertToDto(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        return customerDTO;
    }
    
    private List<CustomerDTO> convertToDto(List<Customer> customers){
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for(Customer c : customers){
            CustomerDTO customerDTO = convertToDto(c);
            customerDTOs.add(customerDTO);
        }
        return customerDTOs;
    }
    
    private Customer convertToEntity(CustomerDTO customerDTO){
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        return customer;
    }
    
    private List<Customer> convertToEntity(List<CustomerDTO> customerDTOs){
        List<Customer> customers = new ArrayList<>();
        for(CustomerDTO dto : customerDTOs){
            Customer customer = convertToEntity(dto);
            customers.add(customer);
        }
        return customers;
    }
}
