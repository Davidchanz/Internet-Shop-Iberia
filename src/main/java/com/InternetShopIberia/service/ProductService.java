package com.InternetShopIberia.service;

import com.InternetShopIberia.dto.Sort;
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

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProductsNameLike(String name){
        return productRepository.searchByNameLike(name);
    }

    public List<Product> getAllProductsNameLikeSortBy(String name, Sort sort) {
        return productRepository.searchByNameLikeSortBy(name, sort.getSortBy(), sort.getSortTo());
    }

    public List<Product> getAllProductsInCategoryByIdSortBy(Long categoryId, Sort sort) {
        return productRepository.findAllByCategoryIdSortBy(categoryId, sort.getSortBy(), sort.getSortTo());
    }
}
