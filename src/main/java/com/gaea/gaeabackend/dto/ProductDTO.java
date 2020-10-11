/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ndry93
 */
@Data
@NoArgsConstructor
public class ProductDTO {
    
    private Integer id;
    private String productCode;
    private String productName;
    private String productDescription;
    private String productType;
    private String sizeLower;
    private String sizeUpper;
    private String notes;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
//    private List<ProductHPPDTO> productHppList;
    private List<ProductPictureDTO> productPicturesList = new ArrayList<>();
    private String displayPictureUrl;
    private String tags;
    private double sellingPrice;
    private double hpp;
}
