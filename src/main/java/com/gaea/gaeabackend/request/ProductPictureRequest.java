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
public class ProductPictureRequest {
    private String pictureName;
    private String pictureDescription;
}
