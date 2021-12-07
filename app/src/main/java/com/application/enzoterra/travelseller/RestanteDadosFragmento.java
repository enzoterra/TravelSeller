package com.application.enzoterra.travelseller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class RestanteDadosFragmento extends Fragment{

    String cidade, hotel, localizador, numeroVenda, embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes;
    int id;
    double valorTotal, valorComissao;
    EditText edtObservacoes, edtValorTotal, edtComissao;
    String telaPrincipal;
    InterfaceComunicacao interfaceListener;

    public void setDadosTotais(String[] dados) {
        this.id = Integer.parseInt(dados[0]);
        this.cidade = dados[1];
        this.hotel = dados[2];
        this.localizador = dados[3];
        this.numeroVenda = dados[4];
        this.embarqueHora = dados[5];
        this.embarqueData = dados[6];
        this.desembarqueHora = dados[7];
        this.desembarqueData = dados[8];
        this.observacoes = dados[9];
        this.valorTotal = Double.parseDouble(dados[10]);
        this.valorComissao = Double.parseDouble(dados[11]);
        this.telaPrincipal = dados[12];
    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        if(activity instanceof InterfaceComunicacao){
            interfaceListener = (InterfaceComunicacao) activity;
        } else {
            throw new RuntimeException("Algo de errado aconteceu! Entre em contato com o desenvolvedor e reporte o problema");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restante_dados, container, false);

        edtObservacoes = view.findViewById(R.id.ETobservacoesFragment);
        edtValorTotal = view.findViewById(R.id.ETvalorTotalFragment);
        edtComissao = view.findViewById(R.id.ETcomissaoFragment);

        edtObservacoes.setText(observacoes);
        edtValorTotal.setText(String.valueOf(valorTotal));
        edtComissao.setText(String.valueOf(valorComissao));

        Button integrantes = view.findViewById(R.id.buttonIntegrantesFragment);
        if(telaPrincipal.equals("estatisticas")){
            integrantes.setVisibility(View.INVISIBLE);
        }
        integrantes.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ListaPessoas.class);
            String[] dadosActivity = interfaceListener.getDados();
            String[] dados = getDados();

            intent.putExtra("id", Integer.parseInt(dados[0]));
            intent.putExtra("tela", "fragmento");
            intent.putExtra("cidade", dadosActivity[1]);
            intent.putExtra("hotel", dadosActivity[2]);
            intent.putExtra("localizador", dadosActivity[3]);
            intent.putExtra("numeroVenda", dadosActivity[4]);
            intent.putExtra("embarqueHora", dadosActivity[5]);
            intent.putExtra("embarqueData", dadosActivity[6]);
            intent.putExtra("desembarqueHora", dadosActivity[7]);
            intent.putExtra("desembarqueData", dadosActivity[8]);
            intent.putExtra("observacoes", dados[9]);
            intent.putExtra("valorTotal", dados[10]);
            intent.putExtra("valorComissao", dados[11]);
            intent.putExtra("telaPrincipal", telaPrincipal);

            startActivity(intent);
        });

        return view;
    }


    public String[] getDados(){
        observacoes = edtObservacoes.getText().toString();
        valorTotal = Double.parseDouble(edtValorTotal.getText().toString());
        valorComissao = Double.parseDouble(edtComissao.getText().toString());
        return new String[]{String.valueOf(id), cidade, hotel, localizador, numeroVenda,
                embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes, String.valueOf(valorTotal), String.valueOf(valorComissao)};
    }

    @Override
    public void onPause() {
        super.onPause();
        interfaceListener.setRestanteDados(getDados());
    }

}