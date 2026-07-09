package com.helamiel.helamiel.controller;

import com.helamiel.helamiel.model.Ruta;
import com.helamiel.helamiel.service.RutaService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RutaControllerTest {

    @Test
    void inicioShouldReturnIndexViewAndPopulateModel() {
        RutaService service = mock(RutaService.class);
        RutaController controller = new RutaController(service);
        Model model = new ConcurrentModel();
        List<Ruta> rutas = List.of(new Ruta("A", "B", 10.0));

        when(service.listar()).thenReturn(rutas);

        String view = controller.inicio(model);

        assertEquals("index", view);
        assertSame(rutas, model.getAttribute("rutas"));
        assertNotNull(model.getAttribute("ruta"));
        assertTrue(model.getAttribute("ruta") instanceof Ruta);
        verify(service, times(1)).listar();
    }

    @Test
    void guardarShouldRedirectToRootAfterSaving() {
        RutaService service = mock(RutaService.class);
        RutaController controller = new RutaController(service);
        Ruta ruta = new Ruta("Pozos", "Panama", 180.0);

        String view = controller.guardar(ruta);

        assertEquals("redirect:/", view);
        verify(service, times(1)).guardar(ruta);
    }
}
