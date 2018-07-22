package com.example.als.webapi;

import java.io.Serializable;

public class Veiculo implements Serializable {
    private String proprietario;
    private String doc;
    private String renavan;
    private String chassi;
    private String uf;
    private String saldo;

    public Veiculo(String proprietario, String doc, String renavan, String chassi, String uf, String saldo) {
        this.proprietario = proprietario;
        this.doc = doc;
        this.renavan = renavan;
        this.chassi = chassi;
        this.uf = uf;
        this.saldo = saldo;
    }

    public String getProprietario() {
        return proprietario;
    }

    public String getDoc() {
        return doc;
    }

    public String getRenavan() {
        return renavan;
    }

    public String getChassi() {
        return chassi;
    }

    public String getUf() {
        return uf;
    }

    public String getSaldo() {
        return saldo;
    }
}
