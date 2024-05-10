package com.example.gyumolcszoldseg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gyumolcszoldseg.models.aruModell;

import java.util.ArrayList;

public class cartRecycleViewAdapter extends RecyclerView.Adapter<cartRecycleViewAdapter.mviewHolder>{
    Context context;
    ArrayList<aruModell> aruk;

    public cartRecycleViewAdapter(Context context, ArrayList<aruModell> aruk){
        this.context = context;
        this.aruk = aruk;
    }
    public void setAruk(ArrayList<aruModell> arukuj){
        aruk = arukuj;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public cartRecycleViewAdapter.mviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_row, parent, false);
        return new cartRecycleViewAdapter.mviewHolder((ViewGroup) view);
    }

    @Override
    public void onBindViewHolder(@NonNull mviewHolder holder, int position) {

        holder.nev.setText(aruk.get(position).getNev());

        int ar = aruk.get(position).getAr();
        int mennyiseg = aruk.get(position).getDarab();
        holder.arcimke.setText((ar * mennyiseg) + " Ft");
        holder.mennyiseg.setText(mennyiseg+" "+aruk.get(position).getMertekegyseg());
    }

    @Override
    public int getItemCount() {
        return aruk.size();
    }
    public static class mviewHolder extends RecyclerView.ViewHolder {
        TextView nev, arcimke, mennyiseg;

        public mviewHolder(@NonNull ViewGroup parent) {
            super(parent);
            nev = parent.findViewById(R.id.kosarAruNev);
            mennyiseg = parent.findViewById(R.id.kosarAruMennyiseg);
            arcimke = parent.findViewById(R.id.kosarAruAr);

        }
    }
}
