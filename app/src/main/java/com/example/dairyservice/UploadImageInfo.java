package com.example.dairyservice;

public class UploadImageInfo {

    String Price;
    String Age;
    String Email;
    String Animal;
    String ImageURL;



    public UploadImageInfo() {
    }

    public UploadImageInfo(String Price, String Age, String Email,String Animal, String ImageURL) {
        this.Price = Price;
        this.Age = Age;
        this.Email = Email;
        this.Animal = Animal;
        this.ImageURL = ImageURL;
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
        return ImageURL;
    }

    public void setImageURL(String ImageURL) {
        this.ImageURL = ImageURL;
    }


}
