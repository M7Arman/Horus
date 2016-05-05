package com.arman.horus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.horus.R;
import com.arman.horus.adapters.CardItemsAdapter;
import com.arman.horus.listeners.OnPlaceCardClickListener;
import com.arman.horus.listeners.OnTripCardClickListener;
import com.arman.horus.models.CardItem;
import com.arman.horus.services.PlaceService;
import com.arman.horus.services.ServiceGenerator;
import com.arman.horus.services.TripService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardTabFragment extends Fragment {

    private static final String LOG_TAG = BoardTabFragment.class.getName() + " ->> ";
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.tab_board, container, false);
        addPlacesListToBoard();
        addTripsListToBoard();
        return mView;
    }

    private void addPlacesListToBoard() {
        final RecyclerView placesRecyclerView = (RecyclerView) mView.findViewById(R.id.places);
        placesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        placesRecyclerView.setLayoutManager(layoutManager);
        PlaceService placeService = ServiceGenerator.createService(PlaceService.class);
        Call<List<CardItem>> call = placeService.getPopularPlaces();
        call.enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(Call<List<CardItem>> call, Response<List<CardItem>> response) {
                if (response.isSuccessful()) {
                    List<CardItem> cardItems = response.body();
                    RecyclerView.Adapter cardsAdapter = new CardItemsAdapter(cardItems, new OnPlaceCardClickListener(getContext()));
                    placesRecyclerView.setAdapter(cardsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CardItem>> call, Throwable t) {
                Log.d(LOG_TAG, "Failure:", t);
            }
        });
    }

    private void addTripsListToBoard() {
        final RecyclerView tripsRecyclerView = (RecyclerView) mView.findViewById(R.id.trips);
        tripsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        tripsRecyclerView.setLayoutManager(layoutManager);
        TripService tripService = ServiceGenerator.createService(TripService.class);
        Call<List<CardItem>> call = tripService.getPopularTrips();
        call.enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(Call<List<CardItem>> call, Response<List<CardItem>> response) {
                if (response.isSuccessful()) {
                    List<CardItem> cardItems = response.body();
                    RecyclerView.Adapter cardsAdapter = new CardItemsAdapter(cardItems, new OnTripCardClickListener(getContext()));
                    tripsRecyclerView.setAdapter(cardsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CardItem>> call, Throwable t) {
                Log.d(LOG_TAG, "Failure:", t);
            }
        });
    }

}
