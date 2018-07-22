package com.example.als.webapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterVeiculo extends ArrayAdapter<Veiculo> {
    public AdapterVeiculo(@NonNull Context context, @NonNull List<Veiculo> object){
        super(context, 0, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Veiculo veiculo = getItem(position);
        View listVeiculo = convertView;
        if (convertView == null){
            listVeiculo = LayoutInflater.from(getContext()).inflate(R.layout.adapter_veiculo, null);
        }
        TextView proprietario = listVeiculo.findViewById(R.id.txt_prop);
        TextView doc = listVeiculo.findViewById(R.id.txt_doc);
        TextView renavam = listVeiculo.findViewById(R.id.txt_renavam);
        TextView chassi = listVeiculo.findViewById(R.id.txt_chassi);
        TextView uf = listVeiculo.findViewById(R.id.txt_uf);
        TextView saldo = listVeiculo.findViewById(R.id.txt_saldo);
        proprietario.setText(veiculo.getProprietario());
        doc.setText(veiculo.getDoc());
        renavam.setText(veiculo.getRenavan());
        chassi.setText(veiculo.getChassi());
        uf.setText(veiculo.getUf());
        saldo.setText(veiculo.getSaldo());

        return listVeiculo;
    }
}