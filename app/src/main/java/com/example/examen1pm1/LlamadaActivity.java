package com.example.examen1pm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LlamadaActivity extends AppCompatActivity {

    Button btnAtras2;
    TextView etiquetaLLamada;
    String numeroTelefono;

    static final int PETICION_LLAMADA_TELEFONO = 108;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamada);

        btnAtras2 = findViewById(R.id.btnAtras2);
        etiquetaLLamada = findViewById(R.id.etiquetaLlamada);

        //Capturar dato de la pantalla anterior
        numeroTelefono = getIntent().getExtras().getString("TEL");
        etiquetaLLamada.setText(numeroTelefono);

//Evento Regresar a Lista
        btnAtras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
                startActivity(intent);
            }
        });

        llamada();


    }

    private void llamada() {
        try{
            if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, PETICION_LLAMADA_TELEFONO);
                Toast.makeText(LlamadaActivity.this, "If", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));
                startActivity(intent);
                Toast.makeText(LlamadaActivity.this, "Else", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            ex.toString();
        }
    }
}