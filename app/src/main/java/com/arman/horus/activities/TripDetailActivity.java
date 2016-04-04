package com.arman.horus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.arman.horus.R;
import com.arman.horus.models.TripDetail;

public class TripDetailActivity extends AppCompatActivity {

    public static final String ID = "id";

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
        TripDetail tripDetail = TripDetail.dummyTripDetail();
        getSupportActionBar().setTitle(tripDetail.title);
        ImageView imageView = (ImageView) findViewById(R.id.item_detail_image);
        imageView.setImageResource(tripDetail.images);
//        TextView descriptionView = (TextView) findViewById(R.id.item_detail_description);
//        descriptionView.setText(tripDetail.description);
//        TripDetailAdapter.adapt(tripDetail);
        // TODO: create trip detail adapter
        // TODO: pass the trip detail data to adapter to show view
    }

}
