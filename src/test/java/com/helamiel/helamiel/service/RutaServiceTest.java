package com.helamiel.helamiel.service;

import com.helamiel.helamiel.model.Ruta;
import com.helamiel.helamiel.repository.RutaRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RutaServiceTest {

    @Test
    void listarShouldReturnAllRutasFromRepository() {
        RutaRepository repository = mock(RutaRepository.class);
        RutaService service = new RutaService(repository);
        List<Ruta> rutas = List.of(new Ruta("A", "B", 10.0), new Ruta("C", "D", 20.0));

        when(repository.findAll()).thenReturn(rutas);

        List<Ruta> resultado = service.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertSame(rutas, resultado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void guardarShouldReturnSavedRuta() {
        RutaRepository repository = mock(RutaRepository.class);
        RutaService service = new RutaService(repository);
        Ruta ruta = new Ruta("Origen", "Destino", 12.5);
        Ruta guardada = new Ruta("Origen", "Destino", 12.5);

        when(repository.save(ruta)).thenReturn(guardada);

        Ruta resultado = service.guardar(ruta);

        assertSame(guardada, resultado);
        verify(repository, times(1)).save(ruta);
    }
}
