package com.helamiel.helamiel.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void gettersAndSettersShouldPreserveValues() {
        Producto producto = new Producto();
        producto.setIdProducto(12);
        producto.setNombre("Miel de Montaña");
        producto.setCategoria("Alimentos");
        producto.setPrecio(new BigDecimal("15.50"));
        producto.setStock(20);
        producto.setEstado(true);

        assertEquals(12, producto.getIdProducto());
        assertEquals("Miel de Montaña", producto.getNombre());
        assertEquals("Alimentos", producto.getCategoria());
        assertEquals(new BigDecimal("15.50"), producto.getPrecio());
        assertEquals(20, producto.getStock());
        assertTrue(producto.isEstado());
    }

    @Test
    void toStringShouldContainAllFieldValues() {
        Producto producto = new Producto(5, "Miel", "Bebida", new BigDecimal("9.99"), 7, false);

        String texto = producto.toString();

        assertTrue(texto.contains("idProducto=5"));
        assertTrue(texto.contains("nombre='Miel'"));
        assertTrue(texto.contains("categoria='Bebida'"));
        assertTrue(texto.contains("precio=9.99"));
        assertTrue(texto.contains("stock=7"));
        assertTrue(texto.contains("estado=false"));
    }
}
