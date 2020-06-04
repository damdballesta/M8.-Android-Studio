package com.example.projectefinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewCalendarActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private SQLiteDatabase dbs;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar);

        listView = (ListView)findViewById(R.id.ltvListaEvents);
        listView.setOnItemLongClickListener(this);

        Bundle bundle = getIntent().getExtras();
        int dia, mes, any;
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        any = bundle.getInt("any");
        String cadena = dia + " - " + mes + " - " + any;

        AgendaDB bd = new AgendaDB(getApplicationContext(), "Agenda", null, 1);
        dbs = bd.getReadableDatabase();

        String sql = "SELECT * FROM events WHERE fechadesde='"+cadena+"'";
        Cursor c = null;

        String nom, horadesde, horahasta, descripcio, telefon;
        
        try {
            c = dbs.rawQuery(sql, null);

            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

            if(c.moveToFirst()){
                do{
                    nom = c.getString(1);
                    telefon = c.getString(2);
                    horadesde = c.getString(4);
                    horahasta = c.getString(6);
                    descripcio = c.getString(7);
                    arrayAdapter.add(nom + ", " +telefon+ ", " +horadesde+ ", " +horahasta+ ", " +descripcio);

                }while (c.moveToNext());
                listView.setAdapter(arrayAdapter);
            }else{
                this.finish();
            }
            
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Error: " +ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private  void eliminar(String dato){        //Eliminar registre

        String datos[] = dato.split(", ");
        String sql = "DELETE FROM events WHERE nomEvent='"+datos[0]+"' and telefon='"+datos[1]+"' and horadesde='"+datos[2]
                +"' and horahasta='"+datos[3]+"' and descripcio='"+datos[4]+"'";

        try {
            arrayAdapter.remove(dato);
            listView.setAdapter(arrayAdapter);
            dbs.execSQL(sql);
            Toast.makeText(getApplication(), "Event eliminat ",Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Error: " +ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[2];
        items[0] = "Eliminar event";
        items[1] = "Cancelar";
        builder.setTitle("Eliminar event")
                .setItems(items, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){           //eliminar event
                            eliminar(adapterView.getItemAtPosition(i).toString());
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }
}
