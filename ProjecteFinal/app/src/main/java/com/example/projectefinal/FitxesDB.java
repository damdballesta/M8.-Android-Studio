package com.example.projectefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FitxesDB {private static Context context;

    public static void setContext(Context context) {
        FitxesDB.context = context;
    }

    static class FitxesDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "fitxes.db";
        private static final int DATABASE_VERSION = 1;

        private static final String SQL_CREATE_TABLA_FITXES =
                "CREATE TABLE Fitxes (" +
                        "id INTEGER PRIMARY KEY," +
                        "nomMascota TEXT," +
                        "nomAmo TEXT," +
                        "raça TEXT," +
                        "color TEXT," +
                        "telefon1 TEXT," +
                        "telefon2 TEXT," +
                        "info1 TEXT," +
                        "info2 TEXT" +
                        ")";

        public FitxesDbHelper() {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLA_FITXES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            // No hay que hacer nada aquí de momento...
        }
    }

    private static FitxesDB.FitxesDbHelper helper;

    public static FitxesDB.FitxesDbHelper getHelper() {
        if (helper == null) {
            helper = new FitxesDB.FitxesDbHelper();
        }
        return helper;
    }

    public static ArrayList<Fitxa> loadFitxes() {
        ArrayList<Fitxa> resultado = new ArrayList<>();

        SQLiteDatabase db = getHelper().getReadableDatabase();

        String[] columnas = { "id", "nomMascota", "nomAmo", "raça", "color", "telefon1", "telefon2", "info1", "info2" };

        Cursor c = db.query("Fitxes", columnas, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                long id = c.getLong(c.getColumnIndexOrThrow("id"));
                String nomMascota = c.getString(c.getColumnIndexOrThrow("nomMascota"));
                String nomAmo  = c.getString(c.getColumnIndexOrThrow("nomAmo"));
                String raça = c.getString(c.getColumnIndexOrThrow("raça"));
                String color  = c.getString(c.getColumnIndexOrThrow("color"));
                String telefon1 = c.getString(c.getColumnIndexOrThrow("telefon1"));
                String telefon2  = c.getString(c.getColumnIndexOrThrow("telefon2"));
                String info1 = c.getString(c.getColumnIndexOrThrow("info1"));
                String info2  = c.getString(c.getColumnIndexOrThrow("info2"));
                resultado.add(new Fitxa(id, nomMascota, nomAmo, raça, color, telefon1, telefon2, info1, info2));
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();

        return resultado;
    }


    public static Fitxa nueva(String nomMascota, String nomAmo, String raça, String color, String telefon1, String telefon2, String info1, String info2) {
        Fitxa resultado = new Fitxa(nomMascota, nomAmo, raça, color, telefon1, telefon2, info1, info2);
        SQLiteDatabase db = getHelper().getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nomMascota", nomMascota);
        values.put("nomAmo", nomAmo);
        values.put("raça", raça);
        values.put("color", color);
        values.put("telefon1", telefon1);
        values.put("telefon2", telefon2);
        values.put("info1", info1);
        values.put("info2", info2);

        long id = db.insert("Fitxes", null, values);
        db.close();

        resultado.setId(id);
        return resultado;
    }


    public static void actualiza(Fitxa fitxa) {
        SQLiteDatabase db = getHelper().getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nomMascota", fitxa.getNomMascota());
        values.put("nomAmo", fitxa.getNomAmo());
        values.put("raça", fitxa.getRaça());
        values.put("color", fitxa.getColor());
        values.put("telefon1", fitxa.getTelefon1());
        values.put("telefon2", fitxa.getTelefon2());
        values.put("info1", fitxa.getInfo1());
        values.put("info2", fitxa.getInfo2());

        String where = "id = ?";
        String[] args = { Long.toString(fitxa.getId()) };

        db.update("Fitxes", values, where, args);
        db.close();
    }

    public static void borra(Fitxa fitxa) {
        SQLiteDatabase db = getHelper().getWritableDatabase();

        String where = "id = ?";
        String[] args = { Long.toString(fitxa.getId()) };

        db.delete("Fitxes", where, args);
        db.close();
    }
}
