package com.example.dairyservice;

public class Token {

    private String token_number;
    private String customer_name;
    private String price;

    public Token(String token_number, String customer_name, String price) {
        this.token_number = token_number;
        this.customer_name = customer_name;
        this.price = price;
    }

    public String getToken_number() {
        return token_number;
    }

    public void setToken_number(String token_number) {
        this.token_number = token_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
