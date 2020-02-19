package com.tech.cloudnausor.ohmytennis.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennis.model.CalenderModel;

import java.util.ArrayList;

public class CalenderResponseData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("calendarList")
    @Expose
    private ArrayList<CalenderModel> calendarList;

    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setStatus( String status ) {
        this.status = status;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public ArrayList<CalenderModel> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(ArrayList<CalenderModel> calendarList) {
        this.calendarList = calendarList;
    }
}
