package com.example.dawid.visitwroclove.service;

import com.example.dawid.visitwroclove.model.AddressDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.LoggedUserDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.UserDTO;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VisitWroAPI {

    String SERVICE_ENDPOINT = "https://visitwrocloveweb.azurewebsites.net/api/";

    @POST("auth/token")
    Observable<LoggedUserDTO> getToken(@Body UserDTO userDTO);

    @GET("addresses/{addressId}")
    Observable<AddressDTO> getAddress(@Path("addressId") String addressId);

    @GET("places")
    Observable<List<ObjectDTO>> getObjects();

    @GET("events")
    Observable<List<EventDTO>> getEvents();

    @GET("addresses")
    Observable<List<AddressDTO>> getAddresses();

    class Factory {
        public static VisitWroAPI create() {
            return ServiceFactory.createRetrofitService(VisitWroAPI.class, SERVICE_ENDPOINT);
        }
    }
}