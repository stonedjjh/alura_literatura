package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.model.Libro;
import com.aluraapi.literatura.service.LibroService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LibrosRegistradosPorIdioma {
    private final LibroService libroService;
    private final JFrame parentFrame;

    public LibrosRegistradosPorIdioma(LibroService libroService, JFrame parentFrame) {
        this.libroService = libroService;
        this.parentFrame = parentFrame;
    }
    public void ejecutar() {
        String[] opciones = {
                "es - Español", "en - Inglés", "fr - Francés",
                "pt - Portugués", "it - Italiano", "de - Alemán"
        };
        String seleccion = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Selecciona un idioma:",
                "Idiomas disponibles",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        if (seleccion == null) return;

        String codigoIdioma = seleccion.split(" ")[0];
        String nombreIdioma = seleccion.split(" - ")[1];

        List<Libro> libros = libroService.librosPorIdioma(codigoIdioma);

        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No se encontraron libros en ese idioma.");
            return;
        }
        StringBuilder resultado = new StringBuilder("Libros en " + nombreIdioma + ":\n\n");

        for (Libro libro : libros) {
            resultado.append("Título: ").append(libro.getTitulo()).append("\n")
                    .append("Autor: ").append(libro.getAutor().getNombre()).append("\n\n");
        }
        JTextArea textArea = new JTextArea(resultado.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setRows(18);
        textArea.setColumns(45);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 350));

        JOptionPane.showMessageDialog(
                parentFrame,
                scrollPane,
                "Resultado de la Búsqueda",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}