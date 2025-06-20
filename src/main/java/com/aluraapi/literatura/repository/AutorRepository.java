package com.aluraapi.literatura.repository;

import com.aluraapi.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(Integer nacimiento,
                                                                              Integer fallecimiento);
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :fin AND " +
            "(a.fallecimiento >= :inicio OR a.fallecimiento IS NULL)")
    List<Autor> findAutoresVivosEntreAnios(Integer inicio, Integer fin);
}
