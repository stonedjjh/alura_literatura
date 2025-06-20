package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.model.Libro;
import com.aluraapi.literatura.service.LibroService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
public class ListarLibrosRegistrados {

        private final LibroService libroService;
        private final JFrame parentFrame;

        public ListarLibrosRegistrados(LibroService libroService, JFrame parentFrame) {
            this.libroService = libroService;
            this.parentFrame = parentFrame;
        }
        public void ejecutar() {
            List<Libro> libros = libroService.librosRegistrados();
            if (libros.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "No hay libros registrados.");
                return;
            }
            StringBuilder lista = new StringBuilder("Libros almacenados:\n\n");
            for (Libro l : libros) {
                String autorNombre = (l.getAutor() != null) ? l.getAutor().getNombre() : "Desconocido";
                lista.append("Título: ").append(l.getTitulo()).append("\n");
                lista.append("Autor: ").append(autorNombre).append("\n");
                lista.append("Idioma: ").append(l.getIdioma()).append("\n");
                lista.append("Descargas: ").append(l.getDescargas()).append("\n");
                lista.append("--------------------\n");
            }
            JTextArea textArea = new JTextArea(lista.toString());
            textArea.setEditable(false);
            textArea.setRows(20);
            textArea.setColumns(50);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(550, 400));
            JOptionPane.showMessageDialog(parentFrame, scrollPane, "Libros Almacenados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
}

