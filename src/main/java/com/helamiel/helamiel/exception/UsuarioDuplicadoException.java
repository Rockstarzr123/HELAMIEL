package com.helamiel.helamiel.exception;

/**
 * Se lanza cuando se intenta registrar un usuario cuyo nombre de usuario
 * ya existe en la base de datos.
 */
public class UsuarioDuplicadoException extends RuntimeException {

    /**
     * Crea la excepcion con un mensaje descriptivo del usuario duplicado.
     *
     * @param usuario nombre de usuario que ya existe.
     */
    public UsuarioDuplicadoException(String usuario) {
        super("Ya existe un usuario registrado con el nombre \"" + usuario + "\".");
    }
}