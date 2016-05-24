package com.arman.horus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.horus.R;
import com.arman.horus.activities.PlaceDetailActivity;
import com.arman.horus.activities.TripDetailActivity;
import com.arman.horus.models.PlaceLocation;
import com.arman.horus.models.TripLocation;
import com.arman.horus.services.PlaceService;
import com.arman.horus.services.ServiceGenerator;
import com.arman.horus.services.TripService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapTabFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String LOG_TAG = MapTabFragment.class.getName();
    private static final LatLng ARMENIA = new LatLng(40.212441, 44.846191);
    private GoogleMap map;
    private Map<Marker, PlaceLocation> places = new HashMap<>();
    private Map<Marker, TripLocation> trips = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_map, container, false);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        showTripsOnMap();
        showPlacesOnMap();
        return v;
    }

    private void showTripsOnMap() {
        TripService tripService = ServiceGenerator.createService(TripService.class);
        Call<List<TripLocation>> call = tripService.getTripsLocations();
        call.enqueue(new Callback<List<TripLocation>>() {

            @Override
            public void onResponse(Call<List<TripLocation>> call, Response<List<TripLocation>> response) {
                if (!response.isSuccessful()) {
                    Log.e(LOG_TAG, response.message());
                    return;
                    //TODO: Handle this case
                }
                List<TripLocation> res = response.body();
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_flag_trips);
                for (TripLocation tripLocation : res) {
                    Marker marker = map.addMarker(new MarkerOptions()
                            .icon(icon)
                            .position(tripLocation.target.getLatLng())
                            .title(tripLocation.title)
                            .alpha(0.7f));
                    trips.put(marker, tripLocation);
                }
            }

            @Override
            public void onFailure(Call<List<TripLocation>> call, Throwable t) {
                Log.e(LOG_TAG, "Failure while getting trips locations", t);
            }
        });
    }

    private void showPlacesOnMap() {
        PlaceService placeService = ServiceGenerator.createService(PlaceService.class);
        Call<List<PlaceLocation>> call = placeService.getPlacesLocations();
        call.enqueue(new Callback<List<PlaceLocation>>() {

            @Override
            public void onResponse(Call<List<PlaceLocation>> call, Response<List<PlaceLocation>> response) {
                if (!response.isSuccessful()) {
                    Log.e(LOG_TAG, response.message());
                    return;
                    //TODO: Handle this case
                }
                List<PlaceLocation> res = response.body();
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_flag_places);
                for (PlaceLocation placeLocation : res) {
                    try {
                        Marker marker = map.addMarker(new MarkerOptions()
                                .icon(icon)
                                .position(placeLocation.address.getLatLng())
                                .title(placeLocation.title)
                                .alpha(0.7f));
                        places.put(marker, placeLocation);
                    } catch (NullPointerException e) {
                        Log.w(LOG_TAG, "Failed to add a marker for " + placeLocation.title + " place");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PlaceLocation>> call, Throwable t) {
                Log.e(LOG_TAG, "Failure while getting places locations", t);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.setOnInfoWindowClickListener(this);
        CameraUpdate mapCenter = CameraUpdateFactory.newLatLngZoom(ARMENIA, 8);
        map.moveCamera(mapCenter);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (places.containsKey(marker)) {
            onPlaceMarkerClick(places.get(marker));
        } else if (trips.containsKey(marker)) {
            onTripMarkerClick(trips.get(marker));
        }
    }

    private void onPlaceMarkerClick(PlaceLocation placeLocation) {
        Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
        intent.putExtra(PlaceDetailActivity.ID, placeLocation.id);
        startActivity(intent);
    }

    private void onTripMarkerClick(TripLocation tripLocation) {
        Intent intent = new Intent(getContext(), TripDetailActivity.class);
        intent.putExtra(TripDetailActivity.ID, tripLocation.id);
        startActivity(intent);
    }
}
