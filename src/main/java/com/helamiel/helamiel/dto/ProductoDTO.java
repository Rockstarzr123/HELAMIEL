package com.helamiel.helamiel.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la transferencia de datos de Producto.
 * <p>
 * Este objeto encapsula los campos necesarios para crear o actualizar
 * un producto en el sistema HELAMIEL, proporcionando validaciones y
 * una estructura clara para las operaciones CRUD.
 * </p>
 */
public class ProductoDTO {

    private Long id;
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres.")
    private String nombre;
    private String descripcion;
    @NotBlank(message = "La categoria es obligatoria.")
    @Size(max = 60, message = "La categoria no puede superar 60 caracteres.")
    private String categoria;
    @NotNull(message = "El precio es obligatorio.")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero.")
    private BigDecimal precio;
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private int stock;
    private boolean estado = true;
    private LocalDateTime fechaRegistro;

    /**
     * Constructor vacío por defecto (requerido en frameworks Java).
     */
    public ProductoDTO() {
    }

    /**
     * Construye un DTO con los datos del producto.
     *
     * @param nombre     el nombre del producto
     * @param descripcion la descripción opcional del producto
     * @param categoria  la categoría a la que pertenece el producto
     * @param precio     el precio del producto
     * @param stock      el stock disponible del producto
     */
    public ProductoDTO(String nombre, String descripcion, String categoria, 
                       BigDecimal precio, int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
