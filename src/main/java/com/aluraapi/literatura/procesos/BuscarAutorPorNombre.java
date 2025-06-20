package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.dto.LibroDTO;
import com.aluraapi.literatura.service.LibroService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BuscarAutorPorNombre {
    private final LibroService libroService;
    private final JFrame parentFrame;

    public BuscarAutorPorNombre(LibroService libroService, JFrame parentFrame) {
        this.libroService = libroService;
        this.parentFrame = parentFrame;
    }
    public void ejecutar() {
        String nombre = JOptionPane.showInputDialog(
                parentFrame,
                "Nombre del autor:",
                "Buscar Autor",
                JOptionPane.PLAIN_MESSAGE
        );
        if (nombre == null || nombre.isBlank()) return;

        List<LibroDTO> resultados = libroService.buscarPorAutor(nombre);
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No se encontraron libros para ese autor.");
            return;
        }
        StringBuilder salida = new StringBuilder("Libros del autor:\n");
        resultados.forEach(libro ->
                salida.append("- ")
                        .append(libro.titulo())
                        .append(" (")
                        .append(libro.descargas())
                        .append(" descargas)\n")
        );
        JTextArea textArea = new JTextArea(salida.toString());
        textArea.setEditable(false);
        textArea.setRows(15);
        textArea.setColumns(40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(
                parentFrame,
                scrollPane,
                "Libros del Autor",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
