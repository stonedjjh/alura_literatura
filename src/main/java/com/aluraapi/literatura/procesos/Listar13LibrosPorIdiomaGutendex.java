package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.principal.MenuOpciones;
import com.aluraapi.literatura.service.LibroService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Listar13LibrosPorIdiomaGutendex {
    private final LibroService libroService;
    private final MenuOpciones menuPrincipal;

    private static final Map<String, String> IDIOMAS_DISPONIBLES = new LinkedHashMap<>();

    static {
        IDIOMAS_DISPONIBLES.put("Español", "es");
        IDIOMAS_DISPONIBLES.put("Inglés", "en");
        IDIOMAS_DISPONIBLES.put("Francés", "fr");
        IDIOMAS_DISPONIBLES.put("Italiano", "it");
        IDIOMAS_DISPONIBLES.put("Portugués", "pt");
        IDIOMAS_DISPONIBLES.put("Alemán", "de");
    }
    public Listar13LibrosPorIdiomaGutendex(LibroService libroService, MenuOpciones menuPrincipal) {
        this.libroService = libroService;
        this.menuPrincipal = menuPrincipal;
    }
    public void ejecutar() {
        Object[] opcionesIdioma = IDIOMAS_DISPONIBLES.keySet().toArray();

        String seleccionIdiomaNombre = (String) JOptionPane.showInputDialog(
                menuPrincipal,
                "Selecciona el idioma para listar los 13 primeros libros:",
                "Idiomas disponibles",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesIdioma,
                opcionesIdioma[0]
        );
        if (seleccionIdiomaNombre == null) {
            JOptionPane.showMessageDialog(menuPrincipal,
                    "Operación de selección de idioma cancelada.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String codigoIdioma = IDIOMAS_DISPONIBLES.get(seleccionIdiomaNombre);

        if (codigoIdioma == null || codigoIdioma.trim().isEmpty()) {
            JOptionPane.showMessageDialog(menuPrincipal,
                    "No se pudo obtener el código del idioma seleccionado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listarPrimerosLibrosPorIdioma(codigoIdioma);
    }
    private void listarPrimerosLibrosPorIdioma(String codigoIdioma) {
        String url = "https://gutendex.com/books/?languages=" + codigoIdioma;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode resultsNode = rootNode.path("results");

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Los 13 primeros libros en ")
                    .append(idiomaNombre(codigoIdioma))
                    .append(":\n\n");

            int contador = 0;

            for (JsonNode bookNode : resultsNode) {
                if (contador >= 13) break;

                String titulo = bookNode.path("title").asText();
                String autor = bookNode.path("authors").path(0).path("name")
                        .asText("Autor Desconocido");

                mensaje.append("Título: ").append(titulo).append("\n");
                mensaje.append("Autor: ").append(autor).append("\n\n");

                contador++;
            }
            JTextArea textArea = new JTextArea(mensaje.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setRows(18);
            textArea.setColumns(45);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 350));

            JOptionPane.showMessageDialog(menuPrincipal,
                    scrollPane,
                    "Resultados de la Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(menuPrincipal,
                    "Error al obtener libros en el idioma " + codigoIdioma.toUpperCase() + ":\n" +
                            e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private String idiomaNombre(String codigo) {
        return IDIOMAS_DISPONIBLES.entrySet().stream()
                .filter(entry -> codigo.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(codigo.toUpperCase());
    }
}