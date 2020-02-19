package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDataDetails {

    @SerializedName("booking_Id")
    @Expose
    private String booking_Id;
    @SerializedName("BookingTime")
    @Expose
    private String BookingTime = null;
    @SerializedName("Coach_ID")
    @Expose
    private String Coach_ID;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("bookingCourse")
    @Expose
    private String bookingCourse;
    @SerializedName("CourseName")
    @Expose
    private String CourseName;
    @SerializedName("bookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("discount_club")
    @Expose
    private String discount_club;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("payment_Id")
    @Expose
    private String payment_Id = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_Id")
    @Expose
    private String user_Id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("Remarks")
    @Expose
    private String Remarks = null;


    // Getter Methods

    public String getBooking_Id() {
        return booking_Id;
    }

    public String getBookingTime() {
        return BookingTime;
    }

    public String getCoach_ID() {
        return Coach_ID;
    }

    public String getAmount() {
        return amount;
    }

    public String getBookingCourse() {
        return bookingCourse;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getDiscount_club() {
        return discount_club;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPayment_Id() {
        return payment_Id;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_Id() {
        return user_Id;
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

    public String getRemarks() {
        return Remarks;
    }

    // Setter Methods

    public void setBooking_Id(String booking_Id) {
        this.booking_Id = booking_Id;
    }

    public void setBookingTime(String BookingTime) {
        this.BookingTime = BookingTime;
    }

    public void setCoach_ID(String Coach_ID) {
        this.Coach_ID = Coach_ID;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setBookingCourse(String bookingCourse) {
        this.bookingCourse = bookingCourse;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setDiscount_club(String discount_club) {
        this.discount_club = discount_club;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setPayment_Id(String payment_Id) {
        this.payment_Id = payment_Id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }


}