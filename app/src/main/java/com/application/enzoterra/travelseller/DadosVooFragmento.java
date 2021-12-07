package com.application.enzoterra.travelseller;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class DadosVooFragmento extends Fragment{

    //Criação das Variáveis
    String cidade, hotel, localizador, numeroVenda, embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes;
    int id;
    double valorTotal, valorComissao;
    EditText edtCidade, edtHotel, edtLocalizador, edtNumeroVenda, edtEmbarqueData, edtEmbarqueHora, edtDesembarqueData, edtDesembarqueHora;
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
        View view = inflater.inflate(R.layout.fragment_dados_voo, container, false);

        edtCidade = view.findViewById(R.id.ETcidadeFragment);
        edtHotel = view.findViewById(R.id.EThotelFragment);
        edtLocalizador = view.findViewById(R.id.ETlocalizadorFragment);
        edtNumeroVenda = view.findViewById(R.id.ETvendaFragment);
        edtEmbarqueData = view.findViewById(R.id.ETdataEmbarqueFragment);
        edtEmbarqueHora = view.findViewById(R.id.EThoraEmbarqueFragment);
        edtDesembarqueData = view.findViewById(R.id.ETdataDesembarqueFragment);
        edtDesembarqueHora = view.findViewById(R.id.EThoraDesembarqueFragment);

        edtCidade.setText(cidade);
        edtHotel.setText(hotel);
        edtLocalizador.setText(localizador);
        edtNumeroVenda.setText(numeroVenda);
        edtEmbarqueData.setText(embarqueData);
        edtEmbarqueHora.setText(embarqueHora);
        edtDesembarqueData.setText(desembarqueData);
        edtDesembarqueHora.setText(desembarqueHora);

        edtEmbarqueData.addTextChangedListener(MaskEditUtil.mask(edtEmbarqueData, MaskEditUtil.FORMAT_DATE));
        edtEmbarqueHora.addTextChangedListener(MaskEditUtil.mask(edtEmbarqueHora, MaskEditUtil.FORMAT_HOUR));
        edtDesembarqueData.addTextChangedListener(MaskEditUtil.mask(edtDesembarqueData, MaskEditUtil.FORMAT_DATE));
        edtDesembarqueHora.addTextChangedListener(MaskEditUtil.mask(edtDesembarqueHora, MaskEditUtil.FORMAT_HOUR));

        return view;
    }


    public String[] getDados(){
        cidade = edtCidade.getText().toString();
        hotel = edtHotel.getText().toString();
        localizador = edtLocalizador.getText().toString();
        numeroVenda = edtNumeroVenda.getText().toString();
        embarqueData = edtEmbarqueData.getText().toString();
        embarqueHora = edtEmbarqueHora.getText().toString();
        desembarqueData = edtDesembarqueData.getText().toString();
        desembarqueHora = edtDesembarqueHora.getText().toString();
        return new String[]{String.valueOf(id), cidade, hotel, localizador, numeroVenda,
                embarqueHora, embarqueData, desembarqueHora, desembarqueData, observacoes, String.valueOf(valorTotal), String.valueOf(valorComissao)};
    }

    @Override
    public void onPause() {
        super.onPause();
        interfaceListener.setDadosVoo(getDados());
    }

}