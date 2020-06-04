package com.example.projectefinal;

import java.util.ArrayList;

public class FitxesLlista {

    private static ArrayList<Fitxa> fitxes;

    public static ArrayList<Fitxa> get() {
        if (fitxes == null) {
            fitxes = FitxesDB.loadFitxes();
        }
        return fitxes;
    }

    public static Fitxa getFitxa(int pos) {
        return fitxes.get(pos);
    }

    public static void nueva(String mascota, String amo, String raza, String colo, String tele1, String tele2, String inf1, String inf2) {
        Fitxa fitxa = FitxesDB.nueva(mascota, amo, raza, colo, tele1, tele2, inf1, inf2);
        fitxes.add(fitxa);
    }

    public static void modifica(int pos, String mascota, String amo, String raza, String colo, String tele1, String tele2, String inf1, String inf2) {
        Fitxa fitxa = fitxes.get(pos);
        fitxa.setNomMascota(mascota);
        fitxa.setNomAmo(amo);
        fitxa.setRaça(raza);
        fitxa.setColor(colo);
        fitxa.setTelefon1(tele1);
        fitxa.setTelefon2(tele2);
        fitxa.setInfo1(inf1);
        fitxa.setInfo2(inf2);
        FitxesDB.actualiza(fitxa);
    }

    public static void borra(int pos) {
        Fitxa fitxa = fitxes.get(pos);
        FitxesDB.borra(fitxa);
        fitxes.remove(pos);
    }
}
