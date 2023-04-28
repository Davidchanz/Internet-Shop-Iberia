package com.InternetShopIberia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
public class SearchResultItem {
    @NotEmpty
    @NotNull
    private String result;

    @NotEmpty
    @NotNull
    private boolean decor;
}
