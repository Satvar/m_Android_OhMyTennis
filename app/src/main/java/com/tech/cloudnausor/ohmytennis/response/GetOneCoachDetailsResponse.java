package com.tech.cloudnausor.ohmytennis.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennis.model.IndiCourseData;

public class GetOneCoachDetailsResponse {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private IndiCourseData indiCourseData;

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

    public IndiCourseData getGetIndiCoachDetailsModel() {
        return indiCourseData;
    }

    public void setGetIndiCoachDetailsModel(IndiCourseData indiCourseData) {
        this.indiCourseData = indiCourseData;
    }

}
