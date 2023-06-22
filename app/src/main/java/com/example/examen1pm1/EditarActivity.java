package com.example.examen1pm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditarActivity extends AppCompatActivity {

    Button btnAtras3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        btnAtras3 = findViewById(R.id.btnAtras3);
        btnAtras3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditarActivity.class);
                startActivity(intent);
            }
        });
    }
}