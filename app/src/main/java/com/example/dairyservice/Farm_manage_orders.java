package com.example.dairyservice;

public class Farm_manage_orders {

    private String Farm_owner_email;
    private String Customer;
    private String Amount;
    private String Total_Price;
    private String Delivery;
    private String Animal;
    private String latitude;
    private String longitude;
    private String Token_number;

    public Farm_manage_orders(String farm_owner_email, String customer, String amount, String total_Price, String delivery, String animal, String latitude, String longitude, String token_number) {
        Farm_owner_email = farm_owner_email;
        Customer = customer;
        Amount = amount;
        Total_Price = total_Price;
        Delivery = delivery;
        Animal = animal;
        this.latitude = latitude;
        this.longitude = longitude;
        Token_number = token_number;
    }

    public String getToken_number() {
        return Token_number;
    }

    public void setToken_number(String token_number) {
        Token_number = token_number;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Farm_manage_orders() {

    }

    public String getTotal_Price() {
        return Total_Price;
    }

    public void setTotal_Price(String total_Price) {
        Total_Price = total_Price;
    }

    public String getDelivery() {
        return Delivery;
    }

    public void setDelivery(String delivery) {
        Delivery = delivery;
    }

    public String getFarm_owner_email() {
        return Farm_owner_email;
    }

    public void setFarm_owner_email(String farm_owner_email) {
        Farm_owner_email = farm_owner_email;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAnimal() {
        return Animal;
    }

    public void setAnimal(String animal) {
        Animal = animal;
    }
}
