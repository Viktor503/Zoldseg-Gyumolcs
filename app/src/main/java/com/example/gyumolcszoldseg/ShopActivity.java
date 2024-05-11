package com.example.gyumolcszoldseg;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.gyumolcszoldseg.models.aruModell;
import com.example.gyumolcszoldseg.models.aruViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopActivity extends baseActivity {

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    ArrayList<aruModell> aruk = new ArrayList<>();
    ArrayList<aruModell> cart = new ArrayList<>();
    aruViewModel aruViewModel;

    aruRecyclerViewAdapter madapter;

    Spinner spinner;

    int sortby = 0;

    boolean currently_requesting = false;
    HashMap<String, Integer> arudb = new HashMap<>();
    HashMap<String, Integer> aruImages = new HashMap<String, Integer>(){{
        put("banán",R.drawable.ban_n);
        put("burgonya",R.drawable.burgonya);
        put("fejeskáposzta",R.drawable.fejesk_poszta);
        put("kapor",R.drawable.kapor);
        put("kígyóuborka",R.drawable.k_gy_uborka);
        put("lilahagyma",R.drawable.lilahagyma);
        put("narancs",R.drawable.narancs);
        put("TV paprika",R.drawable.paprika);
        put("paradicsom",R.drawable.paradicsom);
        put("petrezselyem zöld",R.drawable.petrezselyem);
        put("vöröshagyma",R.drawable.voroshagyma);
        put("kalifornia paprika",R.drawable.kaliforniai_paprika);
        put("újkrumpli",R.drawable.ujkrumpli);
        put("petrezselyem gyökér",R.drawable.petrezselyem_gyoker);
        put("fokhagyma",R.drawable.fokhagyma);
        put("idared alma",R.drawable.idared_alma);
        put("golden alma",R.drawable.golden_alma);
        put("starking alma",R.drawable.starking_alma);
        put("prince alma",R.drawable.prince_alma);
        put("granny smith alma",R.drawable.granny_smith_alma);
        put("kaiser körte",R.drawable.kaiser_korte);
        put("vilmos körte",R.drawable.vilmos_korte);
        put("mazsola szőlő",R.drawable.mazsola_szolo);
        put("citrom",R.drawable.citrom);
        put("grapefruit",R.drawable.grapefruit);
        put("mandarin",R.drawable.mandarin);
        put("földieper",R.drawable.foldieper);
        put("kelkáposzta",R.drawable.kelkaposzta);
        put("karfiol",R.drawable.karfiol);
        put("lilakáposzta",R.drawable.lilakaposzta);
        put("zeller",R.drawable.zeller);
        put("karalábé",R.drawable.karalabe);
        put("zöldhagyma",R.drawable.zoldhagyma);
        put("póréhagyma",R.drawable.porehagyma);
        put("gomba",R.drawable.gomba);
        put("jégsaláta",R.drawable.jegsalata);
        put("spenót",R.drawable.spenot);
        put("rukkola",R.drawable.rukkola);
        put("spárga",R.drawable.sparga);
        put("avokádó",R.drawable.avokado);
        put("mangó",R.drawable.mango);
        put("görögdinnye",R.drawable.gorogdinnye);
        put("sárgadinnye",R.drawable.sargadinnye);
        put("brokkoli",R.drawable.brokkoli);
        put("áfonya",R.drawable.afonya);
        put("málna",R.drawable.malna);
        put("gyöngybab",R.drawable.gyongybab);
        put("kokóbab",R.drawable.kokobab);
        put("óriásfehérbab",R.drawable.orias_feher_bab);
        put("pattogatni való kukorica",R.drawable.pattogtatni_valo_kukorica);
        put("lencse",R.drawable.lencse);
        put("savanyúkáposzta",R.drawable.savanyu_kaposzta);
        put("savanyúkáposzta levél",R.drawable.savanyukaposzta_level);
        put("csalamádé",R.drawable.csalamadejpg);
        put("csemege uborka",R.drawable.csemege_uborka);
        put("kovászos uborka",R.drawable.kovaszos_uborka);
        put("alma paprika",R.drawable.almapaprika);
        put("koktél paradicsom",R.drawable.koktelparadicsom);
        put("sárgarépa",R.drawable.sarga_repa);
    }};

    void getdbfromCart(ArrayList<aruModell> cart){
        String[] nevek = getResources().getStringArray(R.array.gyumolcszoldsegnevek);
        if(cart.size()==0){
            for(String nev: nevek){
                arudb.put(nev, 0);
            }
            return;
        }

        for(int i=0; i<nevek.length; i++){
            for(aruModell aru: cart){
                if(aru.getNev().equals(nevek[i])){
                    arudb.put(aru.getNev(), aru.getDarab());
                    break;
                }
                if(aru == cart.get(cart.size()-1)){
                    arudb.put(nevek[i], 0);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_shop);
        super.onCreate(savedInstanceState);


        aruViewModel = new ViewModelProvider(this).get(aruViewModel.class);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("aruk");

        spinner = findViewById(R.id.spinner);
        RecyclerView recyclerView = findViewById(R.id.shopRecyclerView);
        madapter = new aruRecyclerViewAdapter(this, aruk);
        recyclerView.setAdapter(madapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                aruk.clear();
                getdbfromCart(cart);
                sortby = i;
                queryData();
                madapter.setAruk(aruk);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        aruViewModel.getAruk().observe(this, new Observer<List<aruModell>>() {
            @Override
            public void onChanged(List<aruModell> aruModells) {
                cart = (ArrayList<aruModell>) aruModells;
                getdbfromCart(cart);
                queryData();
                madapter.setAruk(aruk);
            }
        });


    }

    private void queryData() {
        if (aruk.size() == 0 && !currently_requesting) {
            currently_requesting = true;
            switch (sortby) {
                case 0:
                    mItems.orderBy("nev").get().addOnSuccessListener(this::fillData);
                    break;
                case 1:
                    mItems.orderBy("nev", Query.Direction.DESCENDING).get().addOnSuccessListener(this::fillData);
                    break;
                case 2:
                    mItems.orderBy("ar").get().addOnSuccessListener(this::fillData);
                    break;
                case 3:
                    mItems.orderBy("ar", Query.Direction.DESCENDING).get().addOnSuccessListener(this::fillData);
                    break;
            }

        }else{
            for(aruModell aru: aruk){
                aru.setDarab(arudb.containsKey(aru.getNev()) ? arudb.get(aru.getNev()) : 0);
                madapter.notifyDataSetChanged();
            }
        }
    }




    private void fillData(QuerySnapshot queryDocumentSnapshots) {
        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
            aruk.add(new aruModell(
                    document.getString("nev"),
                    document.getString("mertekegyseg"),
                    document.getLong("ar").intValue(),
                    aruImages.get(document.getString("nev")),
                    arudb.containsKey(document.getString("nev")) ? arudb.get(document.getString("nev")) : 0
            ));
        }
        madapter.notifyDataSetChanged();
        currently_requesting = false;
    }

    private void setupModels(){
        aruk.clear();
        String[] nevek = getResources().getStringArray(R.array.gyumolcszoldsegnevek);
        String[] mertekegysegek = getResources().getStringArray(R.array.gyumolcszoldsegmertekegyseg);
        if(arudb.size() == 0){
            arudb = new HashMap<>();
        }

        int[] arak = getResources().getIntArray(R.array.gyumocszoldsegarak);
        for (int i = 0; i < nevek.length; i++) {
            mItems.add(new aruModell(
                    nevek[i],
                    mertekegysegek[i],
                    arak[i],
                    aruImages.get(nevek[i]),
                    0));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aruk.clear();
    }
}