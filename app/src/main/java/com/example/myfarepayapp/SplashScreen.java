package com.example.myfarepayapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView lottie;
    private Handler handler = new Handler();
    private  Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        lottie = findViewById(R.id.lottie);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, WelcomePage.class);
                startActivity(i);
                finish();//to finish the current activity to prevent it from being displayed again when the user
                //presses the back button.
            }
        };
        handler.postDelayed(runnable,4000);
    }
    //onDestroy() to remove the callback when the activity is destroyed to prevent memory leaks.
    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}