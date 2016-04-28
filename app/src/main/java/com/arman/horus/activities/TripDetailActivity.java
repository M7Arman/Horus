package com.arman.horus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.arman.horus.R;
import com.arman.horus.models.TripDetail;
import com.arman.horus.providers.DataProvider;

public class TripDetailActivity extends AppCompatActivity {

    public static final String ID = "trip.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        System.out.println("ID = " + getIntent().getStringExtra(ID));
        String id = getIntent().getStringExtra(ID);
        showTripDetail(id);
    }

    private void showTripDetail(String id) {
        TripDetail tripDetail = DataProvider.dummyTripDetail();
        // set title
        getSupportActionBar().setTitle(tripDetail.title);
        // set image
        ImageView imageView = (ImageView) findViewById(R.id.trip_detail_image);
        imageView.setImageResource(tripDetail.images);

        // set 'from' address
        TextView fromAddressView = (TextView) findViewById(R.id.trip_detail_from);
        fromAddressView.setText(tripDetail.from.getDisplayName());

        // set 'target' address
        TextView targetAddressView = (TextView) findViewById(R.id.trip_detail_target);
        targetAddressView.setText(tripDetail.target.getDisplayName());

        // set description
        TextView descriptionView = (TextView) findViewById(R.id.trip_detail_description);
        descriptionView.setText(tripDetail.description);
    }

}
