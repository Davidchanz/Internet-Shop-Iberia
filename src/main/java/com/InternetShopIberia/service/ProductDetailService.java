package com.InternetShopIberia.service;

import com.InternetShopIberia.model.ProductDetail;
import com.InternetShopIberia.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;

    public void addProductDetail(ProductDetail productDetail){
        productDetailRepository.save(productDetail);
    }
}
