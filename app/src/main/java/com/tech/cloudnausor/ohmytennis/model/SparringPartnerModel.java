package com.tech.cloudnausor.ohmytennis.model;

public class SparringPartnerModel {

    String name;
    String qualification;
    String rating;
    String describtion;

    public SparringPartnerModel() {
    }

    public SparringPartnerModel(String name, String qualification, String rating, String describtion) {
        this.name = name;
        this.qualification = qualification;
        this.rating = rating;
        this.describtion = describtion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
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
