/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.service;

import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.common.GaeaUtils;
import com.gaea.gaeabackend.dto.AttendanceDTO;
import com.gaea.gaeabackend.dto.AttendanceReportDTO;
import com.gaea.gaeabackend.dto.EmployeeDTO;
import com.gaea.gaeabackend.dto.EmployeeSalaryDTO;
import com.gaea.gaeabackend.dto.LookupDTO;
import com.gaea.gaeabackend.dto.LookupDetailDTO;
import com.gaea.gaeabackend.entity.Employee;
import com.gaea.gaeabackend.entity.EmployeeAttendance;
import com.gaea.gaeabackend.entity.EmployeeSalary;
import com.gaea.gaeabackend.repository.EmployeeAttendanceRepo;
import com.gaea.gaeabackend.repository.EmployeeRepo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author ndry93
 */
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private EmployeeAttendanceRepo employeeAttendanceRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LookupService lookupService;
    
    public List<EmployeeDTO> getEmployeeList(){
        return this.convertToDto(employeeRepo.findAll());
    }
    
    public EmployeeDTO getEmployeeById(Integer id){
        Optional<Employee> resp = employeeRepo.findById(id);
        if(resp.isPresent()) return this.convertToDto(resp.get());
        return null;
    }
    
    public void createAndUpdateEmployee(EmployeeDTO employeeDTO){
        Employee newEmployee = employeeRepo.save(this.convertToEntity(employeeDTO));

        Map<String,EmployeeSalary> salaryMapTemp = new HashMap<>();
        for(EmployeeSalary salary : newEmployee.getEmployeeSalaryList()){
            EmployeeSalary temp = salaryMapTemp.get(salary.getSalaryType());
            if(temp  == null){ 
                //new
                salaryMapTemp.put(salary.getSalaryType(), salary);
            }
        }

        lookupService.getSalaryTypes().getLookupDetailList().stream().forEach(
            (salaryType) -> {
                if(salaryMapTemp.get(salaryType.getValue1()) == null){
                    EmployeeSalary salary = new EmployeeSalary();
                    salary.setEmployee(newEmployee);
                    salary.setSalaryType(salaryType.getValue1());   
                    newEmployee.getEmployeeSalaryList().add(salary);
                }
            }
        );

        employeeRepo.save(newEmployee);
    }
    
    public void deleteEmployee(Integer employeeId){
        employeeRepo.deleteById(employeeId);
    }
    
    public void addEmployeeAttendance(List<Integer> employeeIds, Date attendanceDate, String attendanceType,
            String createdBy, Date createdDate){
        for(Integer id : employeeIds){
            
            Optional<Employee> temp = employeeRepo.findById(id);
            if(temp.isPresent()){
                Employee employee = temp.get();
                EmployeeAttendance ea = new EmployeeAttendance();
                ea.setAttendanceType(attendanceType);
                ea.setAttendanceDate(attendanceDate);
                ea.setCreatedBy(createdBy);
                ea.setCreatedDate(createdDate);
                ea.setEmployee(employee);
                ea.setEnable(1);
                employeeAttendanceRepo.save(ea);
            }
            
        }
    }
    
    public void deleteEmployeeAttendance(Integer attendanceId){
        employeeAttendanceRepo.deleteById(attendanceId);
    }
    
    public List<AttendanceDTO> getEmployeesAttendanceList(){
        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        
        for(EmployeeAttendance ea : employeeAttendanceRepo.findAll()){
            AttendanceDTO attendanceDTO = new AttendanceDTO();
            attendanceDTO.setAttendanceDate(ea.getAttendanceDate());
            attendanceDTO.setEmployeeFirstName(ea.getEmployee().getFirstName());
            attendanceDTO.setEmployeeLastName(ea.getEmployee().getLastName());
            attendanceDTO.setEmployeeId(ea.getEmployee().getId());
            attendanceDTO.setId(ea.getId());
            attendanceDTO.setAttendanceType(ea.getAttendanceType());
            attendanceDTO.setAttendanceTypeLabel(getAttendanceTypeLabelFromCache(ea.getAttendanceType()));
            
            attendanceDTOs.add(attendanceDTO);
        }
        return attendanceDTOs;
    }
    
    @Cacheable(value = "attendance-label-by-key", key = "#key")
    private String getAttendanceTypeLabelFromCache(String key){
        LookupDTO lookup = lookupService.getAttendanceTypes();
        List<LookupDetailDTO> types = lookup.getLookupDetailList();
        Map<String, String> typesMap = new HashMap();
        for(LookupDetailDTO type : types){
            String data = typesMap.get(type.getValue1());
            if(data == null){
                typesMap.put(type.getValue1(), type.getValue2());
            }
        }
        return typesMap.get(key);
    }
    
    private EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        
        int _countWorkThisMonth = 0;
        int _countCutiThisMonth = 0;
        int _countLiburThisMonth = 0;
        int _countLemburThisMonth = 0;
//        int limit = 24;
        for(AttendanceDTO attendance:  employeeDTO.getEmployeeAttendanceList()){
            
//            if(limit < 0) break;
            
            attendance.setAttendanceTypeLabel(getAttendanceTypeLabelFromCache(attendance.getAttendanceType()));
            String _thisMMYY = GaeaUtils.getSimpleDateFormatMMYY().format(attendance.getAttendanceDate());
            if(GaeaUtils.getCurrentMMYYinString().equals(_thisMMYY)){
                //this month data
                if(GaeaConstants.ATTENDANCE_TYPE_WORK.equals(attendance.getAttendanceType())) {
                    _countWorkThisMonth = _countWorkThisMonth + 1;
                }else if(GaeaConstants.ATTENDANCE_TYPE_CUTI.equals(attendance.getAttendanceType())){
                    _countCutiThisMonth = _countCutiThisMonth + 1;
                }else if(GaeaConstants.ATTENDANCE_TYPE_LIBUR.equals(attendance.getAttendanceType())){
                    _countLiburThisMonth = _countLiburThisMonth + 1;
                }else if(GaeaConstants.ATTENDANCE_TYPE_LEMBUR.equals(attendance.getAttendanceType())){
                    _countLemburThisMonth = _countLemburThisMonth + 1;
                }
            }
            
            List<AttendanceReportDTO> _tempList = employeeDTO.getMonthlyAttendance().stream().filter(
                (att) -> att.getMmyy().equals(_thisMMYY)
            ).collect(Collectors.toList());
            
            if(_tempList.size() > 0){
                AttendanceReportDTO _existTemp = _tempList.get(0);
                if(GaeaConstants.ATTENDANCE_TYPE_WORK.equals(attendance.getAttendanceType())) {
                    _existTemp.setTotalWork(_existTemp.getTotalWork() + 1);
                }else if(GaeaConstants.ATTENDANCE_TYPE_CUTI.equals(attendance.getAttendanceType())){
                    _existTemp.setTotalCuti(_existTemp.getTotalCuti()+ 1);
                }else if(GaeaConstants.ATTENDANCE_TYPE_LIBUR.equals(attendance.getAttendanceType())){
                    _existTemp.setTotalLibur(_existTemp.getTotalLibur()+ 1);
                }else if(GaeaConstants.ATTENDANCE_TYPE_LEMBUR.equals(attendance.getAttendanceType())){
                    _existTemp.setTotalLembur(_existTemp.getTotalLembur()+ 1);
                }
            }else{
                AttendanceReportDTO newTemp = new AttendanceReportDTO();
                newTemp.setMmyy(_thisMMYY);
                if(GaeaConstants.ATTENDANCE_TYPE_WORK.equals(attendance.getAttendanceType())) {
                    newTemp.setTotalWork(newTemp.getTotalWork() + 1);
                }else if(GaeaConstants.ATTENDANCE_TYPE_CUTI.equals(attendance.getAttendanceType())){
                    newTemp.setTotalCuti(newTemp.getTotalCuti()+ 1);
                }else if(GaeaConstants.ATTENDANCE_TYPE_LIBUR.equals(attendance.getAttendanceType())){
                    newTemp.setTotalLibur(newTemp.getTotalLibur()+ 1);
                }else if(GaeaConstants.ATTENDANCE_TYPE_LEMBUR.equals(attendance.getAttendanceType())){
                    newTemp.setTotalLembur(newTemp.getTotalLembur()+ 1);
                }
                
                
                
                
                employeeDTO.getMonthlyAttendance().add(newTemp);
            }
            
//            --limit;
        }
        
        //sort monthly report
        Collections.sort(employeeDTO.getMonthlyAttendance(), new Comparator<AttendanceReportDTO>() {
            public int compare(AttendanceReportDTO o1, AttendanceReportDTO o2) {
                return o2.getDateMMYY().compareTo(o1.getDateMMYY());
            }
          });
//        employeeDTO.getMonthlyAttendance().sort(c);
        
        employeeDTO.setTotalWorkThisMonth(_countWorkThisMonth);
        employeeDTO.setTotalCutiThisMonth(_countCutiThisMonth);
        employeeDTO.setTotalLiburThisMonth(_countLiburThisMonth);
        employeeDTO.setTotalLemburThisMonth(_countLemburThisMonth);
        return employeeDTO;
    }
    
    private List<EmployeeDTO> convertToDto(List<Employee> employees){
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for(Employee e : employees){
            EmployeeDTO employeeDTO = convertToDto(e);
            employeeDTOs.add(employeeDTO);
        }
        return employeeDTOs;
    }
    
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        for(EmployeeSalaryDTO salary : employeeDTO.getEmployeeSalaryList()){
            System.out.println("-----1 "+salary.getSalary());
        }
        for(EmployeeSalary salary : employee.getEmployeeSalaryList()){
            System.out.println("-----2 "+salary.getSalary());
        }
        System.out.println("-----employee.getPosition() "+employee.getPosition());
        return employee;
    }
    
    private List<Employee> convertToEntity(List<EmployeeDTO> employeeDTOs) {
        List<Employee> employees = new ArrayList<>();
        for(EmployeeDTO dto : employeeDTOs){
            Employee employee = convertToEntity(dto);
            employees.add(employee);
        }
        return employees;
    }
    
//    PropertyMap<Employee, EmployeeDTO> skipToDTOMethods = new PropertyMap<Employee, EmployeeDTO>() {
//      protected void configure() {
//         skip().setAttendanceList(null);
//     }
//   };
}
