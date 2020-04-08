package com.example.projectefinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CalendariActivity extends AppCompatActivity {

    private CalendarView calendari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendari);

        calendari = findViewById(R.id.calendariVista);
        calendari.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getBaseContext(), "Data seleccionada: " +dayOfMonth+"/"+month+"/"+year,Toast.LENGTH_LONG).show();
            }
        });

    }
}
