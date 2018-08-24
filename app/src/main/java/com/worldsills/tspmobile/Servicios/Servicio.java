package com.worldsills.tspmobile.Servicios;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.worldsills.tspmobile.TimeLog;

public class Servicio extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
