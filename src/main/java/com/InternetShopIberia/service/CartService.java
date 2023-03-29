package com.InternetShopIberia.service;

import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart findCartByUser(User user){
        return cartRepository.findCartByUser(user);
    }

    public Cart addCart(Cart cart){
        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart){
        return cartRepository.save(cart);
    }
}
