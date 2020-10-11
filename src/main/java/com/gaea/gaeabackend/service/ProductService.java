/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.service;

import com.gaea.gaeabackend.common.GaeaConstants;
import com.gaea.gaeabackend.dto.ProductDTO;
import com.gaea.gaeabackend.dto.ProductPictureDTO;
import com.gaea.gaeabackend.dto.UploadFileResponse;
import com.gaea.gaeabackend.entity.ProductPictures;
import com.gaea.gaeabackend.entity.Products;
import com.gaea.gaeabackend.entity.QProducts;
import com.gaea.gaeabackend.repository.ProductPicturesRepo;
import com.gaea.gaeabackend.repository.ProductsRepo;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author ndry93
 */
@Service
public class ProductService {
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);
    
    @Autowired
    private ProductsRepo productsRepo;
    @Autowired
    private ProductPicturesRepo productPicturesRepo;
    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDTO> getProductList(String filter,
            double spStart, double spEnd, double hppStart, double hppEnd, int page){        
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QProducts product = QProducts.products;
        System.out.println("-----qproduct "+product+ " filter:"+filter);
        if(filter != null && !filter.isEmpty()){
            booleanBuilder.or(product.productName.contains(filter));
            booleanBuilder.or(product.productCode.contains(filter));
            booleanBuilder.or(product.productDescription.contains(filter));
            booleanBuilder.or(product.tags.contains(filter));
            booleanBuilder.or(product.notes.contains(filter));
            booleanBuilder.or(product.productType.contains(filter));
        }  
        if(spStart > 0 && spEnd > 0){
            booleanBuilder.or(product.sellingPrice.between(spStart,spEnd));
        }
        if(hppStart > 0 && hppEnd > 0){
            booleanBuilder.or(product.hpp.between(hppStart,hppEnd));
        }
        
        PageRequest pageable = PageRequest.of(page - 1, GaeaConstants.LIST_FETCH_SIZE,new Sort(Sort.Direction.DESC, "createdDate"));
        return this.convertToDto(Lists.newArrayList(productsRepo.findAll(booleanBuilder.getValue(),pageable)));
//        return this.convertToDto(productsRepo.findAll());
    }
    
    public ProductDTO getProductById(Integer id){
        Optional<Products> resp = productsRepo.findById(id);
        if(resp.isPresent()) return this.convertToDto(resp.get());
        throw new RuntimeException(GaeaConstants.ERR_PRODUCT_NOTFOUND);
    }
    
    public ProductDTO createAndUpdateProduct(ProductDTO productDTO){
        return this.convertToDto(productsRepo.save(this.convertToEntity(productDTO)));
    }
    
    public void deleteProduct(Integer id){
        productsRepo.deleteById(id);
    }
    
    public List<ProductPictureDTO> getProductPictureByProductId(int productId){
        Optional<Products> product = productsRepo.findById(productId);
        if(!product.isPresent()) throw new RuntimeException(GaeaConstants.ERR_PRODUCT_NOTFOUND);
        
        List<ProductPictureDTO> resp = new ArrayList<>();
        for(ProductPictures pp : productPicturesRepo.findAllByProducts(product.get())){
            resp.add(this.convertToProductPictureDTO(pp));
        }
        return resp;
    }
    
    public ProductPictureDTO getProductPictureById(int pictureId){
        Optional<ProductPictures> ppTemp = productPicturesRepo.findById(pictureId);
        if(!ppTemp.isPresent()) throw new RuntimeException(GaeaConstants.ERR_PRODUCT_NOTFOUND);
        return this.convertToProductPictureDTO(ppTemp.get());
    }
    
    public void addProductPicture(int productId,
                UploadFileResponse res, String createdBy, Date createdDate,
                String pictureName,String pictureDescription, boolean isDisplayPicture){
        
        Optional<Products> product = productsRepo.findById(productId);
        if(!product.isPresent()) throw new RuntimeException(GaeaConstants.ERR_PRODUCT_NOTFOUND);
        
        try{
            ProductPictures pp = new ProductPictures();
            pp.setCreatedBy(createdBy);
            pp.setCreatedDate(createdDate);
            pp.setFileName(res.getFileName());
            pp.setFolderName(res.getFolderName());
            pp.setPictureData(null);
            pp.setPictureUrl(res.getFileDownloadUri());
            pp.setPictureName(pictureName);
            pp.setPictureDescription(pictureDescription);
            pp.setEnable(1);
            pp.setProducts(product.get());
            if(isDisplayPicture) pp.getProducts().setDisplayPictureUrl(pp.getPictureUrl());
            
            productPicturesRepo.save(pp);
        }catch(Exception pe){
            throw new RuntimeException(pe.getMessage());
        }
    }
    
    public void deleteProductPicture(int pictureId){
        
        productPicturesRepo.deleteById(pictureId);
    }
    
    private ProductPictureDTO convertToProductPictureDTO(ProductPictures pp){
        ProductPictureDTO ppDTO = new ProductPictureDTO();
        ppDTO.setProductId(pp.getProducts().getId());
        ppDTO.setCreatedBy(pp.getCreatedBy());
        ppDTO.setCreatedDate(pp.getCreatedDate());
        ppDTO.setEnable(pp.getEnable());
        ppDTO.setId(pp.getId());
        ppDTO.setPictureData(pp.getPictureData());
        ppDTO.setPictureDescription(pp.getPictureDescription());
        ppDTO.setPictureName(pp.getPictureName());
        ppDTO.setPictureUrl(pp.getPictureUrl());
        ppDTO.setUpdatedBy(pp.getUpdatedBy());
        ppDTO.setUpdatedDate(pp.getUpdatedDate());
        ppDTO.setFileName(pp.getFileName());
        ppDTO.setFolderName(pp.getFolderName());
        return ppDTO;
    }
    
    private ProductPictures convertToProductPictures(ProductPictureDTO pp){
        ProductPictures ppDTO = new ProductPictures();
        ppDTO.setCreatedBy(pp.getCreatedBy());
        ppDTO.setCreatedDate(pp.getCreatedDate());
        ppDTO.setEnable(pp.getEnable());
        ppDTO.setId(pp.getId());
        ppDTO.setPictureData(pp.getPictureData());
        ppDTO.setPictureDescription(pp.getPictureDescription());
        ppDTO.setPictureName(pp.getPictureName());
        ppDTO.setPictureUrl(pp.getPictureUrl());
        ppDTO.setUpdatedBy(pp.getUpdatedBy());
        ppDTO.setUpdatedDate(pp.getUpdatedDate());
        ppDTO.setFileName(pp.getFileName());
        ppDTO.setFolderName(pp.getFolderName());
        return ppDTO;
    }
    
    private ProductDTO convertToDto(Products product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setHpp(product.getHpp());
        productDTO.setSellingPrice(product.getSellingPrice());
        productDTO.getProductPicturesList().forEach((ppDTO) -> {
            ppDTO.setProductId(product.getId());
        });
        return productDTO;
    }
    
    private List<ProductDTO> convertToDto(List<Products> products){
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Products p : products){
            ProductDTO productDTO = convertToDto(p);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }
    
    private Products convertToEntity(ProductDTO productDTO) {
        Products product = modelMapper.map(productDTO, Products.class);
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setHpp(productDTO.getHpp());
        product.getProductPicturesList().forEach((pp) -> {
            pp.setProducts(product);
        });
        return product;
    }
    
    private List<Products> convertToEntity(List<ProductDTO> productDTOs) {
        List<Products> products = new ArrayList<>();
        for(ProductDTO dto : productDTOs){
            Products p = convertToEntity(dto);
            products.add(p);
        }
        return products;
    }
    
}
