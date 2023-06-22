package com.example.examen1pm1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.examen1pm1.modelos.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper {

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertaContacto(String pais, String nombre, String telefono, String nota, byte[] imagen) {
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("pais", pais);
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("nota", nota);
            values.put("imagen", imagen);

            id = db.insert(TABLE_CONTACTOS, null, values);

        } catch (Exception ex) {
            ex.toString();
        }


        return id;
    }

    public ArrayList<Contactos> mostrarContactos (){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos  = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS,null);
        if (cursorContactos.moveToFirst()){
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setPais(cursorContactos.getString(1));
                contacto.setNombre(cursorContactos.getString(2));
                contacto.setTelefono(cursorContactos.getString(3));
                contacto.setNota(cursorContactos.getString(4));
                contacto.setImagen(cursorContactos.getBlob(5));
                listaContactos.add(contacto);
            }while (cursorContactos.moveToNext());
        }
        cursorContactos.close();

        return listaContactos;
    }
}
