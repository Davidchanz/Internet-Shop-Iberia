package com.InternetShopIberia.dto;

import com.InternetShopIberia.model.UserProductList;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CollectionDto {
    @NotEmpty
    @NotNull
    private UserProductList collection;

    @NotEmpty
    @NotNull
    private boolean productExist;
}
