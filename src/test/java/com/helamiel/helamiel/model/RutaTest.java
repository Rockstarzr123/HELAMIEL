package com.helamiel.helamiel.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RutaTest {

    @Test
    void defaultConstructorShouldAllowPropertyAssignment() {
        Ruta ruta = new Ruta();
        ruta.setOrigen("Lima");
        ruta.setDestino("Cusco");
        ruta.setDistancia(335.5);

        assertEquals("Lima", ruta.getOrigen());
        assertEquals("Cusco", ruta.getDestino());
        assertEquals(335.5, ruta.getDistancia());
    }

    @Test
    void allArgsConstructorShouldInitializeFields() {
        Ruta ruta = new Ruta("Quito", "Guayaquil", 421.0);

        assertEquals("Quito", ruta.getOrigen());
        assertEquals("Guayaquil", ruta.getDestino());
        assertEquals(421.0, ruta.getDistancia());
    }
}
