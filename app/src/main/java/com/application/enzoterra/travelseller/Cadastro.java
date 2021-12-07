package com.application.enzoterra.travelseller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.application.enzoterra.travelseller.Model.Pessoa;
import com.application.enzoterra.travelseller.Model.Viagens;

import java.util.ArrayList;

//import com.github.rtoshiro.util.format.SimpleMaskFormatter;
//import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Cadastro extends AppCompatActivity {

    long id;
    ViagensBD bd;
    String nome, cpf, rg, dataNascimento, hotel, cidade;
    String[] cidade_estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        ScrollView scrollView = new ScrollView(this);
        ConstraintLayout.LayoutParams layout = new ConstraintLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layout);

        //Contato com o BD
        final Viagens viagem = new Viagens();
        final Pessoa pessoa = new Pessoa();
        bd = new ViagensBD(Cadastro.this);


        //EditTexts
        final EditText edtNome = findViewById(R.id.ETnomeCliente);
        final EditText edtCpf = findViewById(R.id.ETcpf);
        final EditText edtRg = findViewById(R.id.ETrg);
        final EditText edtDataNascimento = findViewById(R.id.ETnascimento);
        final EditText edtHotel = findViewById(R.id.EThotel);
        final EditText edtCidade = findViewById(R.id.ETcidade);
        final EditText edtEstado = findViewById(R.id.ETestado);

        edtCpf.addTextChangedListener(MaskEditUtil.mask(edtCpf, MaskEditUtil.FORMAT_CPF));
        //edtRg.addTextChangedListener(MaskEditUtil.mask(edtRg, MaskEditUtil.FORMAT_RG));
        edtDataNascimento.addTextChangedListener(MaskEditUtil.mask(edtDataNascimento, MaskEditUtil.FORMAT_DATE));


        //Receber Dados
        final Intent getIntent = getIntent();
        String primeiraVez = (String) getIntent.getSerializableExtra("primeiraVez");

        if(primeiraVez.equals("nao")) {
            String varId = (String) getIntent.getSerializableExtra("id");
            id = Long.parseLong(varId);
            int idFinal = Integer.parseInt(String.valueOf(id));
            ArrayList<Viagens> dados = bd.getListaClientesID(idFinal);

            id = dados.get(0).getId();
            nome = dados.get(0).getNomeCliente();
            cpf = dados.get(0).getCpfCliente();
            rg = dados.get(0).getRgCliente();
            dataNascimento = dados.get(0).getDataNascimentoCliente();
            hotel = dados.get(0).getHotel();
            cidade = dados.get(0).getCidade();

            if(cidade.contains("/")) {
                cidade_estado = cidade.split("/");
            }

            edtNome.setText(nome);
            edtCpf.setText(cpf);
            edtRg.setText(rg);
            edtDataNascimento.setText(dataNascimento);
            edtHotel.setText(hotel);
            if(cidade.contains("/")) {
                edtCidade.setText(cidade_estado[0]);
            } else {
                edtCidade.setText(cidade);
            }
            if(cidade_estado!=null && cidade_estado.length>1){
                edtEstado.setText(cidade_estado[1]);
            }
        } else {
            id = -1;
        }


        //Botão Continuar
        Button continuar = findViewById(R.id.buttonContinuar1);
        continuar.setOnClickListener(view -> {
            String nome = edtNome.getText().toString();

            if(nome.equals("")){
                AlertDialog.Builder msgBox = new AlertDialog.Builder(Cadastro.this);
                msgBox.setTitle("Atenção ...");
                msgBox.setMessage("É necessário colocar o Nome do Cliente ");
                msgBox.setPositiveButton("Ok", (dialogInterface, i) -> {

                });msgBox.show();

            } else {

                String cidade = edtCidade.getText().toString();
                String estado = edtEstado.getText().toString();

                if(!cidade.equals("") && !estado.equals("")){
                    cidade = cidade + "/" + estado.toUpperCase();
                }

                viagem.setNomeCliente(nome);
                viagem.setCpfCliente(edtCpf.getText().toString());
                viagem.setRgCliente(edtRg.getText().toString());
                viagem.setDataNascimentoCliente(edtDataNascimento.getText().toString());
                viagem.setHotel(edtHotel.getText().toString());
                viagem.setCidade(cidade);

                if(bd.getId() != id){
                    id = bd.salvarViagem(viagem);
                } else {
                    bd.alterarViagemCadastro(viagem, Integer.parseInt(String.valueOf(id)));
                }

                if(primeiraVez.equals("nao")) {
                    pessoa.setNome(nome);
                    pessoa.setCpf(edtCpf.getText().toString());
                    pessoa.setRg(edtRg.getText().toString());
                    pessoa.setDataNascimento(edtDataNascimento.getText().toString());
                    int primeiroId = Integer.parseInt(String.valueOf(bd.getIdIntegrantes(Integer.parseInt(String.valueOf(id)))));
                    bd.alterarClientes(pessoa, primeiroId);
                } else {
                    pessoa.setNome(nome);
                    pessoa.setCpf(edtCpf.getText().toString());
                    pessoa.setRg(edtRg.getText().toString());
                    pessoa.setDataNascimento(edtDataNascimento.getText().toString());
                    pessoa.setFkViagens(Integer.parseInt(String.valueOf(id)));
                    bd.salvarPessoa(pessoa);
                }

                Intent intent = new Intent(Cadastro.this, ContinuacaoCadastro.class);
                intent.putExtra("id", Integer.parseInt(String.valueOf(id)));
                if(primeiraVez.equals("nao")){
                    intent.putExtra("primeiraVez", "nao");
                } else {
                    intent.putExtra("primeiraVez", "sim");
                }
                bd.close();
                startActivity(intent);
            }
        });


        //Botão Cancelar
        Button cancelar = findViewById(R.id.buttonCancelar1);
        cancelar.setOnClickListener(view -> {
            if(bd.getId()==id) {
                bd.deletarViagem(Integer.parseInt(String.valueOf(id)));
            }
            startActivity(new Intent(Cadastro.this, Clientes.class));
        });


        //Máscara Cpf
        /*SimpleMaskFormatter mascaraCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher visualizadorCpf = new MaskTextWatcher(edtCpf, mascaraCpf);
        edtCpf.addTextChangedListener(visualizadorCpf);

        //Máscaras RG
        SimpleMaskFormatter mascaraRg = new SimpleMaskFormatter("N.NNN.NNN");
        MaskTextWatcher visualizadorRg = new MaskTextWatcher(edtRg, mascaraRg);
        edtRg.addTextChangedListener(visualizadorRg);

        //Máscaras Data Nascimento
        SimpleMaskFormatter mascaraDataNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher visualizadorDataNascimento = new MaskTextWatcher(edtDataNascimento, mascaraDataNascimento);
        edtDataNascimento.addTextChangedListener(visualizadorDataNascimento);*/


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.green_cadastro));

    }


    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4) {
            if(bd.getId()==id) {
                bd.deletarViagem(Integer.parseInt(String.valueOf(id)));
            }
            startActivity(new Intent(Cadastro.this, Clientes.class));
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}