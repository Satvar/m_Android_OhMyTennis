package com.tech.cloudnausor.ohmytennis.model;

import java.util.ArrayList;

public class SelectionTimeDetails {
    public String dateTitte;
    public ArrayList<String> arraySelectionTime;

    public SelectionTimeDetails(String dateTitte, ArrayList<String> arraySelectionTime) {
        this.dateTitte = dateTitte;
        this.arraySelectionTime = arraySelectionTime;
    }

    public String getDateTitte() {
        return dateTitte;
    }

    public void setDateTitte(String dateTitte) {
        this.dateTitte = dateTitte;
    }

    public ArrayList<String> getArraySelectionTime() {
        return arraySelectionTime;
    }

    public void setArraySelectionTime(ArrayList<String> arraySelectionTime) {
        this.arraySelectionTime = arraySelectionTime;
    }
}
