package com.brige.newsapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.brige.newsapp.MainActivity;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("TEST::", "onReceive: Broadcast Received");

        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                Intent serviceIntent = new Intent(context, MyServices.class);
                context.startService(serviceIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TEST::", "onReceive: Sync failed");
        }
    }
}
