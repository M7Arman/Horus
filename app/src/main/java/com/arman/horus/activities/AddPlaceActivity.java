package com.arman.horus.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AddPlaceActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private static final int PLACE_PICKER_REQUEST = 3;

    private EditText placeNameView;
    private EditText placeDescView;
    private TextInputLayout placeNameLayout;
    private TextInputLayout placeDescLayout;
    private LinearLayout addedImagesView;
    private List<Bitmap> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        placeNameView = (EditText) findViewById(R.id.place_name_input);
        placeDescView = (EditText) findViewById(R.id.place_description_input);
        placeNameLayout = (TextInputLayout) findViewById(R.id.place_name_input_layout);
        placeDescLayout = (TextInputLayout) findViewById(R.id.place_description_input_layout);
        addedImagesView = (LinearLayout) findViewById(R.id.added_images);
        Button addImgBtn = (Button) findViewById(R.id.add_image_btn);
        Button pickPlaceBtn = (Button) findViewById(R.id.pick_place_btn);
        Button createPlaceBtn = (Button) findViewById(R.id.create_place);

        placeNameView.addTextChangedListener(new PlaceTextWatcher(placeNameView));
        placeDescView.addTextChangedListener(new PlaceTextWatcher(placeDescView));

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

    private void pickPlace() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            System.out.println("before starting act...");
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        System.out.println("after starting act...");
    }

    private void selectImage() {
        List<String> items = new LinkedList<>(Arrays.asList("Նկարել", "Ընտրել ֆայլերից", "Գնալ հետ"));

        if (!isDeviceSupportCamera()) {
            items.remove(0);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPlaceActivity.this);
        builder.setTitle("Ավելացնել նկար");
        builder.setItems(items.toArray(new CharSequence[items.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (item == 1) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Ընտրել նկար"),
                            SELECT_FILE);
                } else if (item == 2) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode = " + requestCode);
        System.out.println("resultCode = " + resultCode);
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
        System.out.println("AddPlaceActivity.placePickerProcess");
        Place place = PlacePicker.getPlace(this, data);
        System.out.println("AddPlaceActivity.placePickerProcess...");
        String toastMsg = String.format("Place: %s, Coords: %s", place.getName(), place.getLatLng());
        Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
        System.out.println("AddPlaceActivity.placePickerProcess......");
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
        if (!validatePlaceName() || !validatePlaceDesc()) {
            return;
        }
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validatePlaceName() {
        if (placeNameView.getText().toString().trim().isEmpty()) {
            placeNameLayout.setError(getString(R.string.err_msg_place_name));
            requestFocus(placeNameView);
            return false;
        }
        placeNameLayout.setErrorEnabled(false);
        return true;
    }

    private boolean validatePlaceDesc() {
        if (placeDescView.getText().toString().trim().isEmpty()) {
            placeDescLayout.setError(getString(R.string.err_msg_place_description));
            requestFocus(placeDescView);
            return false;
        }
        placeDescLayout.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Checking device has camera hardware or not
     */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
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
                case R.id.place_description_input:
                    validatePlaceDesc();
                    break;
            }
        }
    }

}
