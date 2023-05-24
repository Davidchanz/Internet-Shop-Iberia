package com.InternetShopIberia.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Data
public class Currency {
    Date date;
    String cur_Base;
    ArrayList<CurrencyRate> rates;

    public CurrencyRate getRate(String country){
        if(rates == null)
            return new CurrencyRate("EU", 1.0);
        for(var rate: rates){
            if (rate.getCurAbbreviation().equals(country.toUpperCase())){
                return rate;
            }
        }
        return null;
    }
}
