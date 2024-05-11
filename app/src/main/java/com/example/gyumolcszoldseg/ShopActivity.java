package com.example.gyumolcszoldseg;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gyumolcszoldseg.models.aruModell;
import com.example.gyumolcszoldseg.models.aruViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
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

    HashMap<String, Integer> arudb = new HashMap<>();
    int[] aruImages = {
            R.drawable.ban_n,
            R.drawable.burgonya,
            R.drawable.fejesk_poszta,
            R.drawable.kapor,
            R.drawable.k_gy_uborka,
            R.drawable.lilahagyma,
            R.drawable.narancs,
            R.drawable.paprika,
            R.drawable.paradicsom,
            R.drawable.petrezselyem,
            R.drawable.voroshagyma,
            R.drawable.kaliforniai_paprika,
            R.drawable.ujkrumpli,
            R.drawable.petrezselyem_gyoker,
            R.drawable.fokhagyma,
            R.drawable.idared_alma,
            R.drawable.golden_alma,
            R.drawable.starking_alma,
            R.drawable.prince_alma,
            R.drawable.granny_smith_alma,
            R.drawable.kaiser_korte,
            R.drawable.vilmos_korte,
            R.drawable.mazsola_szolo,
            R.drawable.citrom,
            R.drawable.grapefruit,
            R.drawable.mandarin,
            R.drawable.foldieper,
            R.drawable.kelkaposzta,
            R.drawable.karfiol,
            R.drawable.lilakaposzta,
            R.drawable.zeller,
            R.drawable.karalabe,
            R.drawable.zoldhagyma,
            R.drawable.porehagyma,
            R.drawable.gomba,
            R.drawable.jegsalata,
            R.drawable.spenot,
            R.drawable.rukkola,
            R.drawable.sparga,
            R.drawable.avokado,
            R.drawable.mango,
            R.drawable.gorogdinnye,
            R.drawable.sargadinnye,
            R.drawable.brokkoli,
            R.drawable.afonya,
            R.drawable.malna,
            R.drawable.gyongybab,
            R.drawable.kokobab,
            R.drawable.orias_feher_bab,
            R.drawable.pattogtatni_valo_kukorica,
            R.drawable.lencse,
            R.drawable.savanyu_kaposzta,
            R.drawable.savanyukaposzta_level,
            R.drawable.csalamadejpg,
            R.drawable.csemege_uborka,
            R.drawable.kovaszos_uborka,
            R.drawable.almapaprika,
            R.drawable.koktelparadicsom,
            R.drawable.sarga_repa

    };

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


        RecyclerView recyclerView = findViewById(R.id.shopRecyclerView);
        madapter = new aruRecyclerViewAdapter(this, aruk);
        recyclerView.setAdapter(madapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        if (aruk.size() == 0) {
            mItems.orderBy("nev").get().addOnSuccessListener(queryDocumentSnapshots -> {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    try {
                        aruModell item = new aruModell(doc.getString("nev"),
                                doc.getString("mertekegyseg"),
                                doc.getLong("ar").intValue(),
                                doc.getLong("img").intValue(),
                                arudb.containsKey(doc.getString("nev")) ? arudb.get(doc.getString("nev")) : 0);
                        aruk.add(item);
                    } catch (Exception e) {
                        System.out.println(doc);
                    }
                }
                madapter.notifyDataSetChanged();
            });
        }else{
            for(aruModell aru: aruk){
                aru.setDarab(arudb.containsKey(aru.getNev()) ? arudb.get(aru.getNev()) : 0);
                madapter.notifyDataSetChanged();
            }
        }
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
                    aruImages[i],
                    0));
        }

    }
}