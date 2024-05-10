package com.example.gyumolcszoldseg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gyumolcszoldseg.dao.aruDAO;
import com.example.gyumolcszoldseg.db.aruRoomdb;
import com.example.gyumolcszoldseg.models.aruModell;

import java.util.ArrayList;

public class aruRecyclerViewAdapter extends RecyclerView.Adapter<aruRecyclerViewAdapter.mviewHolder>  {
    Context context;
    ArrayList<aruModell> aruk;
    int lastPosition = -1;
    int firstPosition = 20;


    public aruRecyclerViewAdapter(Context context, ArrayList<aruModell> aruk){
        this.context = context;
        this.aruk = aruk;
    }
    public void setAruk(ArrayList<aruModell> arukuj){
        aruk = arukuj;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public aruRecyclerViewAdapter.mviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shop_row, parent, false);
        return new mviewHolder((ViewGroup) view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull aruRecyclerViewAdapter.mviewHolder holder, int position) {
        holder.imageView.setImageResource(aruk.get(position).getImg());
        holder.nev.setText(aruk.get(position).getNev());
        holder.db.setText(String.valueOf(aruk.get(position).getDarab()));

        holder.ar = aruk.get(position).getAr();
        holder.mertekegyseg = aruk.get(position).getMertekegyseg();
        holder.arcimke.setText(holder.ar + " Ft/" + holder.mertekegyseg);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }



    }

    @Override
    public int getItemCount() {
        return aruk.size();
    }

    public static class mviewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nev, arcimke;

        TextView plus;
        TextView minus;
        TextView db;

        int ar;
        String mertekegyseg;
        int img;


        public mviewHolder(@NonNull ViewGroup parent) {
            super(parent);
            imageView = parent.findViewById(R.id.aruKep);
            nev = parent.findViewById(R.id.aruNev);
            arcimke = parent.findViewById(R.id.aruArcimke);
            plus = parent.findViewById(R.id.aruPlus);
            minus = parent.findViewById(R.id.aruMinus);
            db = parent.findViewById(R.id.aruKosarbandb);

            ar = Integer.parseInt(arcimke.getText().toString().split("/")[0].split(" ")[0]);
            mertekegyseg = arcimke.getText().toString().split("/")[1];
            img = imageView.getId();

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = Integer.parseInt(db.getText().toString());
                    quantity++;
                    addOne addOne = new addOne(aruRoomdb.getInstance(parent.getContext()));
                    addOne.execute(new aruModell(nev.getText().toString(), mertekegyseg, ar, img, quantity));
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = Integer.parseInt(db.getText().toString());
                    if(quantity-1 > 0){
                        quantity--;
                        addOne addOne = new addOne(aruRoomdb.getInstance(parent.getContext()));
                        addOne.execute(new aruModell(nev.getText().toString(), mertekegyseg, ar, img, quantity));
                    }else{
                        db.setText("0");
                        delfromcart delfromcart = new delfromcart(aruRoomdb.getInstance(parent.getContext()));
                        delfromcart.execute(nev.getText().toString());
                    }
                }

            });
        }


        private static class addOne extends AsyncTask<aruModell, Void, Void> {
            private aruDAO dao;


            addOne(aruRoomdb db) {
                this.dao = db.aruModellDAO();
            }

            @Override
            protected Void doInBackground(aruModell... aruModells) {
                dao.insert(aruModells[0]);
                return null;
            }
        }

        private static class delfromcart extends AsyncTask<String, Void, Void> {
            private aruDAO dao;


            delfromcart(aruRoomdb db) {
                this.dao = db.aruModellDAO();
            }

            @Override()
            protected Void doInBackground(String... strings) {
                dao.delete(strings[0]);
                return null;
            }
        }


    }
}
