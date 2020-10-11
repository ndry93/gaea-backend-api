/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ndry93
 */
@Data
@NoArgsConstructor
public class ProductPictureDTO {
    
    private Integer id;
    private String pictureName;
    private String pictureDescription;
    private String pictureUrl;
    private byte[] pictureData;
    private String fileName;
    private String folderName;
    private int enable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private Integer productId;
}
