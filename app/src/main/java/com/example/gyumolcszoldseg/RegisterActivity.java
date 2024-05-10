package com.example.gyumolcszoldseg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gyumolcszoldseg.models.userModell;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.mindrot.jbcrypt.BCrypt;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RegisterActivity extends baseActivity {

    FirebaseAuth mAuth;
    ProgressBar progressBar;
    View the_fog;
    TextView tologin;
    Button registerButton;
    TextView emailin;
    TextView passwordin;
    TextView phonein;
    TextView addressin;

    FirebaseFirestore mFirestore;
    CollectionReference mItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("users");

        progressBar = findViewById(R.id.registerProgressBar);
        the_fog = findViewById(R.id.fadeBackground);
        tologin = findViewById(R.id.toLogin);
        registerButton = findViewById(R.id.registerButton);
        emailin = findViewById(R.id.editTextTextEmailAddress);
        passwordin = findViewById(R.id.editTextTextPassword);
        phonein = findViewById(R.id.editTextPhone);
        addressin = findViewById(R.id.editTextTextPostalAddress);



        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                the_fog.setVisibility(View.VISIBLE);
                String email = String.valueOf(emailin.getText());
                String password = String.valueOf(passwordin.getText());
                String phone = String.valueOf(phonein.getText());
                String address = String.valueOf(addressin.getText());
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
                    Toast.makeText(RegisterActivity.this, "Légyszíves az összes mezőt töltsd ki megfelelően", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    the_fog.setVisibility(View.GONE);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                the_fog.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    String newpass = BCrypt.hashpw(password, BCrypt.gensalt());
                                    userModell user = new userModell(email, newpass, address, phone);

                                    mItems.document(email).set(user);

                                    Toast.makeText(RegisterActivity.this, "Fiók sikeresen létrehozva.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Probléma történt a regisztráció során.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }


}