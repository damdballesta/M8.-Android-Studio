package com.example.projectefinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Nota_Edit_Activity extends AppCompatActivity {

    private Nota original;
    private int pos = -1;
    private EditText edit_titulo;
    private EditText edit_texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_nota);

        edit_titulo = (EditText) findViewById(R.id.edit_titulo);
        edit_texto = (EditText) findViewById(R.id.edit_texto);

        Intent intent = getIntent();
        String action = intent.getAction();
        pos = intent.getIntExtra("pos", -1);
        if (action != null && action.equals(Intent.ACTION_SEND)) {
            // Caso 0: Una app comparte un texto con nosotros.
            original = new Nota();
            String titulo = intent.getStringExtra(Intent.EXTRA_SUBJECT);
            String texto  = intent.getStringExtra(Intent.EXTRA_TEXT);
            edit_titulo.setText(titulo);
            edit_texto.setText(texto);
        } else if (pos != -1) {
            // Caso 1: Editar una nota desde nuestra app
            original = Notas_Llista.getNota(pos);
            edit_titulo.setText(original.getTitulo());
            edit_texto.setText(original.getTexto());
        } else {
            // Caso 2: Nueva nota desde nuestra app
            original = new Nota();
        }
    }

    private boolean hayCambios() {
        String titulo = edit_titulo.getText().toString();
        String texto  = edit_texto.getText().toString();
        return !original.getTitulo().equals(titulo) || !original.getTexto().equals(texto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edita_nota_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String titulo, texto;

        switch (item.getItemId()) {
            case R.id.guardar:
                titulo = edit_titulo.getText().toString();
                texto  = edit_texto.getText().toString();
                if (pos != -1) {
                    Notas_Llista.modifica(pos, titulo, texto);
                } else {
                    Notas_Llista.nueva(titulo, texto);
                }
                setResult(RESULT_OK);
                finish();
                return true;

            case R.id.borrar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.confirmacio);
                builder.setMessage(R.string.confirma_borra);
                builder.setPositiveButton(R.string.borra, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Notas_Llista.borra(pos);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();
                return true;

            case R.id.compartir:
                titulo = edit_titulo.getText().toString();
                texto  = edit_texto.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, titulo);
                intent.putExtra(Intent.EXTRA_TEXT, texto);
                intent.setType("text/plain");
                Intent chooser =
                        Intent.createChooser(intent, getResources().getText(R.string.compartir_amb));
                startActivity(chooser);
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
            builder.setMessage(R.string.confirma_descarta);
            builder.setPositiveButton(R.string.descarta, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Nota_Edit_Activity.super.onBackPressed();
                }
            });
            builder.setNegativeButton(R.string.segueix_editan, null);
            builder.create().show();
        } else {
            super.onBackPressed();
        }
    }
}
