package com.example.examen1pm1.Models;

public class Contactos {
    private Integer id;
    private String nombre;
    private String telefono;
    private String nota;

    public Contactos (Integer id, String nombre, String telefono, String nota){
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nota = nota;
    }

    public Contactos(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() { return telefono;}

    public void setTelefono(String telefonos) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
