package com.InternetShopIberia.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Setter
@Getter
public class Product implements Comparable<Product>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION" , length = 10000)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "PID", nullable = false, unique = true)
    private Long pId;

    @OneToOne
    private ProductImage mainImage;

    @Column(name = "ALL_IMAGES")
    @OneToMany
    private List<ProductImage> allImages;

    @OneToOne
    private Category category;

    @Column(name = "DETAIL")
    @OneToMany
    private List<ProductDetail> details;

    @Column(name = "ABOUT", length = 1000)
    private String about;

    @Override
    public int compareTo(Product product) {
        return this.getId().compareTo(product.getId());
    }
}
