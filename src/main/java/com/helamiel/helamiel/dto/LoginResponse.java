package com.helamiel.helamiel.dto;

/**
 * Objeto de transferencia de datos utilizado como cuerpo de respuesta de
 * los endpoints {@code POST /api/auth/login} y {@code POST /api/auth/register}.
 *
 * <p>Representa el formato de respuesta exigido por la evidencia
 * "Construccion API": un unico campo {@code mensaje} con el resultado de
 * la operacion.</p>
 */
public class LoginResponse {

    private String mensaje;

    /**
     * Crea una respuesta vacia. Requerido por Jackson para la serializacion.
     */
    public LoginResponse() {
    }

    /**
     * Crea una respuesta con el mensaje indicado.
     *
     * @param mensaje texto descriptivo del resultado de la operacion.
     */
    public LoginResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return mensaje descriptivo del resultado de la operacion.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje mensaje descriptivo del resultado de la operacion.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}