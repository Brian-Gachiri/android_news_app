package com.brige.newsapp.utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.brige.newsapp.R;

public class MyServices extends Service {
    MediaPlayer mediaplayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
        mediaplayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaplayer.release();
        mediaplayer = null;
    }
}
