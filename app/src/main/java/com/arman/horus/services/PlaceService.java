package com.arman.horus.services;

import com.arman.horus.models.CardItem;
import com.arman.horus.models.PlaceDetail;
import com.arman.horus.models.PlaceLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by arman on 4/30/16.
 */
public interface PlaceService {

    @GET("/myapp/places/popular")
    Call<List<CardItem>> getPopularPlaces();

    @GET("/myapp/places")
    Call<List<CardItem>> getAllPlaces();

    @GET("/myapp/places/locations")
    Call<List<PlaceLocation>> getPlacesLocations();

    @GET("/myapp/places/{placeId}")
    Call<PlaceDetail> getPlace(@Path("placeId") String placeId);

    @POST("/myapp/places")
    Call<Object> postPlace(@Body PlaceDetail place);

}
