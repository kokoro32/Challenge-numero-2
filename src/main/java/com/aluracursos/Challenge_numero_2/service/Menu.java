package com.aluracursos.Challenge_numero_2.service;

public class Menu {

    public static void mostrarMenu(){
        System.out.print("""
                **************************************************************************
                Escoge una opción válida:
                1) Buscar libro por titulo
                2) Listar libros registrados
                3) Listar autores registrados
                4) Listar autores vivos en un determinado año
                5) Listar libros por idioma
                0) Salir
                **************************************************************************
                """);
    }
}
