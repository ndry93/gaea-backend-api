/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.service;

import com.gaea.gaeabackend.dto.LookupDTO;
import com.gaea.gaeabackend.entity.Lookup;
import com.gaea.gaeabackend.repository.LookupRepo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ndry93
 */
@Service
public class LookupService {
    
    @Autowired
    private LookupRepo lookupRepo;
    @Autowired
    private ModelMapper modelMapper;
    
    public List<LookupDTO> getAllLookups(){
        return this.convertToDto(lookupRepo.findAll());
    }
    
    public LookupDTO getCities(){
        return this.getLookupData("CITY");
    }
    
    public LookupDTO getEmployeePositions(){
        return this.getLookupData("EMP_POSITION");
    }
    
    public LookupDTO getCurrencies(){
        return this.getLookupData("CURRENCY");
    }
    
    public LookupDTO getProductTypes(){
        return this.getLookupData("PRODUCT_TYPE");
    }
    
    public LookupDTO getUOMs(){
        return this.getLookupData("UOM");
    }
    
    public LookupDTO getAttendanceTypes(){
        return this.getLookupData("ATTENDANCE_TYPE");
    }
    
    public LookupDTO getSalaryTypes(){
        return this.getLookupData("SALARY_TYPE");
    }
    
    public LookupDTO getSalesTypes(){
        return this.getLookupData("SALES_TYPE");
    }
    
    private LookupDTO getLookupData(String lookupName){
        Optional<Lookup> temp = lookupRepo.findByLookupName(lookupName);
        if(temp.isPresent()){
            Lookup lookup = temp.get();
            return this.convertToDto(lookup);
        }
        return null;
    }
    
    private LookupDTO convertToDto(Lookup lookup) {
        LookupDTO lookupDTO = modelMapper.map(lookup, LookupDTO.class);
        System.out.println("===== "+lookupDTO.getLookupDetailList().size());
        return lookupDTO;
    }
    
    private List<LookupDTO> convertToDto(List<Lookup> lookups){
        List<LookupDTO> lookupDTOs = new ArrayList<>();
        for(Lookup l : lookups){
            LookupDTO employeeDTO = convertToDto(l);
            lookupDTOs.add(employeeDTO);
        }
        return lookupDTOs;
    }
    
    private Lookup convertToEntity(LookupDTO lookupDTO) throws ParseException {
        Lookup lookup = modelMapper.map(lookupDTO, Lookup.class);
        return lookup;
    }
    
    private List<Lookup> convertToEntity(List<LookupDTO> lookupDTOs) throws ParseException {
        List<Lookup> lookups = new ArrayList<>();
        for(LookupDTO dto : lookupDTOs){
            Lookup lookup = convertToEntity(dto);
            lookups.add(lookup);
        }
        return lookups;
    }
    
}
