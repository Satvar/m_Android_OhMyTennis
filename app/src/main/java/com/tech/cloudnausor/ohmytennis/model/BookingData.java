package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingData {

    @SerializedName("booking")
    @Expose
    ArrayList<BookingDataDetails> bookingDataDetailsArrayList;

    public ArrayList<BookingDataDetails> getBookingDataDetailsArrayList() {
        return bookingDataDetailsArrayList;
    }

    public void setBookingDataDetailsArrayList(ArrayList<BookingDataDetails> bookingDataDetailsArrayList) {
        this.bookingDataDetailsArrayList = bookingDataDetailsArrayList;
    }
}
