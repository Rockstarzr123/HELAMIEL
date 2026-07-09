package com.helamiel.helamiel.exception;

/**
 * Se lanza cuando se intenta consultar, editar o eliminar un producto
 * cuyo identificador no existe en la base de datos.
 */
public class ProductoNoEncontradoException extends RuntimeException {

    /**
     * Crea la excepcion con el identificador del producto no encontrado.
     *
     * @param idProducto identificador que no fue encontrado.
     */
    public ProductoNoEncontradoException(int idProducto) {
        super("No se encontro el producto con id " + idProducto + ".");
    }
}
