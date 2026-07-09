package com.helamiel.helamiel.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Manejador global de excepciones para los controladores MVC del proyecto.
 *
 * <p>Traduce las excepciones de negocio en mensajes flash legibles por el
 * usuario y redirige al listado de productos, evitando pantallas de error
 * genericas (stack traces) en la interfaz.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja el caso en que un producto solicitado no existe.
     *
     * @param exception excepcion capturada.
     * @param redirectAttributes atributos flash para el mensaje al usuario.
     * @return vista de redireccion al listado de productos.
     */
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public String manejarProductoNoEncontrado(ProductoNoEncontradoException exception,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeError", exception.getMessage());
        return "redirect:/productos";
    }

    /**
     * Maneja el caso en que se intenta registrar/editar un producto
     * duplicado (mismo nombre y categoria).
     *
     * @param exception excepcion capturada.
     * @param redirectAttributes atributos flash para el mensaje al usuario.
     * @return vista de redireccion al listado de productos.
     */
    @ExceptionHandler(ProductoDuplicadoException.class)
    public String manejarProductoDuplicado(ProductoDuplicadoException exception,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeError", exception.getMessage());
        return "redirect:/productos";
    }

    /**
     * Maneja cualquier otro error no controlado del modulo de productos,
     * evitando exponer detalles tecnicos al usuario final.
     *
     * @param exception excepcion capturada.
     * @param redirectAttributes atributos flash para el mensaje al usuario.
     * @return vista de redireccion al listado de productos.
     */
    @ExceptionHandler(Exception.class)
    public String manejarErrorGeneral(Exception exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeError",
                "Ocurrio un error inesperado. Intenta nuevamente.");
        return "error";
    }
}
