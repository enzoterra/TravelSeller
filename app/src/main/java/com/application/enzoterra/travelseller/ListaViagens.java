package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Viagens;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

public class ListaViagens extends AppCompatActivity {

    //Variáveis
    ArrayAdapter<Viagens> adapterCustom;
    ViagensBD bd;
    ArrayList<Viagens> list_viagens;
    ListView lista;

    @SuppressLint({"NonConstantResourceId", "ShortAlarm"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_viagens);

        Context context = ListaViagens.this;


        //Verificar Notificações
        //Definir a hora de início
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);

        //boolean alarmUp = (PendingIntent.getBroadcast(context, 0, new Intent(context, NotificationCreator.class), PendingIntent.FLAG_NO_CREATE) != null);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, new Intent(context, NotificationCreator.class), 0);

        //Cancela um possível alarme existente
        alarmManager.cancel(pendingIntent);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        /*if(pendingIntent != null){
            PendingIntent notificacaoPendingIntent = PendingIntent.getBroadcast(context,0, new Intent(context, NotificationCreator.class),0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notificacaoPendingIntent);
            alarmManager.cancel(pendingIntent);
        }*/



        //Lista das Viagens
        this.lista = findViewById(R.id.lista_viagens);
        lista.setOnItemClickListener((adapterView, view, i, j) -> {
            Intent intent = new Intent(ListaViagens.this, EditarViagem.class);

            intent.putExtra("id", (ListaViagens.this.list_viagens.get(i)).getId());
            intent.putExtra("nomeCliente", (ListaViagens.this.list_viagens.get(i)).getNomeCliente());
            intent.putExtra("cpfCliente", (ListaViagens.this.list_viagens.get(i)).getCpfCliente());
            intent.putExtra("rgCliente", (ListaViagens.this.list_viagens.get(i)).getRgCliente());
            intent.putExtra("dataNascimentoCliente", (ListaViagens.this.list_viagens.get(i)).getDataNascimentoCliente());
            intent.putExtra("hotel", (ListaViagens.this.list_viagens.get(i)).getHotel());
            intent.putExtra("cidade", (ListaViagens.this.list_viagens.get(i)).getCidade());
            intent.putExtra("localizador", (ListaViagens.this.list_viagens.get(i)).getLocalizador());
            intent.putExtra("companhiaAerea", (ListaViagens.this.list_viagens.get(i)).getCompanhiaAerea());
            intent.putExtra("numeroVenda", (ListaViagens.this.list_viagens.get(i)).getNumeroVenda());
            intent.putExtra("embarqueHora", (ListaViagens.this.list_viagens.get(i)).getEmbarqueHora());
            intent.putExtra("embarqueData", (ListaViagens.this.list_viagens.get(i)).getEmbarqueData());
            intent.putExtra("desembarqueHora", (ListaViagens.this.list_viagens.get(i)).getDesembarqueHora());
            intent.putExtra("desembarqueData",(ListaViagens.this.list_viagens.get(i)).getDesembarqueData());
            intent.putExtra("observacoes",(ListaViagens.this.list_viagens.get(i)).getObservacoes());
            intent.putExtra("valorTotal",(ListaViagens.this.list_viagens.get(i)).getValorTotal());
            intent.putExtra("valorComissao",(ListaViagens.this.list_viagens.get(i)).getValorComissao());
            intent.putExtra("tela", "viagens");

            ListaViagens.this.startActivity(intent);
        });



        //Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_viagens);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_clientes:
                    Intent intentClientes = new Intent(ListaViagens.this, Clientes.class);
                    ActivityOptionsCompat activityOptionsCompatClientes = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                    ActivityCompat.startActivity(ListaViagens.this, intentClientes, activityOptionsCompatClientes.toBundle());
                    break;

                case R.id.nav_viagens:
                    break;

                case R.id.nav_estatisticas:
                    Intent intentCalendario = new Intent(ListaViagens.this, Estatisticas.class);
                    ActivityOptionsCompat activityOptionsCompatCalendario = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                    ActivityCompat.startActivity(ListaViagens.this, intentCalendario, activityOptionsCompatCalendario.toBundle());
                    break;
            }
            return true;
        });


        //Button Configurações
        ImageButton buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(view -> startActivity(new Intent(ListaViagens.this, Settings.class)));


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue_status));

    }

    public void onResume() {
        super.onResume();
        carregarLista();
    }

    //Carregar Lista
    public void carregarLista() {
        ViagensBD viagensBD = new ViagensBD(this);
        this.bd = viagensBD;
        this.list_viagens = viagensBD.getListaViagens();
        this.bd.close();
        if (this.list_viagens != null) {
            ArrayList<Viagens> viagensAdapter = new ArrayList<>();
            for (int i = 0; i<list_viagens.size(); i++){

                String dataViagem = list_viagens.get(i).getDesembarqueData();

                if(dataViagem==null || dataViagem.equals("")){
                    dataViagem = list_viagens.get(i).getEmbarqueData();
                }

                if(dataViagem!=null && !dataViagem.equals("")){
                    try {
                        Date date = new Date();

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                        String[] desembarqueStr = dataViagem.split("/");
                        String desembarque = desembarqueStr[0].concat("-").concat(desembarqueStr[1]).concat("-").concat(desembarqueStr[2]);
                        Date dataDesembarque = format.parse(desembarque);

                        if(date.before(dataDesembarque)){
                            viagensAdapter.add(list_viagens.get(i));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            list_viagens = viagensAdapter;
            adapterCustom = new ViagensListAdapter(this, list_viagens);
            lista.setAdapter(adapterCustom);
        }
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