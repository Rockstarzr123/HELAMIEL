package com.helamiel.helamiel.dto;

/**
 * DTO para respuesta de Producto en APIs REST.
 * Incluye información adicional como precios y stock disponibles.
 */
public class ProductoResponse {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stockInicial;
    private Integer stockActual;
    private Integer stockDisponible;
    private String categoria;
    private boolean activo;
    private String unidadMedida;
    private String creadoEn;
    private String modificadoEn;

    // Getters y Setters
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(Double precioCompra) { this.precioCompra = precioCompra; }

    public Double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(Double precioVenta) { this.precioVenta = precioVenta; }

    public Integer getStockInicial() { return stockInicial; }
    public void setStockInicial(Integer stockInicial) { this.stockInicial = stockInicial; }

    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }

    public Integer getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(Integer stockDisponible) { this.stockDisponible = stockDisponible; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public String getCreadoEn() { return creadoEn; }
    public void setCreadoEn(String creadoEn) { this.creadoEn = creadoEn; }

    public String getModificadoEn() { return modificadoEn; }
    public void setModificadoEn(String modificadoEn) { this.modificadoEn = modificadoEn; }

}
