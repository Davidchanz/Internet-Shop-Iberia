package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public Cart findCartByUser(User user);
}
