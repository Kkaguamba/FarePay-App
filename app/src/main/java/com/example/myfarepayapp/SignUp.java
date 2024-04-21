package com.example.myfarepayapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    TextView txtSignin;
    EditText edtFullName, edtEmail, edtMobile, edtPassword, edtConf_Pass;
    ProgressBar progressBar;
    Button btnSignUp;
    String txtFullName, txtEmail, txtMobile, txtPassword, txtConf_Pass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        txtSignin = findViewById(R.id.txt_signin);
        edtFullName = findViewById(R.id.full_name);
        edtEmail = findViewById(R.id.email);
        edtMobile = findViewById(R.id.phone);
        edtPassword = findViewById(R.id.password);
        edtConf_Pass = findViewById(R.id.conf_pass);
        progressBar = findViewById(R.id.progressBar_signup);
        btnSignUp = findViewById(R.id.btn_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFullName = edtFullName.getText().toString().trim();
                txtEmail = edtEmail.getText().toString().trim();
                txtMobile = edtMobile.getText().toString().trim();
                txtPassword = edtPassword.getText().toString().trim();
                txtConf_Pass = edtConf_Pass.getText().toString().trim();

                //validating data
                if (!TextUtils.isEmpty(txtFullName)){
                    if (!TextUtils.isEmpty(txtEmail)){
                        if (txtEmail.matches(emailPattern)){
                            if (!TextUtils.isEmpty(txtMobile)){
                                if (txtMobile.length() == 10){
                                    if (!TextUtils.isEmpty(txtPassword)){
                                        if (!TextUtils.isEmpty(txtConf_Pass)){
                                            if (txtConf_Pass.equals(txtPassword)){
                                                SignUpUser();
                                            }else {
                                                edtConf_Pass.setError("Password must be the same!");
                                            }
                                        }else {
                                            edtConf_Pass.setError("Confirm Password field can't be empty!");
                                        }
                                    }else {
                                        edtPassword.setError("Password field can't be empty!");
                                    }
                                }else {
                                    edtMobile.setError("Enter valid mobile number!");
                                }
                            }else {
                                edtMobile.setError("Phone field can't be empty!");
                            }
                        }else {
                            edtEmail.setError("Enter valid email address");
                        }
                    }else {
                        edtEmail.setError("Email field can't be empty!");
                    }
                }else {
                    edtFullName.setError("Name field can't be empty!");
                }
            }
        });

    }
    private void SignUpUser() {
        //progress bar visibility to show processing sign to user and hiding signup button
        //to avoid multiple clicks.
        progressBar.setVisibility(View.VISIBLE);
        btnSignUp.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(txtEmail,txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUp.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, "Error! "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
            }
        });
    }
}