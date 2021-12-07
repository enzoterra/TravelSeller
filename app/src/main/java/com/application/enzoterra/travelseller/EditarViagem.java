package com.application.enzoterra.travelseller;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Estatistica;
import com.application.enzoterra.travelseller.Model.Viagens;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

public class EditarViagem extends AppCompatActivity implements InterfaceComunicacao{

    String[] dados, dadosVoo, restanteDados;
    Button salvar;
    FragmentAdapter fragmentAdapter;
    int ano = 0;
    String tela;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_viagem);

        //Receber Dados
        final Intent getIntent = getIntent();
        long parId = (long) getIntent.getSerializableExtra("id");
        String varId = String.valueOf(parId);
        final int id = Integer.parseInt(varId);
        final String nomeCliente = (String) getIntent.getSerializableExtra("nomeCliente");
        final String cpfCliente = (String) getIntent.getSerializableExtra("cpfCliente");
        final String rgCliente = (String) getIntent.getSerializableExtra("rgCliente");
        final String dataNascimentoCliente = (String) getIntent.getSerializableExtra("dataNascimentoCliente");
        final String hotel = (String) getIntent.getSerializableExtra("hotel");
        final String cidade = (String) getIntent.getSerializableExtra("cidade");
        final String localizador = (String) getIntent.getSerializableExtra("localizador");
        final String numeroVenda = (String) getIntent.getSerializableExtra("numeroVenda");
        final String embarqueHora = (String) getIntent.getSerializableExtra("embarqueHora");
        final String embarqueData = (String) getIntent.getSerializableExtra("embarqueData");
        final String desembarqueHora = (String) getIntent.getSerializableExtra("desembarqueHora");
        final String desembarqueData = (String) getIntent.getSerializableExtra("desembarqueData");
        final String observacoes = (String) getIntent.getSerializableExtra("observacoes");
        final double valorTotal = (double) getIntent.getSerializableExtra("valorTotal");
        final double valorComissao = (double) getIntent.getSerializableExtra("valorComissao");
        tela = (String) getIntent.getSerializableExtra("tela");
        String telaPrincipal = tela;

        dados = new String[]{String.valueOf(id), cidade, hotel, localizador, numeroVenda, embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes, String.valueOf(valorTotal), String.valueOf(valorComissao), telaPrincipal};
        passarValores();


        //PagerView
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        final ViewPager2 viewPager = findViewById(R.id.viewPager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fragmentManager, getLifecycle());

        fragmentAdapter.setDados(dados);

        viewPager.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Dados do Voo"));
        tabLayout.addTab(tabLayout.newTab().setText("Outras Informações"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        //Button Salvar
        salvar = findViewById(R.id.buttonSalvarEditar);
        salvar.setOnClickListener(view -> {

            dados = fragmentAdapter.getUpdate();

            if(tela.equals("viagens") || tela.equals("clientes")) {

                ViagensBD bd = new ViagensBD(EditarViagem.this);
                //ArrayList<Configuracoes> configuracoesLista = bd.getConfiguracoes();
                //int passar = configuracoesLista.get(0).getPassar();

                //if(passar==1){

                //}

                Viagens viagem = new Viagens();
                viagem.setCidade(dados[1]);
                viagem.setHotel(dados[2]);
                viagem.setLocalizador(dados[3]);
                viagem.setNumeroVenda(dados[4]);
                viagem.setEmbarqueHora(dados[5]);
                viagem.setEmbarqueData(dados[6]);
                viagem.setDesembarqueHora(dados[7]);
                viagem.setDesembarqueData(dados[8]);
                viagem.setObservacoes(dados[9]);
                viagem.setValorTotal(Double.parseDouble(dados[10]));
                viagem.setValorComissao(Double.parseDouble(dados[11]));

                bd.alterarViagem(viagem, id);

                //Alterar Estatisticas
                ArrayList<Viagens> list = bd.getListaClientesID(id);
                Estatistica estatistica = new Estatistica();

                String dataViagem = list.get(0).getEmbarqueData();
                String mes = "";

                if (dataViagem.equals("")) {
                    dataViagem = list.get(0).getDesembarqueData();
                }

                if (!dataViagem.equals("")) {
                    String[] anoStr = dataViagem.split("/");
                    ano = Integer.parseInt(anoStr[2]);
                    mes = anoStr[1];
                }

                estatistica.setNomeCliente(list.get(0).getNomeCliente());
                estatistica.setCpfCliente(list.get(0).getCpfCliente());
                estatistica.setRgCliente(list.get(0).getRgCliente());
                estatistica.setDataNascimentoCliente(list.get(0).getDataNascimentoCliente());
                estatistica.setCidade(dados[1]);
                estatistica.setHotel(dados[2]);
                estatistica.setLocalizador(dados[3]);
                estatistica.setNumeroVenda(dados[4]);
                estatistica.setEmbarqueHora(dados[5]);
                estatistica.setEmbarqueData(dados[6]);
                estatistica.setDesembarqueHora(dados[7]);
                estatistica.setDesembarqueData(dados[8]);
                estatistica.setObservacoes(dados[9]);
                estatistica.setValorTotal(Double.parseDouble(dados[10]));
                estatistica.setValorComissao(Double.parseDouble(dados[11]));
                estatistica.setAno(ano);
                estatistica.setMes(mes);

                viagem = list.get(0);

                String[] dadosEstatistica = {viagem.getNomeCliente(), viagem.getCpfCliente(), viagem.getRgCliente(), viagem.getDataNascimentoCliente(),
                        /*viagem.getCidade(), viagem.getHotel(), viagem.getLocalizador(),*/ dados[4] /*viagem.getEmbarqueHora(), viagem.getEmbarqueData(),
                            viagem.getDesembarqueHora(), viagem.getDesembarqueData(), viagem.getObservacoes()*/};

                ArrayList<Estatistica> estatisticaArrayList = bd.getEstatisticaEspecifica(dadosEstatistica);

                if(estatisticaArrayList.size()!=0) {
                    int idEstatistica = Integer.parseInt(String.valueOf(estatisticaArrayList.get(0).getId()));
                    bd.alterarViagemEstatisticas(estatistica, idEstatistica);
                } else {
                    bd.salvarViagemEstatisticas(estatistica);
                }

                bd.close();

            } else {
                Estatistica estatistica = new Estatistica();
                ViagensBD bd = new ViagensBD(EditarViagem.this);

                String dataViagem = dados[6];
                String mes = "";

                if (dataViagem.equals("")) {
                    dataViagem = dados[8];
                }

                if (!dataViagem.equals("")) {
                    String[] anoStr = dataViagem.split("/");
                    ano = Integer.parseInt(anoStr[2]);
                    mes = anoStr[1];
                }

                estatistica.setCidade(dados[1]);
                estatistica.setHotel(dados[2]);
                estatistica.setLocalizador(dados[3]);
                estatistica.setNumeroVenda(dados[4]);
                estatistica.setEmbarqueHora(dados[5]);
                estatistica.setEmbarqueData(dados[6]);
                estatistica.setDesembarqueHora(dados[7]);
                estatistica.setDesembarqueData(dados[8]);
                estatistica.setObservacoes(dados[9]);
                estatistica.setValorTotal(Double.parseDouble(dados[10]));
                estatistica.setValorComissao(Double.parseDouble(dados[11]));
                estatistica.setAno(ano);
                estatistica.setMes(mes);

                bd.alterarViagemEstatisticas(estatistica, id);
                bd.close();
            }
            if (tela.equals("viagens")) {
                startActivity(new Intent(EditarViagem.this, ListaViagens.class));
            } else if (tela.equals("clientes")) {
                startActivity(new Intent(EditarViagem.this, Clientes.class));
            } else {
                startActivity(new Intent(EditarViagem.this, Estatisticas.class));
            }
        });


        //Button Excluir
        Button excluir = findViewById(R.id.buttonExluirEditar);
        excluir.setOnClickListener(view -> {
            if(tela.equals("estatisticas")){
                AlertDialog.Builder msgBoxExcluir = new AlertDialog.Builder(EditarViagem.this);
                msgBoxExcluir.setTitle("Atenção ...");
                msgBoxExcluir.setMessage("Deseja excluir esta viagem das estatísticas?");
                msgBoxExcluir.setPositiveButton("Sim", (dialogInterface, i) -> {
                    ViagensBD bd = new ViagensBD(EditarViagem.this);
                    bd.deletarEstatistica(id);
                    bd.close();
                    Toast.makeText(EditarViagem.this, "Excluído com Sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditarViagem.this, Estatisticas.class));
                });
                msgBoxExcluir.setNegativeButton("Não", (dialogInterface, i) -> {
                });
                msgBoxExcluir.show();
            } else {
                AlertDialog.Builder msgBoxExcluir = new AlertDialog.Builder(EditarViagem.this);
                msgBoxExcluir.setTitle("Atenção ...");
                msgBoxExcluir.setMessage("Deseja excluir todas estas informações?\n\n(Também serão excluídos TODOS OS INTEGRANTES dessa viagem!!!)\n\n" +
                        "Se quiser apenas limpar as informações da viagem, aperte no botão localizado no canto superior direito");
                msgBoxExcluir.setPositiveButton("Sim", (dialogInterface, i) -> {
                    ViagensBD bd = new ViagensBD(EditarViagem.this);
                    bd.deletarViagem(id);
                    bd.close();
                    Toast.makeText(EditarViagem.this, "Excluído com Sucesso", Toast.LENGTH_SHORT).show();
                    if(tela.equals("viagens")){
                        startActivity(new Intent(EditarViagem.this, ListaViagens.class));
                    } else {
                        startActivity(new Intent(EditarViagem.this, Clientes.class));
                    }
                });
                msgBoxExcluir.setNegativeButton("Não", (dialogInterface, i) -> {
                });
                msgBoxExcluir.show();
            }
        });


        //Button Voltar
        ImageButton voltar = findViewById(R.id.buttonVoltarEditar);
        voltar.setOnClickListener(v -> {
            if (tela.equals("viagens")) {
                startActivity(new Intent(EditarViagem.this, ListaViagens.class));
            } else if (tela.equals("clientes")) {
                startActivity(new Intent(EditarViagem.this, Clientes.class));
            } else {
                startActivity(new Intent(EditarViagem.this, Estatisticas.class));
            }
        });


        //Button Zerar Informações
        ImageButton zerar = findViewById(R.id.buttonZerarEditar);
        if(tela.equals("estatisticas")){
            zerar.setVisibility(View.INVISIBLE);
        }
        zerar.setOnClickListener(v -> {
            AlertDialog.Builder msgBoxZerar = new AlertDialog.Builder(EditarViagem.this);
            msgBoxZerar.setTitle("Atenção ...");
            msgBoxZerar.setMessage("Deseja apagar as informações referentes a viagem, como, por exemplo, os dados do voo e dos valores?");
            msgBoxZerar.setPositiveButton("Sim", (dialogInterface, i) -> {

                ViagensBD bd = new ViagensBD(EditarViagem.this);

                //Alterar Estatisticas
                Estatistica estatistica = new Estatistica();
                ArrayList<Viagens> list = bd.getListaClientesID(id);

                String dataViagem = list.get(0).getEmbarqueData();
                String mes = "";

                if (dataViagem.equals("")) {
                    dataViagem = list.get(0).getDesembarqueData();
                }

                if (!dataViagem.equals("")) {
                    String[] anoStr = dataViagem.split("/");
                    ano = Integer.parseInt(anoStr[2]);
                    mes = anoStr[1];
                }

                estatistica.setNomeCliente(list.get(0).getNomeCliente());
                estatistica.setCpfCliente(list.get(0).getCpfCliente());
                estatistica.setRgCliente(list.get(0).getRgCliente());
                estatistica.setDataNascimentoCliente(list.get(0).getDataNascimentoCliente());
                estatistica.setCidade(list.get(0).getCidade());
                estatistica.setHotel(list.get(0).getHotel());
                estatistica.setLocalizador(list.get(0).getLocalizador());
                estatistica.setNumeroVenda(list.get(0).getNumeroVenda());
                estatistica.setEmbarqueHora(list.get(0).getEmbarqueHora());
                estatistica.setEmbarqueData(list.get(0).getEmbarqueData());
                estatistica.setDesembarqueHora(list.get(0).getDesembarqueHora());
                estatistica.setDesembarqueData(list.get(0).getDesembarqueData());
                estatistica.setObservacoes(list.get(0).getObservacoes());
                estatistica.setValorTotal(list.get(0).getValorTotal());
                estatistica.setValorComissao(list.get(0).getValorComissao());
                estatistica.setAno(ano);
                estatistica.setMes(mes);

                Viagens viagem = list.get(0);

                String[] dadosEstatistica = {viagem.getNomeCliente(), viagem.getCpfCliente(), viagem.getRgCliente(), viagem.getDataNascimentoCliente(), viagem.getNumeroVenda()};

                ArrayList<Estatistica> estatisticaArrayList = bd.getEstatisticaEspecifica(dadosEstatistica);

                if(estatisticaArrayList.size()!=0) {
                    int idEstatistica = Integer.parseInt(String.valueOf(estatisticaArrayList.get(0).getId()));
                    bd.alterarViagemEstatisticas(estatistica, idEstatistica);
                } else {
                    bd.salvarViagemEstatisticas(estatistica);
                }

                bd.zerarViagem(id);
                bd.close();

                startActivity(new Intent(EditarViagem.this, ListaViagens.class));
            });
            msgBoxZerar.setNegativeButton("Não", (dialogInterface, i) -> { });
            msgBoxZerar.show();
        });


        //Cor da Barra de Status
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.green_cadastro));
    }


    protected void passarValores() {
        this.dadosVoo = dados;
        this.restanteDados = dados;
    }

    public void setDadosVoo(String[] dadosVoo) {
        this.dadosVoo = dadosVoo;
        dados[1] = dadosVoo[1];
        dados[2] = dadosVoo[2];
        dados[3] = dadosVoo[3];
        dados[4] = dadosVoo[4];
        dados[5] = dadosVoo[5];
        dados[6] = dadosVoo[6];
        dados[7] = dadosVoo[7];
        dados[8] = dadosVoo[8];
        fragmentAdapter.setDadosVoo(dadosVoo);
    }

    public void setRestanteDados(String[] restanteDados) {
        this.restanteDados = restanteDados;
        dados[9] = restanteDados[9];
        dados[10] = restanteDados[10];
        dados[11] = restanteDados[11];
        fragmentAdapter.setRestanteDados(restanteDados);
    }


    public String[] getDados(){
        return dados;
    }


    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4) {
            if(tela.equals("estatisticas")){
                startActivity(new Intent(EditarViagem.this, Estatisticas.class));
            } else if (tela.equals("clientes")){
                startActivity(new Intent(EditarViagem.this, Clientes.class));
            } else {
                startActivity(new Intent(EditarViagem.this, ListaViagens.class));
            }
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}