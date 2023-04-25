package com.InternetShopIberia.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER_COLLECTION")
@Setter
@Getter
public class UserProductList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    private String name;

    @Column(nullable = false)
    @OneToMany
    private List<CartProduct> products;
}
