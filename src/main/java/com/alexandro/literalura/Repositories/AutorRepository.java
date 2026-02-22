package com.alexandro.literalura.Repositories;

import com.alexandro.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Para evitar duplicar autores
    Optional<Autor> findByNombre(String nombre);

    // Autores vivos en determinado año
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(
            Integer anioNacimiento,
            Integer anioFallecimiento
    );

     // Autores que siguen vivos (sin anio de fallecimiento)
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNull(
            Integer anioNacimiento
    );

    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    List<Autor> findByAnioNacimientoGreaterThan(Integer anio);
}