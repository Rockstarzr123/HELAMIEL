package com.helamiel.helamiel.dao;

import com.helamiel.helamiel.model.Producto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoDAOTest {

    @Test
    void asignarParametrosProductoShouldSetAllStatementParameters() throws Exception {
        PreparedStatement statement = mock(PreparedStatement.class);
        Producto producto = new Producto(1, "Miel", "Alimentos", new BigDecimal("12.30"), 50, true);

        ProductoDAO dao = new ProductoDAO();
        var method = ProductoDAO.class.getDeclaredMethod("asignarParametrosProducto", PreparedStatement.class, Producto.class);
        method.setAccessible(true);
        method.invoke(dao, statement, producto);

        verify(statement).setString(1, "Miel");
        verify(statement).setString(2, "Alimentos");
        verify(statement).setBigDecimal(3, new BigDecimal("12.30"));
        verify(statement).setInt(4, 50);
        verify(statement).setBoolean(5, true);
    }

    @Test
    void asignarIdGeneradoShouldSetIdWhenKeysPresent() throws Exception {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet generatedKeys = mock(ResultSet.class);
        when(statement.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(42);

        Producto producto = new Producto();
        ProductoDAO dao = new ProductoDAO();

        var method = ProductoDAO.class.getDeclaredMethod("asignarIdGenerado", PreparedStatement.class, Producto.class);
        method.setAccessible(true);
        method.invoke(dao, statement, producto);

        assertEquals(42, producto.getIdProducto());
    }

    @Test
    void mapearProductoShouldCreateProductoFromResultSet() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id_producto")).thenReturn(7);
        when(resultSet.getString("nombre")).thenReturn("Miel de Bosque");
        when(resultSet.getString("categoria")).thenReturn("Bebidas");
        when(resultSet.getBigDecimal("precio")).thenReturn(new BigDecimal("8.75"));
        when(resultSet.getInt("stock")).thenReturn(14);
        when(resultSet.getBoolean("estado")).thenReturn(false);

        ProductoDAO dao = new ProductoDAO();
        var method = ProductoDAO.class.getDeclaredMethod("mapearProducto", ResultSet.class);
        method.setAccessible(true);
        Producto producto = (Producto) method.invoke(dao, resultSet);

        assertNotNull(producto);
        assertEquals(7, producto.getIdProducto());
        assertEquals("Miel de Bosque", producto.getNombre());
        assertEquals("Bebidas", producto.getCategoria());
        assertEquals(new BigDecimal("8.75"), producto.getPrecio());
        assertEquals(14, producto.getStock());
        assertFalse(producto.isEstado());
    }
}
