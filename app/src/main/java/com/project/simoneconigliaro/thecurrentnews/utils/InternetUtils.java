package com.project.simoneconigliaro.thecurrentnews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.project.simoneconigliaro.thecurrentnews.R;

public class InternetUtils {

    public static boolean checkConnection(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            return true;
        } else {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show();
            return false;
        }
    }
}



