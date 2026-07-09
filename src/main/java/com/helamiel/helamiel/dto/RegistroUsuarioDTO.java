package com.helamiel.helamiel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Objeto de transferencia de datos utilizado por el endpoint
 * {@code POST /api/auth/register} para recibir los datos de un nuevo
 * usuario.
 *
 * <p>Se separa de la entidad {@link com.helamiel.helamiel.model.Usuario}
 * para no acoplar las reglas de validacion del formulario de registro a la
 * entidad JPA (por ejemplo, aqui se valida la contrasena en texto plano
 * antes de cifrarla; la entidad solo almacena la version ya cifrada).</p>
 */
public class RegistroUsuarioDTO {

    @NotBlank(message = "El usuario es obligatorio.")
    private String usuario;

    @NotBlank(message = "La contrasena es obligatoria.")
    @Size(min = 8, message = "La contrasena debe tener minimo 8 caracteres.")
    private String password;

    /**
     * Crea un DTO vacio, requerido para el binding de Spring (deserializacion JSON).
     */
    public RegistroUsuarioDTO() {
    }

    /**
     * @return nombre de usuario ingresado.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario nombre de usuario ingresado.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return contrasena en texto plano ingresada (se cifra en el service antes de persistir).
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password contrasena en texto plano ingresada por el usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}