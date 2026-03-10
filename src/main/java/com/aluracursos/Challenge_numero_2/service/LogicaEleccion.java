package com.aluracursos.Challenge_numero_2.service;

import com.aluracursos.Challenge_numero_2.DTO.DatosAutor;
import com.aluracursos.Challenge_numero_2.DTO.DatosLibro;
import com.aluracursos.Challenge_numero_2.DTO.DatosRespuesta;
import com.aluracursos.Challenge_numero_2.model.Autor;
import com.aluracursos.Challenge_numero_2.model.Libro;
import com.aluracursos.Challenge_numero_2.repository.AutorRepository;
import com.aluracursos.Challenge_numero_2.repository.LibroRepository;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class LogicaEleccion {

    @Autowired
    private ConsumoAPI consumoApi;
    @Autowired
    ConvierteDatos conversor;
    @Autowired
    public LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;


    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);

    Optional<Libro> libroBuscadoenBD;

    @Transactional
    public void logicaSeleccion(int opcion){

        switch (opcion){
            case 1 -> buscarYGuardarLibros();
            case 2 -> listarLibros();
            case 3 -> listarAutores();
            case 4 -> {
                System.out.println("Ingrese el año que desea buscar para revisar sí un autor vivió en esa fecha: ");
                int anio = teclado.nextInt();
                teclado.nextLine();
                listarAutoresVivosEnDeterminadoAnio(anio);
            }
            case 5 -> {
                System.out.println("""
            Ingrese el idioma para buscar los libros:
            es- español
            en- inglés
            fr- francés
            pt- portugés
            """);
                String idioma = teclado.nextLine();
                listarLibrosPorIdioma(idioma);
            }
        }
    }

    @Transactional
    private void listarLibrosPorIdioma(String idioma) {
        if (idioma.length() < 2) {
            System.out.println("Debes ingresar al menos dos letras del idioma.");
            return;
        }

        String formatoCorrectoIdioma = idioma.toLowerCase().trim().substring(0,2);

        List<Libro> listaDeLibrosPorIdioma = libroRepository.findByIdioma(formatoCorrectoIdioma);
        if (listaDeLibrosPorIdioma.isEmpty()){
            System.out.println("No se encontraron libros con el lenguaje proporcionado, recuerda que debes de colocar las primeras dos letras");
            return;
        }
        listaDeLibrosPorIdioma.forEach(System.out::println);
    }

    @Transactional
    public void buscarYGuardarLibros() {
        System.out.println("Ingrese el nombnre del libro que desea buscar: ");
        String libroABuscar = teclado.nextLine();
        String libroABuscarCodificado = URLEncoder.encode(libroABuscar, StandardCharsets.UTF_8);
        var json = consumoApi.obtenerDatos(URL_BASE+libroABuscarCodificado);

        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.results() == null || respuesta.results().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
            return;
        }

        Optional<DatosLibro> libro = respuesta.results().stream()
                        .filter(l -> l.media_type() != null)
                        .filter(l -> l.media_type().equalsIgnoreCase("Text"))
                        .findFirst();

        if(libro.isEmpty()){
            System.out.println("No se encontró ningún libro pero existen otros formatos para la bsuqueda solicitada");
            return;
        }

        DatosLibro datosLibro = libro.get();
        Optional<Libro> existeLibro = libroRepository.findByIdGuntendex(datosLibro.id_gutendex());
        if(existeLibro.isPresent()){
            Libro libroenBd = existeLibro.get();
            System.out.println("El libro ya existe en la base de datos\n"+
                    libroenBd.getTitulo() + " con el id: " + libroenBd.getId());
            return;
        }

        Libro libroNuevo = new Libro(datosLibro);
        List<Autor> autoresFinales = new ArrayList<>();

        if (datosLibro.authors() != null) {
            for (DatosAutor datosAutor : datosLibro.authors()) {
                Optional<Autor> buscarAutor = autorRepository.findByName(datosAutor.name());
                Autor autorFinal = buscarAutor.orElseGet(() -> new Autor(datosAutor));
                autoresFinales.add(autorFinal);
            }
        }

        libroNuevo.setAutores(autoresFinales);

        for (Autor autor : autoresFinales) {
            autor.getLibros().add(libroNuevo);
        }
        libroRepository.save(libroNuevo);

        System.out.println("\nResumen de la operación:");
        System.out.println(libroNuevo);
    }

    @Transactional
    public void listarLibros(){

        List<Libro> listaDeLibrosExistentesEnBaseDeDatos = libroRepository.findAll(Sort.by("titulo"));
        if(listaDeLibrosExistentesEnBaseDeDatos.isEmpty()){
            System.out.println("No se han registrado libros en la base de datos");
            return;
        }
        listaDeLibrosExistentesEnBaseDeDatos.forEach(System.out::println);
    }

    @Transactional
    public void listarAutores(){
        List<Autor> autoresRegistradosEnLaBaseDeDatos = autorRepository.findAll(Sort.by("name"));
        if(autoresRegistradosEnLaBaseDeDatos.isEmpty()){
            System.out.println("No existen autores registrados en la Base de Datos");
            return;
        }
        autoresRegistradosEnLaBaseDeDatos.forEach(System.out::println);
    }

    @Transactional
    public void listarAutoresVivosEnDeterminadoAnio(int anio){
        List<Autor> autoresVivosEnElAnioProporcionado = autorRepository.findAutoresVivos(anio);
        if(autoresVivosEnElAnioProporcionado.isEmpty()) {
            System.out.println("No hay autores que vivieron durante el año proporcionado de: " + anio);
            return;
        }
        autoresVivosEnElAnioProporcionado.forEach(System.out::println);
    }


}

