package com.example.examen1pm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
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
    Button guardar;
    Button guardados;

    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imgView);

<<<<<<< HEAD
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        //LISTA DESPLEGALE
        mSpinner= (Spinner) findViewById(R.id.mSpinner);
        ArrayList<String>elementos= new ArrayList<>();
        elementos.add("Honduras (504)");
        elementos.add("Costa Rica (506)");
        elementos.add("Guatemala (502)");
        elementos.add("El Salvador (503)");
        ArrayAdapter adp= new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,elementos);

        mSpinner.setAdapter(adp);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String elemento= (String) mSpinner.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this, "Seleccionaste; "+elemento, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nombre= (EditText) findViewById(R.id.txtNombre);
        telefono= (EditText) findViewById(R.id.txtTelefono);
        nota= (EditText)  findViewById(R.id.txtNota);
        guardar= (Button)  findViewById(R.id.btnGuardar);
        guardados= (Button)  findViewById(R.id.btnGuardados);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().isEmpty() && !telefono.getText().toString().isEmpty() && !nota.getText().toString().isEmpty())
                {
                    AgregarContactoSQL();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Porfavor llene todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });


=======

        btnCamara.setOnClickListener(v -> abrirCamara());
>>>>>>> origin/Kensy_develop
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

<<<<<<< HEAD
    private void AgregarContactoSQL(){
        SQLiteConexion conexion= new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        SQLiteDatabase db= conexion.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Transacciones.nombres, nombre.getText().toString());
        values.put(Transacciones.telefono, telefono.getText().toString());
        values.put(Transacciones.nota, nota.getText().toString());

        Long result = db.insert(Transacciones.tablacontactos, Transacciones.id, values);
        Toast.makeText(getApplicationContext(), "Registro Exitoso " + result.toString()
                ,Toast.LENGTH_LONG).show();

        db.close();

        ScreenClean();
    }
    private void ScreenClean()
    {
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
    }

=======
    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imgTemp = File.createTempFile(nombreImagen, ".jpg", directorio);

        rutaImagen = imgTemp.getAbsolutePath();
        return imgTemp;
    }
>>>>>>> origin/Kensy_develop
}