package com.tech.cloudnausor.ohmytennis.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennis.model.LoginModel;
import com.tech.cloudnausor.ohmytennis.model.PaymentAvaibilityArray;

public class PaymentResponseData {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private PaymentAvaibilityArray data;

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;

    public PaymentAvaibilityArray getData ()
    {
        return data;
    }

    public void setData (PaymentAvaibilityArray data)
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
