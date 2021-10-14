package com.example.intent;

public class KontakSuper {
    private String nama;
    private String nomor;

    public KontakSuper(String nama, String nomor) {
        this.nama = nama;
        this.nomor = nomor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }
}
