package com.example.gyumolcszoldseg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gyumolcszoldseg.models.aruModell;

import java.util.ArrayList;

public class ShopActivity extends baseActivity {

    ArrayList<aruModell> aruk = new ArrayList<>();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_shop);
        super.onCreate(savedInstanceState);
        setupModels();
        RecyclerView recyclerView = findViewById(R.id.shopRecyclerView);
        aru_RecyclerViewAdapter adapter = new aru_RecyclerViewAdapter(this, aruk);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupModels(){
        String[] nevek = getResources().getStringArray(R.array.gyumolcszoldsegnevek);
        String[] mertekegysegek = getResources().getStringArray(R.array.gyumolcszoldsegmertekegyseg);
        int[] arak = getResources().getIntArray(R.array.gyumocszoldsegarak);
        for (int i = 0; i < nevek.length; i++) {
            aruk.add(new aruModell(
                    nevek[i],
                    mertekegysegek[i],
                    arak[i],
                    aruImages[i]));
        }
    }
}