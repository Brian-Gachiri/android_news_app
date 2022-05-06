package com.brige.newsapp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.brige.newsapp.HomeActivity;
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

        Intent intent = new Intent(context, HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context,0, intent,PendingIntent.FLAG_IMMUTABLE);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Brian is inviting you to join NewsAPP");
        shareIntent.putExtra(Intent.EXTRA_TITLE, "Join NewsAPP");

        PendingIntent sharePendingIntent = PendingIntent
                .getActivity(context, 0, shareIntent, PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, channel_id)
                .setSmallIcon(R.drawable.ic_explore)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_share, "Share", sharePendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;

    }

    public NotificationCompat.Builder bigTextNotification(String title, String text){
        //This method will be used to build a notification and add the necessary details

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, REGISTER_NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_explore)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;

    }

    public NotificationCompat.Builder bigImageNotification(String title, Bitmap image){
        //This method will be used to build a notification and add the necessary details

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, "Random text");

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, REGISTER_NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_explore)
                .setContentTitle(title)
                .setLargeIcon(image)
                .setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(image)
                .bigLargeIcon(null))
                .addAction(R.drawable.ic_share, "Share", pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;

    }
}
