package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoacationBaseCoachDataDetails {

    @SerializedName("Coach_ID")
    @Expose
    private String Coach_ID;
    @SerializedName("Coach_Fname")
    @Expose
    private String Coach_Fname;
    @SerializedName("Coach_Lname")
    @Expose
    private String Coach_Lname;
    @SerializedName("Coach_Email")
    @Expose
    private String Coach_Email;
    @SerializedName("Coach_Phone")
    @Expose
    private String Coach_Phone;
    @SerializedName("Coach_Password")
    @Expose
    private String Coach_Password;
    @SerializedName("Coach_Price")
    @Expose
    private String Coach_Price;
    @SerializedName("Coach_PriceX10")
    @Expose
    private String Coach_PriceX10;
    @SerializedName("Coach_Aviailability")
    @Expose
    private String Coach_Aviailability = null;
    @SerializedName("Coach_Rayon")
    @Expose
    private String Coach_Rayon;
    @SerializedName("Coach_Services")
    @Expose
    ArrayList<String> Coach_Services = new ArrayList<String>();
    @SerializedName("Active_City")
    @Expose
    private String Active_City = null;
    @SerializedName("Coach_transport")
    @Expose
    private String Coach_transport;
    @SerializedName("Coach_City")
    @Expose
    private String Coach_City = null;
    @SerializedName("Coach_Image")
    @Expose
    private String Coach_Image;
    @SerializedName("Coach_Resume")
    @Expose
    private String Coach_Resume;
    @SerializedName("Coach_Status")
    @Expose
    private String Coach_Status = null;
    @SerializedName("Coach_Description")
    @Expose
    private String Coach_Description;
    @SerializedName("Active_Status")
    @Expose
    private String Active_Status;
    @SerializedName("Availability_StartDate")
    @Expose
    private String Availability_StartDate = null;
    @SerializedName("Availability_EndDate")
    @Expose
    private String Availability_EndDate = null;
    @SerializedName("Coach_payment_type")
    @Expose
    private String Coach_payment_type;
    @SerializedName("Coach_Bank_Name")
    @Expose
    private String Coach_Bank_Name;
    @SerializedName("Branch_Code")
    @Expose
    private String Branch_Code;
    @SerializedName("Coach_Bank_ACCNum")
    @Expose
    private String Coach_Bank_ACCNum;
    @SerializedName("Coach_Bank_City")
    @Expose
    private String Coach_Bank_City;
    @SerializedName("User_type")
    @Expose
    private String User_type;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("address")
    @Expose
    private String address = null;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("cityId")
    @Expose
    private String cityId;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken = null;
    @SerializedName("deviceName")
    @Expose
    private String deviceName = null;
    @SerializedName("deviceVersion")
    @Expose
    private String deviceVersion = null;
    @SerializedName("latitude")
    @Expose
    private String latitude = null;
    @SerializedName("longitude")
    @Expose
    private String longitude = null;
    @SerializedName("roleId")
    @Expose
    private String roleId;
    @SerializedName("isOtpVerified")
    @Expose
    private String isOtpVerified;
    @SerializedName("isEmailConfirmed")
    @Expose
    private String isEmailConfirmed;
    @SerializedName("isActive")
    @Expose
    private String isActive;
    @SerializedName("hashKey")
    @Expose
    private String hashKey = null;


    // Getter Methods 

    public String getCoach_ID() {
        return Coach_ID;
    }

    public String getCoach_Fname() {
        return Coach_Fname;
    }

    public String getCoach_Lname() {
        return Coach_Lname;
    }

    public String getCoach_Email() {
        return Coach_Email;
    }

    public String getCoach_Phone() {
        return Coach_Phone;
    }

    public String getCoach_Password() {
        return Coach_Password;
    }

    public String getCoach_Price() {
        return Coach_Price;
    }

    public String getCoach_PriceX10() {
        return Coach_PriceX10;
    }

    public String getCoach_Aviailability() {
        return Coach_Aviailability;
    }

    public String getCoach_Rayon() {
        return Coach_Rayon;
    }

    public String getActive_City() {
        return Active_City;
    }

    public String getCoach_transport() {
        return Coach_transport;
    }

    public String getCoach_City() {
        return Coach_City;
    }

    public String getCoach_Image() {
        return Coach_Image;
    }

    public String getCoach_Resume() {
        return Coach_Resume;
    }

    public String getCoach_Status() {
        return Coach_Status;
    }
    public ArrayList<String> getCoach_Services() {
        return Coach_Services;
    }

    public void setCoach_Services(ArrayList<String> coach_Services) {
        Coach_Services = coach_Services;
    }
    public String getCoach_Description() {
        return Coach_Description;
    }

    public String getActive_Status() {
        return Active_Status;
    }

    public String getAvailability_StartDate() {
        return Availability_StartDate;
    }

    public String getAvailability_EndDate() {
        return Availability_EndDate;
    }

    public String getCoach_payment_type() {
        return Coach_payment_type;
    }

    public String getCoach_Bank_Name() {
        return Coach_Bank_Name;
    }

    public String getBranch_Code() {
        return Branch_Code;
    }

    public String getCoach_Bank_ACCNum() {
        return Coach_Bank_ACCNum;
    }

    public String getCoach_Bank_City() {
        return Coach_Bank_City;
    }

    public String getUser_type() {
        return User_type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCityId() {
        return cityId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getIsOtpVerified() {
        return isOtpVerified;
    }

    public String getIsEmailConfirmed() {
        return isEmailConfirmed;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getHashKey() {
        return hashKey;
    }

    // Setter Methods 

    public void setCoach_ID( String Coach_ID ) {
        this.Coach_ID = Coach_ID;
    }

    public void setCoach_Fname( String Coach_Fname ) {
        this.Coach_Fname = Coach_Fname;
    }

    public void setCoach_Lname( String Coach_Lname ) {
        this.Coach_Lname = Coach_Lname;
    }

    public void setCoach_Email( String Coach_Email ) {
        this.Coach_Email = Coach_Email;
    }

    public void setCoach_Phone( String Coach_Phone ) {
        this.Coach_Phone = Coach_Phone;
    }

    public void setCoach_Password( String Coach_Password ) {
        this.Coach_Password = Coach_Password;
    }

    public void setCoach_Price( String Coach_Price ) {
        this.Coach_Price = Coach_Price;
    }

    public void setCoach_PriceX10( String Coach_PriceX10 ) {
        this.Coach_PriceX10 = Coach_PriceX10;
    }

    public void setCoach_Aviailability( String Coach_Aviailability ) {
        this.Coach_Aviailability = Coach_Aviailability;
    }

    public void setCoach_Rayon( String Coach_Rayon ) {
        this.Coach_Rayon = Coach_Rayon;
    }

    public void setActive_City( String Active_City ) {
        this.Active_City = Active_City;
    }

    public void setCoach_transport( String Coach_transport ) {
        this.Coach_transport = Coach_transport;
    }

    public void setCoach_City( String Coach_City ) {
        this.Coach_City = Coach_City;
    }

    public void setCoach_Image( String Coach_Image ) {
        this.Coach_Image = Coach_Image;
    }

    public void setCoach_Resume( String Coach_Resume ) {
        this.Coach_Resume = Coach_Resume;
    }

    public void setCoach_Status( String Coach_Status ) {
        this.Coach_Status = Coach_Status;
    }

    public void setCoach_Description( String Coach_Description ) {
        this.Coach_Description = Coach_Description;
    }

    public void setActive_Status( String Active_Status ) {
        this.Active_Status = Active_Status;
    }

    public void setAvailability_StartDate( String Availability_StartDate ) {
        this.Availability_StartDate = Availability_StartDate;
    }

    public void setAvailability_EndDate( String Availability_EndDate ) {
        this.Availability_EndDate = Availability_EndDate;
    }

    public void setCoach_payment_type( String Coach_payment_type ) {
        this.Coach_payment_type = Coach_payment_type;
    }

    public void setCoach_Bank_Name( String Coach_Bank_Name ) {
        this.Coach_Bank_Name = Coach_Bank_Name;
    }

    public void setBranch_Code( String Branch_Code ) {
        this.Branch_Code = Branch_Code;
    }

    public void setCoach_Bank_ACCNum( String Coach_Bank_ACCNum ) {
        this.Coach_Bank_ACCNum = Coach_Bank_ACCNum;
    }

    public void setCoach_Bank_City( String Coach_Bank_City ) {
        this.Coach_Bank_City = Coach_Bank_City;
    }

    public void setUser_type( String User_type ) {
        this.User_type = User_type;
    }

    public void setCreatedAt( String createdAt ) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt( String updatedAt ) {
        this.updatedAt = updatedAt;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public void setGender( String gender ) {
        this.gender = gender;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public void setMobile( String mobile ) {
        this.mobile = mobile;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public void setPostalCode( String postalCode ) {
        this.postalCode = postalCode;
    }

    public void setCityId( String cityId ) {
        this.cityId = cityId;
    }

    public void setDeviceToken( String deviceToken ) {
        this.deviceToken = deviceToken;
    }

    public void setDeviceName( String deviceName ) {
        this.deviceName = deviceName;
    }

    public void setDeviceVersion( String deviceVersion ) {
        this.deviceVersion = deviceVersion;
    }

    public void setLatitude( String latitude ) {
        this.latitude = latitude;
    }

    public void setLongitude( String longitude ) {
        this.longitude = longitude;
    }

    public void setRoleId( String roleId ) {
        this.roleId = roleId;
    }

    public void setIsOtpVerified( String isOtpVerified ) {
        this.isOtpVerified = isOtpVerified;
    }

    public void setIsEmailConfirmed( String isEmailConfirmed ) {
        this.isEmailConfirmed = isEmailConfirmed;
    }

    public void setIsActive( String isActive ) {
        this.isActive = isActive;
    }

    public void setHashKey( String hashKey ) {
        this.hashKey = hashKey;
    }
}
