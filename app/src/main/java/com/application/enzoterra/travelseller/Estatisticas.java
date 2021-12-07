package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Estatistica;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;


public class Estatisticas extends AppCompatActivity {

    ArrayAdapter<Integer> adapterCustomInteger;
    ArrayAdapter<String> adapterCustomString;
    ArrayAdapter<Estatistica> adapterCustomViagens;
    ViagensBD bd;
    ArrayList<Estatistica> list_estatisticas, list_viagens;
    ArrayList<Integer> list_anos = new ArrayList<>();
    ArrayList<String> list_meses = new ArrayList<>();
    ListView lista;
    TextView tituloLista, subTitulo1Lista, subTitulo2Lista;
    Button voltar;
    String titulo, ajudaAno, ajudaMes;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estatisticas);


        tituloLista = findViewById(R.id.textViewLista);
        subTitulo1Lista = findViewById(R.id.textViewSubTitulo1);
        subTitulo2Lista = findViewById(R.id.textViewSubTitulo2);

        //Lista dos Clientes
        this.lista = findViewById(R.id.lista_anos);

        lista.setOnItemClickListener((adapterView, view, i, j) -> {
            titulo = tituloLista.getText().toString();

            if(titulo.equals("Valores Anuais")) {
                ajudaAno = String.valueOf(list_anos.get(i));
                tituloLista.setText("Valores Mensais - " + ajudaAno);
                subTitulo1Lista.setText("Para ver as informações das viagens,");
                subTitulo2Lista.setText("clique em algum mês da lista");
                carregarListaMeses();

            } else if (titulo.equals("Valores Mensais - " + ajudaAno)){
                ajudaMes = list_meses.get(i);
                tituloLista.setText("Valores de " + calculaMes(ajudaMes));
                subTitulo1Lista.setText("Para ver as informações detalhadas,");
                subTitulo2Lista.setText("clique em alguma viagem da lista");
                carregarListaViagens(ajudaMes, Integer.parseInt(ajudaAno));

            } else if(titulo.equals("Valores de " + calculaMes(ajudaMes))){
                Intent intent = new Intent(Estatisticas.this, EditarViagem.class);

                intent.putExtra("id", (Estatisticas.this.list_viagens.get(i)).getId());
                intent.putExtra("nomeCliente", (Estatisticas.this.list_viagens.get(i)).getNomeCliente());
                intent.putExtra("cpfCliente", (Estatisticas.this.list_viagens.get(i)).getCpfCliente());
                intent.putExtra("rgCliente", (Estatisticas.this.list_viagens.get(i)).getRgCliente());
                intent.putExtra("dataNascimentoCliente", (Estatisticas.this.list_viagens.get(i)).getDataNascimentoCliente());
                intent.putExtra("hotel", (Estatisticas.this.list_viagens.get(i)).getHotel());
                intent.putExtra("cidade", (Estatisticas.this.list_viagens.get(i)).getCidade());
                intent.putExtra("localizador", (Estatisticas.this.list_viagens.get(i)).getLocalizador());
                intent.putExtra("numeroVenda", (Estatisticas.this.list_viagens.get(i)).getNumeroVenda());
                intent.putExtra("embarqueHora", (Estatisticas.this.list_viagens.get(i)).getEmbarqueHora());
                intent.putExtra("embarqueData", (Estatisticas.this.list_viagens.get(i)).getEmbarqueData());
                intent.putExtra("desembarqueHora", (Estatisticas.this.list_viagens.get(i)).getDesembarqueHora());
                intent.putExtra("desembarqueData",(Estatisticas.this.list_viagens.get(i)).getDesembarqueData());
                intent.putExtra("observacoes",(Estatisticas.this.list_viagens.get(i)).getObservacoes());
                intent.putExtra("valorTotal",(Estatisticas.this.list_viagens.get(i)).getValorTotal());
                intent.putExtra("valorComissao",(Estatisticas.this.list_viagens.get(i)).getValorComissao());
                intent.putExtra("tela", "estatisticas");

                Estatisticas.this.startActivity(intent);
            }
        });


        //Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById
                (R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_estatisticas);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_clientes:
                    Intent intentClientes = new Intent(Estatisticas.this, Clientes.class);
                    ActivityOptionsCompat activityOptionsCompatClientes = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                    ActivityCompat.startActivity(Estatisticas.this, intentClientes, activityOptionsCompatClientes.toBundle());
                    break;

                case R.id.nav_viagens:
                    Intent intentViagens = new Intent(Estatisticas.this, ListaViagens.class);
                    ActivityOptionsCompat activityOptionsCompatViagens = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                    ActivityCompat.startActivity(Estatisticas.this, intentViagens, activityOptionsCompatViagens.toBundle());
                    break;

                case R.id.nav_estatisticas:
                    return true;

            }
            return true;
        });


        //Button Voltar
        voltar = findViewById(R.id.buttonVoltarEstatisticas);
        voltar.setOnClickListener(view -> {
            titulo = tituloLista.getText().toString();
            if(titulo.equals("Valores de " + calculaMes(ajudaMes))){
                tituloLista.setText("Valores Mensais - " + ajudaAno);
                subTitulo1Lista.setText("Para ver as informações das viagens,");
                subTitulo2Lista.setText("clique em algum mês da lista");
                carregarListaMeses();
            } else if(titulo.equals("Valores Mensais - " + ajudaAno)){
                tituloLista.setText("Valores Anuais");
                subTitulo1Lista.setText("Para ver os valores mensais,");
                subTitulo2Lista.setText("clique em algum ano da lista");
                carregarListaAnos();
            }
        });


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.lightblue_status));

    }

    public void onResume() {
        super.onResume();
        carregarListaAnos();
    }

    //Carregar Lista de Anos
    public void carregarListaAnos() {
        this.bd = new ViagensBD(this);
        list_anos = new ArrayList<>();
        ArrayList<Integer> list_total = new ArrayList<>();
        ArrayList<Integer> list_comissao = new ArrayList<>();
        voltar.setVisibility(View.INVISIBLE);
        this.list_estatisticas = bd.getListaEstatisticas("ano", 0);
        this.bd.close();
        int a = 0;

        if (this.list_estatisticas != null && this.list_estatisticas.size()!=0) {
            list_anos.add(list_estatisticas.get(0).getAno());
            list_total.add(0);
            list_comissao.add(0);

            for(int i = 0; i < list_estatisticas.size(); i++) {
                int ano = list_estatisticas.get(i).getAno();

                if (list_anos.get(a) != ano) {
                    list_anos.add(list_estatisticas.get(i).getAno());
                    list_total.add((int) list_estatisticas.get(i).getValorTotal());
                    list_comissao.add((int) list_estatisticas.get(i).getValorComissao());
                    a++;

                } else {
                        double total = list_estatisticas.get(i).getValorTotal();
                        int totalLista = list_total.get(a);
                        totalLista = totalLista + (int) total;
                        list_total.set(a, totalLista);

                        double comissao = list_estatisticas.get(i).getValorComissao();
                        int comissaoLista = list_comissao.get(a);
                        comissaoLista = comissaoLista + (int) comissao;
                        list_comissao.set(a, comissaoLista);
                }

            }
            adapterCustomInteger = new EstatisticasAnosListAdapter(this, list_anos, list_total, list_comissao);
            this.lista.setAdapter(adapterCustomInteger);
        }
    }

    //Carregar Lista de Meses
    public void carregarListaMeses() {
        this.bd = new ViagensBD(this);
        list_meses = new ArrayList<>();
        ArrayList<Integer> list_total = new ArrayList<>();
        ArrayList<Integer> list_comissao = new ArrayList<>();
        voltar.setVisibility(View.VISIBLE);
        this.list_estatisticas = bd.getListaEstatisticas("mes", Integer.parseInt(ajudaAno));
        this.bd.close();
        int a = 0;

        if (this.list_estatisticas != null && this.list_estatisticas.size()!=0) {
            list_meses.add(list_estatisticas.get(0).getMes());
            list_total.add(0);
            list_comissao.add(0);

            for(int i = 0; i< list_estatisticas.size(); i++) {
                String mes = list_estatisticas.get(i).getMes();
                if (!list_meses.get(a).equals(mes)) {
                    list_meses.add(list_estatisticas.get(i).getMes());
                    list_total.add((int) list_estatisticas.get(i).getValorTotal());
                    list_comissao.add((int) list_estatisticas.get(i).getValorComissao());
                    a++;
                } else {
                    double total = list_estatisticas.get(i).getValorTotal();
                    int totalLista = list_total.get(a);
                    totalLista = totalLista + (int) total;
                    list_total.set(a, totalLista);

                    double comissao = list_estatisticas.get(i).getValorComissao();
                    int comissaoLista = list_comissao.get(a);
                    comissaoLista = comissaoLista + (int) comissao;
                    list_comissao.set(a, comissaoLista);
                }
            }
            adapterCustomString = new EstatisticasMesesListAdapter(this, list_meses, list_total, list_comissao);
            this.lista.setAdapter(adapterCustomString);
        }
    }

    public void carregarListaViagens(String mes, int ano) {
        this.bd = new ViagensBD(this);
        voltar.setVisibility(View.VISIBLE);
        this.list_viagens = bd.getListaViagensEstatisticas(mes, ano);
        this.bd.close();
        if (this.list_viagens != null) {
            this.adapterCustomViagens = new EstatisticasViagemListAdapter(Estatisticas.this, list_viagens);
            this.lista.setAdapter(adapterCustomViagens);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4) {
            titulo = tituloLista.getText().toString();
            if(titulo.equals("Valores de " + calculaMes(ajudaMes))){
                tituloLista.setText("Valores Mensais - " + ajudaAno);
                subTitulo1Lista.setText("Para ver as informações das viagens,");
                subTitulo2Lista.setText("clique em algum mês da lista");
                carregarListaMeses();
            } else if(titulo.equals("Valores Mensais - " + ajudaAno)){
                tituloLista.setText("Valores Anuais");
                subTitulo1Lista.setText("Para ver os valores mensais,");
                subTitulo2Lista.setText("clique em algum ano da lista");
                carregarListaAnos();
            }
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }


    public String calculaMes(String mes){
        switch (mes){
            case "01":
                mes = "Janeiro";
                break;
            case "02":
                mes = "Fevereiro";
                break;
            case "03":
                mes = "Março";
                break;
            case "04":
                mes = "Abril";
                break;
            case "05":
                mes = "Maio";
                break;
            case "06":
                mes = "Junho";
                break;
            case "07":
                mes = "Julho";
                break;
            case "08":
                mes = "Agosto";
                break;
            case "09":
                mes = "Setembro";
                break;
            case "10":
                mes = "Outubro";
                break;
            case "11":
                mes = "Novembro";
                break;
            case "12":
                mes = "Dezembro";
                break;
        }
        return mes;
    }


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}