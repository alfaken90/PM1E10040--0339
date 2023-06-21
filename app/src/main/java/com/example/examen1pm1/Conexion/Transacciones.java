package com.example.examen1pm1.Conexion;

public class Transacciones {

    public static final String NameDatabase = "BD_CONTACTOS";
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String nota = "nota";
    public static final String telefono = "telefono";
    public static final String tablacontactos = "contactos";

    public static final String CreateTableContactos= "CREATE TABLE contactos " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombres TEXT, nota TEXT)";
}
