package com.aluracursos.Challenge_numero_2.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
