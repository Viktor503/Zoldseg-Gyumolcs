package com.example.gyumolcszoldseg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends baseActivity {

    FirebaseAuth mAuth;
    ProgressBar progressBar;
    View the_fog;

    TextView toregister;
    Button loginButton;
    TextView emailin;
    TextView passwordin;


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.loginProgressBar);
        the_fog = findViewById(R.id.fadeBackground);
        toregister = findViewById(R.id.toRegister);
        loginButton = findViewById(R.id.buttonLogin);
        emailin = findViewById(R.id.editTextTextEmailAddress);
        passwordin = findViewById(R.id.editTextTextPassword);


        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                the_fog.setVisibility(View.VISIBLE);
                String email = String.valueOf(emailin.getText());
                String password = String.valueOf(passwordin.getText());
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Légyszíves az összes mezőt töltsd ki megfelelően", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    the_fog.setVisibility(View.GONE);
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                the_fog.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Sikertelen bejelentkezés.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

}
