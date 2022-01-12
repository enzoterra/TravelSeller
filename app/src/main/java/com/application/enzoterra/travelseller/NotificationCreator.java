package com.application.enzoterra.travelseller;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.application.enzoterra.travelseller.BDHelper.ViagensBD;
import com.application.enzoterra.travelseller.Model.Configuracoes;
import com.application.enzoterra.travelseller.Model.Estatistica;
import com.application.enzoterra.travelseller.Model.Viagens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import androidx.core.app.NotificationCompat;

public class NotificationCreator extends BroadcastReceiver {

    ViagensBD bd;
    ArrayList<Viagens> list_viagens;
    int embarque, desembarque, umaHora, umDia, doisDias, limpar, dias, passar;

    @Override
    public void onReceive(Context context, Intent intent) {

        bd = new ViagensBD(context);

        ArrayList<Configuracoes> configuracoesLista = bd.getConfiguracoes();
        embarque = configuracoesLista.get(0).getEmbarqueConfiguracoes();
        desembarque = configuracoesLista.get(0).getDesembarqueConfiguracoes();
        umaHora = configuracoesLista.get(0).getUmaHora();
        umDia = configuracoesLista.get(0).getUmDia();
        doisDias = configuracoesLista.get(0).getDoisDias();
        limpar = configuracoesLista.get(0).getLimpar();
        passar = configuracoesLista.get(0).getPassar();

        pegarLista(context, limpar);

        for(int i = 0; i < list_viagens.size(); i++){
            String embarqueData = list_viagens.get(i).getEmbarqueData();
            String nome = list_viagens.get(i).getNomeCliente();
            String desembarqueData = list_viagens.get(i).getDesembarqueData();

            if(embarque==1) {
                if(umDia==1) {
                    dias = 1;
                    notificacaoEmbarqueDias(context, embarqueData, nome, desembarqueData, dias);
                }
                if(doisDias==1){
                    dias = 2;
                    notificacaoEmbarqueDias(context, embarqueData, nome, desembarqueData, dias);
                }
            } else if (embarque==0 && desembarque==1){
                if(umDia==1) {
                    dias = 1;
                    notificacaoDesembarqueDias(context, desembarqueData, nome, dias);
                }
                if(doisDias==1){
                    dias = 2;
                    notificacaoDesembarqueDias(context, desembarqueData, nome, dias);
                }
            }
            try { Thread.sleep (5000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }


    //Criar lógica da notificação
    public void notificacaoEmbarqueDias(Context context, String embarqueData, String nome, String desembarqueData, int diasVerificacao){
        if(embarqueData!=null && !embarqueData.equals("")){
            try {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                String[] embarqueStr = embarqueData.split("/");
                String embarque = embarqueStr[0].concat("-").concat(embarqueStr[1]).concat("-").concat(embarqueStr[2]);
                Date dataEmbarque = format.parse(embarque);

                Date dataAtual = new Date();
                String dataAtualStr = format.format(dataAtual);
                dataAtual = format.parse(dataAtualStr);

                Calendar cal = Calendar.getInstance();
                cal.setTime(Objects.requireNonNull(dataAtual));
                cal.add(Calendar.DAY_OF_MONTH, diasVerificacao);
                dataAtual = cal.getTime();

                if(dataAtual.equals(dataEmbarque)){
                    criarNotificacao(context, tituloEmbarque(nome, diasVerificacao));
                } else {
                    if(desembarque==1) {
                        notificacaoDesembarqueDias(context, desembarqueData, nome, diasVerificacao);
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    //Criar notificação do desembarque
    public void notificacaoDesembarqueDias(Context context, String desembarqueData, String nome, int diasVerificacao){
        if(desembarqueData!=null && !desembarqueData.equals("")){
            try {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                String[] desembarqueStr = desembarqueData.split("/");
                String desembarque = desembarqueStr[0].concat("-").concat(desembarqueStr[1]).concat("-").concat(desembarqueStr[2]);
                Date dataDesembarque = format.parse(desembarque);

                Date dataAtual = new Date();
                String dataAtualStr = format.format(dataAtual);
                dataAtual = format.parse(dataAtualStr);

                Calendar cal = Calendar.getInstance();
                cal.setTime(Objects.requireNonNull(dataAtual));
                cal.add(Calendar.DAY_OF_MONTH, diasVerificacao);
                dataAtual = cal.getTime();

                if(dataAtual.equals(dataDesembarque)){
                    criarNotificacao(context, tituloDesembarque(nome, diasVerificacao));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    //Pegar Lista
    public void pegarLista(Context context, int limparInt) {
        ViagensBD viagensBD = new ViagensBD(context);
        ArrayList<Viagens> viagens = new ArrayList<>();
        String dataViagemFinal;
        int anoFinal = 0;
        this.bd = viagensBD;
        this.list_viagens = viagensBD.getListaViagens();
        this.bd.close();
        if (this.list_viagens != null) {
            for (int i = 0; i<list_viagens.size(); i++){
                String dataViagem = list_viagens.get(i).getDesembarqueData();
                String desembarque = list_viagens.get(i).getDesembarqueData();

                if(dataViagem==null || dataViagem.equals("")){
                    dataViagem = list_viagens.get(i).getEmbarqueData();
                }

                if(dataViagem!=null && !dataViagem.equals("")){
                    try {
                        Date dataAtual = new Date();
                        Date dataDesembarque = null;

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                        String[] dataViagemStr = dataViagem.split("/");
                        String dataViagemStr2 = dataViagemStr[0].concat("-").concat(dataViagemStr[1]).concat("-").concat(dataViagemStr[2]);
                        Date dataViagemDate = format.parse(dataViagemStr2);

                        if(desembarque!=null && !desembarque.equals("")) {
                            String[] desembarqueStr = desembarque.split("/");
                            String desembarqueStr2 = desembarqueStr[0].concat("-").concat(desembarqueStr[1]).concat("-").concat(desembarqueStr[2]);
                            dataDesembarque = format.parse(desembarqueStr2);
                        }

                        if(dataAtual.before(dataViagemDate)){
                            viagens.add(list_viagens.get(i));
                        } else if(dataDesembarque != null){

                            if(dataAtual.after(dataDesembarque) && limparInt==1) {
                                Estatistica estatistica = new Estatistica();

                                dataViagemFinal = list_viagens.get(i).getEmbarqueData();
                                String mes = "";

                                if (dataViagemFinal.equals("")) {
                                    dataViagemFinal = list_viagens.get(i).getDesembarqueData();
                                }

                                if (!dataViagemFinal.equals("")) {
                                    String[] anoStr = dataViagemFinal.split("/");
                                    anoFinal = Integer.parseInt(anoStr[2]);
                                    mes = anoStr[1];
                                }

                                estatistica.setNomeCliente(list_viagens.get(i).getNomeCliente());
                                estatistica.setCpfCliente(list_viagens.get(i).getCpfCliente());
                                estatistica.setRgCliente(list_viagens.get(i).getRgCliente());
                                estatistica.setDataNascimentoCliente(list_viagens.get(i).getDataNascimentoCliente());
                                estatistica.setCidade(list_viagens.get(i).getCidade());
                                estatistica.setHotel(list_viagens.get(i).getHotel());
                                estatistica.setLocalizador(list_viagens.get(i).getLocalizador());
                                estatistica.setCompanhiaAerea(list_viagens.get(i).getCompanhiaAerea());
                                estatistica.setNumeroVenda(list_viagens.get(i).getNumeroVenda());
                                estatistica.setEmbarqueHora(list_viagens.get(i).getEmbarqueHora());
                                estatistica.setEmbarqueData(list_viagens.get(i).getEmbarqueData());
                                estatistica.setDesembarqueHora(list_viagens.get(i).getDesembarqueHora());
                                estatistica.setDesembarqueData(list_viagens.get(i).getDesembarqueData());
                                estatistica.setObservacoes(list_viagens.get(i).getObservacoes());
                                estatistica.setValorTotal(list_viagens.get(i).getValorTotal());
                                estatistica.setValorComissao(list_viagens.get(i).getValorComissao());
                                estatistica.setAno(anoFinal);
                                estatistica.setMes(mes);

                                Viagens viagem = list_viagens.get(i);

                                String[] dadosEstatistica = {viagem.getNomeCliente(), viagem.getCpfCliente(), viagem.getRgCliente(), viagem.getDataNascimentoCliente(), viagem.getNumeroVenda()};

                                ArrayList<Estatistica> estatisticaArrayList = bd.getEstatisticaEspecifica(dadosEstatistica);

                                if(estatisticaArrayList.size()!=0) {
                                    int idEstatistica = Integer.parseInt(String.valueOf(estatisticaArrayList.get(0).getId()));
                                    bd.alterarViagemEstatisticas(estatistica, idEstatistica);
                                } else {
                                    bd.salvarViagemEstatisticas(estatistica);
                                }

                                bd.zerarViagem(Integer.parseInt(String.valueOf(list_viagens.get(i).getId())));
                                bd.close();
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.list_viagens = viagens;
        }
    }


    public void criarNotificacao(Context context, String titulo){
        Random random = new Random();
        int id = random.nextInt(1000);
        int icone = R.drawable.icon_notification;
        String texto = "Clique aqui para acessar as informações da viagem";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String nomeCanal = "canal_notificacao";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            nomeCanal = "canal_notificacao";
            CharSequence name = "canal";
            String Description = "Canal de Notificação do TravelSeller";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel canal = new NotificationChannel(nomeCanal, name, importance);
            canal.setDescription(Description);
            canal.enableLights(true);
            canal.setLightColor(Color.WHITE);
            canal.enableVibration(true);
            canal.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            canal.setShowBadge(false);

            notificationManager.createNotificationChannel(canal);
        }

        Intent resultIntent = new Intent(context, ListaViagens.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ListaViagens.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(context, nomeCanal);
        notificacao.setSmallIcon(icone);
        notificacao.setContentTitle(titulo);
        notificacao.setContentText(texto);
        notificacao.setContentIntent(resultPendingIntent);

        notificationManager.notify(id, notificacao.build());
    }

    public String tituloEmbarque(String nome, int diasContagem){
        String strFinal;
        if (diasContagem == 1){
            strFinal = " 1 dia!";
        } else{
            strFinal = " 2 dias!";
        }
        return nome + " irá viajar daqui a" + strFinal;
    }

    public String tituloDesembarque(String nome, int diasContagem){
        String strFinal;
        if (diasContagem == 1){
            strFinal = " 1 dia!";
        } else{
            strFinal = " 2 dias!";
        }
        return nome + " voltará da viagem daqui a" + strFinal;
    }

    /*public String calculaMes(String mes){
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
    }*/

}
