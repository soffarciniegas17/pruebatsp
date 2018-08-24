package com.worldsills.tspmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.worldsills.tspmobile.Modelos.ItemInformation;
import com.worldsills.tspmobile.TextViewMelo;

import java.util.ArrayList;

public class AdapterInformation extends BaseAdapter {

    private Context context;
    private int layoutItem;
    private ArrayList<ItemInformation> informations;

    public AdapterInformation(Context context, int layoutItem, ArrayList<ItemInformation> informations) {
        this.context = context;
        this.layoutItem = layoutItem;
        this.informations = informations;
    }

    @Override
    public int getCount() {
        return informations.size();
    }

    @Override
    public Object getItem(int position) {
        return informations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row=view;
        Holder holder=new Holder();

        if (row==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layoutItem, null);



        }else{

        }
        return null;
    }
    class Holder{
        TextViewMelo phase, porcentaje;
    }
}
