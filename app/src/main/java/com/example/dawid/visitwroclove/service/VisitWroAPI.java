package com.example.dawid.visitwroclove.service;

import android.content.Context;

import com.example.dawid.visitwroclove.model.AddressDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.LoggedUserDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.PaymentData;
import com.example.dawid.visitwroclove.model.RegistrationDTO;
import com.example.dawid.visitwroclove.model.Response;
import com.example.dawid.visitwroclove.model.ReviewDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.model.SendPointDTO;
import com.example.dawid.visitwroclove.model.SendRouteDTO;
import com.example.dawid.visitwroclove.model.UserDTO;
import com.example.dawid.visitwroclove.model.UserInformationDTO;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VisitWroAPI {

    String SERVICE_ENDPOINT = "https://visitwrocloveweb.azurewebsites.net/api/";

    @POST("auth/token")
    Observable<LoggedUserDTO> getToken(@Body UserDTO userDTO);

    @POST("reviews")
    Observable<ReviewDTO>sendReview(@Body ReviewDTO reviewDTO);

    @GET("addresses/{addressId}")
    Observable<AddressDTO> getAddress(@Path("addressId") String addressId);

    @GET("places")
    Observable<List<ObjectDTO>> getObjects();

    @GET("events")
    Observable<List<EventDTO>> getEvents();

    @GET("routes")
    Observable<List<RouteDTO>> getRoutes();

    @POST("RoutePoints")
    Observable<SendPointDTO> sendRoutePoints(@Body SendPointDTO sendPointDTO);

    @GET("RoutePoints")
    Observable<List<SendPointDTO>> getRoutePoints();

    @POST("routes")
    Observable<SendRouteDTO>sendRoute(@Body SendRouteDTO sendRouteDTO);

    @GET("addresses")
    Observable<List<AddressDTO>> getAddresses();

    @POST("users")
    Observable<Response> register(@Body RegistrationDTO registrationDTO);

    @GET("users/{userId}")
    Observable<UserInformationDTO> getUsersInformation(@Path("userId") int userId);

    @POST("payments")
    Observable<Response> sendPaymentData(@Body PaymentData paymentData);

    @PUT("users/{userId}")
    Observable<Response> updateUser(@Path("userId") int userId, @Body RegistrationDTO registrationDTO);

    class Factory {
        public static VisitWroAPI create(Context context) {
            return ServiceFactory.createRetrofitService(VisitWroAPI.class, SERVICE_ENDPOINT, context);
        }

        public static VisitWroAPI createLogin(String token) {
            return ServiceFactory.createLoginRetrofitService(VisitWroAPI.class, SERVICE_ENDPOINT, token);
        }
    }
}