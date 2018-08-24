package com.worldsills.tspmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    Spinner spinner;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        gridView= findViewById(R.id.grid);
        spinner= findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(this,R.array.fases, android.R.layout.activity_list_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
    }


    private void cargar(){

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
