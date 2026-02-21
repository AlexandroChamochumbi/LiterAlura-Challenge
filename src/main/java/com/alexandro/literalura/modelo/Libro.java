package com.alexandro.literalura.modelo;

import java.util.List;

public class Libro {

    private String titulo;
    private List<Autor> autores;
    private List<String> idiomas;
    private Integer numeroDescargas;

    public Libro(String titulo, List<Autor> autores, List<String> idiomas, Integer numeroDescargas) {
        this.titulo = titulo;
        this.autores = autores;
        this.idiomas = idiomas;
        this.numeroDescargas = numeroDescargas;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }
    public List<String> getIdiomas() {
            return idiomas;
        }
    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }
    @Override
    public String toString() {
        return """
                ----- LIBRO -----
                Título: %s
                Autores: %s
                Idiomas: %s
                Descargas: %d
                """.formatted(titulo, autores, idiomas, numeroDescargas);
    }
}