package com.tech.cloudnausor.ohmytennis.model.gettimeslot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetAvaibilityTime {

    @SerializedName("SlotId")
    @Expose
    private String SlotId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("Availability")
    @Expose
    private String Availability;

    public SetAvaibilityTime(String slotId, String description, String availability) {
        SlotId = slotId;
        this.description = description;
        Availability = availability;
    }

// Getter Methods

    public String getSlotId() {
        return SlotId;
    }

    public String getDescription() {
        return description;
    }

    public String getAvailability() {
        return Availability;
    }

    // Setter Methods 

    public void setSlotId( String SlotId ) {
        this.SlotId = SlotId;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public void setAvailability( String Availability ) {
        this.Availability = Availability;
    }
}
