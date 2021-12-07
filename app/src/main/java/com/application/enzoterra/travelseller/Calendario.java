package com.application.enzoterra.travelseller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Calendario extends AppCompatActivity {

    CalendarView calendar;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);

        calendar = findViewById(R.id.calendar);

        //Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById
                (R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_calendario);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_clientes:
                    Intent intentClientes = new Intent(Calendario.this, Clientes.class);
                    ActivityOptionsCompat activityOptionsCompatClientes = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                    ActivityCompat.startActivity(Calendario.this, intentClientes, activityOptionsCompatClientes.toBundle());
                    break;

                case R.id.nav_viagens:
                    Intent intentViagens = new Intent(Calendario.this, ListaViagens.class);
                    ActivityOptionsCompat activityOptionsCompatViagens = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                    ActivityCompat.startActivity(Calendario.this, intentViagens, activityOptionsCompatViagens.toBundle());
                    break;

                case R.id.nav_calendario:
                    return true;

            }
            return true;
        });


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.lightblue_status));

    }


    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}