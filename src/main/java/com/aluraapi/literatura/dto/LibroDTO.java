package com.aluraapi.literatura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record LibroDTO(
        @JsonProperty("id") Long id,
        @JsonProperty("title") String titulo,
        @JsonProperty("languages") List<String> idiomas,
        @JsonProperty("download_count") Integer descargas,
        @JsonProperty("authors") List<AutorDTO> autores
) {}
