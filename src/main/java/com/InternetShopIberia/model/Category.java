package com.InternetShopIberia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
@Setter
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SUB_CATEGORIES")
    @OneToMany
    @NotNull
    private List<Category> subCategories;

    public Category(String title, String description, List<Category> subCategories) {
        this.title = title;
        this.description = description;
        this.subCategories = subCategories;
    }
}
