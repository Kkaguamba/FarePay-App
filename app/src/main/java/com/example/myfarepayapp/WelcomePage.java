package com.example.myfarepayapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomePage extends AppCompatActivity {
    Button btn_login,btn_signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        btn_login = findViewById(R.id.login_btn);
        btn_signup = findViewById(R.id.signup_btn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

       /* if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(WelcomePage.this, MainActivity.class));
        }else {
            startActivity(new Intent(WelcomePage.this, Login.class));
        }
        finish();*/

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });
    }
}