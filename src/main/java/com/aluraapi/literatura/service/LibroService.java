package com.aluraapi.literatura.service;

import com.aluraapi.literatura.client.GutendexClient;
import com.aluraapi.literatura.dto.AutorDTO;
import com.aluraapi.literatura.dto.LibroDTO;
import com.aluraapi.literatura.model.Autor;
import com.aluraapi.literatura.model.Libro;
import com.aluraapi.literatura.repository.AutorRepository;
import com.aluraapi.literatura.repository.LibroRepository;
import com.aluraapi.literatura.util.LibroUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final GutendexClient gutendexClient;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(GutendexClient gutendexClient, LibroRepository libroRepository,
                        AutorRepository autorRepository) {
        this.gutendexClient = gutendexClient;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    public List<LibroDTO> buscarPorTitulo(String titulo) {
            return gutendexClient.buscarPorTitulo(titulo);
    }
    public List<LibroDTO> buscarPorAutor(String autor) {
            return gutendexClient.buscarPorAutor(autor);
    }
    public List<LibroDTO> obtenerTop11MasDescargados() {
            return gutendexClient.obtenerTop13MasDescargados();
    }

    @Transactional
    public String guardarLibro(LibroDTO dto) {

        Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(dto.titulo());

        if (libroExistente.isPresent()) {
            String mensaje = "El libro '" + dto.titulo() + "' ya está registrado " +
                    "en la base de datos local.";
            System.out.println(mensaje);
            return mensaje;
        }

        if (dto.autores() == null || dto.autores().isEmpty()) {
            String mensaje = "El libro '" + dto.titulo() + "' no contiene información de autor. " +
                    "No se guardará.";
            System.out.println(mensaje);
            return mensaje;
        }
        Autor autor = mapearYGuardarAutor(dto.autores().get(0));

        String idioma = LibroUtils.obtenerIdiomaPrincipal(dto.idiomas());

        Libro libro = new Libro();
        if (dto.id() != null) {
            libro.setId(dto.id());
        } else {
            System.err.println("Advertencia: El LibroDTO de Gutendex no tiene ID. No se puede guardar.");
            return "Error: El libro de Gutendex no tiene un ID válido.";
        }
        libro.setTitulo(dto.titulo());
        libro.setIdioma(idioma);
        libro.setDescargas(dto.descargas());
        libro.setAutor(autor);

        try {
            libroRepository.save(libro);
            String mensajeExito = "✅ Libro '" +
                    libro.getTitulo() + "' guardado exitosamente en la base de datos local.";
            System.out.println(mensajeExito);
            return mensajeExito;
        } catch (Exception e) {
            System.err.println("Error al guardar el libro '" + dto.titulo() + "': " + e.getMessage());
            e.printStackTrace();
            return "Error al guardar el libro '" + dto.titulo() + "': " + e.getMessage();
        }
    }
    @Transactional
    private Autor mapearYGuardarAutor(AutorDTO autorDTO) {
        Optional<Autor> existente = autorRepository.findByNombreIgnoreCase(autorDTO.nombre());

        return existente.orElseGet(() -> {
            Autor nuevo = new Autor();
            nuevo.setNombre(autorDTO.nombre());
            if (autorDTO.nacimiento() != null) {
                nuevo.setNacimiento(autorDTO.nacimiento());
            }
            if (autorDTO.fallecimiento() != null) {
                nuevo.setFallecimiento(autorDTO.fallecimiento());
            }
            System.out.println("✅ Guardando nuevo autor: " + nuevo.getNombre());
            return autorRepository.save(nuevo);
        });
    }
    public List<Libro> librosRegistrados() {
        return libroRepository.findAll();
    }

    public List<Libro> librosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    public List<Libro> top13MasDescargadosLocales() {
        return libroRepository.
                findTop13ByOrderByDescargasDesc();
    }
}