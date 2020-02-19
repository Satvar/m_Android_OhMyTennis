package com.tech.cloudnausor.ohmytennis.model;

public class ListPartnerModel {

    String name;
    String rating;
    String describtion;

    public ListPartnerModel() {
    }

    public ListPartnerModel(String name, String rating, String describtion) {
        this.name = name;
        this.rating = rating;
        this.describtion = describtion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

}
