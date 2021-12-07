package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EstatisticasMesesListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> meses;
    private final ArrayList<Integer> valorTotal, valorComissao;

    //Adapter
    public EstatisticasMesesListAdapter(Context context, ArrayList<String> elementos, ArrayList<Integer> valorTotal, ArrayList<Integer> valorComissao){
        super(context, R.layout.linha_anos_meses, elementos);
        this.context = context;
        this.meses = elementos;
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
        String mes;
        if(meses.get(position).equals("") || meses.get(position)==null){
            mes = "Sem Mês";
        } else {
            mes = calculaMes(String.valueOf(meses.get(position)));
        }
        ano_mes.setText(mes);
        total.setText(totalString);
        comissao.setText(comissaoString);


        return rowView;
    }

    public String calculaMes(String mes){
        switch (mes){
            case "01":
                mes = "Janeiro";
                break;
            case "02":
                mes = "Fevereiro";
                break;
            case "03":
                mes = "Março";
                break;
            case "04":
                mes = "Abril";
                break;
            case "05":
                mes = "Maio";
                break;
            case "06":
                mes = "Junho";
                break;
            case "07":
                mes = "Julho";
                break;
            case "08":
                mes = "Agosto";
                break;
            case "09":
                mes = "Setembro";
                break;
            case "10":
                mes = "Outubro";
                break;
            case "11":
                mes = "Novembro";
                break;
            case "12":
                mes = "Dezembro";
                break;
        }
        return mes;
    }
}