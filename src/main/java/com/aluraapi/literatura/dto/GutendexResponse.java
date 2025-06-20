package com.aluraapi.literatura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record GutendexResponse(
        @JsonProperty("results") List<LibroDTO> results
) {}