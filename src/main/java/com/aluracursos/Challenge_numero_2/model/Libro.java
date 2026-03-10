package com.aluracursos.Challenge_numero_2.model;

import com.aluracursos.Challenge_numero_2.DTO.DatosLibro;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long idGuntendex;

    private String titulo;

    @ElementCollection
    private List<String> lenguajes = new ArrayList<>();


    private int numeroDescargas;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.title();
        this.idGuntendex = datosLibro.id_gutendex();
        this.lenguajes = datosLibro.languages();
        this.numeroDescargas = datosLibro.download_count();
    }

    public Long getIdGuntendex() {
        return idGuntendex;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        String nombresAutores = autores.stream()
                .map(Autor::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Sin autores");

        return """
            -----------------LIBRO-------------
            Titulo: %s
            Autor(es): %s
            Idioma(s): %s
            Numero de descargas: %d
            """.formatted(titulo, nombresAutores, lenguajes, numeroDescargas);
    }
}
