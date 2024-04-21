package com.example.myfarepayapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    Button btnReset,btnBack;
    ProgressBar progressBar;
    EditText edtEmail;
    String  txtEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        //Initialization
        btnBack = findViewById(R.id.btn_back);
        btnReset = findViewById(R.id.btn_reset);
        progressBar = findViewById(R.id.progressBar_reset);
        edtEmail = findViewById(R.id.email);


        mAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail = edtEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(txtEmail)){
                    if (txtEmail.matches(emailPattern)){
                        ResetPassword();
                    }else {
                        edtEmail.setError("Enter valid email address");
                    }
                }else {
                    edtEmail.setError("Email field can't be empty!");
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void ResetPassword() {
        progressBar.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(txtEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPassword.this, "Reset password has been sent to the registered email",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ForgotPassword.this, Login.class);
                startActivity(i);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPassword.this, "Error :- " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        });
    }
}