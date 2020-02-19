package com.tech.cloudnausor.ohmytennis.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TeamBuildingDTO {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("data")
    @Expose
    TeamBuildingData DataObject;
    @SerializedName("message")
    @Expose
    private String message;

    // Getter Methods

    public String getIsSuccess() {
        return isSuccess;
    }

    public TeamBuildingData getData() {
        return DataObject;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setIsSuccess( String isSuccess ) {
        this.isSuccess = isSuccess;
    }

    public void setData( TeamBuildingData dataObject ) {
        this.DataObject = dataObject;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public class TeamBuildingData {
        @SerializedName("course")
        @Expose
        ArrayList<TeamBuildingCourse> course = new ArrayList<TeamBuildingCourse>();

        public ArrayList<TeamBuildingCourse> getCourse() {
            return course;
        }

        public void setCourse(ArrayList<TeamBuildingCourse> course) {
            this.course = course;
        }
    }

    public class TeamBuildingCourse{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("Eventname")
        @Expose
        private String Eventname;
        @SerializedName("from_date")
        @Expose
        private String from_date;
        @SerializedName("to_date")
        @Expose
        private String to_date;
        @SerializedName("Description")
        @Expose
        private String Description;
        @SerializedName("Location")
        @Expose
        private String Location;
        @SerializedName("Postalcode")
        @Expose
        private String Postalcode;
        @SerializedName("Mode_of_transport")
        @Expose
        private String Mode_of_transport;
        @SerializedName("Eventdetails")
        @Expose
        private String Eventdetails;
        @SerializedName("Photo")
        @Expose
        private String Photo;
        @SerializedName("filename")
        @Expose
        private String filename;
        @SerializedName("Price")
        @Expose
        private String Price;
        @SerializedName("Plan")
        @Expose
        private String Plan;
        @SerializedName("Coach_Id")
        @Expose
        private String Coach_Id;
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
        @SerializedName("User_Level")
        @Expose
        private String User_Level = null;
        @SerializedName("User_Team")
        @Expose
        private String User_Team;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("address")
        @Expose
        private String address = null;
        @SerializedName("User_Location")
        @Expose
        private String User_Location;
        @SerializedName("postalCode")
        @Expose
        private String postalCode;
        @SerializedName("cityId")
        @Expose
        private String cityId;
        @SerializedName("roleId")
        @Expose
        private String roleId;
        @SerializedName("isOtpVerified")
        @Expose
        private String isOtpVerified;
        @SerializedName("User_Image")
        @Expose
        private String User_Image = null;
        @SerializedName("isActive")
        @Expose
        private String isActive;
        @SerializedName("hashKey")
        @Expose
        private String hashKey;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;


        // Getter Methods

        public String getId() {
            return id;
        }

        public String getEventname() {
            return Eventname;
        }

        public String getFrom_date() {
            return from_date;
        }

        public String getTo_date() {
            return to_date;
        }

        public String getDescription() {
            return Description;
        }

        public String getLocation() {
            return Location;
        }

        public String getPostalcode() {
            return Postalcode;
        }

        public String getMode_of_transport() {
            return Mode_of_transport;
        }

        public String getEventdetails() {
            return Eventdetails;
        }

        public String getPhoto() {
            return Photo;
        }

        public String getFilename() {
            return filename;
        }

        public String getPrice() {
            return Price;
        }

        public String getPlan() {
            return Plan;
        }

        public String getCoach_Id() {
            return Coach_Id;
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

        public String getUser_Level() {
            return User_Level;
        }

        public String getUser_Team() {
            return User_Team;
        }

        public String getMobile() {
            return mobile;
        }

        public String getAddress() {
            return address;
        }

        public String getUser_Location() {
            return User_Location;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getCityId() {
            return cityId;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getIsOtpVerified() {
            return isOtpVerified;
        }

        public String getUser_Image() {
            return User_Image;
        }

        public String getIsActive() {
            return isActive;
        }

        public String getHashKey() {
            return hashKey;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        // Setter Methods

        public void setId( String id ) {
            this.id = id;
        }

        public void setEventname( String Eventname ) {
            this.Eventname = Eventname;
        }

        public void setFrom_date( String from_date ) {
            this.from_date = from_date;
        }

        public void setTo_date( String to_date ) {
            this.to_date = to_date;
        }

        public void setDescription( String Description ) {
            this.Description = Description;
        }

        public void setLocation( String Location ) {
            this.Location = Location;
        }

        public void setPostalcode( String Postalcode ) {
            this.Postalcode = Postalcode;
        }

        public void setMode_of_transport( String Mode_of_transport ) {
            this.Mode_of_transport = Mode_of_transport;
        }

        public void setEventdetails( String Eventdetails ) {
            this.Eventdetails = Eventdetails;
        }

        public void setPhoto( String Photo ) {
            this.Photo = Photo;
        }

        public void setFilename( String filename ) {
            this.filename = filename;
        }

        public void setPrice( String Price ) {
            this.Price = Price;
        }

        public void setPlan( String Plan ) {
            this.Plan = Plan;
        }

        public void setCoach_Id( String Coach_Id ) {
            this.Coach_Id = Coach_Id;
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

        public void setUser_Level( String User_Level ) {
            this.User_Level = User_Level;
        }

        public void setUser_Team( String User_Team ) {
            this.User_Team = User_Team;
        }

        public void setMobile( String mobile ) {
            this.mobile = mobile;
        }

        public void setAddress( String address ) {
            this.address = address;
        }

        public void setUser_Location( String User_Location ) {
            this.User_Location = User_Location;
        }

        public void setPostalCode( String postalCode ) {
            this.postalCode = postalCode;
        }

        public void setCityId( String cityId ) {
            this.cityId = cityId;
        }

        public void setRoleId( String roleId ) {
            this.roleId = roleId;
        }

        public void setIsOtpVerified( String isOtpVerified ) {
            this.isOtpVerified = isOtpVerified;
        }

        public void setUser_Image( String User_Image ) {
            this.User_Image = User_Image;
        }

        public void setIsActive( String isActive ) {
            this.isActive = isActive;
        }

        public void setHashKey( String hashKey ) {
            this.hashKey = hashKey;
        }

        public void setCreatedAt( String createdAt ) {
            this.createdAt = createdAt;
        }

        public void setUpdatedAt( String updatedAt ) {
            this.updatedAt = updatedAt;
        }
    }


}
