/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.common;

import com.gaea.gaeabackend.dto.LookupDTO;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ndry93
 */
public class GaeaConstants {
    public static final String CREATED_BY = "gaea";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ATTENDANCE_TYPE_WORK = "KERJA";
    public static final String ATTENDANCE_TYPE_CUTI = "CUTI";
    public static final String ATTENDANCE_TYPE_LIBUR = "LIBUR";
    public static final String ATTENDANCE_TYPE_LEMBUR = "LEMBUR";
    
    public static final String ERR_USER_NOTFOUND = "User not found";
    public static final String ERR_CUSTOMER_NOTFOUND = "Customer not found";
    public static final String ERR_EMPLOYEE_NOTFOUND = "Employee not found";
    public static final String ERR_PRODUCT_NOTFOUND = "Product not found";
    public static final String ERR_PHONE_MUST_8_DIGITS = "Phone must have minimum 8 digits";
    public static final String ERR_PASSWORD_MUST_8_DIGITS = "Password must have minimum 8 digits";
    public static final String ERR_PASSWORD_CONFIRM_MISMATCH = "Password confirmation does not match";
    public static final String ERR_EMAIL_PASSWORD_EMPTY = "Email and password must not be empty";
    
    public static final int LIST_FETCH_SIZE = 10;
    
}
