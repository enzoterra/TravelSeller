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
import android.widget.Toast;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Estatistica;
import com.application.enzoterra.travelseller.Model.Viagens;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

//import com.github.rtoshiro.util.format.SimpleMaskFormatter;
//import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class ContinuacaoCadastro extends AppCompatActivity {

    int id = 0;
    ViagensBD bd;
    String localizador, numeroVenda, embarqueHora, embarqueData, desembarqueHora, desembarqueData;
    EditText edtLocalizador, edtVenda, edtEmbarqueData, edtEmbarqueHora, edtDesembarqueData, edtDesembarqueHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continuacao_cadastro);

        ScrollView scrollView = new ScrollView(this);
        ConstraintLayout.LayoutParams layout = new ConstraintLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layout);

        //Contato com o BD
        final Viagens viagem = new Viagens();
        ViagensBD bd = new ViagensBD(ContinuacaoCadastro.this);

        //Receber Dados
        final Intent getIntent = getIntent();
        id = (int) getIntent.getSerializableExtra("id");
        String primeiraVez = (String) getIntent.getSerializableExtra("primeiraVez");


        //EditTexts
        edtLocalizador = findViewById(R.id.ETlocalizador);
        edtVenda = findViewById(R.id.ETvenda);
        edtEmbarqueData = findViewById(R.id.ETdataEmbarque);
        edtEmbarqueHora = findViewById(R.id.EThoraEmbarque);
        edtDesembarqueData = findViewById(R.id.ETdataDesembarque);
        edtDesembarqueHora = findViewById(R.id.EThoraDesembarque);

        edtEmbarqueData.addTextChangedListener(MaskEditUtil.mask(edtEmbarqueData, MaskEditUtil.FORMAT_DATE));
        edtEmbarqueHora.addTextChangedListener(MaskEditUtil.mask(edtEmbarqueHora, MaskEditUtil.FORMAT_HOUR));
        edtDesembarqueData.addTextChangedListener(MaskEditUtil.mask(edtDesembarqueData, MaskEditUtil.FORMAT_DATE));
        edtDesembarqueHora.addTextChangedListener(MaskEditUtil.mask(edtDesembarqueHora, MaskEditUtil.FORMAT_HOUR));


        if(primeiraVez.equals("nao")) {
            ArrayList<Viagens> dados = bd.getListaClientesID(id);

            localizador = dados.get(0).getLocalizador();
            numeroVenda = dados.get(0).getNumeroVenda();
            embarqueData = dados.get(0).getEmbarqueData();
            embarqueHora = dados.get(0).getEmbarqueHora();
            desembarqueData = dados.get(0).getDesembarqueData();
            desembarqueHora = dados.get(0).getDesembarqueHora();

            edtLocalizador.setText(localizador);
            edtVenda.setText(numeroVenda);
            edtEmbarqueData.setText(embarqueData);
            edtEmbarqueHora.setText(embarqueHora);
            edtDesembarqueData.setText(desembarqueData);
            edtDesembarqueHora.setText(desembarqueHora);
        }


        //Botão Continuar
        Button continuar = findViewById(R.id.buttonContinuarContinuacaoCadastro);
        continuar.setOnClickListener(view -> {

            viagem.setLocalizador(edtLocalizador.getText().toString());
            viagem.setEmbarqueData(edtEmbarqueData.getText().toString());
            viagem.setEmbarqueHora(edtEmbarqueHora.getText().toString());
            viagem.setDesembarqueData(edtDesembarqueData.getText().toString());
            viagem.setDesembarqueHora(edtDesembarqueHora.getText().toString());

            if (!edtVenda.getText().toString().equals("")) {
                ArrayList<Estatistica> list = bd.getEstatisticaNumeroViagem(new String[]{edtVenda.getText().toString()});

                if (list.size() != 0) {
                    AlertDialog.Builder msgBox = new AlertDialog.Builder(ContinuacaoCadastro.this);
                    msgBox.setTitle("Atenção ...");
                    msgBox.setMessage("Alguma viagem de " + list.get(0).getNomeCliente() + " já possui este número da venda.\n\nDeseja prosseguir?");
                    msgBox.setPositiveButton("Sim", (dialogInterface, i) -> {
                        viagem.setNumeroVenda(edtVenda.getText().toString());
                        bd.salvarViagemPasso2(viagem, id);

                        bd.close();

                        Intent intent = new Intent(ContinuacaoCadastro.this, ContinuacaoCadastroFinal.class);
                        intent.putExtra("id", id);
                        if (primeiraVez.equals("nao")) {
                            intent.putExtra("primeiraVezCadastro", "nao");
                        } else {
                            intent.putExtra("primeiraVezCadastro", "sim");
                        }
                        startActivity(intent);
                    });
                    msgBox.setNegativeButton("Não", (dialogInterface, i) -> {
                    });
                    msgBox.show();
                } else {
                    viagem.setNumeroVenda(edtVenda.getText().toString());
                    bd.salvarViagemPasso2(viagem, id);

                    bd.close();

                    Intent intent = new Intent(ContinuacaoCadastro.this, ContinuacaoCadastroFinal.class);
                    intent.putExtra("id", id);
                    if (primeiraVez.equals("nao")) {
                        intent.putExtra("primeiraVezCadastro", "nao");
                    } else {
                        intent.putExtra("primeiraVezCadastro", "sim");
                    }
                    startActivity(intent);
                }
            } else {
                viagem.setNumeroVenda(edtVenda.getText().toString());
                bd.salvarViagemPasso2(viagem, id);

                bd.close();

                Intent intent = new Intent(ContinuacaoCadastro.this, ContinuacaoCadastroFinal.class);
                intent.putExtra("id", id);
                if (primeiraVez.equals("nao")) {
                    intent.putExtra("primeiraVezCadastro", "nao");
                } else {
                    intent.putExtra("primeiraVezCadastro", "sim");
                }
                startActivity(intent);
            }
        });

        //Botão Voltar
        Button voltar = findViewById(R.id.buttonCancelarContinuacaoCadastro);
        voltar.setOnClickListener(view -> {
            Intent intent = new Intent(ContinuacaoCadastro.this, Cadastro.class);

            intent.putExtra("primeiraVez", "nao");
            intent.putExtra("id", String.valueOf(id));

            viagem.setLocalizador(edtLocalizador.getText().toString());
            viagem.setNumeroVenda(edtVenda.getText().toString());
            viagem.setEmbarqueData(edtEmbarqueData.getText().toString());
            viagem.setEmbarqueHora(edtEmbarqueHora.getText().toString());
            viagem.setDesembarqueData(edtDesembarqueData.getText().toString());
            viagem.setDesembarqueHora(edtDesembarqueHora.getText().toString());

            bd.salvarViagemPasso2(viagem, id);
            bd.close();

            startActivity(intent);
        });


        //Máscaras Datas
        /*SimpleMaskFormatter mascaraData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher visualizadorDataEmbarque = new MaskTextWatcher(edtEmbarqueData, mascaraData);
        MaskTextWatcher visualizadorDataDesembarque = new MaskTextWatcher(edtDesembarqueData, mascaraData);
        edtEmbarqueData.addTextChangedListener(visualizadorDataEmbarque);
        edtDesembarqueData.addTextChangedListener(visualizadorDataDesembarque);

        //Máscaras Horas
        SimpleMaskFormatter mascaraHora = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher visualizadorHoraEmbarque = new MaskTextWatcher(edtEmbarqueHora, mascaraHora);
        MaskTextWatcher visualizadorHoraDesembarque = new MaskTextWatcher(edtDesembarqueHora, mascaraHora);
        edtEmbarqueHora.addTextChangedListener(visualizadorHoraEmbarque);
        edtDesembarqueHora.addTextChangedListener(visualizadorHoraDesembarque);*/


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.green_cadastro));

    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4) {
            Intent intent = new Intent(ContinuacaoCadastro.this, Cadastro.class);
            Viagens viagem = new Viagens();
            ViagensBD bd = new ViagensBD(ContinuacaoCadastro.this);

            intent.putExtra("primeiraVez", "nao");
            intent.putExtra("id", String.valueOf(id));

            viagem.setLocalizador(edtLocalizador.getText().toString());
            viagem.setNumeroVenda(edtVenda.getText().toString());
            viagem.setEmbarqueData(edtEmbarqueData.getText().toString());
            viagem.setEmbarqueHora(edtEmbarqueHora.getText().toString());
            viagem.setDesembarqueData(edtDesembarqueData.getText().toString());
            viagem.setDesembarqueHora(edtDesembarqueHora.getText().toString());

            bd.salvarViagemPasso2(viagem, id);
            bd.close();

            startActivity(intent);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}