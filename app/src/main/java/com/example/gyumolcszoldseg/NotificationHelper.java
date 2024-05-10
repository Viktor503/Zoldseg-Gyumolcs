package com.example.gyumolcszoldseg;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;


public class NotificationHelper {
    private static final String CHANNEL_ID = "zoldseggyumolcs_channel_01";
    private final int NOTIFICATION_ID = 0;

    private NotificationManager mNotifyManager;
    private Context mContext;


    public NotificationHelper(Context context) {
        this.mContext = context;
        this.mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {

        NotificationChannel channel = new NotificationChannel
                (CHANNEL_ID, "ZöldségGyümölcs Értesítés", NotificationManager.IMPORTANCE_HIGH);

        channel.enableLights(true);
        channel.setLightColor(Color.MAGENTA);
        channel.enableVibration(true);
        channel.setDescription("Értesítés a ZöldségGyümölcs alkalmazásból");

        mNotifyManager.createNotificationChannel(channel);
    }

    public void send(String message) {
        Intent contentIntent = new Intent(mContext, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (mContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID);

        Notification notification = builder
                .setContentTitle("ZöldségGyümölcs Értesítés")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSmallIcon(R.drawable.carrot_svgrepo_com)
                .setContentIntent(contentPendingIntent).build();

        mNotifyManager.notify(NOTIFICATION_ID, notification);
    }
}
