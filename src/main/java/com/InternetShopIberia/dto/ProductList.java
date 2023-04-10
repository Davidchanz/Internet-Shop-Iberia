package com.InternetShopIberia.dto;

import com.InternetShopIberia.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ProductList {
    @NotEmpty
    @NotNull
    private List<Product> products;
}
