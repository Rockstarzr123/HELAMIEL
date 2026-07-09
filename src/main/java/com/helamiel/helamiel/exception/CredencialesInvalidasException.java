package com.helamiel.helamiel.exception;

/**
 * Se lanza cuando el usuario o la contrasena proporcionados durante el
 * inicio de sesion no son validos.
 *
 * <p>El mensaje es intencionalmente generico (no indica si el usuario no
 * existe o si la contrasena es incorrecta) para no revelar informacion
 * util a un posible atacante.</p>
 */
public class CredencialesInvalidasException extends RuntimeException {

    /**
     * Crea la excepcion con el mensaje generico de error de autenticacion.
     */
    public CredencialesInvalidasException() {
        super("Error en la autenticación");
    }
}