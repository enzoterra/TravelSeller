package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.application.enzoterra.travelseller.Model.Pessoa;

import java.util.ArrayList;

public class PessoasListAdapter extends ArrayAdapter<Pessoa> {

    private final Context context;
    private final ArrayList<Pessoa> elementos;

    //Adapter
    public PessoasListAdapter(Context context, ArrayList<Pessoa> elementos){
        super(context, R.layout.linha_integrante, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Aplicar Adapter
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.linha_integrante, parent, false);


        //Encontrar TextViews
        TextView nome = rowView.findViewById(R.id.nomeIntegranteLinha);
        TextView dataNascimento = rowView.findViewById(R.id.dataNascimentoLinha);
        TextView cpf = rowView.findViewById(R.id.cpfLinha);
        TextView rg = rowView.findViewById(R.id.rgLinha);


        //Setar Textos
        String rgString = "RG: " + elementos.get(position).getRg();
        String cpfString = "CPF: " + elementos.get(position).getCpf();

        nome.setText(elementos.get(position).getNome());
        dataNascimento.setText(elementos.get(position).getDataNascimento());
        rg.setText(rgString);
        cpf.setText(cpfString);


        return rowView;
    }
}


