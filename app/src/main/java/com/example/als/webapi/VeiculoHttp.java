package com.example.als.webapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class VeiculoHttp {
    public static String URL;

    public static void setURL(String placa){
        URL = "https://api.procob.com/veiculos/v1/V0005/" + placa;
    }

    private static HttpURLConnection connectar(String urlWebservice){
        final int SEGUNDOS = 10000;

        try {
            java.net.URL url = new URL(urlWebservice);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;
        } catch (IOException e) {
            Log.d(String.valueOf(R.string.validar_erro), e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean hasConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static Veiculo getVEiculoFromJson(JSONObject json){
        String proprietario;
        String doc;
        String renavam;
        String chassi;
        String uf;
        String saldo;
        Veiculo veiculo = null;

        try {
            proprietario = json.getString("nome_proprietario");
            doc = json.getString("documento_proprietario");
            renavam = json.getString("renavam");
            chassi = json.getString("chassi");
            uf = json.getString("uf");
            saldo = json.getString("saldo");

            veiculo = new Veiculo(proprietario, doc, renavam, chassi, uf, saldo);
        }catch (JSONException e){
            Log.d(String.valueOf(R.string.validar_erro), e.getMessage());
        }
        return veiculo;
    }

    public static ArrayList<Veiculo> readJsonVeiculo(JSONObject json){
        ArrayList<Veiculo> arrayList = new ArrayList<>();
        try {
            JSONObject results = json.getJSONObject("content");
            JSONArray forecast = results.getJSONArray("detalhes");

            for (int i=0; i<forecast.length(); i++) {
                JSONObject a = forecast.getJSONObject(i);
                arrayList.add(getVEiculoFromJson(a));
            }
        }catch (JSONException e){
            Log.d(String.valueOf(R.string.erro_json), e.getMessage());
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<Veiculo> loadVeiculo() {
        try {
            HttpURLConnection connection = connectar(URL);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                ArrayList<Veiculo>  veiculoList = readJsonVeiculo(json);
                return veiculoList;
            }

        } catch (Exception e) {
            Log.d(String.valueOf(R.string.validar_erro), e.getMessage());
        }
        return null;
    }

    private static String bytesParaString(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int byteslidos;
        try {
            while ((byteslidos = inputStream.read(buffer)) != -1) {
                bufferzao.write(buffer, 0, byteslidos);

            }
            return new String(bufferzao.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}