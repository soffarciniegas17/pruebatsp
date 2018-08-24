package com.worldsills.tspmobile;

import android.app.Dialog;
import android.content.Intent;
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

public class TimeLog extends AppCompatActivity {

    Spinner spinner;
    Chronometer chronometer;
    private String FechaI, FechaF, fase, coments;
    TextView fechaInicio, FechaFinal;
    private int interruption, delta, id_pro;
    EditText comentarios, interrup;
    Dialog dialogTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);


        spinner= findViewById(R.id.spinner);
        comentarios= findViewById(R.id.comentarios);
        interrup= findViewById(R.id.interrupciones);

        ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(this,R.array.fases, android.R.layout.activity_list_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);

        chronometer= findViewById(R.id.cronotime);


        dialogTime= new Dialog(this);
        dialogTime.setCanceledOnTouchOutside(false);
        dialogTime.setContentView(R.layout.dialog_saved);

        Bundle bundle= getIntent().getExtras();
        id_pro= bundle.getInt("CODIGO");

        spinnerOnClick();
    }

    public void spinnerOnClick(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fase= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void iniciarFase(View view) {
        chronometer.start();
        Intent iniciarServicio = new Intent(this, Servicio.class);
        iniciarServicio.putExtra("actividad", 1);
        startService(iniciarServicio);

        DateFormat format= new SimpleDateFormat("HH:MM:SS DD/MM/YYYY");
        Date date = new Date();
        FechaI= format.format(date);

        fechaInicio.setText(FechaI);

    }

    private int total=0;
    public void agregar(View view) {
        interruption= Integer.parseInt(interrup.getText().toString());
        total+=interruption;
        interrup.setText("");
    }

    public void detenerFase(View view) {
        chronometer.stop();
        chronometer=null;


        Intent intent= new Intent(this, Servicio.class);
        stopService(intent);


        Long elapsed= chronometer.getBase() - SystemClock.elapsedRealtime();
        int tiempo= (int) (elapsed/1000)/60;
        delta= (tiempo-interruption);

    }


    public void guarda(View view) {
        coments= comentarios.getText().toString();

        TextView t1, t2, t3 ,t4, t5, t6;
        t1= dialogTime.findViewById(R.id.t1);
        t2= dialogTime.findViewById(R.id.t2);
        t3= dialogTime.findViewById(R.id.t3);
        t4= dialogTime.findViewById(R.id.t4);
        t5= dialogTime.findViewById(R.id.t5);
        t6= dialogTime.findViewById(R.id.t6);


        DataBaseTSP db = new DataBaseTSP(this);
        db.saveTimeLog(""+id_pro, FechaI, FechaF, fase,delta, coments);

        t1.setText("Inicio: "+FechaI);
        t2.setText("Fase :"+fase);
        t3.setText("Interrupciones" +total);
        t4.setText("Delta" +delta);
        t5.setText("Final"+ FechaF);
        t6.setText("Comentarios "+coments);

        Button cerrar = dialogTime.findViewById(R.id.salir);

    }
}
