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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Viagens;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Clientes extends AppCompatActivity {

    //Variáveis
    ArrayAdapter<Viagens> adapterCustom;
    ViagensBD bd;
    ArrayList<Viagens> list_viagens;
    ListView lista;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes);


        //Lista dos Clientes
        this.lista = findViewById(R.id.lista_clientes);

        lista.setOnItemClickListener((adapterView, view, i, j) -> {
            Intent intent = new Intent(Clientes.this, EditarViagem.class);

            intent.putExtra("id", (Clientes.this.list_viagens.get(i)).getId());
            intent.putExtra("nomeCliente", (Clientes.this.list_viagens.get(i)).getNomeCliente());
            intent.putExtra("cpfCliente", (Clientes.this.list_viagens.get(i)).getCpfCliente());
            intent.putExtra("rgCliente", (Clientes.this.list_viagens.get(i)).getRgCliente());
            intent.putExtra("dataNascimentoCliente", (Clientes.this.list_viagens.get(i)).getDataNascimentoCliente());
            intent.putExtra("hotel", (Clientes.this.list_viagens.get(i)).getHotel());
            intent.putExtra("cidade", (Clientes.this.list_viagens.get(i)).getCidade());
            intent.putExtra("localizador", (Clientes.this.list_viagens.get(i)).getLocalizador());
            intent.putExtra("numeroVenda", (Clientes.this.list_viagens.get(i)).getNumeroVenda());
            intent.putExtra("embarqueHora", (Clientes.this.list_viagens.get(i)).getEmbarqueHora());
            intent.putExtra("embarqueData", (Clientes.this.list_viagens.get(i)).getEmbarqueData());
            intent.putExtra("desembarqueHora", (Clientes.this.list_viagens.get(i)).getDesembarqueHora());
            intent.putExtra("desembarqueData",(Clientes.this.list_viagens.get(i)).getDesembarqueData());
            intent.putExtra("observacoes",(Clientes.this.list_viagens.get(i)).getObservacoes());
            intent.putExtra("valorTotal",(Clientes.this.list_viagens.get(i)).getValorTotal());
            intent.putExtra("valorComissao",(Clientes.this.list_viagens.get(i)).getValorComissao());
            intent.putExtra("tela", "clientes");

            Clientes.this.startActivity(intent);
        });


        //Searchview
        final SearchView searchView = findViewById(R.id.pesquisaCliente);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String pesquisa) {
                carregarListaPesquisa(pesquisa);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String pesquisa) {
                if (pesquisa != null && !pesquisa.equals("")) {
                    carregarListaPesquisaAlteracao(pesquisa);
                } else {
                    carregarLista();
                }
                return false;
            }
        });


        //Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_clientes);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_clientes:
                    return true;

                case R.id.nav_viagens:
                    Intent intentViagens = new Intent(Clientes.this, ListaViagens.class);
                    ActivityOptionsCompat activityOptionsCompatViagens = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                    ActivityCompat.startActivity(Clientes.this, intentViagens, activityOptionsCompatViagens.toBundle());
                    break;

                case R.id.nav_estatisticas:
                    Intent intentCalendario = new Intent(Clientes.this, Estatisticas.class);
                    ActivityOptionsCompat activityOptionsCompatCalendario = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                    ActivityCompat.startActivity(Clientes.this, intentCalendario, activityOptionsCompatCalendario.toBundle());
                    break;
            }
            return true;
        });


        //ImageButton para Cadastrar Viagem
        FloatingActionButton floatingButton = findViewById(R.id.floatingButton);
        floatingButton.setOnClickListener(view -> {
            Intent intent = new Intent(Clientes.this, Cadastro.class);
            intent.putExtra("primeiraVez", "sim");
            Clientes.this.startActivity(intent);
        });


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.greenblue_status));

    }

    public void onResume() {
        super.onResume();
        carregarLista();
    }

    //Carregar Lista
    public void carregarLista() {
        ViagensBD viagensBD = new ViagensBD(this);
        this.bd = viagensBD;
        this.list_viagens = viagensBD.getListaClientes();
        this.bd.close();
        if (this.list_viagens != null) {
            this.adapterCustom = new ClientesListAdapter(this, list_viagens);
            this.lista.setAdapter(adapterCustom);
        }
    }

    public void carregarListaPesquisa(String pesquisa) {
        ViagensBD viagensBD = new ViagensBD(this);
        this.bd = viagensBD;
        this.list_viagens = viagensBD.getListaClientesPesquisa(pesquisa);
        this.bd.close();
        if (this.list_viagens != null) {
            this.adapterCustom = new ClientesListAdapter(this, list_viagens);
            this.lista.setAdapter(adapterCustom);
        }
    }

    public void carregarListaPesquisaAlteracao(String pesquisa){
        this.bd = new ViagensBD(this);
        list_viagens = this.bd.getListaClientes();

        if (list_viagens != null) {
            ArrayList<Viagens> nomes = new ArrayList<>();
            for (int i = 0; i < list_viagens.size(); i++) {
                if(list_viagens.get(i).getNomeCliente().toUpperCase().contains(pesquisa.toUpperCase())) {
                    nomes.add(list_viagens.get(i));
                }
            }
            list_viagens = nomes;
            this.adapterCustom = new ClientesListAdapter(this, list_viagens);
            this.lista.setAdapter(adapterCustom);
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