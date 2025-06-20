package com.aluraapi.literatura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AutorDTO(
        @JsonProperty("name") String nombre,
        @JsonProperty("birth_year") Integer nacimiento,
        @JsonProperty("death_year") Integer fallecimiento
) {}