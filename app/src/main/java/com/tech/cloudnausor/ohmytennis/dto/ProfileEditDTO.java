package com.tech.cloudnausor.ohmytennis.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfileEditDTO {
    @SerializedName("isSuccess")
    @Expose
    String isSuccess;
    @SerializedName("data")
    @Expose
    ProfileDataArray profileDataArray;
    @SerializedName("message")
    @Expose
    String message;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ProfileDataArray getProfileDataArray() {
        return profileDataArray;
    }

    public void setProfileDataArray(ProfileDataArray profileDataArray) {
        this.profileDataArray = profileDataArray;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ProfileDataArray{
        @SerializedName("User_list")
        @Expose
        ArrayList<profileInformation> User_list;

        public ArrayList<profileInformation> getUser_list() {
            return User_list;
        }

        public void setUser_list(ArrayList<profileInformation> user_list) {
            User_list = user_list;
        }
    }

    public static class profileInformation{
        @SerializedName("id")
        @Expose
        String id;
        @SerializedName("firstName")
        @Expose
        String firstName;
        @SerializedName("lastName")
        @Expose
        String lastName;
        @SerializedName("email")
        @Expose
        String email;
        @SerializedName("gender")
        @Expose
        String gender;
        @SerializedName("password")
        @Expose
        String password;
        @SerializedName("User_Level")
        @Expose
        String User_Level;
        @SerializedName("User_Team")
        @Expose
        String User_Team;
        @SerializedName("mobile")
        @Expose
        String mobile;
        @SerializedName("address")
        @Expose
        String address;
        @SerializedName("User_Location")
        @Expose
        String User_Location;
        @SerializedName("postalCode")
        @Expose
        String postalCode;
        @SerializedName("cityId")
        @Expose
        String cityId;
        @SerializedName("roleId")
        @Expose
        String roleId;
        @SerializedName("isOtpVerified")
        @Expose
        String isOtpVerified;
        @SerializedName("User_Image")
        @Expose
        String User_Image;
        @SerializedName("isActive")
        @Expose
        String isActive;
        @SerializedName("hashKey")
        @Expose
        String hashKey;
        @SerializedName("createdAt")
        @Expose
        String createdAt;
        @SerializedName("updatedAt")
        @Expose
        String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUser_Level() {
            return User_Level;
        }

        public void setUser_Level(String user_Level) {
            User_Level = user_Level;
        }

        public String getUser_Team() {
            return User_Team;
        }

        public void setUser_Team(String user_Team) {
            User_Team = user_Team;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUser_Location() {
            return User_Location;
        }

        public void setUser_Location(String user_Location) {
            User_Location = user_Location;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getIsOtpVerified() {
            return isOtpVerified;
        }

        public void setIsOtpVerified(String isOtpVerified) {
            this.isOtpVerified = isOtpVerified;
        }

        public String getUser_Image() {
            return User_Image;
        }

        public void setUser_Image(String user_Image) {
            User_Image = user_Image;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getHashKey() {
            return hashKey;
        }

        public void setHashKey(String hashKey) {
            this.hashKey = hashKey;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
