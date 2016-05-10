package com.arman.horus.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by arman on 5/5/16.
 */
public class H {

    public static ByteArrayInputStream bitmapToInputStream(Bitmap image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();
        return new ByteArrayInputStream(bitmapData);
    }

    /**
     * Checking device has camera hardware or not
     */
    public static boolean isDeviceSupportCamera(Context appContext) {
        if (appContext.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
