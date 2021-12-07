package com.application.enzoterra.travelseller;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Configuracoes;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Settings extends AppCompatActivity {

    int embarque, desembarque, umaHora, umDia, doisDias, limpar, passar;
    CheckBox checkBoxEmbarque, checkBoxDesembarque, checkBox1dia, checkBox2dias, checkBoxLimpar, checkBoxPassar;
    Configuracoes configuracoes = new Configuracoes();
    int ativado = 1;
    int desativado = 0;
    ViagensBD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Context context = Settings.this;
        bd = new ViagensBD(context);

        ScrollView scrollView = new ScrollView(this);
        ConstraintLayout.LayoutParams layout = new ConstraintLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layout);


        checkBoxEmbarque = findViewById(R.id.checkBoxEmbarque);
        checkBoxDesembarque = findViewById(R.id.checkBoxDesembarque);
        //checkBox1Hora = findViewById(R.id.checkBox1hora);
        checkBox1dia = findViewById(R.id.checkBox1dia);
        checkBox2dias = findViewById(R.id.checkBox2dias);
        checkBoxLimpar = findViewById(R.id.checkBoxLimpar);
        checkBoxPassar = findViewById(R.id.checkBoxPassar);

        ArrayList<Configuracoes> configuracoesLista = bd.getConfiguracoes();

        embarque = configuracoesLista.get(0).getEmbarqueConfiguracoes();
        desembarque = configuracoesLista.get(0).getDesembarqueConfiguracoes();
        umaHora = configuracoesLista.get(0).getUmaHora();
        umDia = configuracoesLista.get(0).getUmDia();
        doisDias = configuracoesLista.get(0).getDoisDias();
        limpar = configuracoesLista.get(0).getLimpar();
        passar = configuracoesLista.get(0).getPassar();


        checkBoxEmbarque.setChecked(embarque == 1);
        checkBoxDesembarque.setChecked(desembarque==1);
        //checkBox1Hora.setActivated(umaHora==1);
        checkBox1dia.setChecked(umDia==1);
        checkBox2dias.setChecked(doisDias==1);
        checkBoxLimpar.setChecked(limpar==1);
        checkBoxPassar.setChecked(passar==1);


        //Button Salvar
        Button salvar = findViewById(R.id.buttonSalvarConfiguracoes);
        salvar.setOnClickListener(view -> {
            salvarConfiguracoes();
            startActivity(new Intent(Settings.this, ListaViagens.class));
        }
        );


        //Button Voltar
        Button voltar = findViewById(R.id.buttonVoltarConfiguracoes);
        voltar.setOnClickListener(view -> {
                    startActivity(new Intent(Settings.this, ListaViagens.class));
                }
        );
    }

    public void salvarConfiguracoes(){
        if(checkBoxEmbarque.isChecked()){
            embarque = ativado;
        } else {
            embarque = desativado;
        }

        if(checkBoxDesembarque.isChecked()){
            desembarque = ativado;
        } else {
            desembarque = desativado;
        }

        /*if(checkBox1Hora.isActivated()){
            umaHora = ativado;
        } else {
            umaHora = desativado;
        }*/

        if(checkBox1dia.isChecked()){
            umDia = ativado;
        } else {
            umDia = desativado;
        }

        if(checkBox2dias.isChecked()){
            doisDias = ativado;
        } else {
            doisDias = desativado;
        }

        if(checkBoxLimpar.isChecked()){
            limpar = ativado;
        } else {
            limpar = desativado;
        }

        if(checkBoxPassar.isChecked()){
            passar = ativado;
        } else {
            passar = desativado;
        }

        configuracoes.setEmbarqueConfiguracoes(embarque);
        configuracoes.setDesembarqueConfiguracoes(desembarque);
        configuracoes.setUmaHora(umaHora);
        configuracoes.setUmDia(umDia);
        configuracoes.setDoisDias(doisDias);
        configuracoes.setLimpar(limpar);
        configuracoes.setPassar(passar);

        bd.alterarConfiguracoes(configuracoes);
        bd.close();
    }
}