package com.arman.horus.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.arman.horus.R;
import com.arman.horus.models.PlaceDetail;
import com.arman.horus.services.PlaceService;
import com.arman.horus.services.ServiceGenerator;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailActivity extends AppCompatActivity {

    public static final String ID = "com.arman.horus.activities.PlaceDetailActivity.ID";
    private static final String LOG_TAG = PlaceDetailActivity.class.getName();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new SpotsDialog(this);

        String id = getIntent().getStringExtra(ID);
        getPlaceDetail(id);
    }

    /**
     * Gets place detail by the specified ID and shows it
     *
     * @param id place detail ID
     */
    private void getPlaceDetail(String id) {
        PlaceService placeService = ServiceGenerator.createService(PlaceService.class);
        Call<PlaceDetail> call = placeService.getPlace(id);
        call.enqueue(new PlaceDetailCallback());
        dialog.show();
    }

    public class PlaceDetailCallback implements Callback<PlaceDetail> {
        @Override
        public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {
            if (!response.isSuccessful()) {
                Log.d(LOG_TAG, "Request wasn't successful!");
                dialog.dismiss();
                //TODO: Implement this case
                return;
            }
            showPlaceDetail(response.body());
            dialog.dismiss();
        }

        @Override
        public void onFailure(Call<PlaceDetail> call, Throwable t) {
            Log.e(LOG_TAG, "Failed to connect to " + call.request().url().toString());
        }
    }

    /**
     * Renders place detail UI
     *
     * @param pDetail
     */
    public void showPlaceDetail(PlaceDetail pDetail) {
        // set title
        getSupportActionBar().setTitle(pDetail.title);

        // set image
        ImageView imageView = (ImageView) findViewById(R.id.place_detail_image);
        Picasso.with(imageView.getContext())
                .load(pDetail.images[0])
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.oops)
                .into(imageView);

        // set address
        TextView fromAddressView = (TextView) findViewById(R.id.place_detail_address);
        fromAddressView.setText(pDetail.address.getDisplay_name());

        // set description
        TextView descriptionView = (TextView) findViewById(R.id.place_detail_description);
        descriptionView.setText(pDetail.description);
    }

}
