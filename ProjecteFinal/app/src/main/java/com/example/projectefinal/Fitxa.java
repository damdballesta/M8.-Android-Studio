package com.example.projectefinal;

public class Fitxa {
    private long id;
    private String mascota;
    private String amo;
    private String raza;
    private String colo;
    private String tele1;
    private String tele2;
    private String inf1;
    private String inf2;

    public Fitxa() {
        this.id = -1;
        this.mascota = "";
        this.amo = "";
        this.raza = "";
        this.colo = "";
        this.tele1 = "";
        this.tele2 = "";
        this.inf1 = "";
        this.inf2 = "";
    }

    public Fitxa(long id, String mascota, String amo, String raza, String colo, String tele1, String tele2, String inf1, String inf2) {
        this.id = id;
        this.mascota = mascota;
        this.amo = amo;
        this.raza = raza;
        this.colo = colo;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.inf1 = inf1;
        this.inf2 = inf2;
    }

    public Fitxa(String mascota, String amo, String raza, String colo, String tele1, String tele2, String inf1, String inf2) {
        this.id = -1;
        this.mascota = mascota;
        this.amo = amo;
        this.raza = raza;
        this.colo = colo;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.inf1 = inf1;
        this.inf2 = inf2;
    }

    public String getNomMascota() {
        return mascota;
    }

    public void setNomMascota(String mascota) {
        this.mascota = mascota;
    }

    public String getNomAmo() {
        return amo;
    }

    public void setNomAmo(String amo) {
        this.amo = amo;
    }

    public String getRaça() {
        return raza;
    }

    public void setRaça(String raza) {
        this.raza = raza;
    }

    public String getColor() {
        return colo;
    }

    public void setColor(String colo) { this.colo = colo; }

    public String getTelefon1() {
        return tele1;
    }

    public void setTelefon1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTelefon2() {
        return tele2;
    }

    public void setTelefon2(String tele2) {
        this.tele2 = tele2;
    }

    public String getInfo1() {
        return inf1;
    }

    public void setInfo1(String inf1) {
        this.inf1 = inf1;
    }

    public String getInfo2() {
        return inf2;
    }

    public void setInfo2(String inf2) {
        this.inf2 = inf2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return mascota;
    }
}


