package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import com.application.enzoterra.travelseller.Model.Viagens;

public class ViagensListAdapter extends ArrayAdapter<Viagens> {

    private final Context context;
    private final ArrayList<Viagens> elementos;

    //Adapter
    public ViagensListAdapter(Context context, ArrayList<Viagens> elementos){
        super(context, R.layout.linha_viagem, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Aplicar Adapter
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.linha_viagem, parent, false);


        //Encontrar TextViews
        TextView nome = rowView.findViewById(R.id.nomeClienteLinha);
        TextView destino = rowView.findViewById(R.id.destinoViagemLinha);
        TextView embarque = rowView.findViewById(R.id.DiaEmbarqueLinha);
        TextView desembarque = rowView.findViewById(R.id.DiaDesembarqueLinha);


        //Máscaras Datas
        //Embarque
        String[] embarqueData = elementos.get(position).getEmbarqueData().split("/");
        String[] ano;
        String dataEmbarque = "";

        if(embarqueData.length == 3) {
            ano = embarqueData[2].split("");
            if(ano.length > 3) {
                dataEmbarque = embarqueData[0].concat("/").concat(embarqueData[1]).concat("/").concat(ano[2]).concat(ano[3]);
            }
        }

        //Desembarque
        String[] desembarqueData = elementos.get(position).getDesembarqueData().split("/");
        String[] ano2;
        String dataDesembarque = "";

        if(desembarqueData.length == 3) {
            ano2 = desembarqueData[2].split("");
            if(ano2.length > 3) {
                dataDesembarque = desembarqueData[0].concat("/").concat(desembarqueData[1]).concat("/").concat(ano2[2]).concat(ano2[3]);
            }
        }


        //Setar Textos
        String destinoString = /*"Destino: " +*/ elementos.get(position).getCidade();
        nome.setText(elementos.get(position).getNomeCliente());
        destino.setText(destinoString);
        embarque.setText(dataEmbarque);
        desembarque.setText(dataDesembarque);


        return rowView;
    }
}

