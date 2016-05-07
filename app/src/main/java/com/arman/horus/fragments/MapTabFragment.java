package com.arman.horus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.horus.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapTabFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private static final LatLng ARMENIA = new LatLng(40.212441, 44.846191);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_map, container, false);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        getTrips();
        getPlaces();
        return v;
    }

    private void getTrips() {
        //TODO: get all trips(coords and title) and render on the map
    }

    private void getPlaces() {
        //TODO: get all map_flag_places(coords and title) and render on the map
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.setOnMarkerClickListener(this);
        CameraUpdate mapCenter = CameraUpdateFactory.newLatLngZoom(ARMENIA, 8);
        map.moveCamera(mapCenter);

//        map.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_flag_trips))
//                .position(new LatLng(40.775862, 44.548016)));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        System.out.println("MapTabFragment.onMarkerClick");
        return false;
    }
}
