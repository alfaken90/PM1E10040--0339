package com.example.examen1pm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.examen1pm1.adaptadores.ListaContactosAdapter;
import com.example.examen1pm1.db.DbContactos;
import com.example.examen1pm1.modelos.Contactos;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    RecyclerView rvListaContactos;
    ArrayList<Contactos> listaArrayContactos;

    Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        btnAtras = findViewById(R.id.btnAtras);

        rvListaContactos = findViewById(R.id.rvListaContactos);
        rvListaContactos.setLayoutManager(new LinearLayoutManager(this));

        DbContactos dbContactos = new DbContactos(ListaActivity.this);

        listaArrayContactos = new ArrayList<>();

        ListaContactosAdapter adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        rvListaContactos.setAdapter(adapter);

        //Regresar a pagina principal
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}