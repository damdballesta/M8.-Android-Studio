package com.example.projectefinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CalendariActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendari);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);

    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int day, int month, int year) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[3];
        items[0]="Agregar Cita";
        items[1]="Veure Cites";
        items[2]="Cancelar";

        final int dia, mes, any;
        dia = day;
        mes = month+1;
        any = year;

        builder.setTitle("Seleccioni una tasca")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i==0){
                            Intent intent = new Intent(getApplication(), AgendaActivity.class);       //Activitat Agregar Event
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("any", any);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }else if(i==1){
                            //Veure Events
                            Intent intent = new Intent(getApplication(), ViewCalendarActivity.class);       //Veure Events
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("any", any);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            return;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
