package com.example.examen1pm1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class dbPaises extends DbHelper{
    public dbPaises(@Nullable Context context) {
        super(context);
    }

    public Cursor mostrarPaises(){
        try {
            SQLiteDatabase bd = this.getReadableDatabase();
            Cursor filas = bd.rawQuery("SELECT * FROM "+ TABLE_PAISES + "",null);
            if (filas.moveToFirst()){
                return filas;
            }else {return null;}
        }catch (Exception ex){return null;}
    }
}
