package com.worldsills.tspmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.worldsills.tspmobile.Adapter.AdapterHome;
import com.worldsills.tspmobile.Modelos.ItemProyecto;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    GridView gridView;
    AdapterHome adapter;
    ArrayList<ItemProyecto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        gridView= findViewById(R.id.grid);

        ingresarProyecto();
    }


    private void cargar(){

        DataBaseTSP dataBaseTSP= new DataBaseTSP(this);
        try {
            adapter = new AdapterHome(this, R.layout.item_proyecto, list);
            list= new ArrayList<>();

            Cursor cursor= dataBaseTSP.proyecto(2, "", 0, "");

            if(cursor.moveToFirst()){
                do {

                    list.add(new ItemProyecto(cursor.getString(1),cursor.getInt(0)));
                }while (cursor.moveToNext());
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e){}

    }

    private String nombre_proyecto;

    private void ingresarProyecto(){
        final DataBaseTSP dataBaseTSP= new DataBaseTSP(this);
        final Dialog dialog = new Dialog(this);
        TextView textView= findViewById(R.id.onclick);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText editText= dialog.findViewById(R.id.name);
                nombre_proyecto= editText.getText().toString();


                final Button guardar = dialog.findViewById(R.id.sent);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.equals("")){
                            final AlertDialog alertDialog= new AlertDialog.Builder(getApplicationContext()).create();
                            alertDialog.setTitle("ERROR");
                            alertDialog.setMessage("El campo está vacío");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });

                            dialog.show();
                        } else {
                            dataBaseTSP.proyecto(1, nombre_proyecto, 0, "");

                        }

                        editText.setText("");
                        cargar();
                    }
                });

                dialog.show();
            }
        });
    }
}
