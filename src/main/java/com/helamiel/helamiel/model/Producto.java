package com.helamiel.helamiel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa un producto registrado en la base de datos.
 *
 * <p>La entidad se mapea sobre la tabla {@code Productos} y es administrada
 * mediante Spring Data JPA desde {@code ProductoRepository}.</p>
 */
@Entity
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private int idProducto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "categoria", nullable = false, length = 60)
    private String categoria;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    /**
     * Crea un producto sin datos iniciales. Requerido por JPA e Hibernate.
     */
    public Producto() {
    }

    /**
     * Crea un producto con sus datos basicos (constructor historico, se
     * mantiene por compatibilidad con el codigo y las pruebas existentes).
     *
     * @param idProducto identificador unico del producto.
     * @param nombre nombre comercial del producto.
     * @param categoria categoria a la que pertenece el producto.
     * @param precio precio del producto.
     * @param stock cantidad disponible.
     * @param estado indica si el producto esta activo.
     */
    public Producto(int idProducto, String nombre, String categoria, BigDecimal precio, int stock, boolean estado) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
    }

    /**
     * Crea un producto con todos sus datos, incluyendo los campos
     * incorporados para la evidencia GA7-220501096-AA4-EV03.
     *
     * @param idProducto identificador unico del producto.
     * @param nombre nombre comercial del producto.
     * @param descripcion descripcion detallada del producto.
     * @param categoria categoria a la que pertenece el producto.
     * @param precio precio del producto.
     * @param stock cantidad disponible.
     * @param estado indica si el producto esta activo.
     * @param fechaRegistro fecha y hora en que se registro el producto.
     */
    public Producto(int idProducto, String nombre, String descripcion, String categoria, BigDecimal precio,
            int stock, boolean estado, LocalDateTime fechaRegistro) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return identificador unico del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto identificador unico del producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return nombre comercial del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre nombre comercial del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return descripcion detallada del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion descripcion detallada del producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return categoria a la que pertenece el producto.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria categoria a la que pertenece el producto.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return precio del producto.
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio precio del producto.
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return cantidad disponible.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock cantidad disponible.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return true si el producto esta activo.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado indica si el producto esta activo.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return fecha y hora en que se registro el producto.
     */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro fecha y hora en que se registro el producto.
     */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Producto{"
                + "idProducto=" + idProducto
                + ", nombre='" + nombre + '\''
                + ", categoria='" + categoria + '\''
                + ", precio=" + precio
                + ", stock=" + stock
                + ", estado=" + estado
                + '}';
    }
}
