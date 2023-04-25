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
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(nullable = false)
    @ManyToMany
    @JoinColumn
    private List<Product> products;
}
