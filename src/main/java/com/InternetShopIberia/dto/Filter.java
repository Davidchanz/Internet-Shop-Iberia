package com.InternetShopIberia.dto;

import com.InternetShopIberia.model.ProductDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Filter {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private List<String> values;
}
