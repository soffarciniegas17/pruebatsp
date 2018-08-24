package com.worldsills.tspmobile;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseTSP extends SQLiteOpenHelper {

    private static final String NOMBRE="registros.bd";
    private static final int VERSION=1;
    private static final String TABLA_TIME_LOG="";
    private static final String TABLA_DEFECT_LOG="";
    private static final String TABLA_PROYECTOS="";




    DataBaseTSP (Context context){
        super(context,NOMBRE,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
