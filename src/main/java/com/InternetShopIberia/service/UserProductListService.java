package com.InternetShopIberia.service;

import com.InternetShopIberia.controller.ProductController;
import com.InternetShopIberia.dto.Sort;
import com.InternetShopIberia.exception.UserProductListNotExistException;
import com.InternetShopIberia.model.Currency;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.repository.UserProductListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserProductListService {

    @Autowired
    private UserProductListRepository userProductListRepository;

    @Autowired
    private Currency currency;

    public UserProductList save(UserProductList userProductList){
        return userProductListRepository.save(userProductList);
    }

    public UserProductList findUserProductListById(long id){
        return userProductListRepository.findById(id).orElseThrow(() -> new UserProductListNotExistException("User Collection with Id = '"+id+"' not exist!"));
    }

    public List<Product> findAllProductsInUserListById(long id){
        return setProductPrice(userProductListRepository.findAllProductsInUserListById(id));
    }

    public List<Product> findAllProductsInUserListById(long id, Pageable pageable){
        return userProductListRepository.findAllProductsInUserListById(id, pageable);
    }

    public List<Product> findAllProductsInUserListByIdSortBy(Long collectionId, Sort sort) {
        return setProductPrice(userProductListRepository.findAllProductsInUserListByIdSortBy(collectionId, sort.getSortBy(), sort.getSortTo()));
    }

    public List<Product> findAllProductsInUserListByIdSortBy(Long collectionId, Pageable pageable) {
        return userProductListRepository.findAllProductsInUserListByIdSortBy(collectionId, pageable);
    }

    public void deleteCollection(UserProductList collection){
        userProductListRepository.deleteById(collection.getId());
    }

    private List<Product> setProductPrice(List<Product> product){
        var cur = java.util.Currency.getInstance(LocaleContextHolder.getLocale()).getCurrencyCode();
        for(var pr: product) {
            pr.setPrice(pr.getOrigPrice().multiply(BigDecimal.valueOf(currency.getRate(cur).getCurRate())));
        }
        return product;
    }
}
