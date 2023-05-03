package com.InternetShopIberia.service;

import com.InternetShopIberia.model.Currency;
import com.InternetShopIberia.model.CurrencyRate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class CurrencyService {
    @Autowired
    private Currency model;

    public Currency getCurrencyRate(String message) throws IOException, ParseException {
        URL url = new URL("https://api.exchangerate.host/latest");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()){
            result += scanner.nextLine();
        }
        JSONObject object = new JSONObject(result);

        model.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(object.getString("date")));
        //model.setDate(new Date(object.getInt("timestamp")));
        model.setCur_Base(object.getString("base"));
        var rates = object.getJSONObject("rates");
        ArrayList<CurrencyRate> currencyRates = new ArrayList<>();
        if(message.toUpperCase().equals("ALL")){
            rates.keySet().forEach(key -> {
                currencyRates.add(new CurrencyRate(key, rates.getDouble(key)));
            });
        }else {
            rates.keySet().forEach(key -> {
                if(key.equals(message.toUpperCase()))
                    currencyRates.add(new CurrencyRate(key, rates.getDouble(key)));
            });
        }
        model.setRates(currencyRates);

        return model;

    }
}