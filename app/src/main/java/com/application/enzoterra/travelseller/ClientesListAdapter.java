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

public class ClientesListAdapter extends ArrayAdapter<Viagens> {

    private final Context context;
    private final ArrayList<Viagens> elementos;

    //Adapter
    public ClientesListAdapter(Context context, ArrayList<Viagens> elementos){
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
        TextView nome = rowView.findViewById(R.id.nomeLinha);


        //Setar Textos
        nome.setText(elementos.get(position).getNomeCliente());


        return rowView;
    }

}


