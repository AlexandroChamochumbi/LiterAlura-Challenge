package com.alexandro.literalura.modelo;

public class Autor {

    private String nombre;

    public Autor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Autor: " + nombre;
    }
}