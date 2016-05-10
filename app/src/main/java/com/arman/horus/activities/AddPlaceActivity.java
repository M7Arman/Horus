package com.arman.horus.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arman.horus.R;
import com.arman.horus.models.Address;
import com.arman.horus.models.PlaceDetail;
import com.arman.horus.utils.H;
import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AddPlaceActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private static final int PLACE_PICKER_REQUEST = 3;

    private EditText placeNameView;
    private EditText placeDescView;
    private EditText placeAddressView;
    private TextInputLayout placeNameLayout;
    private TextInputLayout placeDescLayout;
    private TextInputLayout placeAddressLayout;
    private LinearLayout addedImagesView;
    private List<Bitmap> images = new ArrayList<>();
    private PlaceDetail place;
    private Cloudinary cloudinary;
    private SpotsDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new SpotsDialog(AddPlaceActivity.this);
        place = new PlaceDetail();
        cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(this));
        getViews();
        setListeners();
    }

    private void setListeners() {
        Button addImgBtn = (Button) findViewById(R.id.add_image_btn);
        Button pickPlaceBtn = (Button) findViewById(R.id.pick_place_btn);
        Button createPlaceBtn = (Button) findViewById(R.id.create_place);

        createPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        pickPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPlace();
            }
        });
    }

    private void getViews() {
        placeNameView = (EditText) findViewById(R.id.place_name_input);
        placeDescView = (EditText) findViewById(R.id.place_description_input);
        placeAddressView = (EditText) findViewById(R.id.place_address_input);
        placeNameLayout = (TextInputLayout) findViewById(R.id.place_name_input_layout);
        placeAddressLayout = (TextInputLayout) findViewById(R.id.place_address_layout);
        addedImagesView = (LinearLayout) findViewById(R.id.added_images);

        placeNameView.addTextChangedListener(new PlaceTextWatcher(placeNameView));
        placeAddressView.addTextChangedListener(new PlaceTextWatcher(placeAddressView));
    }

    private void pickPlace() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void selectImage() {
        final List<String> items = new LinkedList<>(Arrays.asList("Նկարել", "Ընտրել ֆայլերից", "Գնալ հետ"));
        final boolean hasCamera = H.isDeviceSupportCamera(getApplicationContext());
        if (!hasCamera) {
            items.remove(0);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPlaceActivity.this);
        builder.setTitle("Ավելացնել նկար");
        builder.setItems(items.toArray(new CharSequence[items.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (hasCamera && item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if ((hasCamera && item == 1) || (!hasCamera && item == 0)) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Ընտրել նկար"),
                            SELECT_FILE);
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Oops...", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == REQUEST_CAMERA || requestCode == SELECT_FILE)
            addImageProcess(requestCode, data);
        if (requestCode == PLACE_PICKER_REQUEST)
            placePickerProcess(data);
    }

    private void placePickerProcess(Intent data) {
        Place pickedPlace = PlacePicker.getPlace(this, data);
        String addressText = pickedPlace.getAddress().toString();
        double[] coords = new double[2];
        coords[0] = pickedPlace.getLatLng().latitude;
        coords[1] = pickedPlace.getLatLng().longitude;
        Address address = new Address();
        address.setCoord(coords);
        place.address = address;
        if (!addressText.isEmpty()) {
            placeAddressView.setText(addressText);
        }
    }

    private void addImageProcess(int requestCode, Intent data) {
        Bitmap image = null;
        if (requestCode == REQUEST_CAMERA) {
            image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == SELECT_FILE) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                    null);
            Cursor cursor = cursorLoader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            image = BitmapFactory.decodeFile(selectedImagePath, options);
        }
        if (images.isEmpty()) {
            addedImagesView.removeViewAt(0);
        }
        ImageView imgView = new ImageView(this);
        imgView.setPadding(5, 5, 5, 5);
        imgView.setScaleType(ImageView.ScaleType.FIT_START);
        imgView.setImageBitmap(image);
        addedImagesView.addView(imgView);
        images.add(image);
    }

    private void submitForm() {
        if (!validatePlaceName() || !validatePlaceAddress()) {
            return;
        }
        place.description = placeDescView.getText().toString().trim();
        AsyncTaskRunner taskRunner = new AsyncTaskRunner();
        taskRunner.execute(images.toArray(new Bitmap[images.size()]));

        //       Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
//        finish();
    }

    private boolean validatePlaceName() {
        String placeName = placeNameView.getText().toString().trim();
        if (placeName.isEmpty()) {
            placeNameLayout.setError(getString(R.string.err_msg_place_name));
            requestFocus(placeNameView);
            return false;
        }
        placeNameLayout.setErrorEnabled(false);
        place.title = placeName;
        return true;
    }

    private boolean validatePlaceAddress() {
        String placeAddress = placeAddressView.getText().toString().trim();
        if (placeAddressView.getText().toString().trim().isEmpty()) {
            placeAddressLayout.setError(getString(R.string.err_msg_place_address));
            requestFocus(placeAddressView);
            return false;
        }
        placeAddressLayout.setErrorEnabled(false);
        place.address.setDisplayName(placeAddress);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class PlaceTextWatcher implements TextWatcher {

        private View view;

        private PlaceTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.place_name_input:
                    validatePlaceName();
                    break;
                case R.id.place_address_input:
                    validatePlaceAddress();
                    break;
            }
        }
    }

    public class AsyncTaskRunner extends AsyncTask<Bitmap, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Bitmap... images) {
            String[] imagesUrls = new String[images.length];
            String imageName = place.title.replace(" ", "_") + "_";

            for (int i = 0; i < images.length; ++i) {
                Bitmap image = images[i];
                ByteArrayInputStream stream = H.bitmapToInputStream(image);
                try {
                    String publicId = imageName + i;
                    cloudinary.uploader().upload(stream, ObjectUtils.asMap("public_id", publicId));
                    imagesUrls[i] = cloudinary.url().generate(publicId);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            place.images = imagesUrls;
            //TODO: post place
            return null;
        }


        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
            dialog.dismiss();
        }
    }
}
