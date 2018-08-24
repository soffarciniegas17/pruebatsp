package com.worldsills.tspmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.worldsills.tspmobile.Modelos.ItemProyecto;
import com.worldsills.tspmobile.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterHome extends BaseAdapter {

    Context context;
    private int plantilla;
    private ArrayList<ItemProyecto> list;


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= convertView;
        Holder holder= new Holder();

        if(view==null){
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(plantilla, null);

            holder.title= view.findViewById(R.id.proyecto_name);

            view.setTag(holder);
        } else {
            holder= (Holder) view.getTag();
        }

        ItemProyecto itemProyecto= list.get(position);

        holder.title.setText(itemProyecto.getNombre());

        return view;
    }


    public class Holder{
        TextView title;
    }
}
