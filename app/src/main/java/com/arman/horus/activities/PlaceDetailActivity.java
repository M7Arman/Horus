package com.arman.horus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.arman.horus.R;
import com.arman.horus.models.PlaceDetail;
import com.arman.horus.providers.DataProvider;

public class PlaceDetailActivity extends AppCompatActivity {

    public static final String ID = "place.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        System.out.println("ID = " + getIntent().getStringExtra(ID));
        String id = getIntent().getStringExtra(ID);
        showPlaceDetail(id);
    }

    private void showPlaceDetail(String id) {
        PlaceDetail placeDetail = DataProvider.dummyPlaceDetail();

        // set title
        getSupportActionBar().setTitle(placeDetail.title);

        // set image
        ImageView imageView = (ImageView) findViewById(R.id.place_detail_image);
        System.out.println("imageView: " + imageView);
        imageView.setImageResource(placeDetail.images);

        // set address
        TextView fromAddressView = (TextView) findViewById(R.id.place_detail_address);
        fromAddressView.setText(placeDetail.address.getDisplayName());

        // set description
        TextView descriptionView = (TextView) findViewById(R.id.place_detail_description);
        descriptionView.setText(placeDetail.description);
    }

}
