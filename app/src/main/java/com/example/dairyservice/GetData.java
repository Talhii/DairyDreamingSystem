package com.example.dairyservice;

public class GetData {
    String Price;
    String Age;
    String Email;
    String Animal;
    String imageURL;


    public GetData() {
    }

    public GetData(String Price, String Age, String Email,String Animal, String imageURL) {
        this.Price = Price;
        this.Age = Age;
        this.Email = Email;
        this.Animal = Animal;
        this.imageURL = imageURL;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAnimal() {
        return Animal;
    }

    public void setAnimal(String Animal) {
        this.Animal = Animal;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
