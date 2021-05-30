package com.ozi.adresdefterim;

public class KisiModel {

    private long id;
    private  String ad;
    private  String mail;
    private  String telefon;
    private  String adres;
    private byte [] profilFoto;

    public KisiModel() {
    }

    public KisiModel(String ad, String mail, String telefon, String adres) {
        this.ad = ad;
        this.mail = mail;
        this.telefon = telefon;
        this.adres = adres;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public byte[] getProfilFoto() {
        return profilFoto;
    }

    public void setProfilFoto(byte[] profilFoto) {
        this.profilFoto = profilFoto;
    }
}
