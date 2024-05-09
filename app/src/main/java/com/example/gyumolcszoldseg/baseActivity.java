package com.example.gyumolcszoldseg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class baseActivity extends AppCompatActivity {

    protected Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(toolbar);


        findViewById(R.id.login).setOnClickListener(v -> {
            Intent intent = new Intent(baseActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(baseActivity.this, MainActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bolt).setOnClickListener(v -> {
            Intent intent = new Intent(baseActivity.this, ShopActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.account).setOnClickListener(v -> {
            Intent intent = new Intent(baseActivity.this, ProfileActivity.class);
            startActivity(intent);
        });


    }

}
