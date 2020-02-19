package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookArray {
    @SerializedName("bookArray")
    ArrayList<RequestToBook> requestToBooks;

    public ArrayList<RequestToBook> getRequestToBooks() {
        return requestToBooks;
    }

    public void setRequestToBooks(ArrayList<RequestToBook> requestToBooks) {
        this.requestToBooks = requestToBooks;
    }

}
