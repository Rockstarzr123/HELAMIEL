package com.helamiel.helamiel.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Objeto de transferencia de datos utilizado por el endpoint
 * {@code POST /api/auth/login} para recibir las credenciales de acceso.
 *
 * <p>Solo valida que ambos campos vengan informados; la verificacion real
 * de la contrasena (comparacion contra el hash BCrypt almacenado) ocurre
 * en {@link com.helamiel.helamiel.service.AuthService}.</p>
 */
public class LoginRequest {

    @NotBlank(message = "El usuario es obligatorio.")
    private String usuario;

    @NotBlank(message = "La contrasena es obligatoria.")
    private String password;

    /**
     * Crea un DTO vacio, requerido para el binding de Spring (deserializacion JSON).
     */
    public LoginRequest() {
    }

    /**
     * @return nombre de usuario ingresado para iniciar sesion.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario nombre de usuario ingresado para iniciar sesion.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return contrasena en texto plano ingresada para iniciar sesion.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password contrasena en texto plano ingresada para iniciar sesion.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}