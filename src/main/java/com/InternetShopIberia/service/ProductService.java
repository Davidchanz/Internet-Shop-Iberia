package com.InternetShopIberia.service;

import com.InternetShopIberia.dto.Sort;
import com.InternetShopIberia.exception.ProductNotExistException;
import com.InternetShopIberia.model.Category;
import com.InternetShopIberia.model.Currency;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Currency currency;

    public Product getProductById(Long id){
        return setProductPrice(productRepository.findById(id).orElseThrow(() -> new ProductNotExistException("Product with Id=" + id+" don't exist!")));
    }

    public List<Product> getAllProductsInCategoryById(Long categoryId) {
        return setProductPrice(productRepository.findAllByCategoryId(categoryId));
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProductsNameLike(String name){
        return setProductPrice(productRepository.searchByNameLike(name));
    }

    public List<Product> getAllProductsNameLikeSortBy(String name, Sort sort) {
        return setProductPrice(productRepository.searchByNameLikeSortBy(name, sort.getSortBy(), sort.getSortTo()));
    }

    public List<Product> getAllProductsInCategoryByIdSortBy(Long categoryId, Sort sort) {
        return setProductPrice(productRepository.findAllByCategoryIdSortBy(categoryId, sort.getSortBy(), sort.getSortTo()));
    }

    private Product setProductPrice(Product product){
        var cur = java.util.Currency.getInstance(LocaleContextHolder.getLocale()).getCurrencyCode();
        product.setPrice(product.getOrigPrice().multiply(BigDecimal.valueOf(currency.getRate(cur).getCurRate())));
        return product;
    }

    private List<Product> setProductPrice(List<Product> product){
        var cur = java.util.Currency.getInstance(LocaleContextHolder.getLocale()).getCurrencyCode();
        for(var pr: product) {
            pr.setPrice(pr.getOrigPrice().multiply(BigDecimal.valueOf(currency.getRate(cur).getCurRate())));
        }
        return product;
    }
}
