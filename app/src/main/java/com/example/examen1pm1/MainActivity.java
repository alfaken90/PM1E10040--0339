package com.example.examen1pm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examen1pm1.Conexion.SQLiteConexion;
import com.example.examen1pm1.Conexion.Transacciones;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnCamara;
    ImageView imgView;
    String rutaImagen;
    EditText nombre;
    EditText telefono;
    EditText nota;
    Button guardados;
    Button guardar;
    Spinner combocontactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imgView);

        nombre= (EditText) findViewById(R.id.txtnombre);
        telefono= (EditText) findViewById(R.id.txttelefono);
        nota= (EditText)  findViewById(R.id.txtcita);
        combocontactos=(Spinner) findViewById(R.id.mSpinner);
        guardados= (Button)  findViewById(R.id.btnGuardados);
        guardar= (Button)  findViewById(R.id.btnGuardar);

        ArrayList<String> elementos= new ArrayList<>();
        elementos.add("");
        elementos.add("Honduras 504");
        elementos.add("Costa Rica 506");
        elementos.add("Guatemala 502");
        elementos.add("El Salvador 503");
        ArrayAdapter adp= new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,elementos);

        combocontactos= (Spinner) findViewById(R.id.mSpinner);

        combocontactos.setAdapter(adp);

        combocontactos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String elemento= (String) combocontactos.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this,"Selecionaste: "+ elemento,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     AddContacto();
                     Toast.makeText(getApplicationContext(),"Datos: " + nombre.getText().toString() + " "+telefono.getText().toString() + " "+nota.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

        guardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityList.class);
                startActivity(intent);
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        btnCamara.setOnClickListener(v -> abrirCamara());
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if (intent.resolveActivity(getPackageManager()) != null) {

            File imgArchivo = null;
            try {
                imgArchivo = crearImagen();
            } catch (IOException ex) {
                Log.e("Error", ex.toString());
            }

            if (imgArchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(this,
                        "com.example.examen1pm1.fileprovider", imgArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intent, 1);
            }

        //}

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgView.setImageBitmap(imgBitmap);
        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imgTemp = File.createTempFile(nombreImagen, ".jpg", directorio);

        rutaImagen = imgTemp.getAbsolutePath();
        return imgTemp;
    }

    private void AddContacto()
    {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombre, nombre.getText().toString());
        valores.put(Transacciones.telefono, telefono.getText().toString());
        valores.put(Transacciones.nota, nota.getText().toString());
        Long result = db.insert(Transacciones.tablacontactos, Transacciones.id, valores);
        Toast.makeText(getApplicationContext(), "Registro Ingresado : " + result.toString(),Toast.LENGTH_LONG ).show();
        db.close();
        CleanScreen();
    }

    private void CleanScreen()
    {
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
    }
}