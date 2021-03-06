package com.arman.horus.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.arman.horus.R;
import com.arman.horus.models.TripDetail;
import com.arman.horus.services.ServiceGenerator;
import com.arman.horus.services.TripService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailActivity extends AppCompatActivity {

    public static final String ID = "com.arman.horus.activities.TripDetailActivity.ID";
    private static final String LOG_TAG = TripDetailActivity.class.getName();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(ID);
        getTripDetail(id);
    }

    /**
     * Gets trip detail by the specified ID and shows it
     *
     * @param id trip detail ID
     */
    private void getTripDetail(String id) {
        TripService tripService = ServiceGenerator.createService(TripService.class);
        Call<TripDetail> call = tripService.getTrip(id);
        call.enqueue(new TripDetailCallback());
        dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
    }

    public class TripDetailCallback implements Callback<TripDetail> {
        @Override
        public void onResponse(Call<TripDetail> call, Response<TripDetail> response) {
            if (!response.isSuccessful()) {
                Log.d(LOG_TAG, "Request wasn't successful!");
                dialog.dismiss();
                //TODO: Implement this case
                return;
            }
            showTripDetail(response.body());
            dialog.dismiss();
        }

        @Override
        public void onFailure(Call<TripDetail> call, Throwable t) {
            Log.d(LOG_TAG, "Failed to connect to " + call.request().url().toString());
        }
    }

    /**
     * Renders trip detail UI
     *
     * @param tripDetail
     */
    private void showTripDetail(TripDetail tripDetail) {
        // set title
        getSupportActionBar().setTitle(tripDetail.title);
        // set image
        ImageView imageView = (ImageView) findViewById(R.id.trip_detail_image);
        Picasso.with(imageView.getContext())
                .load(tripDetail.images[0])
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.oops)
                .into(imageView);

        // set 'from' address
        TextView fromAddressView = (TextView) findViewById(R.id.trip_detail_from);
        fromAddressView.setText(tripDetail.from.getDisplayName());

        // set 'target' address
        TextView targetAddressView = (TextView) findViewById(R.id.trip_detail_target);
        targetAddressView.setText(tripDetail.target.getDisplayName());

        // set description
        TextView descriptionView = (TextView) findViewById(R.id.trip_detail_description);
        descriptionView.setText(tripDetail.description);

        // set start date
        TextView startDateView = (TextView) findViewById(R.id.trip_detail_date);
        startDateView.setText(tripDetail.startDate);
    }

}
