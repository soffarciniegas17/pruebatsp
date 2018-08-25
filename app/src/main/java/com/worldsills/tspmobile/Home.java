package com.worldsills.tspmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.worldsills.tspmobile.Adapter.AdapterHome;
import com.worldsills.tspmobile.Modelos.ItemProyecto;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    GridView gridView;
    AdapterHome adapter;
    Dialog dialog;
    ArrayList<ItemProyecto> list;
    private int Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


         dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);

        gridView= findViewById(R.id.grid);

        ingresarProyecto();
        cargar();
    }


    private void cargar(){

        DataBaseTSP dataBaseTSP= new DataBaseTSP(this);
        list= new ArrayList<>();
        try {
            adapter = new AdapterHome(this, R.layout.item_proyecto, list);

            Cursor cursor= dataBaseTSP.proyecto(2, "", "0", "");

            if(cursor.moveToFirst()){
                do {
                    list.add(new ItemProyecto(cursor.getString(1),cursor.getInt(0)));
                }while (cursor.moveToNext());
            }

            gridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e){}




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ItemProyecto itemProyecto= list.get(position);
                Code= itemProyecto.getId();
                abreMenuFunciones();
            }
        });


    }

    private String nombre_proyecto;

    private void ingresarProyecto(){

        TextView textView= findViewById(R.id.onclick);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Button guardar = dialog.findViewById(R.id.sent);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText= dialog.findViewById(R.id.name);
                        nombre_proyecto= editText.getText().toString();



                        if (nombre_proyecto.equals("")){
                           /* final AlertDialog alertDialog= new AlertDialog.Builder(getApplicationContext()).create();
                            alertDialog.setTitle("ERROR");
                            alertDialog.setMessage("El campo está vacío");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.show();*/
                            Toast.makeText(Home.this, "NO GUARDA", Toast.LENGTH_SHORT).show();

                        } else {
                            DataBaseTSP dataBaseTSP= new DataBaseTSP(Home.this);
                            dataBaseTSP.proyecto(1, nombre_proyecto, "0", "");
                            Toast.makeText(Home.this, "CREO PROYECTO", Toast.LENGTH_SHORT).show();
                            cargar();
                            editText.setText("");
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();
            }
        });
    }


    public void abreMenuFunciones(){


        final Dialog dialogMenu=new Dialog(this);
        dialogMenu.setContentView(R.layout.dialog_menu_funciones);
        dialogMenu.setCanceledOnTouchOutside(false);
        dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView nombreProyec=dialogMenu.findViewById(R.id.menu_funciones_nombre_proyect);
        Button btonTimeLog, btonDefecLog, btonSumary;

        btonTimeLog=dialogMenu.findViewById(R.id.menu_funciones_bton_time_log);
        btonDefecLog=dialogMenu.findViewById(R.id.menu_funciones_bton_defect_log);
        btonSumary=dialogMenu.findViewById(R.id.menu_funciones_bton_primary_sumary);
        ImageButton btonSalir=dialogMenu.findViewById(R.id.menu_funciones_cerrar);





        btonTimeLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent= new Intent(Home.this, TimeLog.class );
             intent.putExtra("CODIGO", Code);
             startActivity(intent);
             finish();
             dialogMenu.dismiss();

            }
        });
        btonDefecLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Home.this, DefectLog.class );
                intent.putExtra("CODIGO", Code);
                startActivity(intent);
                finish();
                dialogMenu.dismiss();
            }
        });
        btonSumary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Home.this, PlanSumary.class );
                intent.putExtra("CODIGO", Code);
                startActivity(intent);
                finish();
                dialogMenu.dismiss();

            }
        });
        btonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMenu.dismiss();
            }
        });

        dialogMenu.show();
    }

}
