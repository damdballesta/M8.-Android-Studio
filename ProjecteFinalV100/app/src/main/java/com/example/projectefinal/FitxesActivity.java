package com.example.projectefinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FitxesActivity extends AppCompatActivity {

    public static final int NUEVA_FITXA = 0;
    public static final int EDITA_FITXA = 1;
    private FitxesAdapter adapter;
    private ListView lista_fitxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitxes);

        FitxesDB.setContext(this);

        adapter = new FitxesAdapter();

        lista_fitxes = findViewById(R.id.lista_fitxes);
        lista_fitxes.setAdapter(adapter);

        lista_fitxes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                onEditaFitxa(pos);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NUEVA_FITXA:
            case EDITA_FITXA:
                if (resultCode == RESULT_OK) {
                    adapter.notifyDataSetChanged();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onEditaFitxa(int pos) {
        Intent intent = new Intent(this, Fitxa_Edit_Activity.class);
        intent.putExtra("pos", pos);
        startActivityForResult(intent, EDITA_FITXA);
    }

    public void onNuevaFitxa(View view) {
        Intent intent = new Intent(this, Fitxa_Edit_Activity.class);
        startActivityForResult(intent, NUEVA_FITXA);
    }

    private class FitxesAdapter extends ArrayAdapter<Fitxa> {
        FitxesAdapter() {
            super(FitxesActivity.this, R.layout.item_lista_fitxes,
                    FitxesLlista.get());
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result = convertView;
            if (result == null) {
                LayoutInflater inflater = getLayoutInflater();
                result = inflater.inflate(R.layout.item_lista_fitxes, parent, false);
            }
            Fitxa fitxa = getItem(position);
            TextView mascota = (TextView) result.findViewById(R.id.mascota);
            mascota.setText(fitxa.getNomMascota());
            TextView amo = (TextView) result.findViewById(R.id.amo);
            amo.setText(fitxa.getNomAmo().replace("\n", " "));
            TextView raça = (TextView) result.findViewById(R.id.raça);
            raça.setText(fitxa.getRaça().replace("\n", " "));
            return result;
        }

    }
}

