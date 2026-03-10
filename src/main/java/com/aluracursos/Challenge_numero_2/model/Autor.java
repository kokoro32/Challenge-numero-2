package com.aluracursos.Challenge_numero_2.model;

import com.aluracursos.Challenge_numero_2.DTO.DatosAutor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int birthYear;
    private int deathYear;

    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros = new ArrayList<>();

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.name = datosAutor.name();
        this.birthYear = datosAutor.birth_year();
        this.deathYear = datosAutor.death_year();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }


    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public void agregarLibro(Libro libro){
        this.libros.add(libro);
    }

    @Override
    public String toString() {
        String titulosLibros = libros.stream()
                .map(Libro::getTitulo)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Sin libros registrados");

        return """
            -----------------AUTOR-------------
            Nombre: %s
            Año de nacimiento: %d
            Año de fallecimiento: %d
            Libros: %s
            """.formatted(name, birthYear, deathYear, titulosLibros);
    }
}
