package com.helamiel.helamiel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de listado de productos.
 * <p>
 * Incluye todos los campos del producto con sus valores actuales,
 * incluyendo estado, fecha de registro y precio formateado.
 * </p>
 */
public class ProductoListadoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private BigDecimal precio;
    private int stock;
    private Boolean estado;
    private LocalDateTime fechaRegistro;

    /**
     * Constructor vacío por defecto.
     */
    public ProductoListadoDTO() {
    }

    /**
     * Obtiene el ID del producto.
     * @return el ID del producto
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del producto.
     * @param id el ID a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     * @return el nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre el nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del producto.
     * @return la descripción del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcion la descripción a asignar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la categoría del producto.
     * @return la categoría del producto
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto.
     * @param categoria la categoría a asignar
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el precio del producto.
     * @return el precio del producto
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio el precio a asignar
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el stock disponible del producto.
     * @return el stock del producto
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock del producto.
     * @param stock el stock a asignar
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Obtiene el estado del producto (activo/inactivo).
     * @return true si está activo, false si está inactivo
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * Establece el estado del producto.
     * @param estado el estado a asignar (true=activo, false=inactivo)
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la fecha de registro del producto.
     * @return la fecha y hora en que se registró el producto
     */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Establece la fecha de registro del producto.
     * @param fechaRegistro la fecha de registro a asignar
     */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
