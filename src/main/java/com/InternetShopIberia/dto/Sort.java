package com.InternetShopIberia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Sort {
    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String sortBy;

    @NotEmpty
    @NotNull
    private String sortTo;

    @NotEmpty
    @NotNull
    private boolean selected;
}
