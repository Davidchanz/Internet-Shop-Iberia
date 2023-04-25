package com.InternetShopIberia.service;

import com.InternetShopIberia.controller.ProductController;
import com.InternetShopIberia.dto.Sort;
import com.InternetShopIberia.exception.UserProductListNotExistException;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.repository.UserProductListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProductListService {

    @Autowired
    private UserProductListRepository userProductListRepository;

    public UserProductList save(UserProductList userProductList){
        return userProductListRepository.save(userProductList);
    }

    public UserProductList findUserProductListById(long id){
        return userProductListRepository.findById(id).orElseThrow(() -> new UserProductListNotExistException("User Collection with Id = '"+id+"' not exist!"));
    }

    public List<Product> findAllProductsInUserListByIdSortBy(Long collectionId, Sort sort) {
        return userProductListRepository.findAllProductsInUserListByIdSortBy(collectionId, sort.getSortBy(), sort.getSortTo());
    }
}
