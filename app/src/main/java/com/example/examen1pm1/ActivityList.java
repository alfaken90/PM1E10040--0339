package com.example.examen1pm1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.examen1pm1.Conexion.SQLiteConexion;
import com.example.examen1pm1.Conexion.Transacciones;
import com.example.examen1pm1.Models.Contactos;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView listcontactos;
    ArrayList<Contactos> lista;
    ArrayList<String> Arreglocontactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        listcontactos = (ListView) findViewById(R.id.listcontactos);

        ObtenerTabla();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglocontactos);
        listcontactos.setAdapter(adp);

        listcontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }

        private void ObtenerTabla ()
        {
            SQLiteDatabase db = conexion.getReadableDatabase();
            Contactos contac = null;
            lista = new ArrayList<Contactos>();

            Cursor cursor = db.rawQuery(Transacciones.SelectTableContactos, null);

            while (cursor.moveToNext()) {
                contac = new Contactos();
                contac.setId(cursor.getInt(0));
                contac.setNombre(cursor.getString(1));
                contac.setTelefono(cursor.getString(2));
                contac.setNota(cursor.getString(3));
                lista.add(contac);
            }
            cursor.close();
            fillList();
        }

        private void fillList () {
            Arreglocontactos = new ArrayList<String>();

            for (int i = 0; i < lista.size(); i++) {
                Arreglocontactos.add(lista.get(i).getId() + " - "
                        + lista.get(i).getNombre() + " - "
                        + lista.get(i).getTelefono() + " - "
                        + lista.get(i).getNota());
            }
        }
}