package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.dto.LibroDTO;
import com.aluraapi.literatura.service.LibroService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Top13Gutendex {
    private final LibroService libroService;
    private final JFrame parentFrame;

    public Top13Gutendex(LibroService libroService, JFrame parentFrame) {
        this.libroService = libroService;
        this.parentFrame = parentFrame;
    }
    public void ejecutar() {
        List<LibroDTO> top = libroService.obtenerTop11MasDescargados();
        StringBuilder salida = new StringBuilder("Top 13 de Gutendex:\n\n");

        if (top.isEmpty()) {
            salida.append("No se encontraron libros en el top 13 de Gutendex.");
        } else {
            for (int i = 0; i < Math.min(top.size(), 13); i++) {
                LibroDTO libro = top.get(i);

                String nombresAutores = libro.autores().stream()
                        .map(autor -> autor.nombre())
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Autor desconocido");

                salida.append((i + 1)).append(".\n");
                salida.append("Título: ").append(libro.titulo()).append("\n");
                salida.append("Autor: ").append(nombresAutores).append("\n");
            }
            if (top.size() < 13) {
                salida.append("(Solo se encontraron ")
                        .append(top.size())
                        .append(" libros en el top de Gutendex)");
            }
        }
        JTextArea textArea = new JTextArea(salida.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setRows(18);
        textArea.setColumns(45);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 350));

        JOptionPane.showMessageDialog(
                parentFrame,
                scrollPane,
                "Top 13 desde Gutendex",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}