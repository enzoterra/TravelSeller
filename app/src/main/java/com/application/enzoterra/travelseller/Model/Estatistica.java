package com.application.enzoterra.travelseller.Model;

public class Estatistica {
    private String nomeCliente, cpfCliente, rgCliente, dataNascimentoCliente, hotel, cidade;
    private String embarqueData, desembarqueData, embarqueHora, desembarqueHora, observacoes;
    private String localizador, numeroVenda, mes;
    private double valorComissao, valorTotal;
    private long id;
    private int ano;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getRgCliente() {
        return rgCliente;
    }

    public void setRgCliente(String rgCliente) {
        this.rgCliente = rgCliente;
    }

    public String getDataNascimentoCliente() {
        return dataNascimentoCliente;
    }

    public void setDataNascimentoCliente(String dataNascimentoCliente) {
        this.dataNascimentoCliente = dataNascimentoCliente;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmbarqueHora() {
        return embarqueHora;
    }

    public void setEmbarqueHora(String embarqueHora) {
        this.embarqueHora = embarqueHora;
    }

    public String getEmbarqueData() {
        return embarqueData;
    }

    public void setEmbarqueData(String embarqueData){
        this.embarqueData = embarqueData;
    }

    public String getDesembarqueHora() {
        return desembarqueHora;
    }

    public void setDesembarqueHora(String desembarqueHora) {
        this.desembarqueHora = desembarqueHora;
    }

    public String getDesembarqueData() {
        return desembarqueData;
    }

    public void setDesembarqueData(String desembarqueData) {
        this.desembarqueData = desembarqueData;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    public String getNumeroVenda() {
        return numeroVenda;
    }

    public void setNumeroVenda(String numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public double getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(double valorComissao) {
        this.valorComissao = valorComissao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}

