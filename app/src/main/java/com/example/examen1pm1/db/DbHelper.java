package com.example.examen1pm1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DbHelper extends SQLiteOpenHelper {

    private static final String BASE_NOMBRE = "db_examen1.db";
    private static final int BASE_VERSION = 2;
    protected static final String TABLE_PAISES = "t_paises";
    protected static final String TABLE_CONTACTOS = "t_contactos";

    public DbHelper(@Nullable Context context) {
        super(context, BASE_NOMBRE, null, BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PAISES + "(" +
                "id INTEGER PRIMARY KEY NOT NULL, " +
                "nombre TEXT NOT NULL)");

        db.execSQL("INSERT INTO " + TABLE_PAISES + "(id, nombre) VALUES " +
                "(504, 'Honduras'), " +
                "(501, 'Belice'), " +
                "(502, 'Guatemala')," +
                "(503, 'El Salvador'), " +
                "(505, 'Nicaragua')");

        db.execSQL("CREATE TABLE "+ TABLE_CONTACTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pais TEXT, " +
                "nombre TEXT, " +
                "telefono TEXT, " +
                "nota TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTOS);
        onCreate(db);
    }
}
