package com.tech.cloudnausor.ohmytennis.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteClubDTO {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("message")
    @Expose
    private String message;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
