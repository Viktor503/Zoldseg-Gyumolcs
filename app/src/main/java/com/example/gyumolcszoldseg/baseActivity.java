package com.example.gyumolcszoldseg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class baseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    FloatingActionButton boltbutton;
    BottomNavigationItemView accountbutton, homebutton, loginbutton,cartbutton;
    BottomNavigationView bottomNavigationView;

    FirebaseAuth mAuth;



    public void highlightElementInToolbar(String element){
        switch (element){
            case "home":
                bottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case "bolt":
                bottomNavigationView.setSelectedItemId(R.id.bolt);
                break;
            case "login":
                bottomNavigationView.setSelectedItemId(R.id.login);
                break;
            case "account":
                bottomNavigationView.setSelectedItemId(R.id.account);
                break;
            case "cart":
                bottomNavigationView.setSelectedItemId(R.id.cart);
                break;
            default:

                break;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = findViewById(R.id.bottomAppBar);

        boltbutton = findViewById(R.id.bolt);
        homebutton = findViewById(R.id.home);
        accountbutton = findViewById(R.id.account);
        loginbutton = findViewById(R.id.login);
        cartbutton = findViewById(R.id.cart);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



        loginbutton.setOnClickListener(v -> {
            if (currentUser != null) {
                Toast.makeText(baseActivity.this, "Már be vagy jelentkezve, ha nagyon be szeretnél jelentkezni, akkor jelentkezz ki előtte.",
                        Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(baseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        homebutton.setOnClickListener(v -> {
            Intent intent = new Intent(baseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        boltbutton.setOnClickListener(v -> {
            Intent intent = new Intent(baseActivity.this, ShopActivity.class);
            startActivity(intent);
            finish();
        });
        accountbutton.setOnClickListener(v -> {
            if (currentUser == null) {
                Toast.makeText(baseActivity.this, "Először jelentkezz be, hogy megnézd a profilod.",
                        Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(baseActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cartbutton.setOnClickListener(v -> {
            Intent intent = new Intent(baseActivity.this, cartActivity.class);
            startActivity(intent);
        });


    }

}
