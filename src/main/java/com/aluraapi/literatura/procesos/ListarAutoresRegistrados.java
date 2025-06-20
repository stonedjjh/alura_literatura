package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.model.Autor;
import com.aluraapi.literatura.service.AutorService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListarAutoresRegistrados {
    private final AutorService autorService;
    private final JFrame parentFrame;

    public ListarAutoresRegistrados(AutorService autorService, JFrame parentFrame) {
        this.autorService = autorService;
        this.parentFrame = parentFrame;
    }
    public void ejecutar() {
        List<Autor> autores = autorService.autoresRegistrados();
        if (autores.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No hay autores registrados.");
            return;
        }
        StringBuilder lista = new StringBuilder("Autores almacenados:\n");
        autores.forEach(a -> lista.append("- ").append(a.getNombre())
                .append(" (").append(a.getNacimiento()).append(" - ")
                .append(a.getFallecimiento() != null ? a.getFallecimiento() : "Vivo").append(")\n"));
        JTextArea textArea = new JTextArea(lista.toString());
        textArea.setEditable(false);
        textArea.setRows(15);
        textArea.setColumns(40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 300));
        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Autores Almacenados",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
