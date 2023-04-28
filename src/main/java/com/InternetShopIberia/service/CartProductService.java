package com.InternetShopIberia.service;

import com.InternetShopIberia.model.CartProduct;
import com.InternetShopIberia.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProductService {
    @Autowired
    private CartProductRepository cartProductRepository;

    public CartProduct addCartProduct(CartProduct product){
        return cartProductRepository.save(product);
    }

    public CartProduct updateCartProduct(CartProduct cartProduct) {
        return cartProductRepository.save(cartProduct);
    }

    public void removeCartProduct(CartProduct product){cartProductRepository.delete(product);}
}
