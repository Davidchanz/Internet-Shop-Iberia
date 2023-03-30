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
public class Product {
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

    @Column(name = "MAIN_IMAGE")
    private String mainImageSrc;

    @OneToOne
    private Category category;

    @Column(name = "DETAIL")
    @OneToMany
    private List<ProductDetail> details;
}
