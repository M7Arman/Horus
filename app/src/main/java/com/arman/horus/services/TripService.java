package com.arman.horus.services;

import com.arman.horus.models.CardItem;
import com.arman.horus.models.TripDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by arman on 4/30/16.
 */
public interface TripService {

    @GET("/myapp/trips/popular")
    Call<List<CardItem>> getPopularTrips();

    @GET("/myapp/trips")
    Call<List<CardItem>> getAllTrips();

    @GET("/myapp/trips/{tripId}")
    Call<TripDetail> getTrip(String tripId);
}
