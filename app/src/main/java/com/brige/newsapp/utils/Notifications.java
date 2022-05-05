package com.brige.newsapp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.brige.newsapp.R;

public class Notifications {

    public static final String REGISTER_NOTIFICATION_ID = "com.brige.newsapp.register_notification";
    public static final String LOGIN_NOTIFICATION_ID = "com.brige.newsapp.login_notification";
    private Context context;

    public Notifications(Context context) {
        this.context = context;
    }

    /**
     * This function registers our notification with the Android system
     * Without Notification & Notification channel registration, we cannot display
     * notifications from our app
     */
    public void createNotificationChannel(String name,String description, String channel_id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //This will only apply to Android 8 (API 26) and above
            //Android 7 and below registers Notifications in a different way

//            CharSequence name = "NewsApp Notifications";
//            String description = "Registration notification from ngomapp";

            //This variable will let the Android system know how to display the notification
            //Notifications with higher importance will be able to interrupt the user activity
            //Those with less importance can be displayed silently.
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    channel_id, name, importance);

            channel.setDescription(description);

            //Here we register the channel with the system.
            NotificationManager manager = context.getSystemService(
                    NotificationManager.class);

            manager.createNotificationChannel(channel);
        }
    }

    public NotificationCompat.Builder registrationNotification(String channel_id, String title, String text){
        //This method will be used to build a notification and add the necessary details

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, channel_id)
                .setSmallIcon(R.drawable.ic_explore)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;

    }
}
