package com.helamiel.helamiel.dto;

/**
 * DTO para respuesta de Usuario en APIs REST.
 */
public class UsuarioResponse {
    
    private Long id;
    private String nombreCompleto;
    private String email;
    private String rol;
    private boolean activo;
    private String creadoEn;
    private String modificadoEn;

    // Getters y Setters
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getCreadoEn() { return creadoEn; }
    public void setCreadoEn(String creadoEn) { this.creadoEn = creadoEn; }

    public String getModificadoEn() { return modificadoEn; }
    public void setModificadoEn(String modificadoEn) { this.modificadoEn = modificadoEn; }

}
