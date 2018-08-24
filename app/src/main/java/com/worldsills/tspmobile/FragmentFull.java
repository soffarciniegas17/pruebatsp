package com.worldsills.tspmobile;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.worldsills.tspmobile.Adapter.AdapterInformation;
import com.worldsills.tspmobile.Modelos.ItemInformation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFull extends Fragment {

    private int idProyec;
    private GridView gridView;
    private AdapterInformation adapterInfo;
    private ArrayList<ItemInformation> itemInfo;


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

        }

        if (getActivity()!=null && isAdded()){

        }



        return viewFragment;
    }
    public boolean validarTiempo(){
        DataBaseTSP db=new DataBaseTSP(getActivity());
        Cursor cursor=db.p;

    }
    public void ingresaTiempo(){

    }

}
