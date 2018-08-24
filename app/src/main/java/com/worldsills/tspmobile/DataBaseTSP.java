package com.worldsills.tspmobile;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseTSP extends SQLiteOpenHelper {

    private static final String NOMBRE="registros.bd";
    private static final int VERSION=1;


    private static final String TABLA_PROYECTOS="CREATE TABLE PROYECTOS(CODPROYEC INTEGER PRIMARY KEY," +
            "NOMPROYECT TEXT, TIEMPOTOTAL INTEGER)";

    private static final String TABLA_TIME_LOG="CREATE TABLE TIMELOG(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " CODPROYEC INTEGER, FECHAINICIO TEXT, FECHAFINAL TEXT, PHASE TEXT, DELTA INTEGER, COMMENTS TEXT," +
            "FOREIGN KEY (CODPROYEC) REFERENCES PROYECTOS (CODPROYEC) )";


    private static final String TABLA_DEFECT_LOG="CREATE TABLE DEFECTLOG(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "CODPROYECT INTEGER, FECHA TEXT, TYPE TEXT, INJECTEDPHASE TEXT, REMOVEDPHASE TEXT, FIXTIME INTEGER, DESCRIPTION TEXT)";





    DataBaseTSP (Context context){
        super(context,NOMBRE,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_TIME_LOG);
        sqLiteDatabase.execSQL(TABLA_DEFECT_LOG);
        sqLiteDatabase.execSQL(TABLA_PROYECTOS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CREATE"+TABLA_PROYECTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CREATE"+TABLA_TIME_LOG);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CREATE"+TABLA_DEFECT_LOG);

        sqLiteDatabase.execSQL(TABLA_PROYECTOS);
        sqLiteDatabase.execSQL(TABLA_TIME_LOG);
        sqLiteDatabase.execSQL(TABLA_DEFECT_LOG);


    }
    public Cursor proyecto(String nombre, int accion, int tiempo, String idProyect){
        SQLiteDatabase db;
        ContentValues valores = new ContentValues();
        Cursor cursor=null;

        if (accion==1) {
            db=getWritableDatabase();
            valores.put("CODPROYEC", nombre);
            db.insert("PROYECTOS", null, valores);
        }else if (accion==2){
            db=getReadableDatabase();
            try {
                cursor=db.rawQuery("SELECT * FROM PROYECTOS",null);
            }catch (Exception e){}

        }else{
            db=getWritableDatabase();
            valores.put("TIEMPOTOTAL",tiempo);
            try {
                String whereClouse = "CODPROYEC" + " = LIKE ";
                String[] whereArs = {idProyect};
                db.update("PROYECTOS", valores, whereClouse, whereArs);
            }catch (Exception e){}
        }
        return cursor;
    }

    public void saveTimeLog(String codProyect, String fechaInicio, String fechaFinal,
                               String phase, int delta, String comments){

        SQLiteDatabase db=getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("CODPROYECT",codProyect);
        valores.put("FECHAINICIO",fechaInicio);
        valores.put("FECHAFINAL",fechaFinal);
        valores.put("PHASE",phase);
        valores.put("DELTA",delta);
        valores.put("COMMENTS",comments);


        db.insert("TIMELOG",null,valores);
    }
    public Cursor getTimelog(int codProyect, String phase){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=null;

        try{
            String[] colums={"DELTA"};
            String selection="CODPROYECT" + " = ? " + " AND "+ "PHASE" + " = ?";
            String[] selectionArgs={codProyect+"",phase};
            cursor=db.query("TIMELOG",colums,selection,selectionArgs,null,null,null);

        }catch (Exception e){}
        return cursor;

    }
    public void saveDefecLog(String codProyect, String fecha, String type,
                               String injected, String removed, int fixTme, String description){

        SQLiteDatabase db=getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("CODPROYECT",codProyect);
        valores.put("FECHA",fecha);
        valores.put("TYPE",type);
        valores.put("INJECTEDPHASE",injected);
        valores.put("REMOVEDPHASE",removed);
        valores.put("FIXTIME",fixTme);
        valores.put("DESCRIPTION",description);


        db.insert("DEFECTLOG",null,valores);
    }
    public Cursor getDefecLog(int codProyec, String injected, String removed){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=null;

        try{
            if (injected!=null){
                String selection="CODPROYECT" + " = ? " + " AND " + "INJECTEDPHASE" + " = ? ";
                String[] selectionArgs={codProyec+"",injected};
                cursor=db.query("DEFECTLOG",null,selection,selectionArgs,null,null,null);

            }else if (removed!=null){
                String selection="CODPROYECT" + " = ? " + " AND " + "REMOVEDPHASE" + " = ? ";
                String[] selectionArgs={codProyec+"",removed};
                cursor=db.query("DEFECTLOG",null,selection,selectionArgs,null,null,null);

            }else{
                String selection="CODPROYECT" + " = ? ";
                String[] selectionArgs={codProyec+""};
                cursor=db.query("DEFECTLOG",null,selection,selectionArgs,null,null,null);
            }


        }catch (Exception e){}

        return cursor;
    }


}
