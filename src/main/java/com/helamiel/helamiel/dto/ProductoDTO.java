package com.helamiel.helamiel.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Objeto de transferencia de datos utilizado por el formulario Thymeleaf de
 * registro y edicion de productos.
 *
 * <p>Se separa del modelo {@link com.helamiel.helamiel.model.Producto} para
 * poder aplicar validaciones propias del formulario web sin acoplar la
 * entidad JPA a las reglas de presentacion.</p>
 */
public class ProductoDTO {

    private int idProducto;

    @NotBlank(message = "El nombre del producto es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 .,'-]+$",
            message = "El nombre contiene caracteres no permitidos.")
    private String nombre;

    @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres.")
    private String descripcion;

    @NotBlank(message = "La categoria es obligatoria.")
    @Size(min = 3, max = 60, message = "La categoria debe tener entre 3 y 60 caracteres.")
    private String categoria;

    @NotNull(message = "El precio es obligatorio.")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero.")
    @Digits(integer = 8, fraction = 2, message = "El precio admite maximo 2 decimales.")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer stock;

    private boolean estado = true;

    /**
     * Crea un DTO vacio, requerido para el binding de Thymeleaf/Spring MVC.
     */
    public ProductoDTO() {
    }

    /**
     * @return identificador del producto (0 cuando aun no se ha guardado).
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto identificador del producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return nombre comercial ingresado en el formulario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre nombre comercial ingresado en el formulario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return descripcion ingresada en el formulario.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion descripcion ingresada en el formulario.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return categoria ingresada en el formulario.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria categoria ingresada en el formulario.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return precio ingresado en el formulario.
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio precio ingresado en el formulario.
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return stock disponible ingresado en el formulario.
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock stock disponible ingresado en el formulario.
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @return true si el producto debe quedar activo.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado indica si el producto debe quedar activo.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
