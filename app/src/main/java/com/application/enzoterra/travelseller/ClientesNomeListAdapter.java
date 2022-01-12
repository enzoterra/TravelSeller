package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.application.enzoterra.travelseller.Model.Viagens;

import java.util.ArrayList;

public class ClientesNomeListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> elementos;
    private final ArrayList<String> nomes = null;

    //Adapter
    public ClientesNomeListAdapter(Context context, ArrayList<String> elementos){
        super(context, R.layout.linha_pessoa, elementos);
        this.context = context;
        this.elementos = elementos;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Aplicar Adapter
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.linha_pessoa, parent, false);


        //Encontrar TextViews
        TextView nome = (TextView) rowView.findViewById(R.id.nomeLinha);


        //Setar Textos
        nome.setText(elementos.get(position));


        return rowView;
    }

}



