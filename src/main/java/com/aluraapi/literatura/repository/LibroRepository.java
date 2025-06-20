package com.aluraapi.literatura.repository;

import com.aluraapi.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    List<Libro> findByIdioma(String idioma);

    List<Libro> findTop13ByOrderByDescargasDesc();
}