package com.InternetShopIberia.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserPurchase {
    @NotEmpty
    @NotNull
    private String fullName;

    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String address;

    @NotEmpty
    @NotNull
    private String zipCode;

    @NotEmpty
    @NotNull
    private String nameOnCard;

    @NotEmpty
    @NotNull
    private String cardNumber;

    @NotEmpty
    @NotNull
    private String expMonth;

    @NotEmpty
    @NotNull
    private String expYear;

    @NotEmpty
    @NotNull
    private String CVV;
}
