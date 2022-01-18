package hu.csepel.gyakorlasdb;

import java.time.LocalDate;
import java.util.Date;

public class Szinesz {
    private int id;
    private String nev;
    private int magassag;
    private LocalDate szuletesiDatum;
    private int dijakSzama;
    private Boolean forgatasonVan;

    public Szinesz(int id, String nev, int magassag, LocalDate szuletesiDatum, int dijakSzama, Boolean forgatasonVan) {
        this.id = id;
        this.nev = nev;
        this.magassag = magassag;
        this.szuletesiDatum = szuletesiDatum;
        this.dijakSzama = dijakSzama;
        this.forgatasonVan = forgatasonVan;
    }

    public int getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public int getMagassag() {
        return magassag;
    }

    public LocalDate getSzuletesiDatum() {
        return szuletesiDatum;
    }

    public int getDijakSzama() {
        return dijakSzama;
    }

    public Boolean getForgatasonVan() {
        return forgatasonVan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setMagassag(int magassag) {
        this.magassag = magassag;
    }

    public void setSzuletesiDatum(LocalDate szuletesiDatum) {
        this.szuletesiDatum = szuletesiDatum;
    }

    public void setDijakSzama(int dijakSzama) {
        this.dijakSzama = dijakSzama;
    }

    public void setForgatasonVan(Boolean forgatasonVan) {
        this.forgatasonVan = forgatasonVan;
    }
}
