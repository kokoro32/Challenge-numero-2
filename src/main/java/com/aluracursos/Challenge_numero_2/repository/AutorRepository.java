package com.aluracursos.Challenge_numero_2.repository;

import com.aluracursos.Challenge_numero_2.model.Autor;
import com.aluracursos.Challenge_numero_2.model.Libro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByName(String name);

    List<Autor> findAll(Sort sort);

    @Query("SELECT a FROM Autor a WHERE :anio BETWEEN a.birthYear AND a.deathYear ")
    List<Autor> findAutoresVivos(int anio);
}
