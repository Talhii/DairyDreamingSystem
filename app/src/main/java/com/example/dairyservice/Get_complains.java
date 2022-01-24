package com.example.dairyservice;

public class Get_complains {

    private String From_customer;
    private String About_farm_owner;
    private String Title;
    private String Message;


    public Get_complains() {
    }

    public String getFrom_customer() {
        return From_customer;
    }

    public void setFrom_customer(String from_customer) {
        From_customer = from_customer;
    }

    public String getAbout_farm_owner() {
        return About_farm_owner;
    }

    public void setAbout_farm_owner(String about_farm_owner) {
        About_farm_owner = about_farm_owner;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
