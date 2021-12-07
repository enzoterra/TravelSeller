package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.application.enzoterra.travelseller.Model.Estatistica;

import java.util.ArrayList;

public class EstatisticasViagemListAdapter extends ArrayAdapter<Estatistica> {

    private final Context context;
    private final ArrayList<Estatistica> viagens;

    //Adapter
    public EstatisticasViagemListAdapter(Context context, ArrayList<Estatistica> elementos) {
        super(context, R.layout.linha_viagem, elementos);
        this.context = context;
        this.viagens = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Aplicar Adapter
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.linha_viagem, parent, false);


        //Encontrar TextViews
        TextView nome = rowView.findViewById(R.id.nomeClienteLinha);
        TextView destino = rowView.findViewById(R.id.destinoViagemLinha);
        TextView total = rowView.findViewById(R.id.DiaEmbarqueLinha);
        TextView comissao = rowView.findViewById(R.id.DiaDesembarqueLinha);


        //Setar Textos
        String totalString = "R$ " + viagens.get(position).getValorTotal();
        String comissaoString = "R$ " + viagens.get(position).getValorComissao();

        nome.setText(viagens.get(position).getNomeCliente());
        destino.setText(viagens.get(position).getCidade());
        total.setText(totalString);
        comissao.setText(comissaoString);


        return rowView;
    }
}

