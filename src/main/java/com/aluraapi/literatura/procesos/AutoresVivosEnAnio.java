package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.model.Autor;
import com.aluraapi.literatura.service.AutorService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AutoresVivosEnAnio {
    private final AutorService autorService;
    private final JFrame parentFrame;

    public AutoresVivosEnAnio(AutorService autorService, JFrame parentFrame) {
        this.autorService = autorService;
        this.parentFrame = parentFrame;
    }
    public void ejecutar() {
        String input = JOptionPane.showInputDialog(
                parentFrame,
                "Año para buscar autores vivos:",
                "Año",
                JOptionPane.PLAIN_MESSAGE
        );
        try {
            int anio = Integer.parseInt(input);
            List<Autor> vivos = autorService.vivosEnAnio(anio);
            if (vivos.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "No se encontraron autores vivos " +
                        "en ese año.");
                return;
            }
            StringBuilder salida = new StringBuilder("Autores vivos en ").append(anio).append(":\n");
            vivos.forEach(a -> salida.append("- ").append(a.getNombre()).append("\n"));
            JTextArea textArea = new JTextArea(salida.toString());
            textArea.setEditable(false);
            textArea.setRows(15);
            textArea.setColumns(40);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 300));
            JOptionPane.showMessageDialog(parentFrame, scrollPane, "Autores Vivos",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parentFrame, "Año inválido.");
        }
    }
}