package com.example.projectefinal;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.projectefinal.R.menu.edita_fitxa_options;

public class Fitxa_Edit_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Fitxa fitxaOriginal;
    private int pos = -1;
    private EditText nomMascota;
    private EditText nomAmo;
    private EditText raça;
    private EditText color;
    private EditText telefon1;
    private EditText telefon2;
    private EditText info1;
    private EditText info2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_fitxa);

        nomMascota = findViewById(R.id.nomMascota);
        nomAmo = findViewById(R.id.nomAmo);
        raça = findViewById(R.id.raçaSpinner);
        color = findViewById(R.id.color);
        telefon1 = findViewById(R.id.telefon1);
        telefon2 = findViewById(R.id.telefon2);
        info1 = findViewById(R.id.info1);
        info2 = findViewById(R.id.info2);

        Intent intent = getIntent();
        //String action = intent.getAction();
        pos = intent.getIntExtra("pos", -1);

        if (pos != -1) {
            // Caso 1: Editar una fitxa desde nuestra app
            fitxaOriginal = FitxesLlista.getFitxa(pos);
            nomMascota.setText(fitxaOriginal.getNomMascota());
            nomAmo.setText(fitxaOriginal.getNomAmo());
            raça.setText(fitxaOriginal.getRaça());
            color.setText(fitxaOriginal.getColor());
            telefon1.setText(fitxaOriginal.getTelefon1());
            telefon2.setText(fitxaOriginal.getTelefon2());
            info1.setText(fitxaOriginal.getInfo1());
            info2.setText(fitxaOriginal.getInfo2());
        } else {
            // Caso 2: Nueva nota desde nuestra app
            fitxaOriginal = new Fitxa();
        }
    }

    private boolean hayCambios() {
        String mascota = nomMascota.getText().toString();
        String amo  = nomAmo.getText().toString();
        String raza = raça.getText().toString();
        String colo  = color.getText().toString();
        String tele1 = telefon1.getText().toString();
        String tele2  = telefon2.getText().toString();
        String inf1 = info1.getText().toString();
        String inf2  = info2.getText().toString();
        return !fitxaOriginal.getNomMascota().equals(mascota) || !fitxaOriginal.getNomAmo().equals(amo)
                || !fitxaOriginal.getRaça().equals(raza) || !fitxaOriginal.getColor().equals(colo)
                || !fitxaOriginal.getTelefon1().equals(tele1) || !fitxaOriginal.getTelefon2().equals(tele2)
                || !fitxaOriginal.getInfo1().equals(inf1) || !fitxaOriginal.getInfo2().equals(inf2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(edita_fitxa_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mascota;
        String amo;
        String raza;
        String colo;
        String tele1;
        String tele2;
        String inf1;
        String inf2;

        switch (item.getItemId()) {
            case R.id.guardar:
                mascota = nomMascota.getText().toString();
                amo  = nomAmo.getText().toString();
                raza = raça.getText().toString();
                colo  = color.getText().toString();
                tele1 = telefon1.getText().toString();
                tele2 = telefon2.getText().toString();
                inf1 = info1.getText().toString();
                inf2 = info2.getText().toString();
                if (pos != -1) {
                    FitxesLlista.modifica(pos, mascota, amo, raza, colo, tele1, tele2, inf1, inf2);
                } else {
                    FitxesLlista.nueva(mascota, amo, raza, colo, tele1, tele2, inf1, inf2);
                }
                setResult(RESULT_OK);
                finish();
                return true;

            case R.id.borrar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.confirmacio);
                builder.setMessage(R.string.confirma_borra_fitxa);
                builder.setPositiveButton(R.string.borra, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FitxesLlista.borra(pos);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (hayCambios()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.confirmacio);
            builder.setMessage(R.string.confirma_descarta_fitxa);
            builder.setPositiveButton(R.string.descarta, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Fitxa_Edit_Activity.super.onBackPressed();
                }
            });
            builder.setNegativeButton(R.string.segueix_editan, null);
            builder.create().show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String res = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}









