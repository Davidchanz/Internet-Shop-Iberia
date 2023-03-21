package com.InternetShopIberia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PRODUCT_DETAIL")
@Setter
@Getter
@NoArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "VALUE")
    @NotNull
    private String value;

    public ProductDetail(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
