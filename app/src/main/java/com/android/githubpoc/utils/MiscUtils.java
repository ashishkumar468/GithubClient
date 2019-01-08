package com.android.githubpoc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.android.githubpoc.App;
import com.android.githubpoc.BuildConfig;

public class MiscUtils {
    public static boolean isConnectedToInternet() {
        Context context = App.getContext();
        ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isProduction() {
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            return true;
        } else {
            return false;
        }
    }

    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}