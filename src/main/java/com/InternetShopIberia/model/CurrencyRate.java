package com.InternetShopIberia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CurrencyRate {
    private String curAbbreviation;
    private Double curRate;

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "curAbbreviation='" + curAbbreviation + '\'' +
                ", curRate=" + curRate +
                '}';
    }
}
