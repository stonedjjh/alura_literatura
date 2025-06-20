package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.model.Libro;
import com.aluraapi.literatura.service.LibroService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Top13RegistroLocal {
    private final LibroService libroService;
    private final JFrame parentFrame;

    public Top13RegistroLocal(LibroService libroService, JFrame parentFrame) {
        this.libroService = libroService;
        this.parentFrame = parentFrame;
    }
    public void ejecutar() {
        List<Libro> top = libroService.top13MasDescargadosLocales();
        StringBuilder salida = new StringBuilder("Top 13 en base de datos local:\n\n");

        if (top.isEmpty()) {
            salida.append("No se encontraron libros en el top 13 local.");
        } else {
            for (int i = 0; i < Math.min(top.size(), 13); i++) {
                Libro libro = top.get(i);
                salida.append((i + 1)).append(".\n");
                salida.append("Título: ").append(libro.getTitulo()).append("\n");
                salida.append("Autor: ").append(libro.getAutor().getNombre()).append("\n");
            }
            if (top.size() < 13) {
                salida.append("(Solo se encontraron ").append(top.size())
                        .append(" libros en el top local)");
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
                "Top 13 desde registro local",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}