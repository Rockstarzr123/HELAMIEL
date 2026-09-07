package com.helamiel.helamiel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El origen es obligatorio.")
    @Size(max = 100, message = "El origen no puede superar 100 caracteres.")
    private String origen;

    @NotBlank(message = "El destino es obligatorio.")
    @Size(max = 100, message = "El destino no puede superar 100 caracteres.")
    private String destino;

    @DecimalMin(value = "0.01", message = "La distancia debe ser mayor que cero.")
    private double distancia;

    public Ruta() {
    }

    public Ruta(String origen, String destino, double distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    public Long getId() {
        return id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
