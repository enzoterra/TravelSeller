package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EstatisticasAnosListAdapter extends ArrayAdapter<Integer> {

    private final Context context;
    private final ArrayList<Integer> anos, valorTotal, valorComissao;

    //Adapter
    public EstatisticasAnosListAdapter(Context context, ArrayList<Integer> elementos, ArrayList<Integer> valorTotal, ArrayList<Integer> valorComissao){
        super(context, R.layout.linha_anos_meses, elementos);
        this.context = context;
        this.anos = elementos;
        this.valorTotal = valorTotal;
        this.valorComissao = valorComissao;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Aplicar Adapter
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.linha_anos_meses, parent, false);


        //Encontrar TextViews
        TextView ano_mes = rowView.findViewById(R.id.anoOuMesLinha);
        TextView total = rowView.findViewById(R.id.totalLinhaEstatisticas);
        TextView comissao = rowView.findViewById(R.id.comissaoLinhaEstatisticas);


        //Setar Textos
        String totalString = "Total: R$ " + valorTotal.get(position).toString();
        String comissaoString = "Comissão: R$ " + valorComissao.get(position).toString();
        String ano;
        if(anos.get(position)==0 || anos.get(position)==null){
            ano = "Sem Ano";
        } else {
            ano = String.valueOf(anos.get(position));
        }
        ano_mes.setText(ano);
        total.setText(totalString);
        comissao.setText(comissaoString);


        return rowView;
    }
}