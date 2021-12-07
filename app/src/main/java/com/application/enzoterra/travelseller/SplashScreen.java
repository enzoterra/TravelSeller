package com.application.enzoterra.travelseller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_splash));

        // Timer da splash screen
        int SPLASH_TIME_OUT = 1500;
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, ListaViagens.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT);


    }

}