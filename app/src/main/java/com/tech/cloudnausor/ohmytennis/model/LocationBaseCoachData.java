package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationBaseCoachData {
    @SerializedName("coach_list")
    @Expose
    ArrayList<LoacationBaseCoachDataDetails> loacationBaseCoachDataDetailsArrayList;
    public ArrayList<LoacationBaseCoachDataDetails> getLoacationBaseCoachDataDetailsArrayList() {
        return loacationBaseCoachDataDetailsArrayList;
    }

    public void setLoacationBaseCoachDataDetailsArrayList(ArrayList<LoacationBaseCoachDataDetails> loacationBaseCoachDataDetailsArrayList) {
        this.loacationBaseCoachDataDetailsArrayList = loacationBaseCoachDataDetailsArrayList;
    }
}
