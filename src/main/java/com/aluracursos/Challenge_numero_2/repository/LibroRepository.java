package com.aluracursos.Challenge_numero_2.repository;

import com.aluracursos.Challenge_numero_2.model.Libro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIdGuntendex(Long idGutendex);

    List<Libro> findAll(Sort sort);

    @Query("SELECT l FROM Libro l JOIN l.lenguajes idioma WHERE idioma = :idioma")
    List<Libro> findByIdioma(String idioma);
}
