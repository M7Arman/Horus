package com.arman.horus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.arman.horus.R;
import com.arman.horus.models.PlaceDetail;
import com.arman.horus.providers.ContentProvider;
import com.squareup.picasso.Picasso;

public class PlaceDetailActivity extends AppCompatActivity {

    public static final String ID = "com.arman.horus.activities.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(ID);
        showPlaceDetail(id);
    }

    private void showPlaceDetail(String id) {
        //TODO: get the place detail using the given ID
        PlaceDetail placeDetail = ContentProvider.dummyPlaceDetail();

        // set title
        getSupportActionBar().setTitle(placeDetail.title);

        // set image
        ImageView imageView = (ImageView) findViewById(R.id.place_detail_image);
        Picasso.with(imageView.getContext())
                .load(placeDetail.images[0])
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.oops)
                .into(imageView);

        // set address
        TextView fromAddressView = (TextView) findViewById(R.id.place_detail_address);
        fromAddressView.setText(placeDetail.address.getDisplay_name());

        // set description
        TextView descriptionView = (TextView) findViewById(R.id.place_detail_description);
        descriptionView.setText(placeDetail.description);
    }

}
