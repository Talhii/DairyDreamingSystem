package com.example.dairyservice;

public class Farm_animal_orders {

    private String Farm_owner_email;
    private String Customer;
    private String Age;
    private String Price;
    private String Delivery;
    private String Animal;
    private String latitude;
    private String longitude;
    private String Token_number;

    public Farm_animal_orders() {
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

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDelivery() {
        return Delivery;
    }

    public void setDelivery(String delivery) {
        Delivery = delivery;
    }

    public String getAnimal() {
        return Animal;
    }

    public void setAnimal(String animal) {
        Animal = animal;
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

    public String getToken_number() {
        return Token_number;
    }

    public void setToken_number(String token_number) {
        Token_number = token_number;
    }
}
