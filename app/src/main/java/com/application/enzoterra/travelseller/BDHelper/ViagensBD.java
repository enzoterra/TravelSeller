package com.application.enzoterra.travelseller.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.application.enzoterra.travelseller.Model.Configuracoes;
import com.application.enzoterra.travelseller.Model.Estatistica;
import com.application.enzoterra.travelseller.Model.Pessoa;
import com.application.enzoterra.travelseller.Model.Viagens;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ViagensBD extends SQLiteOpenHelper {

    private static final String DATABASE = "bdviagens";
    private static final int VERSION = 1;

    //Banco de Dados
    public ViagensBD(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String viagem = "CREATE TABLE IF NOT EXISTS viagens(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " nomeCliente VARCHAR," +
                " cpfCliente VARCHAR," +
                " rgCliente VARCHAR," +
                " dataNascimentoCliente VARCHAR," +
                " hotel VARCHAR," +
                " localizador VARCHAR," +
                " companhiaAerea VARCHAR," +
                " numeroVenda VARCHAR," +
                " cidade VARCHAR," +
                " embarqueHora VARCHAR," +
                " embarqueData DATE," +
                " desembarqueHora VARCHAR," +
                " desembarqueData DATE," +
                " observacoes VARCHAR," +
                " valorComissao REAL," +
                " valorTotal REAL, " +
                " mes VARCHAR, " +
                " ano VARCHAR " +
                "); ";

        String clientes = "CREATE TABLE IF NOT EXISTS clientes (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " nome VARCHAR," +
                " rg VARCHAR," +
                " cpf VARCHAR," +
                " dataNascimento VARCHAR," +
                " fk_viagens_id INTEGER, " +
                " FOREIGN KEY (fk_viagens_id) REFERENCES viagens(id)" +
                "); ";

        String configuracoes = "CREATE TABLE IF NOT EXISTS configuracoes (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " embarque INTEGER," +
                " desembarque INTEGER," +
                " umaHora INTEGER," +
                " umDia INTEGER," +
                " doisDias INTEGER, " +
                " limpar INTEGER, " +
                " passar INTEGER " +
                "); ";

        String estatisticas = "CREATE TABLE IF NOT EXISTS estatisticas(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " nomeCliente VARCHAR," +
                " cpfCliente VARCHAR," +
                " rgCliente VARCHAR," +
                " dataNascimentoCliente VARCHAR," +
                " hotel VARCHAR," +
                " localizador VARCHAR," +
                " companhiaAerea VARCHAR," +
                " numeroVenda VARCHAR," +
                " cidade VARCHAR," +
                " embarqueHora VARCHAR," +
                " embarqueData DATE," +
                " desembarqueHora VARCHAR," +
                " desembarqueData DATE," +
                " observacoes VARCHAR," +
                " valorComissao REAL," +
                " valorTotal REAL," +
                " ano INTEGER," +
                " mes VARCHAR" +
                "); ";


        db.execSQL(viagem);
        db.execSQL(clientes);
        db.execSQL(configuracoes);
        db.execSQL(estatisticas);
        criarConfiguracoes(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String viagem = "DROP TABLE IF EXISTS viagens";
        String clientes = "DROP TABLE IF EXISTS clientes";
        String configuracoes = "DROP TABLE IF EXISTS configuracoes";
        String estatisticas = "DROP TABLE IF EXISTS estatisticas";
        db.execSQL(viagem);
        db.execSQL(clientes);
        db.execSQL(configuracoes);
        db.execSQL(estatisticas);
    }


    //SALVAR

    //Cadastrar Viagem
    public long salvarViagem(Viagens viagem) {
        ContentValues values = new ContentValues();

        values.put("nomeCliente", viagem.getNomeCliente());
        values.put("cpfCliente", viagem.getCpfCliente());
        values.put("rgCliente", viagem.getRgCliente());
        values.put("dataNascimentoCliente", viagem.getDataNascimentoCliente());
        values.put("hotel", viagem.getHotel());
        values.put("cidade", viagem.getCidade());
        values.put("embarqueHora", viagem.getEmbarqueHora());
        values.put("embarqueData", viagem.getEmbarqueData());
        values.put("desembarqueHora", viagem.getDesembarqueHora());
        values.put("desembarqueData", viagem.getDesembarqueData());
        values.put("observacoes", viagem.getObservacoes());
        values.put("localizador", viagem.getLocalizador());
        values.put("companhiaAerea", viagem.getCompanhiaAerea());
        values.put("numeroVenda", viagem.getNumeroVenda());
        values.put("valorComissao", viagem.getValorComissao());
        values.put("valorTotal", viagem.getValorTotal());
        values.put("mes", "");
        values.put("ano", "");

        return getWritableDatabase().insert("viagens", null, values);
    }

    //Cadastrar Viagem Passo 2
    public void salvarViagemPasso2(Viagens viagem, int id) {
        ContentValues values = new ContentValues();

        String dataViagem = viagem.getEmbarqueData();
        String mes = "";
        String ano = "";

        if (dataViagem.equals("")) {
            dataViagem = viagem.getDesembarqueData();
        }

        if (!dataViagem.equals("")) {
            String[] anoStr = dataViagem.split("/");
            mes = anoStr[1];
            ano = anoStr[2];
        }

        values.put("embarqueHora", viagem.getEmbarqueHora());
        values.put("embarqueData", viagem.getEmbarqueData());
        values.put("desembarqueHora", viagem.getDesembarqueHora());
        values.put("desembarqueData", viagem.getDesembarqueData());
        values.put("localizador", viagem.getLocalizador());
        values.put("companhiaAerea", viagem.getCompanhiaAerea());
        values.put("numeroVenda", viagem.getNumeroVenda());
        values.put("mes", mes);
        values.put("ano", ano);

        getWritableDatabase().update("viagens", values, "id = " + id, new String[]{});
    }

    //Cadastrar Viagem Passo 3
    public void salvarViagemPasso3(Viagens viagem, int id) {
        ContentValues values = new ContentValues();

        values.put("observacoes", viagem.getObservacoes());
        values.put("valorComissao", viagem.getValorComissao());
        values.put("valorTotal", viagem.getValorTotal());

        getWritableDatabase().update("viagens", values, "id = " + id, new String[]{});
    }


    //Cadastrar Cliente
    public void salvarPessoa(Pessoa pessoa) {
        ContentValues values = new ContentValues();

        values.put("nome", pessoa.getNome());
        values.put("cpf", pessoa.getCpf());
        values.put("rg", pessoa.getRg());
        values.put("dataNascimento", pessoa.getDataNascimento());
        values.put("fk_viagens_id", pessoa.getFkViagens());

        getWritableDatabase().insert("clientes", null, values);
    }

    //Salvar Cliente da Viagem
    public void salvarClienteViagem(Viagens viagem, int id){
        ContentValues values = new ContentValues();

        values.put("nomeCliente", viagem.getNomeCliente());
        values.put("cpfCliente", viagem.getCpfCliente());
        values.put("rgCliente", viagem.getRgCliente());
        values.put("dataNascimentoCliente", viagem.getDataNascimentoCliente());

        getWritableDatabase().update("viagens", values, "id = " + id, new String[]{});
    }

    //Salvar Viagem Estatisticas
    public void salvarViagemEstatisticas(Estatistica estatistica) {
        ContentValues values = new ContentValues();

        values.put("nomeCliente", estatistica.getNomeCliente());
        values.put("cpfCliente", estatistica.getCpfCliente());
        values.put("rgCliente", estatistica.getRgCliente());
        values.put("dataNascimentoCliente", estatistica.getDataNascimentoCliente());
        values.put("hotel", estatistica.getHotel());
        values.put("cidade", estatistica.getCidade());
        values.put("embarqueHora", estatistica.getEmbarqueHora());
        values.put("embarqueData", estatistica.getEmbarqueData());
        values.put("desembarqueHora", estatistica.getDesembarqueHora());
        values.put("desembarqueData", estatistica.getDesembarqueData());
        values.put("observacoes", estatistica.getObservacoes());
        values.put("localizador", estatistica.getLocalizador());
        values.put("companhiaAerea", estatistica.getCompanhiaAerea());
        values.put("numeroVenda", estatistica.getNumeroVenda());
        values.put("valorComissao", estatistica.getValorComissao());
        values.put("valorTotal", estatistica.getValorTotal());
        values.put("ano", estatistica.getAno());
        values.put("mes", estatistica.getMes());

        getWritableDatabase().insert("estatisticas", null, values);
    }


    //ALTERAR

    //Alterar Viagem
    public void alterarViagem(Viagens viagem, int id) {
        ContentValues values = new ContentValues();

        String dataViagem = viagem.getEmbarqueData();
        String mes = "";
        String ano = "";

        if (dataViagem.equals("")) {
            dataViagem = viagem.getDesembarqueData();
        }

        if (!dataViagem.equals("")) {
            String[] anoStr = dataViagem.split("/");
            mes = anoStr[1];
            ano = anoStr[2];
        }

        /*values.put("nomeCliente", viagem.getNomeCliente());
        values.put("cpfCliente", viagem.getCpfCliente());
        values.put("rgCliente", viagem.getRgCliente());
        values.put("dataNascimentoCliente", viagem.getDataNascimentoCliente());*/
        values.put("hotel", viagem.getHotel());
        values.put("cidade", viagem.getCidade());
        values.put("embarqueHora", viagem.getEmbarqueHora());
        values.put("embarqueData", viagem.getEmbarqueData());
        values.put("desembarqueHora", viagem.getDesembarqueHora());
        values.put("desembarqueData", viagem.getDesembarqueData());
        values.put("localizador", viagem.getLocalizador());
        values.put("companhiaAerea", viagem.getCompanhiaAerea());
        values.put("numeroVenda", viagem.getNumeroVenda());
        values.put("observacoes", viagem.getObservacoes());
        values.put("valorComissao", viagem.getValorComissao());
        values.put("valorTotal", viagem.getValorTotal());
        values.put("mes", mes);
        values.put("ano", ano);

        getWritableDatabase().update("viagens", values, "id = " + id, new String[]{});
    }


    //Alterar Viagem no Cadastro
    public void alterarViagemCadastro(Viagens viagem,  int id){
        ContentValues values = new ContentValues();

        values.put("nomeCliente", viagem.getNomeCliente());
        values.put("cpfCliente", viagem.getCpfCliente());
        values.put("rgCliente", viagem.getRgCliente());
        values.put("dataNascimentoCliente", viagem.getDataNascimentoCliente());
        values.put("hotel", viagem.getHotel());
        values.put("cidade", viagem.getCidade());

        getWritableDatabase().update("viagens", values, "id = " + id, new String[]{});
    }


    //Alterar Clientes
    public void alterarClientes(Pessoa pessoa, int id) {
        ContentValues values = new ContentValues();

        values.put("nome", pessoa.getNome());
        values.put("cpf", pessoa.getCpf());
        values.put("rg", pessoa.getRg());
        values.put("dataNascimento", pessoa.getDataNascimento());

        getWritableDatabase().update("clientes", values, "id = " + id, new String[]{});
    }


    //Alterar Viagem Estatisticas
    public void alterarViagemEstatisticas(Estatistica estatistica, int id) {
        ContentValues values = new ContentValues();

        /*values.put("nomeCliente", viagem.getNomeCliente());
        values.put("cpfCliente", viagem.getCpfCliente());
        values.put("rgCliente", viagem.getRgCliente());
        values.put("dataNascimentoCliente", viagem.getDataNascimentoCliente());*/
        values.put("hotel", estatistica.getHotel());
        values.put("cidade", estatistica.getCidade());
        values.put("embarqueHora", estatistica.getEmbarqueHora());
        values.put("embarqueData", estatistica.getEmbarqueData());
        values.put("desembarqueHora", estatistica.getDesembarqueHora());
        values.put("desembarqueData", estatistica.getDesembarqueData());
        values.put("localizador", estatistica.getLocalizador());
        values.put("companhiaAerea", estatistica.getCompanhiaAerea());
        values.put("numeroVenda", estatistica.getNumeroVenda());
        values.put("observacoes", estatistica.getObservacoes());
        values.put("valorComissao", estatistica.getValorComissao());
        values.put("valorTotal", estatistica.getValorTotal());
        values.put("ano", estatistica.getAno());
        values.put("mes", estatistica.getMes());

        getWritableDatabase().update("estatisticas", values, "id = " + id, new String[]{});
    }


    //Zerar informações da Viagem
    public void zerarViagem(int id) {
        ContentValues values = new ContentValues();

        String valor = "";

        values.put("hotel", valor);
        values.put("cidade", valor);
        values.put("embarqueHora", valor);
        values.put("embarqueData", valor);
        values.put("desembarqueHora", valor);
        values.put("desembarqueData", valor);
        values.put("localizador", valor);
        values.put("companhiaAerea", valor);
        values.put("numeroVenda", valor);
        values.put("valorComissao", 0);
        values.put("valorTotal", 0);
        values.put("mes", valor);
        values.put("ano", valor);

        getWritableDatabase().update("viagens", values, "id = " + id, new String[]{});
    }


    //Deletar Cliente
    public void deletarViagem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM clientes WHERE fk_viagens_id= "+ id);
        db.execSQL("DELETE FROM viagens WHERE id= "+ id);
        db.close();
    }

    //Deletar Cliente
    public void deletarCliente(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM clientes WHERE id = " + id);
        db.close();
    }

    //Deletar Estatistica
    public void deletarEstatistica(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM estatisticas WHERE id = " + id);
        db.close();
    }


    //Pegar último ID
    public long getId(){
        String[] columns = {"id"};
        Cursor cursor = getWritableDatabase().query("viagens", columns, null, null, null, null, null, null);
        ArrayList<Viagens> listaViagens = new ArrayList<>();

        while (cursor.moveToNext()) {
            Viagens viagem = new Viagens();
            viagem.setId(cursor.getLong(0));
            listaViagens.add(viagem);
        }
        cursor.close();
        int indexLista = listaViagens.size();
        if(indexLista!=0){
            return listaViagens.get(indexLista - 1).getId();
        }

        return 0;
    }


    //Pegar último ID integrantes
    public long getIdIntegrantes(int fkId){
        String[] columns = {"id"};
        Cursor cursor = getWritableDatabase().query("clientes", columns, "fk_viagens_id = " + fkId, null, null, null, null, null);
        ArrayList<Pessoa> listaPessoas = new ArrayList<>();

        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setId(cursor.getLong(0));
            listaPessoas.add(pessoa);
        }
        cursor.close();
        if(listaPessoas.size()!=0){
            return listaPessoas.get(0).getId();
        }

        return 0;
    }


    //Receber Lista de Viagens
    public ArrayList<Viagens> getListaViagens() {

        String[] columns = {"id", "nomeCliente", "cpfCliente", "rgCliente", "dataNascimentoCliente", "hotel", "cidade", "embarqueHora",
                "embarqueData", "desembarqueHora", "desembarqueData", "observacoes", "localizador", "companhiaAerea", "numeroVenda", "valorComissao", "valorTotal", "mes", "ano"};

        Cursor cursor = getWritableDatabase().query("viagens", columns, null, null, null, null, "ano, mes, embarqueData", null);

        return returnViagens(cursor);
    }


    //Receber Lista de Clientes
    public ArrayList<Viagens> getListaClientes() {

        String[] columns = {"id", "nomeCliente", "cpfCliente", "rgCliente", "dataNascimentoCliente", "hotel", "cidade", "embarqueHora",
                "embarqueData", "desembarqueHora", "desembarqueData", "observacoes", "localizador", "companhiaAerea", "numeroVenda", "valorComissao", "valorTotal"};

        Cursor cursor = getWritableDatabase().query("viagens", columns, null, null, null, null, "nomeCliente ASC", null);

        return returnViagens(cursor);
    }


    //Receber Lista de Integrantes
    public ArrayList<Pessoa> getListaIntegrantes(int id) {
        String[] columns = {"id", "nome", "cpf", "rg", "dataNascimento", "fk_viagens_id"};

        Cursor cursor = getWritableDatabase().query("clientes", columns, "fk_viagens_id = " + id, null, null, null, null, null);
        ArrayList<Pessoa> listaClientes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setId(cursor.getLong(0));
            pessoa.setNome(cursor.getString(1));
            pessoa.setCpf(cursor.getString(2));
            pessoa.setRg(cursor.getString(3));
            pessoa.setDataNascimento(cursor.getString(4));
            pessoa.setFkViagens((cursor.getInt(5)));

            listaClientes.add(pessoa);
        }
        cursor.close();
        return listaClientes;
    }

    //Receber Lista Total Integrantes
    public ArrayList<Pessoa> getListaAllIntegrantes() {
        String[] columns = {"id", "nome", "cpf", "rg", "dataNascimento", "fk_viagens_id"};

        Cursor cursor = getWritableDatabase().query("clientes", columns, null, null, null, null, null, null);
        ArrayList<Pessoa> listaClientes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setId(cursor.getLong(0));
            pessoa.setNome(cursor.getString(1));
            pessoa.setCpf(cursor.getString(2));
            pessoa.setRg(cursor.getString(3));
            pessoa.setDataNascimento(cursor.getString(4));
            pessoa.setFkViagens((cursor.getInt(5)));

            listaClientes.add(pessoa);
        }
        cursor.close();
        return listaClientes;
    }


    //Receber Lista de Clientes com Pesquisa
    public ArrayList<Viagens> getListaClientesPesquisa(String nome) {

        String[] columns = {"id", "nomeCliente", "cpfCliente", "rgCliente", "dataNascimentoCliente", "hotel", "cidade", "embarqueHora",
                "embarqueData", "desembarqueHora", "desembarqueData", "observacoes", "localizador", "companhiaAerea", "numeroVenda", "valorComissao", "valorTotal"};

        Cursor cursor = getWritableDatabase().query("viagens", columns, "UPPER(nomeCliente) = '" + nome.toUpperCase() +"'", null, null, null, null, null);

        return returnViagens(cursor);
    }


    //Receber Lista de Clientes com ID
    public ArrayList<Viagens> getListaClientesID(int id) {

        String[] columns = {"id", "nomeCliente", "cpfCliente", "rgCliente", "dataNascimentoCliente", "hotel", "cidade", "embarqueHora",
                "embarqueData", "desembarqueHora", "desembarqueData", "observacoes", "localizador", "companhiaAerea", "numeroVenda", "valorComissao", "valorTotal"};

        Cursor cursor = getWritableDatabase().query("viagens", columns, "id = " + id, null, null, null, null, null);
        ArrayList<Viagens> listaClientes = new ArrayList<>();
        cursor.moveToFirst();

        Viagens viagem = new Viagens();
        viagem.setId(cursor.getLong(0));
        viagem.setNomeCliente(cursor.getString(1));
        viagem.setCpfCliente(cursor.getString(2));
        viagem.setRgCliente(cursor.getString(3));
        viagem.setDataNascimentoCliente(cursor.getString(4));
        viagem.setHotel(cursor.getString(5));
        viagem.setCidade(cursor.getString(6));
        viagem.setEmbarqueHora(cursor.getString(7));
        viagem.setEmbarqueData(cursor.getString(8));
        viagem.setDesembarqueHora(cursor.getString(9));
        viagem.setDesembarqueData(cursor.getString(10));
        viagem.setObservacoes(cursor.getString(11));
        viagem.setLocalizador(cursor.getString(12));
        viagem.setCompanhiaAerea(cursor.getString(13));
        viagem.setNumeroVenda(cursor.getString(14));
        viagem.setValorComissao(cursor.getDouble(15));
        viagem.setValorTotal(cursor.getDouble(16));

        listaClientes.add(viagem);

        cursor.close();
        return listaClientes;
    }


    //Receber Lista de Anos e Meses
    public ArrayList<Estatistica> getListaEstatisticas(String ano_mes, int ano) {

        String[] columns = {"id", "valorComissao", "valorTotal", "ano", "mes"};

        Cursor cursor;

        if(ano_mes.equals("ano")) {
            cursor = getWritableDatabase().query("estatisticas", columns, null, null, null, null, "ano DESC", null);
        } else{
            cursor = getWritableDatabase().query("estatisticas", columns, "ano = " + ano, null, null, null, "mes ASC", null);
        }

        ArrayList<Estatistica> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            Estatistica estatistica = new Estatistica();
            estatistica.setId(cursor.getLong(0));
            estatistica.setValorComissao(cursor.getDouble(1));
            estatistica.setValorTotal(cursor.getDouble(2));
            estatistica.setAno(cursor.getInt(3));
            estatistica.setMes(cursor.getString(4));

            lista.add(estatistica);
        }
        cursor.close();
        return lista;
    }


    //Receber Lista de Viagens Estatisticas
    public ArrayList<Estatistica> getListaViagensEstatisticas(String mes, int ano) {

        String[] columns = {"id", "nomeCliente", "cpfCliente", "rgCliente", "dataNascimentoCliente", "hotel", "cidade", "embarqueHora",
                "embarqueData", "desembarqueHora", "desembarqueData", "observacoes", "localizador", "companhiaAerea", "numeroVenda", "valorComissao", "valorTotal", "ano", "mes"};

        Cursor cursor = getWritableDatabase().query("estatisticas", columns, "mes = '" + mes +"'AND ano = " + ano, null, null, null, "nomeCliente ASC", null);

        return returnEstatisticas(cursor);
    }


    //Receber Lista de Viagens Estatisticas
    public ArrayList<Estatistica> getListaAllEstatisticas() {

        String[] columns = {"id", "nomeCliente", "cpfCliente", "rgCliente", "dataNascimentoCliente", "hotel", "cidade", "embarqueHora",
                "embarqueData", "desembarqueHora", "desembarqueData", "observacoes", "localizador", "companhiaAerea", "numeroVenda", "valorComissao", "valorTotal", "ano", "mes"};

        Cursor cursor = getWritableDatabase().query("estatisticas", columns, null, null, null, null, null, null);

        return returnEstatisticas(cursor);
    }


    //Receber Lista de Viagens Estatisticas
    public ArrayList<Estatistica> getEstatisticaEspecifica(String[] dados) {

        String where = "nomeCliente = ? and " +
                "cpfCliente = ? and " +
                "rgCliente = ? and " +
                "dataNascimentoCliente = ? and " +
                /*"cidade = ? and " +
                "hotel = ? and " +
                "localizador = ? and " +*/
                "numeroVenda = ?";
                /*"embarqueHora = ? and " +
                "embarqueData = ? and " +
                "desembarqueHora = ? and " +
                "desembarqueData = ? and " +
                "observacoes = ?";*/

        String[] columns = {"id", "nomeCliente", "cpfCliente", "rgCliente", "dataNascimentoCliente", "hotel", "cidade", "embarqueHora",
                "embarqueData", "desembarqueHora", "desembarqueData", "observacoes", "localizador", "companhiaAerea", "numeroVenda", "valorComissao", "valorTotal", "ano", "mes"};

        Cursor cursor = getWritableDatabase().query("estatisticas", columns, where, dados, null, null, null, null);

        return returnEstatisticas(cursor);
    }

    //Receber Lista de Viagens Estatisticas
    public ArrayList<Estatistica> getEstatisticaNumeroViagem(String[] dados) {

        String where = "numeroVenda = ?";

        String[] columns = {"id", "nomeCliente", "numeroVenda"};

        Cursor cursor = getWritableDatabase().query("estatisticas", columns, where, dados, null, null, null, null);

        ArrayList<Estatistica> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            Estatistica estatistica = new Estatistica();

            estatistica.setId(cursor.getLong(0));
            estatistica.setNomeCliente(cursor.getString(1));
            estatistica.setNumeroVenda(cursor.getString(2));

            lista.add(estatistica);
        }
        cursor.close();
        return lista;
    }


    //Retornar lista de viagens
    public ArrayList<Viagens> returnViagens(Cursor cursor){
        ArrayList<Viagens> listaClientes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Viagens viagem = new Viagens();
            viagem.setId(cursor.getLong(0));
            viagem.setNomeCliente(cursor.getString(1));
            viagem.setCpfCliente(cursor.getString(2));
            viagem.setRgCliente(cursor.getString(3));
            viagem.setDataNascimentoCliente(cursor.getString(4));
            viagem.setHotel(cursor.getString(5));
            viagem.setCidade(cursor.getString(6));
            viagem.setEmbarqueHora(cursor.getString(7));
            viagem.setEmbarqueData(cursor.getString(8));
            viagem.setDesembarqueHora(cursor.getString(9));
            viagem.setDesembarqueData(cursor.getString(10));
            viagem.setObservacoes(cursor.getString(11));
            viagem.setLocalizador(cursor.getString(12));
            viagem.setCompanhiaAerea(cursor.getString(13));
            viagem.setNumeroVenda(cursor.getString(14));
            viagem.setValorComissao(cursor.getDouble(15));
            viagem.setValorTotal(cursor.getDouble(16));

            listaClientes.add(viagem);
        }
        cursor.close();
        return listaClientes;
    }


    //Retornar lista de viagens
    public ArrayList<Estatistica> returnEstatisticas(Cursor cursor){
        ArrayList<Estatistica> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            Estatistica estatistica = new Estatistica();
            estatistica.setId(cursor.getLong(0));
            estatistica.setNomeCliente(cursor.getString(1));
            estatistica.setCpfCliente(cursor.getString(2));
            estatistica.setRgCliente(cursor.getString(3));
            estatistica.setDataNascimentoCliente(cursor.getString(4));
            estatistica.setHotel(cursor.getString(5));
            estatistica.setCidade(cursor.getString(6));
            estatistica.setEmbarqueHora(cursor.getString(7));
            estatistica.setEmbarqueData(cursor.getString(8));
            estatistica.setDesembarqueHora(cursor.getString(9));
            estatistica.setDesembarqueData(cursor.getString(10));
            estatistica.setObservacoes(cursor.getString(11));
            estatistica.setLocalizador(cursor.getString(12));
            estatistica.setCompanhiaAerea(cursor.getString(13));
            estatistica.setNumeroVenda(cursor.getString(14));
            estatistica.setValorComissao(cursor.getDouble(15));
            estatistica.setValorTotal(cursor.getDouble(16));
            estatistica.setAno(cursor.getInt(17));
            estatistica.setMes(cursor.getString(18));

            lista.add(estatistica);
        }
        cursor.close();
        return lista;
    }


    public String getNomePrincipal(int id){
        String[] columns = {"nome"};
        Cursor cursor = getWritableDatabase().query("clientes", columns, "fk_viagens_id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        ArrayList<Pessoa> listaPessoas = new ArrayList<>();

        cursor.moveToFirst();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(cursor.getString(0));
        listaPessoas.add(pessoa);
        cursor.close();

        return listaPessoas.get(0).getNome();
    }


    public void criarConfiguracoes(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put("embarque", 1);
        values.put("desembarque", 1);
        values.put("umaHora", 0);
        values.put("umDia", 0);
        values.put("doisDias", 1);
        values.put("limpar", 1);
        values.put("passar", 1);

        db.insert("configuracoes", null, values);
    }

    public void alterarConfiguracoes(Configuracoes configuracoes){
        ContentValues values = new ContentValues();

        values.put("embarque", configuracoes.getEmbarqueConfiguracoes());
        values.put("desembarque", configuracoes.getDesembarqueConfiguracoes());
        values.put("umaHora", configuracoes.getUmaHora());
        values.put("umDia", configuracoes.getUmDia());
        values.put("doisDias", configuracoes.getDoisDias());
        values.put("limpar", configuracoes.getLimpar());
        values.put("passar", configuracoes.getPassar());

        getWritableDatabase().update("configuracoes", values, "id = 1", new String[]{});
    }

    public ArrayList<Configuracoes> getConfiguracoes() {

        String[] columns = {"embarque", "desembarque", "umaHora", "umDia", "doisDias", "limpar", "passar"};

        Cursor cursor = getWritableDatabase().query("configuracoes", columns, "id = 1", null, null, null, null);
        ArrayList<Configuracoes> lista = new ArrayList<>();

        cursor.moveToFirst();
        Configuracoes configuracoes = new Configuracoes();

        configuracoes.setEmbarqueConfiguracoes(cursor.getInt(0));
        configuracoes.setDesembarqueConfiguracoes(cursor.getInt(1));
        configuracoes.setUmaHora(cursor.getInt(2));
        configuracoes.setUmDia(cursor.getInt(3));
        configuracoes.setDoisDias(cursor.getInt(4));
        configuracoes.setLimpar(cursor.getInt(5));
        configuracoes.setPassar(cursor.getInt(6));

        lista.add(configuracoes);

        cursor.close();
        return lista;
    }
}
