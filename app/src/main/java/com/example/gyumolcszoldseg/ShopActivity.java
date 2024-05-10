package com.example.gyumolcszoldseg;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gyumolcszoldseg.models.aruModell;
import com.example.gyumolcszoldseg.models.aruViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends baseActivity {

    ArrayList<aruModell> aruk = new ArrayList<>();
    ArrayList<aruModell> cart = new ArrayList<>();
    aruViewModel aruViewModel;

    int[] arudb = {};
    int[] aruImages = {R.drawable.ban_n,
            R.drawable.burgonya,
            R.drawable.fejesk_poszta,
            R.drawable.kapor,
            R.drawable.k_gy_uborka,
            R.drawable.lilahagyma,
            R.drawable.narancs,
            R.drawable.paprika,
            R.drawable.paradicsom,
            R.drawable.petrezselyem,
            R.drawable.voroshagyma
    };

    void getdbfromCart(ArrayList<aruModell> cart){
        String[] nevek = getResources().getStringArray(R.array.gyumolcszoldsegnevek);
        int[] db = new int[nevek.length];
        for(int i=0; i<nevek.length; i++){
            for(aruModell aru: cart){
                if(aru.getNev().equals(nevek[i])){
                    db[i] = aru.getDarab();
                    break;
                }
                if(aru == cart.get(cart.size()-1)){
                    db[i] = 0;
                }
            }
        }
        arudb = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_shop);
        super.onCreate(savedInstanceState);


        aruViewModel = new ViewModelProvider(this).get(aruViewModel.class);


        setupModels();
        RecyclerView recyclerView = findViewById(R.id.shopRecyclerView);
        aruRecyclerViewAdapter adapter = new aruRecyclerViewAdapter(this, aruk);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        aruViewModel.getAruk().observe(this, new Observer<List<aruModell>>() {
            @Override
            public void onChanged(List<aruModell> aruModells) {
                cart = (ArrayList<aruModell>) aruModells;
                getdbfromCart(cart);
                setupModels();
                adapter.setAruk(aruk);
            }
        });


    }

    private void setupModels(){
        aruk.clear();
        String[] nevek = getResources().getStringArray(R.array.gyumolcszoldsegnevek);
        String[] mertekegysegek = getResources().getStringArray(R.array.gyumolcszoldsegmertekegyseg);
        if(arudb.length == 0){
            arudb = new int[nevek.length];
        }

        int[] arak = getResources().getIntArray(R.array.gyumocszoldsegarak);
        for (int i = 0; i < nevek.length; i++) {
            aruk.add(new aruModell(
                    nevek[i],
                    mertekegysegek[i],
                    arak[i],
                    aruImages[i],
                    arudb[i]));
        }
    }
}