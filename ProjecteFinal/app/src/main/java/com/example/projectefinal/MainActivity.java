package com.example.projectefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.security.Principal;

public class MainActivity extends AppCompatActivity {

    private Button calendari;
    private Button fitxes;
    private Button agenda;
    private Button notes;
    private Button galeria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendari = (Button)findViewById(R.id.btncalendari);
        fitxes = (Button)findViewById(R.id.btnbuscar);
        agenda = (Button)findViewById(R.id.btnagenda);
        notes = (Button)findViewById(R.id.btnnotes);
        galeria = (Button)findViewById(R.id.btngaleria);


        calendari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCalendari = new Intent(MainActivity.this, CalendariActivity.class);
                startActivity(intentCalendari);
            }
        });

        fitxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFitxes = new Intent(MainActivity.this, BuscarActivity.class);
                startActivity(intentFitxes);
            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAgenda = new Intent(MainActivity.this, AgendaActivity.class);
                startActivity(intentAgenda);
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNotes = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intentNotes);
            }
        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGaleria = new Intent(MainActivity.this, GaleriaActivity.class);
                startActivity(intentGaleria);
            }
        });
    }
}
