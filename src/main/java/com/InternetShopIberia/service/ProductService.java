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

    public Product getProduct(ProductDto productDto){
        var product = productRepository.findBypId(productDto.getPId());;
        if(product == null)
            throw new ProductNotExistException("Product with PID=" + productDto.getPId()+" don't exist!");
        else
            return product;
    }

    public List<Product> getAllProductsInCategory(Category category){
        return productRepository.findAllByCategoryId(category.getId());
    }

    public List<Product> getAllProductsInCategoryById(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }
}
