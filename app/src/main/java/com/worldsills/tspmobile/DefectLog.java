package com.worldsills.tspmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.worldsills.tspmobile.Servicios.Servicio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefectLog extends AppCompatActivity {


    Spinner spinnerT, spinerR, spinerI;
    Chronometer cronometro;
    private String fecha, tipo, faseR, faseI, solicion;
    TextView date;
    private int id_proD,tiempo;
    Dialog DialogDefect;
    EditText comentarios;
    private boolean modo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_log);

        date= findViewById(R.id.date);
        comentarios= findViewById(R.id.comentarios);

        spinnerT= findViewById(R.id.spinnerdefect);
        spinerR= findViewById(R.id.spinnerremove);
        spinerI= findViewById(R.id.spinerinjected);

        DialogDefect=new Dialog(this);
        DialogDefect.setContentView(R.layout.dialog_saved);
        DialogDefect.setCanceledOnTouchOutside(false);
        DialogDefect.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cronometro= findViewById(R.id.cronoDefect);

        ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(this,R.array.fases, android.R.layout.activity_list_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        ArrayAdapter<CharSequence> arrayAdapteer= ArrayAdapter.createFromResource(this, R.array.tipos_defectos, android.R.layout.activity_list_item);
        arrayAdapteer.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        Bundle bundle= getIntent().getExtras();
        id_proD=bundle.getInt("CODIGO");

        spinnerRemoved();
        spinnerInjected();
        spinnerTipo();

    }

    public void spinnerRemoved(){
        spinerR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faseR= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void spinnerInjected(){
        spinerI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faseI= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void spinnerTipo(){
        spinnerT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    public void iniciarD(View view) {

        Long elapsed;
        switch (view.getId()){

            case R.id.start:
                cronometro.start();
                modo=true;
                Intent iniciarServicio = new Intent(this, Servicio.class);
                iniciarServicio.putExtra("actividad", 2);
                startService(iniciarServicio);

                break;
            case R.id.stop:
                cronometro.stop();
                cronometro=null;
                modo=false;

                 elapsed= cronometro.getBase() - SystemClock.elapsedRealtime();
                tiempo= (int) (elapsed/1000)/60;

                Intent cerrar = new Intent(this, Servicio.class);
                stopService(cerrar);
                break;

            case R.id.restaurar:
                cronometro=null;
                break;
        }




    }

    private int total=0;
    public void iniciarF(View view) {

        DateFormat format= new SimpleDateFormat("HH:MM:SS DD/MM/YYYY");
        Date dataf = new Date();
        fecha= format.format(dataf);

        date.setText(fecha);

    }


    public void guarda(View view) {
        solicion= comentarios.getText().toString();

        final  TextView t1, t2, t3 ,t4, t5, t6;
        t1= DialogDefect.findViewById(R.id.t1);
        t2= DialogDefect.findViewById(R.id.t2);
        t3= DialogDefect.findViewById(R.id.t3);
        t4= DialogDefect.findViewById(R.id.t4);
        t5= DialogDefect.findViewById(R.id.t5);
        t6= DialogDefect.findViewById(R.id.t6);


        DataBaseTSP db = new DataBaseTSP(this);
        db.saveDefecLog(""+id_proD, fecha, tipo, faseI, faseR, tiempo, solicion);

        t1.setText("Tipo: "+tipo);
        t2.setText("Inicio :"+fecha);
        t3.setText("Removida" +faseR);
        t4.setText("Injectada" +faseI);
        t5.setText("Tiempo de reparo"+ tiempo);
        t6.setText("Solucion "+comentarios);

        Button cerrar = DialogDefect.findViewById(R.id.salir);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setText("");
                fecha="";

                comentarios.setText("");
                
            }
        });

        DialogDefect.show();

    }

    public void cancelar(View view) {
        if(modo){
            final AlertDialog alertDialog= new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage("La fase sigue corriendo, no puede acceder a otros proyectos");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        } else {
            Intent intent= new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }
    }


}


