package com.application.enzoterra.travelseller;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Configuracoes;
import com.application.enzoterra.travelseller.Model.Estatistica;
import com.application.enzoterra.travelseller.Model.Viagens;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ContinuacaoCadastroFinal extends AppCompatActivity {

    int id;
    String primeiraVez, valorTotal, valorComissao, observacoes;
    EditText edtValorTotal, edtPorcentagem, edtObservacoes;
    int passar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continuacao_cadastro_final);

        ScrollView scrollView = new ScrollView(this);
        ConstraintLayout.LayoutParams layout = new ConstraintLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layout);

        //Contato com o BD
        final Viagens viagem = new Viagens();
        final ViagensBD bd = new ViagensBD(ContinuacaoCadastroFinal.this);

        //Receber Dados
        final Intent getIntent = getIntent();
        id = (int) getIntent.getSerializableExtra("id");
        primeiraVez = getIntent.getStringExtra("primeiraVezCadastro");

        //EditTexts
        edtValorTotal = findViewById(R.id.ETvalorTotal);
        edtObservacoes = findViewById(R.id.ETobservacoes);
        edtPorcentagem = findViewById(R.id.ETporcentagemComissao);


        if(primeiraVez.equals("nao")){
            ArrayList<Viagens> dados = bd.getListaClientesID(id);

            valorTotal = String.valueOf(dados.get(0).getValorTotal());

            if(Double.parseDouble(valorTotal)!=0) {
                valorComissao = String.valueOf((dados.get(0).getValorComissao() * 100) / dados.get(0).getValorTotal());
            } else {
                valorComissao = "";
            }

            observacoes = dados.get(0).getObservacoes();

            edtValorTotal.setText(valorTotal);
            edtPorcentagem.setText(valorComissao);
            edtObservacoes.setText(observacoes);
        }

        //Botão Salvar
        Button salvar = findViewById(R.id.buttonSalvarCadastroFinal);
        salvar.setOnClickListener(view -> {

            ArrayList<Configuracoes> configuracoesLista = bd.getConfiguracoes();
            passar = configuracoesLista.get(0).getPassar();

            String valorTotal = edtValorTotal.getText().toString();
            double valorTotalDouble;
            if(valorTotal.equals("")){
                valorTotalDouble = 0000.00;
            } else {
                valorTotalDouble = Double.parseDouble(valorTotal);
            }

            double porcentagem;
            if (edtPorcentagem.getText().toString().equals("")){
                porcentagem = 12;
            } else {
                porcentagem = Double.parseDouble(edtPorcentagem.getText().toString());
            }

            viagem.setValorTotal(valorTotalDouble);
            viagem.setObservacoes(edtObservacoes.getText().toString());
            viagem.setValorComissao(valorTotalDouble*(porcentagem/100));
            bd.salvarViagemPasso3(viagem, id);

            if(passar==1){
                ArrayList<Viagens> listaViagem = bd.getListaClientesID(id);
                Estatistica estatistica = new Estatistica();

                String dataViagem = listaViagem.get(0).getEmbarqueData();
                String mes = "";
                int ano = 0;

                if (dataViagem.equals("")) {
                    dataViagem = listaViagem.get(0).getDesembarqueData();
                }

                if (!dataViagem.equals("")) {
                    String[] anoStr = dataViagem.split("/");
                    ano = Integer.parseInt(anoStr[2]);
                    mes = anoStr[1];
                }

                estatistica.setNomeCliente(listaViagem.get(0).getNomeCliente());
                estatistica.setCpfCliente(listaViagem.get(0).getCpfCliente());
                estatistica.setRgCliente(listaViagem.get(0).getRgCliente());
                estatistica.setDataNascimentoCliente(listaViagem.get(0).getDataNascimentoCliente());
                estatistica.setHotel(listaViagem.get(0).getHotel());
                estatistica.setCidade(listaViagem.get(0).getCidade());
                estatistica.setLocalizador(listaViagem.get(0).getLocalizador());
                estatistica.setNumeroVenda(listaViagem.get(0).getNumeroVenda());
                estatistica.setEmbarqueData(listaViagem.get(0).getEmbarqueData());
                estatistica.setEmbarqueHora(listaViagem.get(0).getEmbarqueHora());
                estatistica.setDesembarqueData(listaViagem.get(0).getDesembarqueData());
                estatistica.setDesembarqueHora(listaViagem.get(0).getDesembarqueHora());
                estatistica.setValorTotal(listaViagem.get(0).getValorTotal());
                estatistica.setValorComissao(listaViagem.get(0).getValorComissao());
                estatistica.setObservacoes(listaViagem.get(0).getObservacoes());
                estatistica.setAno(ano);
                estatistica.setMes(mes);

                bd.salvarViagemEstatisticas(estatistica);
            }

            bd.close();
            startActivity(new Intent(ContinuacaoCadastroFinal.this, Clientes.class));
        });


        //Botão Adicionar Pessoas
        Button addPessoas = findViewById(R.id.buttonAdicionarPessoas);
        addPessoas.setOnClickListener(view -> {
            Intent intent = new Intent(ContinuacaoCadastroFinal.this, ListaPessoas.class);
            intent.putExtra("id", id);
            intent.putExtra("tela", "cadastro");
            intent.putExtra("primeiraVezCadastro", "sim");

            String valorTotal = edtValorTotal.getText().toString();
            double valorTotalDouble;
            if(valorTotal.equals("")){
                valorTotalDouble = 0000.00;
            } else {
                valorTotalDouble = Double.parseDouble(valorTotal);
            }

            double porcentagem;
            if (edtPorcentagem.getText().toString().equals("")){
                porcentagem = 12;
            } else {
                porcentagem = Double.parseDouble(edtPorcentagem.getText().toString());
            }

            viagem.setValorTotal(valorTotalDouble);
            viagem.setObservacoes(edtObservacoes.getText().toString());
            if(valorTotalDouble>0) {
                viagem.setValorComissao(valorTotalDouble * (porcentagem / 100));
            } else {
                viagem.setValorComissao(0);
            }
            bd.salvarViagemPasso3(viagem, id);
            bd.close();

            startActivity(intent);
        });


        //Botão Voltar
        Button voltar = findViewById(R.id.buttonCancelarCadastroFinal);
        voltar.setOnClickListener(view -> {
            Intent intent = new Intent(ContinuacaoCadastroFinal.this, ContinuacaoCadastro.class);

            intent.putExtra("primeiraVez", "nao");
            intent.putExtra("id", id);

            String valorTotal = edtValorTotal.getText().toString();
            double valorTotalDouble;
            if(valorTotal.equals("")){
                valorTotalDouble = 0000.00;
            } else {
                valorTotalDouble = Double.parseDouble(valorTotal);
            }

            double porcentagem;
            if (edtPorcentagem.getText().toString().equals("")){
                porcentagem = 12;
            } else {
                porcentagem = Double.parseDouble(edtPorcentagem.getText().toString());
            }

            viagem.setValorTotal(valorTotalDouble);
            viagem.setObservacoes(edtObservacoes.getText().toString());
            if(valorTotalDouble>0) {
                viagem.setValorComissao(valorTotalDouble * (porcentagem / 100));
            } else {
                viagem.setValorComissao(0);
            }

            bd.salvarViagemPasso3(viagem, id);
            bd.close();

            startActivity(intent);
        });


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.green_cadastro));
    }


    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4) {
            Intent intent = new Intent(ContinuacaoCadastroFinal.this, ContinuacaoCadastro.class);
            Viagens viagem = new Viagens();
            ViagensBD bd = new ViagensBD(ContinuacaoCadastroFinal.this);

            intent.putExtra("primeiraVez", "nao");
            intent.putExtra("id", id);

            String valorTotal = edtValorTotal.getText().toString();
            double valorTotalDouble;
            if(valorTotal.equals("")){
                valorTotalDouble = 0.0;
            } else {
                valorTotalDouble = Double.parseDouble(valorTotal);
            }

            double porcentagem;
            if (edtPorcentagem.getText().toString().equals("")){
                porcentagem = 12;
            } else {
                porcentagem = Double.parseDouble(edtPorcentagem.getText().toString());
            }

            viagem.setValorTotal(valorTotalDouble);
            viagem.setObservacoes(edtObservacoes.getText().toString());
            if(valorTotalDouble>0.0) {
                viagem.setValorComissao(valorTotalDouble * (porcentagem / 100));
            } else {
                viagem.setValorComissao(0);
            }

            bd.salvarViagemPasso3(viagem, id);
            bd.close();

            startActivity(intent);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}