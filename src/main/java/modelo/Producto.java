package modelo;

import java.math.BigDecimal;

/**
 * Representa un producto registrado en la base de datos.
 */
public class Producto {

    private int idProducto;
    private String nombre;
    private String categoria;
    private BigDecimal precio;
    private int stock;
    private boolean estado;

    /**
     * Crea un producto sin datos iniciales.
     */
    public Producto() {
    }

    /**
     * Crea un producto con todos sus datos.
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
