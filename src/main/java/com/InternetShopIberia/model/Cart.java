package com.InternetShopIberia.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "USER_CART")
@Setter
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @OneToMany
    private List<CartProduct> products;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addProduct(CartProduct product){
        this.products.add(product);
    }

    public int getQuantity(){
        int quantity = 0;
        for (var product: getProducts()){
            quantity += product.getQuantity();
        }
        return quantity;
    }
}
