package com.application.enzoterra.travelseller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Pessoa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaPessoas extends AppCompatActivity {

    //Variáveis
    ArrayAdapter<Pessoa> adapterCustom;
    ViagensBD bd;
    ArrayList<Pessoa> list_pessoas;
    ListView lista;
    Button voltar;
    String cidade, hotel, localizador, companhiaAerea, numeroVenda, embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes, valorTotalString, valorComissaoString;
    double valorTotal, valorComissao;
    String tela, telaPrincipal;
    int id;
    String[] dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_pessoas);

        lista = findViewById(R.id.lista_pessoas);
        voltar = findViewById(R.id.buttonVoltarListaPessoas);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) lista.getLayoutParams();
        int buttonHeight = voltar.getHeight() + 75;
        layoutParams.height = 1400 - buttonHeight;
        lista.setLayoutParams(layoutParams);

        //Receber Dados
        final Intent getIntent = getIntent();
        id = (int) getIntent.getSerializableExtra("id");
        tela = (String) getIntent.getSerializableExtra("tela");
        telaPrincipal = (String) getIntent.getSerializableExtra("telaPrincipal");

        //Se a tela anterior for a de editar viagem
        if (tela.equals("fragmento")){
            cidade = (String) getIntent.getSerializableExtra("cidade");
            hotel = (String) getIntent.getSerializableExtra("hotel");
            localizador = (String) getIntent.getSerializableExtra("localizador");
            companhiaAerea = (String) getIntent.getSerializableExtra("companhiaAerea");
            numeroVenda = (String) getIntent.getSerializableExtra("numeroVenda");
            embarqueHora = (String) getIntent.getSerializableExtra("embarqueHora");
            embarqueData = (String) getIntent.getSerializableExtra("embarqueData");
            desembarqueHora = (String) getIntent.getSerializableExtra("desembarqueHora");
            desembarqueData = (String) getIntent.getSerializableExtra("desembarqueData");
            observacoes = (String) getIntent.getSerializableExtra("observacoes");
            valorTotalString = (String) getIntent.getSerializableExtra("valorTotal");
            valorComissaoString = (String) getIntent.getSerializableExtra("valorComissao");
            valorTotal = Double.parseDouble(valorTotalString);
            valorComissao = Double.parseDouble(valorComissaoString);

            dados = new String[]{String.valueOf(id), cidade, hotel, localizador, numeroVenda, embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes, String.valueOf(valorTotal), String.valueOf(valorComissao)};

        } else if(tela.equals("alterarIntegrantes")){
            dados = getIntent.getStringArrayExtra("dados");
            id = Integer.parseInt(dados[0]);
            cidade = dados[1];
            hotel = dados[2];
            localizador = dados[3];
            companhiaAerea = dados[4];
            numeroVenda = dados[5];
            embarqueHora = dados[6];
            embarqueData = dados[7];
            desembarqueHora = dados[8];
            desembarqueData = dados[9];
            observacoes = dados[10];
            valorTotal = Double.parseDouble(dados[11]);
            valorComissao = Double.parseDouble(dados[12]);
            tela = "fragmento";
        }


        //Lista das Viagens
        lista.setOnItemClickListener((adapterView, view, i, j) -> {
            Intent intent = new Intent(ListaPessoas.this, AlterarIntegrantes.class);
            intent.putExtra("idPessoa", (ListaPessoas.this.list_pessoas.get(i)).getId());
            intent.putExtra("nome", (ListaPessoas.this.list_pessoas.get(i)).getNome());
            intent.putExtra("cpf", (ListaPessoas.this.list_pessoas.get(i)).getCpf());
            intent.putExtra("rg", (ListaPessoas.this.list_pessoas.get(i)).getRg());
            intent.putExtra("dataNascimento", (ListaPessoas.this.list_pessoas.get(i)).getDataNascimento());
            intent.putExtra("idViagem", id);
            intent.putExtra("objetivo", "alterar");
            if(tela.equals("fragmento")) {
                intent.putExtra("tela", "fragmento");
                intent.putExtra("telaPrincipal", telaPrincipal);
            } else {
                intent.putExtra("tela", "cadastro");
            }
            intent.putExtra("dados", dados);
            ListaPessoas.this.startActivity(intent);
        });


        //ImageButton para Cadastrar Integrante
        FloatingActionButton floatingButton = findViewById(R.id.floatingButtonIntegrante);
        floatingButton.setOnClickListener(view -> {
            Intent intent = new Intent(ListaPessoas.this, AlterarIntegrantes.class);
            intent.putExtra("idViagem", id);
            intent.putExtra("nome", "");
            intent.putExtra("cpf", "");
            intent.putExtra("rg", "");
            intent.putExtra("dataNascimento", "");
            intent.putExtra("objetivo", "salvar");
            intent.putExtra("tela", "fragmento");
            if(tela.equals("fragmento")) {
                intent.putExtra("tela", "fragmento");
                intent.putExtra("telaPrincipal", telaPrincipal);
            } else {
                intent.putExtra("tela", "cadastro");
            }
            intent.putExtra("dados", dados);
            startActivity(intent);
        });


        //Botão Voltar
        voltar.setOnClickListener(view -> {
            Intent intent;
            if(tela.equals("fragmento")){
                intent = new Intent(ListaPessoas.this, EditarViagem.class);
                intent.putExtra("id", Long.parseLong(String.valueOf(id)));
                intent.putExtra("cidade", cidade);
                intent.putExtra("hotel", hotel);
                intent.putExtra("localizador", localizador);
                intent.putExtra("companhiaAerea", companhiaAerea);
                intent.putExtra("numeroVenda", numeroVenda);
                intent.putExtra("embarqueHora", embarqueHora);
                intent.putExtra("embarqueData", embarqueData);
                intent.putExtra("desembarqueHora", desembarqueHora);
                intent.putExtra("desembarqueData", desembarqueData);
                intent.putExtra("observacoes", observacoes);
                intent.putExtra("valorTotal", valorTotal);
                intent.putExtra("valorComissao", valorComissao);
                intent.putExtra("tela", telaPrincipal);
            } else {
                intent = new Intent(ListaPessoas.this, ContinuacaoCadastroFinal.class);
                intent.putExtra("id", id);
                intent.putExtra("tela", "listaPessoas");
                intent.putExtra("primeiraVezCadastro", "nao");
            }
            startActivity(intent);
        });


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.green_cadastro));
    }

    public void onResume() {
        super.onResume();
        carregarLista();
    }

    //Carregar Lista
    public void carregarLista() {
        /*final Intent getIntent = getIntent();
        final int id = (int) getIntent.getSerializableExtra("id");*/
        ViagensBD viagensBD = new ViagensBD(this);
        this.bd = viagensBD;
        this.list_pessoas = viagensBD.getListaIntegrantes(id);
        this.bd.close();
        if (this.list_pessoas != null) {
            this.adapterCustom = new PessoasListAdapter(this, list_pessoas);
            this.lista.setAdapter(adapterCustom);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4) {
            Intent intent;
            if(tela.equals("fragmento")){
                intent = new Intent(ListaPessoas.this, EditarViagem.class);
                intent.putExtra("id", Long.parseLong(dados[0]));
                intent.putExtra("cidade", dados[1]);
                intent.putExtra("hotel", dados[2]);
                intent.putExtra("localizador", dados[3]);
                intent.putExtra("companhiaAerea", dados[4]);
                intent.putExtra("numeroVenda", dados[5]);
                intent.putExtra("embarqueHora", dados[6]);
                intent.putExtra("embarqueData", dados[7]);
                intent.putExtra("desembarqueHora", dados[8]);
                intent.putExtra("desembarqueData", dados[9]);
                intent.putExtra("observacoes", dados[10]);
                intent.putExtra("valorTotal", Double.parseDouble(dados[11]));
                intent.putExtra("valorComissao", Double.parseDouble(dados[12]));
                intent.putExtra("tela", telaPrincipal);
            } else {
                intent = new Intent(ListaPessoas.this, ContinuacaoCadastroFinal.class);
                intent.putExtra("id", id);
                intent.putExtra("primeiraVezCadastro", "nao");
            }

            startActivity(intent);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}