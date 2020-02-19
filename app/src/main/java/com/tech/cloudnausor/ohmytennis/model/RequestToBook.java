package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.SerializedName;

public class RequestToBook {
    @SerializedName("P_CoachId")
    private String P_CoachId;
    @SerializedName("P_CourseId")
    private String P_CourseId;
    @SerializedName("P_Date")
    private String P_Date;
    @SerializedName("P_Hour")
    private String P_Hour;
    @SerializedName("P_UserId")
    private String P_UserId;
    @SerializedName("P_Amount")
    private String P_Amount;
    @SerializedName("P_Remarks")
    private String P_Remarks;

    public RequestToBook(String p_CoachId, String p_CourseId, String p_Date, String p_Hour, String p_UserId, String p_Amount,String p_Remarks) {
        P_CoachId = p_CoachId;
        P_CourseId = p_CourseId;
        P_Date = p_Date;
        P_Hour = p_Hour;
        P_UserId = p_UserId;
        P_Amount = p_Amount;
        P_Remarks = p_Remarks;
    }

// Getter Methods

    public String getP_Remarks() {
        return P_Remarks;
    }

    public void setP_Remarks(String p_Remarks) {
        P_Remarks = p_Remarks;
    }

    public String getP_CoachId() {
        return P_CoachId;
    }

    public String getP_CourseId() {
        return P_CourseId;
    }

    public String getP_Date() {
        return P_Date;
    }

    public String getP_Hour() {
        return P_Hour;
    }

    public String getP_UserId() {
        return P_UserId;
    }

    public String getP_Amount() {
        return P_Amount;
    }

    // Setter Methods

    public void setP_CoachId( String P_CoachId ) {
        this.P_CoachId = P_CoachId;
    }

    public void setP_CourseId( String P_CourseId ) {
        this.P_CourseId = P_CourseId;
    }

    public void setP_Date( String P_Date ) {
        this.P_Date = P_Date;
    }

    public void setP_Hour( String P_Hour ) {
        this.P_Hour = P_Hour;
    }

    public void setP_UserId( String P_UserId ) {
        this.P_UserId = P_UserId;
    }

    public void setP_Amount( String P_Amount ) {
        this.P_Amount = P_Amount;
    }
}
