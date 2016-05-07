package com.arman.horus.services;

import com.arman.horus.models.CardItem;
import com.arman.horus.models.PlaceDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by arman on 4/30/16.
 */
public interface PlaceService {

    @GET("/myapp/places/popular")
    Call<List<CardItem>> getPopularPlaces();

    @GET("/myapp/places")
    Call<List<CardItem>> getAllPlaces();

    @GET("/myapp/places/{placeId}")
    Call<PlaceDetail> getPlace(@Path("placeId") String placeId);
}
