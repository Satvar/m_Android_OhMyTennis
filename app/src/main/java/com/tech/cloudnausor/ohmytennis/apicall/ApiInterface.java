package com.tech.cloudnausor.ohmytennis.apicall;

import com.tech.cloudnausor.ohmytennis.dto.AnimationDTO;
import com.tech.cloudnausor.ohmytennis.dto.CourseMoreDetailsDTO;
import com.tech.cloudnausor.ohmytennis.dto.DeleteClubDTO;
import com.tech.cloudnausor.ohmytennis.dto.EventDTO;
import com.tech.cloudnausor.ohmytennis.dto.OnDemandDTO;
import com.tech.cloudnausor.ohmytennis.dto.ProfileEditDTO;
import com.tech.cloudnausor.ohmytennis.dto.SetPaymentDTO;
import com.tech.cloudnausor.ohmytennis.dto.StageBookingDTO;
import com.tech.cloudnausor.ohmytennis.dto.StageDTO;
import com.tech.cloudnausor.ohmytennis.dto.TeamBuildingDTO;
import com.tech.cloudnausor.ohmytennis.dto.TournamentDTO;
import com.tech.cloudnausor.ohmytennis.model.BookArray;
import com.tech.cloudnausor.ohmytennis.response.AllCoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.response.BokingDataResponseData;
import com.tech.cloudnausor.ohmytennis.response.BookingRequestStatusResponse;
import com.tech.cloudnausor.ohmytennis.response.CalenderResponseData;
import com.tech.cloudnausor.ohmytennis.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennis.response.ForgotResponseData;
import com.tech.cloudnausor.ohmytennis.response.GetClubResponseData;
import com.tech.cloudnausor.ohmytennis.response.GetCoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennis.response.GetIndiCoachDetailsResponse;
import com.tech.cloudnausor.ohmytennis.response.LoginResponseData;
import com.tech.cloudnausor.ohmytennis.response.PaymentResponseData;
import com.tech.cloudnausor.ohmytennis.response.RegisterPostalCodeList;
import com.tech.cloudnausor.ohmytennis.response.RegisterResponseData;
import com.tech.cloudnausor.ohmytennis.response.ResevertionTimeResponseData;
import com.tech.cloudnausor.ohmytennis.response.SetTimeSot;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

//    @GET("recipes_category")
//    Call<SampleModel> getSampleData();

    @GET("city/{postalcode}")
    Call<RegisterPostalCodeList> getPostalField(@Path("postalcode") String postalcode);

    @POST("user/login")
    @FormUrlEncoded
    Call<LoginResponseData> getLoginFieldDetails(@Field("email") String email, @Field("password") String password);

//    @POST("quickInsertCoach")
//    @FormUrlEncoded
//    Call<RegisterResponseData> getRegisterFieldDetails(@Field("Coach_Fname") String firstName, @Field("Coach_Lname") String secondName, @Field("Coach_Email") String email, @Field("Coach_Password") String pass, @Field("Code_postal") String postalCode, @Field("Coach_City") String PostalCitiy, @Field("Coach_Phone") String telepone);

    @POST("user/register/user")
    @FormUrlEncoded
    Call<RegisterResponseData> getRegisterFieldDetails(@Field("gender") String radio_value,@Field("firstName") String firstName, @Field("lastName") String secondName, @Field("email") String email, @Field("password") String pass, @Field("postalCode") String postalCode, @Field("User_City") String PostalCitiy, @Field("mobile") String telepone,@Field("cityId") String cityId,@Field("roleId") String roleId);

//    @GET("coach/getcoachbycity")
//    Call<AllCoachDetailsResponseData> getGetallcoaches(@Query("ville") String location,@Query("date") String date);

//    @GET("coach/searchByCoach")
//    Call<AllCoachDetailsResponseData> getGetallcoaches(@Query("ville") String location,@Query("date") String date,@Query("rayon") String rayon
//    ,@Query("course") String course);

    @GET("coach/getcoachbycode/{code}")
    Call<AllCoachDetailsResponseData> getGetallcoaches(@Path("code") String location);

    @POST("getcoachbyid")
    @FormUrlEncoded
    Call<CoachDetailsResponseData> getCoachDeatils(@Field("Coach_ID") String coach_id);

    @POST("user/getuserbyid")
    @FormUrlEncoded
    Call<ProfileEditDTO> getuserbyid(@Field("User_Email") String User_Email);

    @POST("/user/userVerification")
    @FormUrlEncoded
    Call<DeleteClubDTO> userVerification(@Field("email") String User_Email);

    @POST("user/updateprofile")
    Call<ProfileEditDTO> setupdateprofile(@Body ProfileEditDTO.profileInformation profileInformation);

    @POST("user/password/forgot")
    @FormUrlEncoded
    Call<ForgotResponseData> getForgotFieldDetails(@Field("email") String email);


    @POST("getCalendarMyCalendar")
    @FormUrlEncoded
    Call<CalenderResponseData> getGetCalendarMyCalendars(@Field("Coach_ID") String Coach_ID);

    @GET("coach/getTimeslot")
    Call<SetTimeSot> getTimeslot(@Query("Coach_ID") String Coach_ID, @Query("Start_Date") String Start_Date,@Query("Course") String Course);

    @POST("coach/setreservation")
    Call<ResevertionTimeResponseData> setreservation(@Body BookArray bookArray);

    @GET("course/getindividualcourse")
    Call<GetIndiCoachDetailsResponse> getGetIndiCourseDetails(@Query("coachId") String Coach_ID);

    @GET("course/getcousecollectivedemanad")
    Call<GetCoachCollectiveOnDemandResponseData> getcousecollectivedemanad(@Query("Coach_ID") String Coach_ID );

    @GET("course/getcousecollectiveclub")
    Call<GetClubResponseData> getcousecollectiveclub(@Query("coachId") String Coach_ID );

    @GET("coach/BookingDetail")
    Call<PaymentResponseData> bookingDetail(@Query("booking_Id") String booking_Id );

    @GET("course/getstagecourse")
    Call<StageDTO> getstagecourse(@Query("coachId") String Coach_ID );

    @GET("course/getteambuildingcourse")
    Call<TeamBuildingDTO> getteambuildingcourse(@Query("coachId") String Coach_ID );

    @GET("course/getstage")
    Call<StageDTO> getstage(@Query("id") String id,@Query("coachId") String Coach_ID );

//    @POST("user/password/reset")
//    @FormUrlEncoded
//    Call<DeleteClubDTO> setRest(@Field("hashkey") String hash, @Field("password")String password );

    @POST("user/resetpassword")
    @FormUrlEncoded
    Call<DeleteClubDTO> setRest(@Field("email") String email ,@Field("hash") String hash,@Field("password")String password );

    @GET("course/gettournament")
    Call<TournamentDTO> gettournament(@Query("coachId") String Coach_ID, @Query("id") String id);

    @GET("course/getanimation")
    Call<AnimationDTO> getanimation(@Query("coachId") String Coach_ID, @Query("animation_id") String id );


    @GET("course/getteambuilding")
    Call<TeamBuildingDTO> getteambuilding(@Query("coachId") String Coach_ID, @Query("id") String id );


    @GET("coachdetail/getallcourse")
    Call<EventDTO> getallcourse(@Query("P_course") String P_course );

    @POST("coachdetail/bookcourse")
    Call<StageDTO> getbookcourse(@Body StageBookingDTO stageBookingDTO);

    @GET("coachdetail/getbookcourse")
    Call<CourseMoreDetailsDTO> getbookcourse(@Query("course") String course, @Query("booking_id") String booking_id );


    @GET("user/getreservation")
    Call<BokingDataResponseData> getBookingDataResponse(@Query("User_ID") String User_ID);

    @POST("coach/setpayment")
    @FormUrlEncoded
    Call<SetPaymentDTO> setpayment(@Field("status") String status,@Field("booking_id") String booking_id,@Field("amount") String amount,@Field("token") String tokenkey);

    @GET("coach/getdemandavailability")
    Call<OnDemandDTO> getdemandavailability(@Query("Coach_ID") String Coach_ID,
                                            @Query("slot") String slot , @Query("date") String date);

    @POST("user/cancelreservation")
    @FormUrlEncoded
    Call<BookingRequestStatusResponse> cancelreservation(@Field("Coach_ID") String Coach_ID, @Field("status") String status, @Field("booking_id") String booking_id,
                                                          @Field("booking_date") String booking_date, @Field("amount") String amount, @Field("course") String course,
                                                          @Field("discount") String discount,@Field("email") String email);

//    @POST("coachForgot")
//    @FormUrlEncoded
//    Call<ForgotResponseData> getForgotFieldDetails(@Field("email") String firstName);
//
//    @POST("coachReset")
//    @FormUrlEncoded
//    Call<ResetResponseData> getResetFieldDetails(@Field("email") String firstName,@Field("password") String password);

}
