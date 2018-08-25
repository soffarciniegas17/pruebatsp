package com.worldsills.tspmobile.Servicios;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.worldsills.tspmobile.DefectLog;
import com.worldsills.tspmobile.R;
import com.worldsills.tspmobile.TimeLog;



public class Servicio extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int lugar = intent.getIntExtra("actividad", 1);
        Intent intentNoti;
        if(lugar==1){
            intentNoti= new Intent(this, TimeLog.class);

        } else {
            intentNoti= new Intent(this, DefectLog.class);
        }

        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intentNoti, 0);

        Notification notification= new NotificationCompat.Builder(this, App.CHANNEL)
                .setContentTitle("Iniciaste una fase ").setSmallIcon(R.drawable.icono_reloj)
                .setContentText("El tiempo de una fase esta corriendo").setColor(getResources().getColor(R.color.azulbase))
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
