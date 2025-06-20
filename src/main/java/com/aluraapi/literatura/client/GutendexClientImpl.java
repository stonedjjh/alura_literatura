package com.aluraapi.literatura.client;

import com.aluraapi.literatura.dto.GutendexResponse;
import com.aluraapi.literatura.dto.LibroDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GutendexClientImpl implements GutendexClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://gutendex.com/books/";

    @Override
    public List<LibroDTO> buscarPorTitulo(String titulo) {

        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("search", titulo)
                .toUriString();
        System.out.println("URL de búsqueda por título (Gutendex): " + url);

        List<LibroDTO> rawResults = obtenerResultadosDesde(url);

        System.out.println("Resultados crudos de Gutendex para título '" + titulo + "': "
                + (rawResults != null ? rawResults.size() : "0") + " libros.");
        if (rawResults != null && !rawResults.isEmpty()) {
            rawResults.forEach(libro -> {
                System.out.println("  - Libro encontrado (crudo): " + libro.titulo());
            });
        }
        return rawResults;
    }
    @Override
    public List<LibroDTO> buscarPorAutor(String autor) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("search", autor)
                .toUriString();
        System.out.println("URL de búsqueda por autor (Gutendex): " + url);

        List<LibroDTO> rawResults = obtenerResultadosDesde(url);

        System.out.println("Resultados crudos de Gutendex para autor '" + autor + "': "
                + (rawResults != null ? rawResults.size() : "0") + " libros.");
        if (rawResults != null) {
            rawResults.forEach(libro -> {
                System.out.println("  - Libro: " + libro.titulo() + " | Autores: " +
                        libro.autores().stream().map(a -> a.nombre()).
                                collect(Collectors.joining(", ")));
            });
        }
        if (rawResults != null && !rawResults.isEmpty()) {
            final String lowerCaseAutor = autor.toLowerCase();
            return rawResults.stream()
                    .filter(libro -> libro.autores() != null && !libro.autores().isEmpty())
                    .filter(libro -> libro.autores().stream()
                            .anyMatch(a -> a.nombre() != null &&
                                    a.nombre().toLowerCase().contains(lowerCaseAutor)))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
    @Override
    public List<LibroDTO> obtenerTop13MasDescargados() {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("sort", "-download_count")
                .queryParam("page_size", 11)
                .toUriString();
        return obtenerResultadosDesde(url);
    }
    private List<LibroDTO> obtenerResultadosDesde(String url) {
        try {
            GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);
            return Optional.ofNullable(response)
                    .map(GutendexResponse::results)
                    .orElse(List.of());
        } catch (Exception e) {
            System.err.println("Error al obtener resultados desde Gutendex URL: " +
                    url + " - " + e.getMessage());
            return List.of();
        }
    }
}