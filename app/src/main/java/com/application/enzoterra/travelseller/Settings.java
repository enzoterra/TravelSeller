package com.application.enzoterra.travelseller;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.Toast;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Configuracoes;
import com.application.enzoterra.travelseller.Model.Estatistica;
import com.application.enzoterra.travelseller.Model.Pessoa;
import com.application.enzoterra.travelseller.Model.Viagens;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Settings extends AppCompatActivity {

    int embarque, desembarque, umaHora, umDia, doisDias, limpar, passar;
    CheckBox checkBoxEmbarque, checkBoxDesembarque, checkBox1dia, checkBox2dias, checkBoxLimpar, checkBoxPassar;
    Configuracoes configuracoes = new Configuracoes();
    int ativado = 1;
    int desativado = 0;
    ViagensBD bd;
    final int REQUEST_PERMISSION_MANAGE_STORAGE = 1;
    final int REQUEST_PERMISSION_WRITE_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Context context = Settings.this;
        bd = new ViagensBD(context);

        ScrollView scrollView = new ScrollView(this);
        ConstraintLayout.LayoutParams layout = new ConstraintLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layout);


        checkBoxEmbarque = findViewById(R.id.checkBoxEmbarque);
        checkBoxDesembarque = findViewById(R.id.checkBoxDesembarque);
        //checkBox1Hora = findViewById(R.id.checkBox1hora);
        checkBox1dia = findViewById(R.id.checkBox1dia);
        checkBox2dias = findViewById(R.id.checkBox2dias);
        checkBoxLimpar = findViewById(R.id.checkBoxLimpar);
        checkBoxPassar = findViewById(R.id.checkBoxPassar);

        ArrayList<Configuracoes> configuracoesLista = bd.getConfiguracoes();

        embarque = configuracoesLista.get(0).getEmbarqueConfiguracoes();
        desembarque = configuracoesLista.get(0).getDesembarqueConfiguracoes();
        umaHora = configuracoesLista.get(0).getUmaHora();
        umDia = configuracoesLista.get(0).getUmDia();
        doisDias = configuracoesLista.get(0).getDoisDias();
        limpar = configuracoesLista.get(0).getLimpar();
        passar = configuracoesLista.get(0).getPassar();


        checkBoxEmbarque.setChecked(embarque == 1);
        checkBoxDesembarque.setChecked(desembarque==1);
        //checkBox1Hora.setActivated(umaHora==1);
        checkBox1dia.setChecked(umDia==1);
        checkBox2dias.setChecked(doisDias==1);
        checkBoxLimpar.setChecked(limpar==1);
        checkBoxPassar.setChecked(passar==1);


        //Button Exportar
        Button exportar = findViewById(R.id.buttonExportar);
        exportar.setOnClickListener(view -> {

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                    exportarArquivos();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_MANAGE_STORAGE);
                }
            } else {
            }*/
            AlertDialog.Builder msgBoxExcluir = new AlertDialog.Builder(this);
            msgBoxExcluir.setTitle("Atenção ...");
            msgBoxExcluir.setMessage("Deseja exportar seus dados?");
            msgBoxExcluir.setPositiveButton("Sim", (dialogInterface, i) -> {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                    exportarArquivos();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_STORAGE);
                }
            });
            msgBoxExcluir.setNegativeButton("Não", (dialogInterface, i) -> {
            });
            msgBoxExcluir.show();
        });


        //Button Importar
        Button importar = findViewById(R.id.buttonImportar);
        importar.setOnClickListener(view -> {
            AlertDialog.Builder msgBoxExcluir = new AlertDialog.Builder(this);
            msgBoxExcluir.setTitle("Atenção ...");
            msgBoxExcluir.setMessage("Deseja importar seus dados?");
            msgBoxExcluir.setPositiveButton("Sim", (dialogInterface, i) -> {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                    importarArquivos();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_STORAGE);
                }
            });
            msgBoxExcluir.setNegativeButton("Não", (dialogInterface, i) -> {
            });
            msgBoxExcluir.show();
        });


        //Button Salvar
        Button salvar = findViewById(R.id.buttonSalvarConfiguracoes);
        salvar.setOnClickListener(view -> {
            salvarConfiguracoes();
            startActivity(new Intent(Settings.this, ListaViagens.class));
        }
        );


        //Button Voltar
        Button voltar = findViewById(R.id.buttonVoltarConfiguracoes);
        voltar.setOnClickListener(view -> startActivity(new Intent(Settings.this, ListaViagens.class)));
    }


    public void salvarConfiguracoes(){
        if(checkBoxEmbarque.isChecked()){
            embarque = ativado;
        } else {
            embarque = desativado;
        }

        if(checkBoxDesembarque.isChecked()){
            desembarque = ativado;
        } else {
            desembarque = desativado;
        }

        /*if(checkBox1Hora.isActivated()){
            umaHora = ativado;
        } else {
            umaHora = desativado;
        }*/

        if(checkBox1dia.isChecked()){
            umDia = ativado;
        } else {
            umDia = desativado;
        }

        if(checkBox2dias.isChecked()){
            doisDias = ativado;
        } else {
            doisDias = desativado;
        }

        if(checkBoxLimpar.isChecked()){
            limpar = ativado;
        } else {
            limpar = desativado;
        }

        if(checkBoxPassar.isChecked()){
            passar = ativado;
        } else {
            passar = desativado;
        }

        configuracoes.setEmbarqueConfiguracoes(embarque);
        configuracoes.setDesembarqueConfiguracoes(desembarque);
        configuracoes.setUmaHora(umaHora);
        configuracoes.setUmDia(umDia);
        configuracoes.setDoisDias(doisDias);
        configuracoes.setLimpar(limpar);
        configuracoes.setPassar(passar);

        bd.alterarConfiguracoes(configuracoes);
        bd.close();
    }


    public void importarArquivos(){
        String diretorio = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();

        String directoryName = "TravelSeller";
        String fileNameViagens = "ListaViagens.csv";
        String fileNameIntegrantes = "ListaIntegrantesViagens.csv";
        String fileNameEstatisticas = "ListaEstatisticas.csv";

        File pasta = new File(diretorio, directoryName);
        String diretorioFinal = pasta.getPath();
        File arquivoViagens = new File(diretorioFinal, fileNameViagens);
        File arquivoIntegrantes = new File(diretorioFinal, fileNameIntegrantes);
        File arquivoEstatisticas = new File(diretorioFinal, fileNameEstatisticas);

        if (!arquivoViagens.exists() && !arquivoIntegrantes.exists() && !arquivoEstatisticas.exists()) {
            Toast.makeText(this, "Os documentos para serem importados não existem", Toast.LENGTH_LONG).show();
        } else {
            try {
                if(arquivoViagens.exists()) {
                    CSVReader reader = new CSVReader(new FileReader(arquivoViagens.getAbsolutePath()));
                    String[] nextLineViagens;
                    while ((nextLineViagens = reader.readNext()) != null) {
                        //System.out.println("ID: " + nextLine[0] + "\nNome: " + nextLine[1] + "\nCPF: " + nextLine[2]);
                        Viagens viagem = new Viagens();
                        viagem.setNomeCliente(nextLineViagens[1]);
                        viagem.setCpfCliente(nextLineViagens[2]);
                        viagem.setRgCliente(nextLineViagens[3]);
                        viagem.setDataNascimentoCliente(nextLineViagens[4]);
                        viagem.setCidade(nextLineViagens[5]);
                        viagem.setHotel(nextLineViagens[6]);
                        viagem.setLocalizador(nextLineViagens[7]);
                        viagem.setCompanhiaAerea(nextLineViagens[8]);
                        viagem.setNumeroVenda(nextLineViagens[9]);
                        viagem.setEmbarqueHora(nextLineViagens[10]);
                        viagem.setEmbarqueData(nextLineViagens[11]);
                        viagem.setDesembarqueHora(nextLineViagens[12]);
                        viagem.setDesembarqueData(nextLineViagens[13]);
                        viagem.setObservacoes(nextLineViagens[14]);
                        viagem.setValorTotal(Double.parseDouble(nextLineViagens[15]));
                        viagem.setValorComissao(Double.parseDouble(nextLineViagens[16]));
                        long id = bd.salvarViagem(viagem);


                        if (arquivoIntegrantes.exists()) {
                            CSVReader readerIntegrantes = new CSVReader(new FileReader(arquivoIntegrantes.getAbsolutePath()));
                            String[] nextLineIntegrantes;
                            while ((nextLineIntegrantes = readerIntegrantes.readNext()) != null) {
                                if (nextLineViagens[0].equals(nextLineIntegrantes[5])) {
                                    Pessoa pessoa = new Pessoa();
                                    pessoa.setNome(nextLineIntegrantes[1]);
                                    pessoa.setCpf(nextLineIntegrantes[2]);
                                    pessoa.setRg(nextLineIntegrantes[3]);
                                    pessoa.setDataNascimento(nextLineIntegrantes[4]);
                                    pessoa.setFkViagens(Integer.parseInt(String.valueOf(id)));
                                    bd.salvarPessoa(pessoa);
                                }
                            }
                        }

                    }
                }


                if(arquivoEstatisticas.exists()) {
                    CSVReader reader = new CSVReader(new FileReader(arquivoEstatisticas.getAbsolutePath()));
                    String[] nextLineEstatisticas;
                    while ((nextLineEstatisticas = reader.readNext()) != null) {
                        Estatistica estatistica = new Estatistica();
                        estatistica.setNomeCliente(nextLineEstatisticas[1]);
                        estatistica.setCpfCliente(nextLineEstatisticas[2]);
                        estatistica.setRgCliente(nextLineEstatisticas[3]);
                        estatistica.setDataNascimentoCliente(nextLineEstatisticas[4]);
                        estatistica.setCidade(nextLineEstatisticas[5]);
                        estatistica.setHotel(nextLineEstatisticas[6]);
                        estatistica.setLocalizador(nextLineEstatisticas[7]);
                        estatistica.setCompanhiaAerea(nextLineEstatisticas[8]);
                        estatistica.setNumeroVenda(nextLineEstatisticas[9]);
                        estatistica.setEmbarqueHora(nextLineEstatisticas[10]);
                        estatistica.setEmbarqueData(nextLineEstatisticas[11]);
                        estatistica.setDesembarqueHora(nextLineEstatisticas[12]);
                        estatistica.setDesembarqueData(nextLineEstatisticas[13]);
                        estatistica.setObservacoes(nextLineEstatisticas[14]);
                        estatistica.setValorTotal(Double.parseDouble(nextLineEstatisticas[15]));
                        estatistica.setValorComissao(Double.parseDouble(nextLineEstatisticas[16]));
                        estatistica.setAno(Integer.parseInt(nextLineEstatisticas[17]));
                        estatistica.setMes(nextLineEstatisticas[18]);
                        bd.salvarViagemEstatisticas(estatistica);
                    }
                }

                Toast.makeText(this, "Dados importados com sucesso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ListaViagens.class));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public void exportarArquivos(){
        String diretorio = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();

        String directoryName = "TravelSeller";
        String fileNameViagens = "ListaViagens.csv";
        String fileNameIntegrantes = "ListaIntegrantesViagens.csv";
        String fileNameEstatisticas = "ListaEstatisticas.csv";

        File pasta = new File(diretorio, directoryName);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        String diretorioFinal = pasta.getPath();
        File arquivoViagens = new File(diretorioFinal, fileNameViagens);
        File arquivoIntegrantes = new File(diretorioFinal, fileNameIntegrantes);
        File arquivoEstatisticas = new File(diretorioFinal, fileNameEstatisticas);

        //Export das Viagens
        criarArquivo(arquivoViagens, diretorioFinal, fileNameViagens);
        exportarViagens(arquivoViagens);

        //Export dos Integrantes
        criarArquivo(arquivoIntegrantes, diretorioFinal, fileNameIntegrantes);
        exportarIntegrantes(arquivoIntegrantes);

        //Export das Estatisticas
        criarArquivo(arquivoEstatisticas, diretorioFinal, fileNameEstatisticas);
        exportarEstatisticas(arquivoEstatisticas);


        Toast.makeText(this, "Dados Exportados para 'Documentos/TravelSeller'", Toast.LENGTH_LONG).show();

        startActivity(new Intent(Settings.this, ListaViagens.class));
    }

    public void criarArquivo(File arquivo, String diretorioFinal, String fileName){
        if(!arquivo.exists() && !arquivo.isDirectory()){
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                arquivo.delete();
                File arquivoNew = new File(diretorioFinal, fileName);
                arquivoNew.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void exportarViagens(File arquivoViagens){
        CSVWriter writerViagens;

        try {
            writerViagens = new CSVWriter(new FileWriter(arquivoViagens));

            List<String[]> dados = new ArrayList<>();

            ArrayList<Viagens> listaViagens = bd.getListaClientes();
            if(listaViagens!=null) {
                for (Viagens viagem : listaViagens) {
                    long id = viagem.getId();
                    String nome = viagem.getNomeCliente();
                    String cpf = viagem.getCpfCliente();
                    String rg = viagem.getRgCliente();
                    String dataNascimento = viagem.getDataNascimentoCliente();
                    String cidade = viagem.getCidade();
                    String hotel = viagem.getHotel();
                    String localizador = viagem.getLocalizador();
                    String companhiaAerea = viagem.getCompanhiaAerea();
                    String numeroVenda = viagem.getNumeroVenda();
                    String embarqueHora = viagem.getEmbarqueHora();
                    String embarqueData = viagem.getEmbarqueData();
                    String desembarqueHora = viagem.getDesembarqueHora();
                    String desembarqueData = viagem.getDesembarqueData();
                    String observacoes = viagem.getObservacoes();
                    double valorTotal = viagem.getValorTotal();
                    double valorComissao = viagem.getValorComissao();

                    String[] dadosViagem = new String[]{String.valueOf(id), nome, cpf, rg, dataNascimento, cidade, hotel, localizador, companhiaAerea, numeroVenda, embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes, String.valueOf(valorTotal), String.valueOf(valorComissao)};

                    dados.add(dadosViagem);
                }
            }

            writerViagens.writeAll(dados);
            writerViagens.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void exportarIntegrantes(File arquivoIntegrantes){
        CSVWriter writerIntegrantes;

        try {
            writerIntegrantes = new CSVWriter(new FileWriter(arquivoIntegrantes));

            List<String[]> dados = new ArrayList<>();

            ArrayList<Pessoa> listaIntegrantes = bd.getListaAllIntegrantes();
            if(listaIntegrantes!=null) {
                for (Pessoa integrante : listaIntegrantes) {
                    long id = integrante.getId();
                    String nome = integrante.getNome();
                    String cpf = integrante.getCpf();
                    String rg = integrante.getRg();
                    String dataNascimento = integrante.getDataNascimento();
                    int fkViagens = integrante.getFkViagens();

                    String[] dadosIntegrantes = new String[]{String.valueOf(id), nome, cpf, rg, dataNascimento, String.valueOf(fkViagens)};

                    dados.add(dadosIntegrantes);
                }
            }

            writerIntegrantes.writeAll(dados);
            writerIntegrantes.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void exportarEstatisticas(File arquivoEstatisticas){
        CSVWriter writerEstatisticas;

        try {
            writerEstatisticas = new CSVWriter(new FileWriter(arquivoEstatisticas));

            List<String[]> dados = new ArrayList<>();

            ArrayList<Estatistica> listaEstatisticas = bd.getListaAllEstatisticas();
            if(listaEstatisticas!=null) {
                for (Estatistica estatisticas : listaEstatisticas) {
                    long id = estatisticas.getId();
                    String nome = estatisticas.getNomeCliente();
                    String cpf = estatisticas.getCpfCliente();
                    String rg = estatisticas.getRgCliente();
                    String dataNascimento = estatisticas.getDataNascimentoCliente();
                    String cidade = estatisticas.getCidade();
                    String hotel = estatisticas.getHotel();
                    String localizador = estatisticas.getLocalizador();
                    String companhiaAerea = estatisticas.getCompanhiaAerea();
                    String numeroVenda = estatisticas.getNumeroVenda();
                    String embarqueHora = estatisticas.getEmbarqueHora();
                    String embarqueData = estatisticas.getEmbarqueData();
                    String desembarqueHora = estatisticas.getDesembarqueHora();
                    String desembarqueData = estatisticas.getDesembarqueData();
                    String observacoes = estatisticas.getObservacoes();
                    double valorTotal = estatisticas.getValorTotal();
                    double valorComissao = estatisticas.getValorComissao();
                    int ano = estatisticas.getAno();
                    String mes = estatisticas.getMes();

                    String[] dadosEstatisticas = new String[]{String.valueOf(id), nome, cpf, rg, dataNascimento, cidade, hotel, localizador, companhiaAerea, numeroVenda, embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes, String.valueOf(valorTotal), String.valueOf(valorComissao), String.valueOf(ano), mes};

                    dados.add(dadosEstatisticas);
                }
            }

            writerEstatisticas.writeAll(dados);
            writerEstatisticas.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION_MANAGE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    exportarArquivos();
                }
            case REQUEST_PERMISSION_WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    exportarArquivos();
                }
        }
    }

}