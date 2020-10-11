/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.util;

import com.gaea.gaeabackend.common.GaeaConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author ndry93
 */
public class GaeaUtil {
    
    public static String generateEmployeeNumber(String phone){
        
        try{
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMM");

            int length = phone.length();
            return phone.substring(length - 4, length) + sdf.format(date);
        }catch(java.lang.StringIndexOutOfBoundsException e){
            throw new RuntimeException(GaeaConstants.ERR_PHONE_MUST_8_DIGITS);
        }
    }
    
    public static DateTime getCurrentDateTime(){
        DateTime dt = new DateTime(new Date());
        return dt;
    }
    
}
