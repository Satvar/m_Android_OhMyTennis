package com.tech.cloudnausor.ohmytennis.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennis.model.BookingData;

public class BokingDataResponseData {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private BookingData data;

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;

    public BookingData getData ()
    {
        return data;
    }

    public void setData (BookingData data)
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
