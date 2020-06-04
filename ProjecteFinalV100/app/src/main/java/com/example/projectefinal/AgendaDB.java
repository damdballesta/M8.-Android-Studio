package com.example.projectefinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AgendaDB extends SQLiteOpenHelper {

    private String sql = "create table events(" +
            "idEvent INTEGER PRIMARY KEY," +
            "nomEvent varchar(40)," +
            "nomAmo varchar(40)," +
            "telefon varchar(15)," +
            "fechadesde date," +
            "horadesde time," +
            "horahasta time," +
            "descripcio varchar(60))";

    public AgendaDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
