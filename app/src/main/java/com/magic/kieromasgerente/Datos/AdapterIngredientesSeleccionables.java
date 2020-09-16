package com.magic.kieromasgerente.Datos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.magic.kieromasgerente.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterIngredientesSeleccionables extends RecyclerView.Adapter<AdapterIngredientesSeleccionables.ViewHolder> {

    private ArrayList<HashMap<String,String>> mData;
    private LayoutInflater mInflater;


    public AdapterIngredientesSeleccionables(Context context, ArrayList<HashMap<String, String>> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public AdapterIngredientesSeleccionables.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_componente,parent,false);
        return new AdapterIngredientesSeleccionables.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterIngredientesSeleccionables.ViewHolder holder, int position) {
        String nombreItem = mData.get(position).get("nombre");
        holder.myTextView.setText(nombreItem);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textView6122264318);
        }
    }
}
