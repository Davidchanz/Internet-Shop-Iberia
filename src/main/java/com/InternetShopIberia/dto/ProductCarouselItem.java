package com.InternetShopIberia.dto;

import com.InternetShopIberia.model.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ProductCarouselItem {
    @NotNull
    @NotEmpty
    private Long categoryId;

    @NotEmpty
    @NotNull
    private String imagePath;
}
