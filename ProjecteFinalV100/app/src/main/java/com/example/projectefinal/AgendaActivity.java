package com.example.projectefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgendaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nomEvent, nomAmo, telefon, fechadesde, horadesde, horahasta;
    private EditText descripcio;

    private Button guardar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        nomEvent = (EditText) findViewById(R.id.edtNombreEvento);       //Declarem les variables d'activity_agenda.xml
        telefon = (EditText) findViewById(R.id.edtTelefon);
        fechadesde = (EditText) findViewById(R.id.edtFechaDesde);
        nomAmo = (EditText) findViewById(R.id.edtNomAmo);
        horadesde = (EditText) findViewById(R.id.edtHoraInicio);
        horahasta = (EditText) findViewById(R.id.edtHoraHasta);
        descripcio = (EditText)findViewById(R.id.edtDescripcion);

        Bundle bundle = getIntent().getExtras();
        int dia, mes, any;

        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        any = bundle.getInt("any");

        fechadesde.setText(any+" - " + mes + " - " + dia);

        guardar = (Button)findViewById(R.id.btnGuardarAgenda);
        cancelar = (Button)findViewById(R.id.btnCancelarAgenda);
        guardar.setOnClickListener(this);                                  //Afegim els events als botons
        cancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {           //Es verifica quin event s'ha clicat
        if(view.getId()==guardar.getId()){      //Guardem les dades
            AgendaDB bd = new AgendaDB(getApplication(), "Agenda", null, 1);
            SQLiteDatabase db = bd.getWritableDatabase();

            String sql = "INSERT INTO events" +
                    "(nomEvent,nomAmo,telefon,fechadesde,horadesde,horahasta,descripcio)" +
                    "VALUES( '"+ nomEvent.getText() +
                            "','"+ nomAmo.getText() +
                            "','"+ telefon.getText() +
                            "','"+ fechadesde.getText() +
                            "','"+ horadesde.getText() +
                            "','"+ horahasta.getText() +
                            "','"+ descripcio.getText() +
                            "')";

            try{
                db.execSQL(sql);
                nomEvent.setText("");
                nomAmo.setText("");
                telefon.setText("");
                fechadesde.setText("");
                horadesde.setText("");
                horahasta.setText("");
                descripcio.setText("");

            }catch (Exception e){
                Toast.makeText(getApplication(), "Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            this.finish();

        }else{
            this.finish();
            return;
        }
    }
}
