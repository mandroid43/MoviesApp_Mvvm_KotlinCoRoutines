package com.manapps.mandroid.moviesapp_mvc_ret.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.manapps.mandroid.moviesapp_mvc_ret.R;


public class CheckNetworkConnection {
    public static boolean isOnline(Context context) {

        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            return (netInfo != null && netInfo.isConnected());
        } else {
            return false;
        }

    }

    public static void noNetworkMessage(Context context) {
        if (context != null) {
            Toast.makeText(context, context.getString(R.string.noNetworkLabel), Toast.LENGTH_SHORT).show();

        }
    }

}
