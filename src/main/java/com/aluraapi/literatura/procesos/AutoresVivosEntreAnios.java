package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.model.Autor;
import com.aluraapi.literatura.service.AutorService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AutoresVivosEntreAnios {
    private final AutorService autorService;
    private final JFrame parentFrame;

    public AutoresVivosEntreAnios(AutorService autorService, JFrame parentFrame) {
        this.autorService = autorService;
        this.parentFrame = parentFrame;
    }
    public void ejecutar() {
        try {
            String inputInicio = JOptionPane.showInputDialog(
                    parentFrame,
                    "Ingrese el año de inicio:",
                    "Año Inicial",
                    JOptionPane.PLAIN_MESSAGE
            );
            String inputFin = JOptionPane.showInputDialog(
                    parentFrame,
                    "Ingrese el año final:",
                    "Año Final",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (inputInicio == null || inputFin == null) {
                JOptionPane.showMessageDialog(parentFrame, "Búsqueda cancelada.");
                return;
            }
            int anioInicio = Integer.parseInt(inputInicio.trim());
            int anioFin = Integer.parseInt(inputFin.trim());

            if (anioInicio > anioFin) {
                JOptionPane.showMessageDialog(parentFrame, "El año de inicio no puede ser mayor " +
                        "al año final.");
                return;
            }
            List<Autor> vivos = autorService.autoresVivosEntreAnios(anioInicio, anioFin);

            if (vivos.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "No se encontraron autores vivos en ese " +
                        "rango de años.");
                return;
            }
            StringBuilder salida = new StringBuilder("Autores vivos entre " + anioInicio + " y " + anioFin + ":\n");
            vivos.forEach(a -> salida.append("- ").append(a.getNombre()).append("\n"));

            JTextArea textArea = new JTextArea(salida.toString());
            textArea.setEditable(false);
            textArea.setRows(15);
            textArea.setColumns(40);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 300));

            JOptionPane.showMessageDialog(
                    parentFrame,
                    scrollPane,
                    "Autores Vivos",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parentFrame, "Por favor, ingrese años válidos.");
        }
    }
}