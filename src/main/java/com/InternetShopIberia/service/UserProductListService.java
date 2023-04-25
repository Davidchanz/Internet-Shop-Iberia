package com.InternetShopIberia.service;

import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.repository.UserProductListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProductListService {

    @Autowired
    private UserProductListRepository userProductListRepository;

    public UserProductList save(UserProductList userProductList){
        return userProductListRepository.save(userProductList);
    }
}
