package com.tech.cloudnausor.ohmytennis.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennis.model.AllCoachDetails;
import com.tech.cloudnausor.ohmytennis.model.LocationBaseCoachData;
import com.tech.cloudnausor.ohmytennis.model.postalCodeList;

import java.util.ArrayList;

public class AllCoachDetailsResponseData {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private LocationBaseCoachData data;
    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;

    public LocationBaseCoachData getData ()
    {
        return data;
    }

    public void setData (LocationBaseCoachData data)
    {
        this.data = data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getIsSuccess ()
    {
        return isSuccess;
    }

    public void setIsSuccess (String isSuccess)
    {
        this.isSuccess = isSuccess;
    }
}
