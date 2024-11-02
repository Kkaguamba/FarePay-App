package com.example.myfarepayapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Scanner extends AppCompatActivity {
    Button btnScan,btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scanner);
        btnScan = findViewById(R.id.btn_scan);
        btnLogout = findViewById(R.id.btn_logout);

        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(Scanner.this, Login.class);
                startActivity(i);
                finish();
                Toast.makeText(Scanner.this, "Logout successful", Toast.LENGTH_SHORT).show();
            }
        });
        btnScan.setOnClickListener(view ->
        {
            scanCode();
        });
    }
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
     if(result.getContents() !=null)
     {
         AlertDialog.Builder builder = new AlertDialog.Builder(Scanner.this);
         builder.setTitle("Proceed to pay");
         builder.setMessage(result.getContents());
         builder.setNegativeButton("CANCEL", (dialog, which) ->{
             Toast.makeText(Scanner.this, "Cancelled", Toast.LENGTH_SHORT).show();
             dialog.dismiss();

         });
         builder.setPositiveButton("OK", (dialog, which) -> {
             Intent i = new Intent(Scanner.this, Payment_Activity.class);
             i.putExtra("phone", result.getContents());
             i.putExtra("amount", result.getContents());
             startActivity(i);
             Toast.makeText(Scanner.this, "Proceed to pay", Toast.LENGTH_SHORT).show();
             dialog.dismiss();
         });
         AlertDialog dialog = builder.create();
         dialog.show();
     }
    });
}