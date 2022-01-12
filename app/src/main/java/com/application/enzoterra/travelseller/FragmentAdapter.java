package com.application.enzoterra.travelseller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter{

    String[] dados, dadosVoo, restanteDados;
    DadosVooFragmento dadosVooFragmento = new DadosVooFragmento();
    RestanteDadosFragmento restanteDadosFragmento = new RestanteDadosFragmento();

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {
            restanteDadosFragmento.setDadosTotais(this.dados);
            return restanteDadosFragmento;
        }
        dadosVooFragmento.setDadosTotais(this.dados);
        return dadosVooFragmento;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void setDados(String[] dados){
        this.dados = dados;
    }

    public void setDadosVoo(String[] dadosVoo){
        this.dados[1] = dadosVoo[1];
        this.dados[2] = dadosVoo[2];
        this.dados[3] = dadosVoo[3];
        this.dados[4] = dadosVoo[4];
        this.dados[5] = dadosVoo[5];
        this.dados[6] = dadosVoo[6];
        this.dados[7] = dadosVoo[7];
        this.dados[8] = dadosVoo[8];
        this.dados[9] = dadosVoo[9];
    }

    public void setRestanteDados(String[] restanteDados){
        dados[9] = restanteDados[9];
        dados[10] = restanteDados[10];
        dados[11] = restanteDados[11];
    }

    public String[] getUpdate(){
        if(dadosVooFragmento.isResumed()) {
            dadosVoo = dadosVooFragmento.getDados();
            dados[1] = dadosVoo[1];
            dados[2] = dadosVoo[2];
            dados[3] = dadosVoo[3];
            dados[4] = dadosVoo[4];
            dados[5] = dadosVoo[5];
            dados[6] = dadosVoo[6];
            dados[7] = dadosVoo[7];
            dados[8] = dadosVoo[8];
            dados[9] = restanteDados[9];
        } else if (restanteDadosFragmento.isResumed()) {
            restanteDados = restanteDadosFragmento.getDados();
            dados[10] = restanteDados[10];
            dados[11] = restanteDados[11];
            dados[12] = restanteDados[12];
        }
        return dados;

    }

}
