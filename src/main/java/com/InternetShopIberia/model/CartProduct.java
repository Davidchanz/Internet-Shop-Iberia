package com.InternetShopIberia.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CART_PRODUCT")
@Setter
@Getter
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal origPrice;

    @Transient
    private BigDecimal price;

    @OneToOne
    private ProductImage mainImage;

    @Column(name = "QUANTITY")
    private int quantity;
}
