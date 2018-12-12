package com.spider.star.nexbaseclient.api;

import com.spider.star.nexbaseclient.login.modal.Login;
import com.spider.star.nexbaseclient.otp.modal.OtpModal;
import com.spider.star.nexbaseclient.rateing.modal.RatingModal;
import com.spider.star.nexbaseclient.ticketcreation.modal.Data;
import com.spider.star.nexbaseclient.ticketcreation.modal.TicketCreation;
import com.spider.star.nexbaseclient.ticketcreation.modal.TicketCreationResponse;
import com.spider.star.nexbaseclient.trackexecutive.modal.TrackLatlangModal;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiCall {

    @POST("sendOtp")
    Observable<Response<GeneralResponse>> login(@Body Login login);

    @POST("otpCheck")
    Observable<Response<GeneralResponse>> otpcheck(@Body OtpModal otpModal);

    @GET("get_product_type")
    Observable<Response<Data>> getProblemList();

    @POST("ticket_create")
    Observable<Response<TicketCreationResponse>> ticketCreation(@Body TicketCreation ticketCreation);

    @POST("client_location")
    Observable<Response<GeneralResponse>> clientLocation(@Body TrackLatlangModal trackLatlangModal);

    @POST("client_feedback")
    Observable<Response<GeneralResponse>> clientFeedback(@Body RatingModal ratingModal);

}
