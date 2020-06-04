package com.example.projectefinal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class GaleriaActivity extends AppCompatActivity {

    ImageView imagenSeleccionada;
    Gallery gallery;
    Button galeria2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        imagenSeleccionada = (ImageView) findViewById(R.id.seleccionada);
        galeria2 = (Button)findViewById(R.id.btnGaleria2);

        final Integer[] imagenes = {R.drawable.logolagosseta, R.drawable.thanosnadal, R.drawable.pelu1, R.drawable.pelu2, R.drawable.pelu3,
                R.drawable.cocker, R.drawable.caniche, R.drawable.collie, R.drawable.husky, R.drawable.maltes, R.drawable.pomerania, R.drawable.schnauzer
        };

        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter((SpinnerAdapter) new GalleryAdapter(this, imagenes));    //Al seleccionar una imatge la mostrem al centre de la pantalla

        //aquest listener, nomes mostra les imatges sobre les que es clica
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                imagenSeleccionada.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), imagenes[position], 300, 0));
            }
        });
    }

    public void onClick(View view) {
        Intent intentGaleria2 = new Intent(GaleriaActivity.this, Galeria2Activity.class);
        startActivity(intentGaleria2);
    }
}

