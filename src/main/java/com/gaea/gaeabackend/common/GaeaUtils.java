/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ndry93
 */
public class GaeaUtils {
    
    public static String getCurrentMMYYinString(){
        Date now = new Date();
        return getSimpleDateFormatMMYY().format(now);
    }
    
    public static SimpleDateFormat getSimpleDateFormatMMYY(){
        return new SimpleDateFormat("MMyy");
    }
    
}
