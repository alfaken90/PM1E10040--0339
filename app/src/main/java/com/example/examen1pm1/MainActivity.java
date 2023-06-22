package com.example.examen1pm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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


import com.example.examen1pm1.db.DbContactos;
import com.example.examen1pm1.db.dbPaises;
import com.example.examen1pm1.modelos.paises;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Elementos en el Layout por orden
    ImageView imgView;
    Button btnCamara;
    Spinner spPaises;
    EditText txtNombre, txtTelefono, txtNota;
    Button btnGuardar;
    Button btnGuardados;


    String rutaImagen;
    int idPais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imgView);
        btnCamara = findViewById(R.id.btnCamara);
        spPaises = findViewById(R.id.spPaises);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtNota = findViewById(R.id.txtNota);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardados = findViewById(R.id.btnGuardados);


        //Llenar Spinner con metodo
        List<paises> listaPaises = llenarPaises();
        ArrayAdapter<paises> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaPaises);
        spPaises.setAdapter(arrayAdapter);

        //Seleccion ID de opcion Spinner
        spPaises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idPais = ((paises) parent.getSelectedItem()).getId();
                String nombrePais = ((paises) parent.getSelectedItem()).getNombre();
                //Toast.makeText(MainActivity.this,"Seleccion: " + idPais + " "+ nombrePais,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Evento abrir camara, tomar foto y guardarla en el dispositivo
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        //Guardar registro primera vez
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Convertir la IMG en un array para BD
                Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                DbContactos dbContactos = new DbContactos(MainActivity.this);
                long id = dbContactos.insertaContacto(Integer.toString(idPais), txtNombre.getText().toString(), txtTelefono.getText().toString(), txtNota.getText().toString(), byteArray);

                if (id > 0) {
                    Toast.makeText(MainActivity.this, "Registro guardado: " + id, Toast.LENGTH_LONG).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(MainActivity.this, "Error al guardar registro", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Pasar a la pantalla de Registros guardados
        btnGuardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
                startActivity(intent);
            }
        });

    }

    //Llenar el Spinner
    @SuppressLint("Range")
    private List<paises> llenarPaises() {
        List<paises> listaPaises = new ArrayList<>();
        dbPaises DBPaises = new dbPaises(MainActivity.this);
        Cursor cursor = DBPaises.mostrarPaises();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    paises p = new paises();
                    p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    p.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    listaPaises.add(p);
                } while (cursor.moveToNext());
            }

        }
        DBPaises.close();
        return listaPaises;
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

    private void limpiarCampos() {
        imgView.setImageBitmap(null);
        txtNombre.setText("");
        txtTelefono.setText("");
        txtNota.setText("");
    }

    public void alerta() {
        if(txtNombre.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Debe llenar el campo Nombre");
            builder.setTitle("Alerta!");
            builder.setCancelable(false);
            builder.setNegativeButton("Cerrar", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        if(txtTelefono.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Debe llenar el campo Telefono");
            builder.setTitle("Alerta!");
            builder.setCancelable(false);
            builder.setNegativeButton("Cerrar", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        if(txtNota.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Debe llenar el campo Nota");
            builder.setTitle("Alerta!");
            builder.setCancelable(false);
            builder.setNegativeButton("Cerrar", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}