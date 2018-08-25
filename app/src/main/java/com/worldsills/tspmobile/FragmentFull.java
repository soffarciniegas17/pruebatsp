package com.worldsills.tspmobile;


import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.worldsills.tspmobile.Adapter.AdapterInformation;
import com.worldsills.tspmobile.Modelos.ItemInformation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFull extends Fragment {

    private int idProyec, posicion, tiempoTotalProyecto;
    private GridView gridView;
    private AdapterInformation adapterInfo;
    private ArrayList<ItemInformation> itemInfo;
    private String[] FASES;


    public FragmentFull() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewFragment=inflater.inflate(R.layout.fragment_fragment_full, container, false);
        gridView=viewFragment.findViewById(R.id.plan_sumary_gridview);

        if (getArguments()!=null){
            idProyec=getArguments().getInt("CODPROYEC");
            posicion=getArguments().getInt("POSICION");

        }

        if (getActivity()!=null && isAdded()){
            FASES=getActivity().getResources().getStringArray(R.array.fases);
            organiza();
        }



        return viewFragment;
    }

    //metodo con el fin de validar si al proyecto ya se le ha agredado un tiempo total
    public boolean validarTiempoProyecto(){
        DataBaseTSP db=new DataBaseTSP(getActivity());
        Cursor cursor=db.proyecto(4,null,0,idProyec+"");

        if (cursor.moveToFirst()){
            Toast.makeText(getActivity(),cursor.getInt(0)+"",Toast.LENGTH_SHORT).show();
            if (cursor.getInt(0)==0){
                return true;
            }
            else {
                tiempoTotalProyecto=cursor.getInt(0);
                Toast.makeText(getActivity(),"ya tiene",Toast.LENGTH_SHORT).show();


            }
        }
        return false;

    }
    public void ingresaTiempo(){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_ingresa_tiempo);
        dialog.setCanceledOnTouchOutside(false);

        ImageButton btonGuardar=dialog.findViewById(R.id.tiempo_guardar_bton);
        final EditText editTextTiempo=dialog.findViewById(R.id.tiempo_edittext);

        btonGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (editTextTiempo.getText().toString().equalsIgnoreCase("") ||

                        Integer.parseInt(editTextTiempo.getText().toString())<1){



                    Toast.makeText(getActivity(),"Ingresa tiempo",Toast.LENGTH_SHORT).show();

                }else{
                    DataBaseTSP db=new DataBaseTSP(getActivity());
                    tiempoTotalProyecto=Integer.parseInt(editTextTiempo.getText().toString());
                    db.proyecto(3,null,tiempoTotalProyecto,idProyec+"");

                    Toast.makeText(getActivity(),"saves: "+tiempoTotalProyecto,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    cargaDatosTimeLog();
                }

            }
        });

        dialog.show();
    }
    //metodo para organizar la informacion que llega de la base de datos al adapter
    public void organiza(){

        itemInfo=new ArrayList<>();

        switch (posicion){

            case 0:
                if (validarTiempoProyecto())ingresaTiempo();
                else cargaDatosTimeLog();
                break;
            case 1:
                cargaDatosDefecLogInjected();
                break;
            case 2:
                cargaDatosDefecLogRemoved();
                break;
        }
        iniciaAdapter();

    }
    public void iniciaAdapter(){
        try{
            adapterInfo=new AdapterInformation(getActivity(),R.layout.item_information,itemInfo);
            gridView.setAdapter(adapterInfo);

        }catch (Exception e){}
        adapterInfo.notifyDataSetChanged();
    }


    public void cargaDatosTimeLog(){
        DataBaseTSP db=new DataBaseTSP(getActivity());
        double porcentaje;

        Cursor cursor=null;
        int delta=0;

        for (int i=0; i<FASES.length; i++){

            cursor=db.getTimelog(idProyec,FASES[i]);
            try{
            if (cursor.moveToFirst() ){
                do{
                    delta+=cursor.getInt(0);
                }while (cursor.moveToNext());
            }

               porcentaje=delta*100/tiempoTotalProyecto;
            }catch (Exception e){
                porcentaje=0.00;
            }

            itemInfo.add(new ItemInformation(porcentaje,FASES[i]));

        }
    }

    private int totalDefect;
    public void cargaDatosDefecLogInjected(){
        DataBaseTSP db=new DataBaseTSP(getActivity());
        totalDefect=totalDefectos();
        int totalDefectFase=0;
        Cursor cursor=null;

        for (int i=0; i<FASES.length;i++){
            cursor=db.getDefecLog(idProyec,FASES[i],null);
            if (cursor==null)return;
            if (cursor.moveToFirst()){
                do{
                    totalDefectFase+=1;
                }while (cursor.moveToNext());
            }
            double porcentaje;
            try {
                 porcentaje = totalDefectFase * 100 / totalDefect;
            }catch (Exception e){
                porcentaje=0.00;
            }
            itemInfo.add(new ItemInformation(porcentaje,FASES[i]));

        }


    }

    public void cargaDatosDefecLogRemoved(){
        DataBaseTSP db=new DataBaseTSP(getActivity());
        totalDefect=totalDefectos();
        int totalDefectFase=0;
        Cursor cursor=null;

        for (int i=0; i<FASES.length;i++){
            cursor=db.getDefecLog(idProyec,null,FASES[i]);
            if (cursor==null)return;
            if (cursor.moveToFirst()){
                do{
                    totalDefectFase+=1;
                }while (cursor.moveToNext());
            }

            double porcentaje;
            try{
                porcentaje=totalDefectFase*100/totalDefect;
            }catch (Exception e){
               porcentaje=0.00;
            }
            itemInfo.add(new ItemInformation(porcentaje,FASES[i]));

        }


    }
    public int totalDefectos(){
        DataBaseTSP db=new DataBaseTSP(getActivity());
        totalDefect=0;
        Cursor cursor=db.getDefecLog(idProyec,null,null);
        if (cursor==null)return 0;
        if (cursor.moveToFirst()){
            do{
                totalDefect++;
            }while (cursor.moveToNext());
        }
        return totalDefect;


    }

    public void abreMenuFunciones(){
        final Dialog dialogMenu=new Dialog(getActivity());
        dialogMenu.setContentView(R.layout.dialog_menu_funciones);
        dialogMenu.setCanceledOnTouchOutside(false);
        dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextViewMelo nombreProyec=dialogMenu.findViewById(R.id.menu_funciones_nombre_proyect);
        Button btonTimeLog, btonDefecLog, btonSumary;

        btonTimeLog=dialogMenu.findViewById(R.id.menu_funciones_bton_time_log);
        btonDefecLog=dialogMenu.findViewById(R.id.menu_funciones_bton_defect_log);
        btonSumary=dialogMenu.findViewById(R.id.menu_funciones_bton_primary_sumary);
        ImageButton btonSalir=dialogMenu.findViewById(R.id.menu_funciones_cerrar);

        btonTimeLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btonDefecLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btonSumary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMenu.dismiss();
            }
        });
    }

}
