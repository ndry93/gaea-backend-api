/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.controller;

import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.dto.ProductDTO;
import com.gaea.gaeabackend.dto.ProductPictureDTO;
import com.gaea.gaeabackend.dto.ResponseDTO;
import com.gaea.gaeabackend.dto.UploadFileResponse;
import com.gaea.gaeabackend.request.ProductRequest;
import com.gaea.gaeabackend.service.ProductService;
import com.gaea.gaeabackend.util.CustomResponseUtil;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ndry93
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private ProductService productService;
    @Autowired
    private FilesController filesController;
    
    @GetMapping("")
    public ResponseEntity<ResponseDTO> getProductList(
            @RequestParam( value = "filter", required = false) String filter,
            @RequestParam( value = "spStart", required = false) Double spStart,
            @RequestParam( value = "spEnd", required = false) Double spEnd,
            @RequestParam( value = "hppStart", required = false) Double hppStart,
            @RequestParam( value = "hppEnd", required = false) Double hppEnd,
            @RequestParam( value = "page") int page) {
      return CustomResponseUtil
          .wrapList(() -> productService.getProductList(filter,
                  spStart == null ? 0 : spStart,
                  spEnd == null ? 0 : spEnd,
                  hppStart == null ? 0 : hppStart,
                  hppEnd  == null ? 0 : hppEnd,
                  page
            )
          );
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDTO> getProductById(
            @PathVariable("productId") @NotBlank Integer productId) {
      return CustomResponseUtil
          .wrap(() -> productService.getProductById(productId));
    }
    
    @PostMapping("")
    public ResponseEntity<ResponseDTO> createProduct(@RequestBody @Valid ProductRequest productRequest) {
      try{
          String createdBy = GaeaConstants.CREATED_BY;
          
          ProductDTO newProduct = new ProductDTO();
          newProduct.setProductCode(productRequest.getProductCode());
          newProduct.setProductDescription(productRequest.getProductDescription());
          newProduct.setProductName(productRequest.getProductName());
          newProduct.setProductType(productRequest.getProductType());
          newProduct.setNotes(productRequest.getNotes());
          newProduct.setSizeLower(productRequest.getSizeLower());
          newProduct.setSizeUpper(productRequest.getSizeUpper());
          newProduct.setCreatedBy(createdBy);
          newProduct.setCreatedDate(new Date());
          newProduct.setEnable(1);
          newProduct.setDisplayPictureUrl(productRequest.getDisplayPictureUrl());
          newProduct.setTags(productRequest.getTags());
          newProduct.setSellingPrice(productRequest.getSellingPrice());
          newProduct.setHpp(productRequest.getHpp());
          
          return CustomResponseUtil.wrap(() -> productService.createAndUpdateProduct(newProduct));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<ResponseDTO> updateProduct
        (@PathVariable("productId") @NotBlank Integer productId, @RequestBody @Valid ProductRequest productRequest) {
      try{
          ProductDTO product = productService.getProductById(productId);
          if(product == null) throw new RuntimeException(GaeaConstants.ERR_PRODUCT_NOTFOUND);
          
          String updatedBy = GaeaConstants.CREATED_BY;
          product.setProductCode(productRequest.getProductCode());
          product.setProductDescription(productRequest.getProductDescription());
          product.setProductName(productRequest.getProductName());
          product.setProductType(productRequest.getProductType());
          product.setNotes(productRequest.getNotes());
          product.setEnable(productRequest.getEnable());
          product.setSizeLower(productRequest.getSizeLower());
          product.setSizeUpper(productRequest.getSizeUpper());
          product.setUpdatedBy(updatedBy);
          product.setUpdatedDate(new Date());
          product.setDisplayPictureUrl(productRequest.getDisplayPictureUrl());
          product.setTags(productRequest.getTags());
          product.setSellingPrice(productRequest.getSellingPrice());
          product.setHpp(productRequest.getHpp());
          
          productService.createAndUpdateProduct(product);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
        
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDTO> deleteProduct
        (@PathVariable("productId") @NotBlank Integer productId) {
      try{
          productService.deleteProduct(productId);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }catch(EmptyResultDataAccessException empty){
          empty.getMessage();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", "Data not found", "[]"));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
    
    @GetMapping("/{productId}/photos")
    public ResponseEntity<ResponseDTO> getProductPictures
        (@PathVariable("productId") @NotBlank Integer productId){
            
        return CustomResponseUtil
          .wrapList(() -> productService.getProductPictureByProductId(productId)
          );
    }
    
            
    @PostMapping("/{productId}/photos") 
    public ResponseEntity<ResponseDTO> addProductPicture
        (@PathVariable("productId") @NotBlank Integer productId,
                @RequestParam("pictureName") String pictureName,
                @RequestParam("pictureDescription") String pictureDescription,
                @RequestParam("file") MultipartFile file,
                @RequestParam("isDisplayPicture") Boolean isDisplayPicture) {
      try{
          String createdBy = GaeaConstants.CREATED_BY;
          Date createdDate = new Date();
          
          
          UploadFileResponse res = filesController.uploadFile(String.valueOf(productId), file);
          productService.addProductPicture(
                  productId, res, createdBy, createdDate,pictureName,pictureDescription,isDisplayPicture);
          System.out.println("-----isDisplayPicture "+isDisplayPicture);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "success", "[]"));
      }catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
      }
    }
    
    @DeleteMapping("/photos/{pictureId}")
    public ResponseEntity<ResponseDTO> deleteProductPicture(@PathVariable("pictureId") @NotBlank Integer id) {
        try {
            ProductPictureDTO ppDTO = productService.getProductPictureById(id);
            
            boolean isDeleted = filesController.deleteFile(ppDTO.getFolderName(), ppDTO.getFileName());
            productService.deleteProductPicture(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "Success", "[]"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("1", e.getMessage(), "[]"));
        }
    }
}
