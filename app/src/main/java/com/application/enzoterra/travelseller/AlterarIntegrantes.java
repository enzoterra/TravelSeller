package com.application.enzoterra.travelseller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Pessoa;
import com.application.enzoterra.travelseller.Model.Viagens;

import java.util.ArrayList;


public class AlterarIntegrantes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alterar_integrantes);

        //Contato com o BD
        final Pessoa pessoa = new Pessoa();
        final ViagensBD bd = new ViagensBD(AlterarIntegrantes.this);

        //Criação de Variáveis
        long idPessoa = 0;
        int idViagem;
        String isNomePrincipal;
        String[] dados = new String[0];
        String telaPrincipal = "viagens";


        //Receber Dados
        final Intent getIntent = getIntent();
        String nome = (String) getIntent.getSerializableExtra("nome");
        String cpf = (String) getIntent.getSerializableExtra("cpf");
        String rg = (String) getIntent.getSerializableExtra("rg");
        String dataNascimento = (String) getIntent.getSerializableExtra("dataNascimento");
        String tela = (String) getIntent.getSerializableExtra("tela");


        //Se a tela anterior a lista for a de editar viagem, receberá os dados
        if(tela.equals("fragmento")){
            dados = getIntent.getStringArrayExtra("dados");
            telaPrincipal = (String) getIntent.getSerializableExtra("telaPrincipal");
        }
        final String[] finalDados = dados;

        final String objetivo = (String) getIntent.getSerializableExtra("objetivo");

        if (objetivo.equals("alterar")) {
            idPessoa = (long) getIntent.getSerializableExtra("idPessoa");
        }

        idViagem = (int) getIntent.getSerializableExtra("idViagem");


        final int pessoaId = Integer.parseInt(String.valueOf(idPessoa));
        final int viagemId = idViagem;


        //Verifica se é o cliente principal
        String nomePrincipal = bd.getNomePrincipal(idViagem);
        if (nomePrincipal.equals(nome)){
            isNomePrincipal = "sim";
        } else {
            isNomePrincipal = "nao";
        }

        //EditTexts
        final EditText edtNome = findViewById(R.id.ETnomeIntegrante);
        final EditText edtCpf = findViewById(R.id.ETcpfIntegrante);
        final EditText edtRg = findViewById(R.id.ETrgIntegrante);
        final EditText edtDataNascimento = findViewById(R.id.ETnascimentoIntegrante);

        //Set Texts
        edtNome.setText(nome);
        edtCpf.setText(cpf);
        edtRg.setText(rg);
        edtDataNascimento.setText(dataNascimento);

        edtCpf.addTextChangedListener(MaskEditUtil.mask(edtCpf, MaskEditUtil.FORMAT_CPF));
        //edtRg.addTextChangedListener(MaskEditUtil.mask(edtRg, MaskEditUtil.FORMAT_RG));
        edtDataNascimento.addTextChangedListener(MaskEditUtil.mask(edtDataNascimento, MaskEditUtil.FORMAT_DATE));


        //Botão Salvar
        Button salvar = findViewById(R.id.buttonSalvarIntegrante);
        String finalTelaPrincipal = telaPrincipal;
        salvar.setOnClickListener(view -> {
            String varNome = edtNome.getText().toString();
            if(varNome.equals("")){
                AlertDialog.Builder msgBox = new AlertDialog.Builder(AlterarIntegrantes.this);
                msgBox.setTitle("Atenção ...");
                msgBox.setMessage("É necessário colocar o Nome do Integrante ");
                msgBox.setPositiveButton("Ok", (dialogInterface, i) -> {

                });msgBox.show();
            } else {
                pessoa.setNome(varNome);
                pessoa.setCpf(edtCpf.getText().toString());
                pessoa.setRg(edtRg.getText().toString());
                pessoa.setDataNascimento(edtDataNascimento.getText().toString());

                if (objetivo.equals("salvar")) {
                    pessoa.setFkViagens(viagemId);
                    bd.salvarPessoa(pessoa);

                } else {
                    bd.alterarClientes(pessoa, pessoaId);

                    if(isNomePrincipal.equals("sim")){
                        Viagens viagem = new Viagens();
                        viagem.setNomeCliente(varNome);
                        viagem.setCpfCliente(edtCpf.getText().toString());
                        viagem.setRgCliente(edtRg.getText().toString());
                        viagem.setDataNascimentoCliente(edtDataNascimento.getText().toString());
                        bd.salvarClienteViagem(viagem, viagemId);
                    }
                }
                bd.close();

                Intent intent = new Intent(AlterarIntegrantes.this, ListaPessoas.class);
                intent.putExtra("id", viagemId);
                if (tela.equals("fragmento")) {
                    intent.putExtra("dados", finalDados);
                    intent.putExtra("tela", "alterarIntegrantes");
                } else {
                    intent.putExtra("tela", "cadastro");
                }
                intent.putExtra("telaPrincipal", finalTelaPrincipal);
                startActivity(intent);
            }
        });


        //Button tornar cliente principal
        ImageButton tornarPrincipal = findViewById(R.id.buttonTornarPrincipal);
        tornarPrincipal.setOnClickListener(view -> {
            {
                AlertDialog.Builder msgBox = new AlertDialog.Builder(AlterarIntegrantes.this);
                msgBox.setTitle("Atenção ...");
                msgBox.setMessage("Deseja tornar este integrante o principal da viagem? ");
                msgBox.setPositiveButton("Sim", (dialogInterface, i) -> {
                    String varNome = edtNome.getText().toString();
                    if (varNome.equals("")) {
                        AlertDialog.Builder msgBox2 = new AlertDialog.Builder(AlterarIntegrantes.this);
                        msgBox2.setTitle("Atenção ...");
                        msgBox2.setMessage("É necessário colocar o Nome do Integrante ");
                        msgBox2.setPositiveButton("Ok", (dialogInterface2, i2) -> {

                        });
                        msgBox2.show();
                    } else {

                        Viagens viagem = new Viagens();
                        viagem.setNomeCliente(varNome);
                        viagem.setCpfCliente(edtCpf.getText().toString());
                        viagem.setRgCliente(edtRg.getText().toString());
                        viagem.setDataNascimentoCliente(edtDataNascimento.getText().toString());
                        bd.salvarClienteViagem(viagem, viagemId);
                        bd.close();

                        Intent intent = new Intent(AlterarIntegrantes.this, ListaPessoas.class);
                        intent.putExtra("id", viagemId);
                        if (tela.equals("fragmento")) {
                            intent.putExtra("dados", finalDados);
                            intent.putExtra("tela", "alterarIntegrantes");
                        } else {
                            intent.putExtra("tela", "cadastro");
                        }
                        intent.putExtra("telaPrincipal", finalTelaPrincipal);
                        startActivity(intent);
                    }
                });
                msgBox.setNegativeButton("Não", (dialogInterface, i) -> {
                });
                msgBox.show();
            }
        });


        //Botão Cancelar
        ImageButton voltar = findViewById(R.id.buttonVoltarIntegrante);
        voltar.setOnClickListener(view -> {
            Intent intent = new Intent(AlterarIntegrantes.this, ListaPessoas.class);
            intent.putExtra("id", viagemId);
            if (tela.equals("fragmento")) {
                intent.putExtra("dados", finalDados);
                intent.putExtra("tela", "alterarIntegrantes");
            } else {
                intent.putExtra("tela", "cadastro");
            }
            intent.putExtra("telaPrincipal", finalTelaPrincipal);
            startActivity(intent);
        });


        //Botão Excluir
        final Button excluir = findViewById(R.id.buttonExcluirIntegrante);
        if(objetivo.equals("alterar")) {
            excluir.setVisibility(View.VISIBLE);
            excluir.setOnClickListener(view -> {
                AlertDialog.Builder msgBox = new AlertDialog.Builder(AlterarIntegrantes.this);
                msgBox.setTitle("Atenção ...");
                msgBox.setMessage("Deseja excluir este integrante da viagem? ");
                msgBox.setPositiveButton("Sim", (dialogInterface, i) -> {
                    Intent intent = new Intent(AlterarIntegrantes.this, ListaPessoas.class);
                    intent.putExtra("id", viagemId);
                    bd.deletarCliente(pessoaId);

                    if(isNomePrincipal.equals("sim")) {
                        ArrayList<Pessoa> listaIntegrantes2 = bd.getListaIntegrantes(viagemId);
                        if(listaIntegrantes2!=null) {
                            Viagens viagem = new Viagens();

                            viagem.setNomeCliente(listaIntegrantes2.get(0).getNome());
                            viagem.setCpfCliente(listaIntegrantes2.get(0).getCpf());
                            viagem.setRgCliente(listaIntegrantes2.get(0).getRg());
                            viagem.setDataNascimentoCliente(listaIntegrantes2.get(0).getDataNascimento());

                            bd.salvarClienteViagem(viagem, viagemId);
                        }
                    }

                    bd.close();

                    Toast.makeText(AlterarIntegrantes.this, "Excluído com Sucesso", Toast.LENGTH_SHORT).show();
                    intent.putExtra("id", viagemId);
                    if (tela.equals("fragmento")) {
                        intent.putExtra("dados", finalDados);
                        intent.putExtra("tela", "alterarIntegrantes");
                    } else {
                        intent.putExtra("tela", "cadastro");
                    }
                    intent.putExtra("telaPrincipal", finalTelaPrincipal);
                    startActivity(intent);
                });
                msgBox.setNegativeButton("Não", (dialogInterface, i) -> {
                });
                msgBox.show();
            });
        }
        else {
            excluir.setVisibility(View.INVISIBLE);
        }


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.green_cadastro));
    }
}