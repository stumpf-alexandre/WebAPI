package com.example.als.webapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    VeiculoTask task;
    ArrayList<Veiculo> veiculo;
    ArrayAdapter<Veiculo> adapter;
    ListView list;
    EditText placa;
    Button btn;
    public static String URL;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placa = findViewById(R.id.txt_placa);
        btn = findViewById(R.id.btn_pesquisa);
        list = findViewById(R.id.list_veiculo);
        
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (placa.getText().length() != 0){
                    URL = placa.getText().toString();
                    VeiculoHttp.setURL(URL);
                    list = findViewById(R.id.list_veiculo);
                    search();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.validar_placa), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void search() {
        if(veiculo == null) {
            veiculo = new ArrayList<Veiculo>();
        }
        adapter = new AdapterVeiculo(getApplicationContext(), veiculo);
        list.setAdapter(adapter);
        startDownload();
        if (task == null) {
            if (VeiculoHttp.hasConnected(this)) {
                startDownload();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.validar_conexao), Toast.LENGTH_LONG).show();
            }
        }
        else if (task.getStatus() == AsyncTask.Status.RUNNING){
            Toast.makeText(getApplicationContext(), getString(R.string.validar_busca),Toast.LENGTH_LONG).show();
        }
    }

    private void startDownload() {
        if (task == null || task.getStatus() != AsyncTask.Status.RUNNING){
            task = new VeiculoTask();
            task.execute();
        }
    }

    private class VeiculoTask extends AsyncTask<Void, Void, ArrayList<Veiculo>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),getString(R.string.validar_busca), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Veiculo> doInBackground(Void... strings) {
            ArrayList<Veiculo> veic = VeiculoHttp.loadVeiculo();
            return veic;
        }

        @Override
        protected void onPostExecute(ArrayList<Veiculo> v) {
            super.onPostExecute(v);
            if (v != null) {
                veiculo.clear();
                veiculo.addAll(v);
                adapter.notifyDataSetChanged();
            } else {

                Toast.makeText(getApplicationContext(), getString(R.string.validar_busca), Toast.LENGTH_LONG).show();
            }
        }
    }
}