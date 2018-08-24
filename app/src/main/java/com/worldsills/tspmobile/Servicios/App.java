package com.worldsills.tspmobile.Servicios;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL= "PSP CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();

        newChannel();
    }

    private void newChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(CHANNEL, "PSP CHANNEL SERVICES", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager= getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
