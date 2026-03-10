package com.aluracursos.Challenge_numero_2.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("id") Long id_gutendex,
        String title,
        List<String> languages,
        List<DatosAutor> authors,
        String media_type,
        int download_count
) {
}
