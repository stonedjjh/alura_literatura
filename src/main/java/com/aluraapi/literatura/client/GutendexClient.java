package com.aluraapi.literatura.client;

import com.aluraapi.literatura.dto.LibroDTO;

import java.util.List;

public interface GutendexClient {
    List<LibroDTO> buscarPorTitulo(String titulo);
    List<LibroDTO> buscarPorAutor(String autor);
    List<LibroDTO> obtenerTop13MasDescargados();
}
