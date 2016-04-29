package com.arman.horus.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by arman on 4/28/16.
 */
public class AppStatus {

    private static final String LOG_TAG = AppStatus.class.getName();
    private static final AppStatus instance = new AppStatus();
    private static Context context;

    private AppStatus() {
    }

    public static void init(Context ctx) {
        context = ctx.getApplicationContext();
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static boolean isOnline() {
        return isNetworkAvailable(context);
        //TODO: update!
//        if (isNetworkAvailable(context)) {
//            try {
//                HttpURLConnection urlc = (HttpURLConnection)
//                        (new URL("http://clients3.google.com/generate_204")
//                                .openConnection());
//                urlc.setRequestProperty("User-Agent", "Android");
//                urlc.setRequestProperty("Connection", "close");
//                urlc.setConnectTimeout(1500);
//                urlc.connect();
//                return (urlc.getResponseCode() == 204 &&
//                        urlc.getContentLength() == 0);
//            } catch (IOException e) {
//                Log.e(LOG_TAG, "Error checking internet connection", e);
//            }
//        } else {
//            Log.d(LOG_TAG, "No network available!");
//        }
//        return false;
    }
}
