package com.example.projectefinal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class GaleriaActivity extends AppCompatActivity {

    ImageView imagenSeleccionada;
    Gallery gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        imagenSeleccionada = (ImageView) findViewById(R.id.seleccionada);

        final Integer[] imagenes = {R.drawable.logolagosseta, R.drawable.thanosnadal, R.drawable.pelu1, R.drawable.pelu2, R.drawable.pelu3,
                R.drawable.cocker, R.drawable.caniche, R.drawable.collie, R.drawable.husky, R.drawable.maltes, R.drawable.pomerania, R.drawable.schnauzer
        };

        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter((SpinnerAdapter) new GalleryAdapter(this, imagenes));
        //al seleccionar una imagen, la mostramos en el centro de la pantalla a mayor tamaño

        //con este listener, sólo se mostrarían las imágenes sobre las que se pulsa
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                imagenSeleccionada.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), imagenes[position], 300, 0));
            }

        });

        //con este otro listener se mostraría la imagen seleccionada en la galería, esto es, la que se encuentre en el centro en cada momento
//      gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//          @Override
//          public void onItemSelected(AdapterView parent, View v, int position, long id)
//          {
//              imagenSeleccionada.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), imagenes[position], 400, 0));
//          }
//
//          @Override
//          public void onNothingSelected(AdapterView<?> arg0)
//          {
//              // TODO Auto-generated method stub
//
//          }
//      });

    }

}

