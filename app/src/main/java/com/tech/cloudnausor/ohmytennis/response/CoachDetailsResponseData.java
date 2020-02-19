package com.tech.cloudnausor.ohmytennis.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennis.model.CoachDetailsModel;

import java.util.ArrayList;

public class CoachDetailsResponseData {
    @SerializedName("isSuccess")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private coachdata coachdata;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public coachdata getData() {
        return coachdata;
    }

    // Setter Methods

    public void setStatus( String status ) {
        this.status = status;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public void setData( coachdata dataObject ) {
        this.coachdata = dataObject;
    }
    public class coachdata{

        @SerializedName("coach_list")
        @Expose
        ArrayList<CoachDetailsModel> coachDetailsModel;

        public ArrayList<CoachDetailsModel> getCoachDetailsModel() {
            return coachDetailsModel;
        }

        public void setCoachDetailsModel(ArrayList<CoachDetailsModel> coachDetailsModel) {
            this.coachDetailsModel = coachDetailsModel;
        }
    }

}