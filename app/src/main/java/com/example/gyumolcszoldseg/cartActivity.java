package com.example.gyumolcszoldseg;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gyumolcszoldseg.models.aruModell;
import com.example.gyumolcszoldseg.models.aruViewModel;

import java.util.ArrayList;
import java.util.List;

public class cartActivity extends baseActivity {

    private com.example.gyumolcszoldseg.models.aruViewModel aruViewModel;
    private ArrayList<aruModell> aruk = new ArrayList<>();

    private NotificationHelper notificationHelper;
    private AlarmManager alarmManager;

    Button deleteCart;
    Button orderCart;

    TextView osszKoltseg;
    TextView szallitasidij;

    TextView vegosszeg;

    private int osszeg(){
        int osszeg = 0;
        for(aruModell aru: aruk){
            osszeg += aru.getAr() * aru.getDarab();
        }
        return osszeg;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_cart);
        super.onCreate(savedInstanceState);
        super.highlightElementInToolbar("cart");
        cartRecycleViewAdapter adapter = new cartRecycleViewAdapter(this, aruk);
        RecyclerView recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        deleteCart = findViewById(R.id.deleteCart);
        orderCart = findViewById(R.id.orderCart);

        osszKoltseg = findViewById(R.id.osszeskoltseg);
        szallitasidij = findViewById(R.id.szallitasi);
        vegosszeg = findViewById(R.id.vegosszeg);


        aruViewModel = new ViewModelProvider(this).get(aruViewModel.class);

        List<aruModell> a = aruViewModel.getAruk().getValue();
        aruViewModel.getAruk().observe(this, new Observer<List<aruModell>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onChanged(List<aruModell> aruModells) {
                        aruk = (ArrayList<aruModell>) aruModells;
                        adapter.setAruk(aruk);
                        if(aruk.size()==0){
                            szallitasidij.setText("Szállítási díj: - Ft");
                            osszKoltseg.setText("Termékek ára: - Ft");
                            vegosszeg.setText("Végösszeg: - Ft");
                        }else {
                            osszKoltseg.setText("Termékek ára: " + osszeg() + " Ft");
                            if (osszeg() < 10000) {
                                szallitasidij.setText("Szállítási díj: 1000 Ft");
                            } else {
                                szallitasidij.setText("Szállítási díj: 0 Ft");
                            }
                            vegosszeg.setText("Végösszeg: " + (osszeg() + (osszeg() < 10000 ? 1000 : 0)) + " Ft");
                        }
                    }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);



        deleteCart.setOnClickListener(v -> {
            if(aruk.size() == 0){
                Toast.makeText(this, "A kosarad már üres.", Toast.LENGTH_SHORT).show();
                return;
            }
            builder.setTitle("Törlés");
            builder.setMessage("Biztosan kiüríted a kosarad?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                aruViewModel.deleteAll();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog alert = builder.create();
            alert.show();
        });

        orderCart.setOnClickListener(v -> {
            if(aruk.size() == 0){
                Toast.makeText(this, "A kosarad üres.", Toast.LENGTH_SHORT).show();
                return;
            }
            builder.setTitle("Rendelés");
            builder.setMessage("Biztosan leadod a rendelésed?");
            builder.setPositiveButton("Yes", (dialog, which) -> {

                StringBuilder s = new StringBuilder();
                for (aruModell aru: aruk){
                    s = s.append(aru.getDarab()).append(" ").append(aru.getMertekegyseg()).append(" ").append(aru.getNev()).append("\n");
                }
                notificationHelper.send("A rendelésed sikeres volt, a futár nemsokára indul.\nA rendelésed:\n"+s);
                setAlarmManager();
                aruViewModel.deleteAll();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog alert = builder.create();
            alert.show();
        });

        notificationHelper = new NotificationHelper(this);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private void setAlarmManager(){
        long arrivaltime = (long) (AlarmManager.INTERVAL_HALF_HOUR*((Math.random()/2.5)+0.8));
        long triggerTime = System.currentTimeMillis() + arrivaltime;
        Intent intent = new Intent(this, AlarmReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }
}