package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentAvaibilityArray {
    @SerializedName("availabilty")
    @Expose
    ArrayList<PaymentAvaibilityData> paymentAvaibilityData;

    public ArrayList<PaymentAvaibilityData> getPaymentAvaibilityData() {
        return paymentAvaibilityData;
    }

    public void setPaymentAvaibilityData(ArrayList<PaymentAvaibilityData> paymentAvaibilityData) {
        this.paymentAvaibilityData = paymentAvaibilityData;
    }
}
