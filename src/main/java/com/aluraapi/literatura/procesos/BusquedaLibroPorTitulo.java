package com.aluraapi.literatura.procesos;

import com.aluraapi.literatura.dto.LibroDTO;
import com.aluraapi.literatura.service.LibroService;
import com.aluraapi.literatura.util.LibroUtils;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.util.List;

public class BusquedaLibroPorTitulo {

    private final LibroService libroService;
    private final Component parentComponent;

    public BusquedaLibroPorTitulo(LibroService libroService, Component parentComponent) {
        this.libroService = libroService;
        this.parentComponent = parentComponent;
    }
    public void ejecutar() {
        String titulo = JOptionPane.showInputDialog(
                parentComponent,
                "Ingresa el título a buscar:",
                "Busca Libro",
                JOptionPane.PLAIN_MESSAGE
        );
        if (titulo == null || titulo.trim().isEmpty()) {
            return;
        }
        List<LibroDTO> resultados = libroService.buscarPorTitulo(titulo);

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(parentComponent, "No se encontraron resultados " +
                    "para el título: " + titulo);
            return;
        }
        LibroDTO libroEncontrado = resultados.get(0);
        String autorNombre = (libroEncontrado.autores() != null && !libroEncontrado.autores().isEmpty()) ?
                libroEncontrado.autores().get(0).nombre() : "Desconocido";

        StringBuilder infoLibro = new StringBuilder("Libro Encontrado:\n");
        infoLibro.append("Título: ").append(libroEncontrado.titulo()).append("\n");
        infoLibro.append("Autor: ").append(autorNombre).append("\n");
        infoLibro.append("Idioma: ").append(LibroUtils.obtenerIdiomaPrincipal(libroEncontrado.idiomas())).
                append("\n");
        infoLibro.append("Descargas: ").append(libroEncontrado.descargas()).append("\n");

        int opcion = JOptionPane.showConfirmDialog(parentComponent, infoLibro.toString() +
                "\n¿Deseas guardar este libro?");

        if (opcion == JOptionPane.YES_OPTION) {
            String mensajeResultado = libroService.guardarLibro(libroEncontrado);
            JOptionPane.showMessageDialog(parentComponent, mensajeResultado); //
        }
    }
}