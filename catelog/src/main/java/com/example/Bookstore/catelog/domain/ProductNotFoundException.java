package com.example.Bookstore.catelog.domain;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String msg){
        super(msg);
    }

    public static ProductNotFoundException forCode(String code){
        return new ProductNotFoundException("Product with code " + code + " not found");
    }
}
