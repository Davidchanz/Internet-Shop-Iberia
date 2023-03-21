package com.InternetShopIberia.exception;

public class ProductNotExistException extends RuntimeException{
    public ProductNotExistException(String message) {
        super(message);
    }
}
