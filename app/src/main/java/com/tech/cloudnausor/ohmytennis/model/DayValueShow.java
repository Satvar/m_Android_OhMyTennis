package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayValueShow {
    @SerializedName("Day")
    @Expose
    String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
