package com.aluracursos.Challenge_numero_2.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoAPI {

    public String obtenerDatos(String url){

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicion = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> respuesta = null;

        try{
            respuesta = cliente.send(requisicion, HttpResponse.BodyHandlers.ofString());
        }catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }

        String json = respuesta.body();
        return json;
    }
}
