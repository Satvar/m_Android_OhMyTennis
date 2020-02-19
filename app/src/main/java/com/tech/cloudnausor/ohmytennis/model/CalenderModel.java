package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalenderModel {
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("allDay")
    @Expose
    private boolean allDay;
    @SerializedName("editable")
    @Expose
    private boolean editable;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("title")
    @Expose
    private String title;


    // Getter Methods

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public boolean getAllDay() {
        return allDay;
    }

    public boolean getEditable() {
        return editable;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    // Setter Methods

    public void setStart( String start ) {
        this.start = start;
    }

    public void setEnd( String end ) {
        this.end = end;
    }

    public void setAllDay( boolean allDay ) {
        this.allDay = allDay;
    }

    public void setEditable( boolean editable ) {
        this.editable = editable;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    public void setTitle( String title ) {
        this.title = title;
    }
}
