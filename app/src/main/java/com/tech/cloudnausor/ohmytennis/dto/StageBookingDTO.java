package com.tech.cloudnausor.ohmytennis.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StageBookingDTO {
        @SerializedName("Coach_id")
        @Expose
        private String Coach_id;
        @SerializedName("user_Id")
          @Expose
        private String user_Id;
       @SerializedName("status")
       @Expose
        private String status;
       @SerializedName("booking_date")
      @Expose
        private String booking_date;
      @SerializedName("bookingEnd")
      @Expose
        private String bookingEnd;
      @SerializedName("course")
     @Expose
        private String course;
       @SerializedName("amount")
       @Expose
        private String amount;
     @SerializedName("reserve")
     @Expose
        ArrayList<Reserve> reserve = new ArrayList<Reserve>();


        // Getter Methods

        public String getCoach_id() {
            return Coach_id;
        }

        public String getUser_Id() {
            return user_Id;
        }

        public String getStatus() {
            return status;
        }

        public String getBooking_date() {
            return booking_date;
        }

        public String getBookingEnd() {
            return bookingEnd;
        }

        public String getCourse() {
            return course;
        }

        public String getAmount() {
            return amount;
        }

        // Setter Methods

        public void setCoach_id( String Coach_id ) {
            this.Coach_id = Coach_id;
        }

        public void setUser_Id( String user_Id ) {
            this.user_Id = user_Id;
        }

        public void setStatus( String status ) {
            this.status = status;
        }

        public void setBooking_date( String booking_date ) {
            this.booking_date = booking_date;
        }

        public void setBookingEnd( String bookingEnd ) {
            this.bookingEnd = bookingEnd;
        }

        public void setCourse( String course ) {
            this.course = course;
        }

        public void setAmount( String amount ) {
            this.amount = amount;
        }

    public ArrayList<Reserve> getReserve() {
        return reserve;
    }

    public void setReserve(ArrayList<Reserve> reserve) {
        this.reserve = reserve;
    }

    public static class Reserve{
            @SerializedName("Coach_Id")
            @Expose
            private String Coach_Id;
            @SerializedName("User_Id")
            @Expose
            private String User_Id;
            @SerializedName("Course")
            @Expose
            private String Course;
            @SerializedName("Name_of_company")
            @Expose
            private String Name_of_company;
            @SerializedName("Email")
            @Expose
            private String Email;
            @SerializedName("Mobile")
            @Expose
            private String Mobile;
            @SerializedName("Date")
            @Expose
            private String Date;
            @SerializedName("Address")
            @Expose
            private String Address;
            @SerializedName("Postalcode")
            @Expose
            private String Postalcode;
            @SerializedName("Level")
            @Expose
            private String Level;
            @SerializedName("Number_of_person")
            @Expose
            private String Number_of_person;

        public Reserve() {
        }

        public Reserve(String coach_Id, String user_Id, String course, String name_of_company, String email, String mobile, String date, String address, String postalcode, String level, String number_of_person) {
                Coach_Id = coach_Id;
                User_Id = user_Id;
                Course = course;
                Name_of_company = name_of_company;
                Email = email;
                Mobile = mobile;
                Date = date;
                Address = address;
                Postalcode = postalcode;
                Level = level;
                Number_of_person = number_of_person;
            }

// Getter Methods

            public String getCoach_Id() {
                return Coach_Id;
            }

            public String getUser_Id() {
                return User_Id;
            }

            public String getCourse() {
                return Course;
            }

            public String getName_of_company() {
                return Name_of_company;
            }

            public String getEmail() {
                return Email;
            }

            public String getMobile() {
                return Mobile;
            }

            public String getDate() {
                return Date;
            }

            public String getAddress() {
                return Address;
            }

            public String getPostalcode() {
                return Postalcode;
            }

            public String getLevel() {
                return Level;
            }

            public String getNumber_of_person() {
                return Number_of_person;
            }

            // Setter Methods

            public void setCoach_Id( String Coach_Id ) {
                this.Coach_Id = Coach_Id;
            }

            public void setUser_Id( String User_Id ) {
                this.User_Id = User_Id;
            }

            public void setCourse( String Course ) {
                this.Course = Course;
            }

            public void setName_of_company( String Name_of_company ) {
                this.Name_of_company = Name_of_company;
            }

            public void setEmail( String Email ) {
                this.Email = Email;
            }

            public void setMobile( String Mobile ) {
                this.Mobile = Mobile;
            }

            public void setDate( String Date ) {
                this.Date = Date;
            }

            public void setAddress( String Address ) {
                this.Address = Address;
            }

            public void setPostalcode( String Postalcode ) {
                this.Postalcode = Postalcode;
            }

            public void setLevel( String Level ) {
                this.Level = Level;
            }

            public void setNumber_of_person( String Number_of_person ) {
                this.Number_of_person = Number_of_person;
            }
        }
}