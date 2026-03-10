package com.aluracursos.Challenge_numero_2.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        String name,
        int birth_year,
        int death_year
) {
}
