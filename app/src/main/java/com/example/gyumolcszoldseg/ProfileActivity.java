package com.example.gyumolcszoldseg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileActivity extends baseActivity {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Button logout;
    TextView email;
    TextView telefon;
    TextView lakhely;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        super.highlightElementInToolbar("account");
        logout = findViewById(R.id.buttonLogout);
        email = findViewById(R.id.email);
        telefon = findViewById(R.id.telefon);
        lakhely = findViewById(R.id.lakcim);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Toast.makeText(ProfileActivity.this, "Először jelentkezz be, hogy megnézd a profilod.",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            email.setText(currentUser.getEmail());
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}