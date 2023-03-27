package com.InternetShopIberia.service;

import com.InternetShopIberia.dto.ProductDto;
import com.InternetShopIberia.exception.ProductNotExistException;
import com.InternetShopIberia.model.Category;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotExistException("Product with Id=" + id+" don't exist!"));
    }

    public List<Product> getAllProductsInCategoryById(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> getAllProductsByName(String name){
        return productRepository.searchByNameLike(name);
    }
}
