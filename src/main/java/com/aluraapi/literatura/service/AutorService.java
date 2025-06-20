package com.aluraapi.literatura.service;

import com.aluraapi.literatura.model.Autor;
import com.aluraapi.literatura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }
    public List<Autor> autoresRegistrados() {
        return autorRepository.findAll();
    }

    public List<Autor> vivosEnAnio(Integer anio) {
        return autorRepository.findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(anio, anio);
    }
    public List<Autor> autoresVivosEntreAnios(Integer inicio, Integer fin) {
        return autorRepository.findAutoresVivosEntreAnios(inicio, fin);
    }
}