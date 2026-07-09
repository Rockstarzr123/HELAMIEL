package com.helamiel.helamiel.service;

import com.helamiel.helamiel.model.Ruta;
import com.helamiel.helamiel.repository.RutaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RutaService {

    private final RutaRepository repository;

    public RutaService(RutaRepository repository) {
        this.repository = repository;
    }

    public List<Ruta> listar() {
        return repository.findAll();
    }

    public Ruta guardar(Ruta ruta) {
        Objects.requireNonNull(ruta, "La ruta no puede ser nula.");
        return repository.save(ruta);
    }
}
