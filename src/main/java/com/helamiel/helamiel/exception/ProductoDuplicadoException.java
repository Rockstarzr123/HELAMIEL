package com.helamiel.helamiel.exception;

/**
 * Se lanza cuando se intenta registrar o editar un producto usando un
 * nombre y una categoria que ya pertenecen a otro producto existente.
 */
public class ProductoDuplicadoException extends RuntimeException {

    /**
     * Crea la excepcion con un mensaje descriptivo del duplicado.
     *
     * @param nombre nombre del producto duplicado.
     * @param categoria categoria del producto duplicado.
     */
    public ProductoDuplicadoException(String nombre, String categoria) {
        super("Ya existe un producto llamado \"" + nombre + "\" en la categoria \"" + categoria + "\".");
    }
}
