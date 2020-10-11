/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ndry93
 */
@Data
@NoArgsConstructor
public class ProductRequest {
    private String productCode;
    private String productDescription;
    private String productName;
    private String productType;
    private String notes;
    private String sizeLower;
    private String sizeUpper;
    private int enable;
    private String displayPictureUrl;
    private String tags;
    private double sellingPrice;
    private double hpp;
}
