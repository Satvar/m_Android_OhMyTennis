package com.tech.cloudnausor.ohmytennis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentAvaibilityData {
    @SerializedName("booking_Id")
    @Expose
    private String booking_Id;
    @SerializedName("Coach_ID")
    @Expose
    private String Coach_ID;
    @SerializedName("user_Id")
    @Expose
    private String user_Id;
    @SerializedName("payment_Id")
    @Expose
    private String payment_Id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("bookingEnd")
    @Expose
    private String bookingEnd = null;
    @SerializedName("bookingCourse")
    @Expose
    private String bookingCourse;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("discount_club")
    @Expose
    private String discount_club;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("BookingTime")
    @Expose
    private String BookingTime;
    @SerializedName("Remarks")
    @Expose
    private String Remarks;


    // Getter Methods

    public String getBooking_Id() {
        return booking_Id;
    }

    public String getCoach_ID() {
        return Coach_ID;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public String getPayment_Id() {
        return payment_Id;
    }

    public String getStatus() {
        return status;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingEnd() {
        return bookingEnd;
    }

    public String getBookingCourse() {
        return bookingCourse;
    }

    public String getAmount() {
        return amount;
    }

    public String getDiscount_club() {
        return discount_club;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getBookingTime() {
        return BookingTime;
    }

    public String getRemarks() {
        return Remarks;
    }

    // Setter Methods

    public void setBooking_Id( String booking_Id ) {
        this.booking_Id = booking_Id;
    }

    public void setCoach_ID( String Coach_ID ) {
        this.Coach_ID = Coach_ID;
    }

    public void setUser_Id( String user_Id ) {
        this.user_Id = user_Id;
    }

    public void setPayment_Id( String payment_Id ) {
        this.payment_Id = payment_Id;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public void setBookingDate( String bookingDate ) {
        this.bookingDate = bookingDate;
    }

    public void setBookingEnd( String bookingEnd ) {
        this.bookingEnd = bookingEnd;
    }

    public void setBookingCourse( String bookingCourse ) {
        this.bookingCourse = bookingCourse;
    }

    public void setAmount( String amount ) {
        this.amount = amount;
    }

    public void setDiscount_club( String discount_club ) {
        this.discount_club = discount_club;
    }

    public void setPaymentStatus( String paymentStatus ) {
        this.paymentStatus = paymentStatus;
    }

    public void setBookingTime( String BookingTime ) {
        this.BookingTime = BookingTime;
    }

    public void setRemarks( String Remarks ) {
        this.Remarks = Remarks;
    }
}
