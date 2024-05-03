package com.example.gyumolcszoldseg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gyumolcszoldseg.models.aruModell;

import java.util.ArrayList;

public class aru_RecyclerViewAdapter extends RecyclerView.Adapter<aru_RecyclerViewAdapter.mviewHolder>  {
    Context context;
    ArrayList<aruModell> aruk;


    public aru_RecyclerViewAdapter(Context context, ArrayList<aruModell> aruk){
        this.context = context;
        this.aruk = aruk;
    }

    @NonNull
    @Override
    public aru_RecyclerViewAdapter.mviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shop_row, parent, false);
        return new mviewHolder((ViewGroup) view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull aru_RecyclerViewAdapter.mviewHolder holder, int position) {
        holder.imageView.setImageResource(aruk.get(position).getImg());
        holder.nev.setText(aruk.get(position).getNev());
        holder.arcimke.setText(aruk.get(position).getAr()+" Ft/"+aruk.get(position).getMertekegyseg());

    }

    @Override
    public int getItemCount() {
        return aruk.size();
    }

    public static class mviewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nev, arcimke;
        public mviewHolder(@NonNull ViewGroup parent) {
            super(parent);
            imageView = parent.findViewById(R.id.aruKep);
            nev = parent.findViewById(R.id.aruNev);
            arcimke = parent.findViewById(R.id.aruArcimke);
        }
    }
}
