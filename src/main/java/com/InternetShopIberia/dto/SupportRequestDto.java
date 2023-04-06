package com.InternetShopIberia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SupportRequestDto {
    private String subject;
    private String email;
    private String message;
}
