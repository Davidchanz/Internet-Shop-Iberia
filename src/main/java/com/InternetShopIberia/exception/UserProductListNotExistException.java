package com.InternetShopIberia.exception;

public class UserProductListNotExistException extends RuntimeException{
    public UserProductListNotExistException(String message){
        super(message);
    }
}
