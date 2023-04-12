package com.InternetShopIberia.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class SortList {
    @NotNull
    @NotEmpty
    private List<Sort> sorts;

    @NotNull
    @NotEmpty
    private String sortTo;
}
