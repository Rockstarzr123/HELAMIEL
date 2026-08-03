package com.helamiel.helamiel.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa un producto del sistema HELAMIEL.
 *
 * <p>La entidad se mapea sobre la tabla {@code productos} y es administrada
 * mediante Spring Data JPA desde {@code ProductoRepository}. Gestiona el
 * catálogo de productos disponibles para la tienda.</p>
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

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
     * Constructor vacío requerido por JPA y Hibernate.
     */
    public Producto() {
    }

    /**
     * Crea un producto con todos sus datos.
     *
     * @param nombre     nombre del producto
     * @param descripcion descripción opcional del producto
     * @param categoria  categoría del producto
     * @param precio     precio del producto (debe ser > 0)
     * @param stock      cantidad disponible en stock (>= 0)
     * @param fechaRegistro fecha de registro del producto
     */
    public Producto(String nombre, String descripcion, String categoria, 
                    BigDecimal precio, int stock, LocalDateTime fechaRegistro) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.estado = true;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDateTime.now();
    }

    public Producto(int id, String nombre, String categoria, BigDecimal precio, int stock, boolean estado) {
        this.id = (long) id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = (long) id;
    }

    public int getIdProducto() {
        return id != null ? id.intValue() : 0;
    }

    public void setIdProducto(int idProducto) {
        this.id = (long) idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.id = idProducto;
    }

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

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + getIdProducto() +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", estado=" + estado +
                '}';
    }
}
