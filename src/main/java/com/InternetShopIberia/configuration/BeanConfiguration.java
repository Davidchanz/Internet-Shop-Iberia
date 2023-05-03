package com.InternetShopIberia.configuration;

import com.InternetShopIberia.model.Currency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public Currency currentCurrency(){
        return new Currency();
    }
}
