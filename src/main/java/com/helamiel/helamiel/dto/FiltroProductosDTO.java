package com.helamiel.helamiel.dto;

import java.math.BigDecimal;

/**
 * DTO para filtrar y buscar productos con diversos criterios.
 */
public class FiltroProductosDTO {

    private String categoria;
    private String nombreMinimo;
    private String nombreMaximo;
    private BigDecimal precioMinimo;
    private BigDecimal precioMaximo;
    private int pagina = 0;
    private int tamanoPagina = 10;

    /**
     * Constructor vacío.
     */
    public FiltroProductosDTO() {
    }

    /**
     * Constructor con todos los parámetros.
     */
    public FiltroProductosDTO(String categoria, String nombreMinimo, 
                             String nombreMaximo, BigDecimal precioMinimo, 
                             BigDecimal precioMaximo, int pagina, int tamanoPagina) {
        this.categoria = categoria;
        this.nombreMinimo = nombreMinimo;
        this.nombreMaximo = nombreMaximo;
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.pagina = pagina;
        this.tamanoPagina = tamanoPagina;
    }

    // Getters y Setters

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreMinimo() {
        return nombreMinimo;
    }

    public void setNombreMinimo(String nombreMinimo) {
        this.nombreMinimo = nombreMinimo;
    }

    public String getNombreMaximo() {
        return nombreMaximo;
    }

    public void setNombreMaximo(String nombreMaximo) {
        this.nombreMaximo = nombreMaximo;
    }

    public BigDecimal getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(BigDecimal precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public BigDecimal getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(BigDecimal precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTamanoPagina() {
        return tamanoPagina;
    }

    public void setTamanoPagina(int tamanoPagina) {
        this.tamanoPagina = tamanoPagina;
    }
}
