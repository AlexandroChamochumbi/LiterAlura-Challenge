package com.alexandro.literalura.Repositories;

import com.alexandro.literalura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByIdioma(String idioma);

    boolean existsByTitulo(String titulo);

    long countByIdioma(String idioma);

    List<Libro> findTop10ByOrderByNumeroDescargasDesc();
}