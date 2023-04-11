package com.InternetShopIberia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
public class FilterValue {
    @NotNull
    @NotEmpty
    private String value;

    @NotNull
    @NotEmpty
    private boolean checked;
}
