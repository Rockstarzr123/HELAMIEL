package com.helamiel.helamiel.dto;

import java.math.BigDecimal;

/**
 * DTO para la actualización de productos.
 * Contiene campos opcionales que se pueden modificar.
 */
public class ActualizarProductoDTO {

    private String nombre;
    private String descripcion;
    private String categoria;
    private BigDecimal precio;
    private int stock;

    /**
     * Constructor vacío.
     */
    public ActualizarProductoDTO() {
    }

    /**
     * Crea un DTO con todos los campos.
     */
    public ActualizarProductoDTO(String nombre, String descripcion, String categoria, 
                                BigDecimal precio, int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
