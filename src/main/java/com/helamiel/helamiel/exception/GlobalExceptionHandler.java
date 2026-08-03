package com.helamiel.helamiel.exception;

import com.helamiel.helamiel.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador global de excepciones para las APIs REST.
 * Proporciona respuestas consistentes y estandarizadas a los clientes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de credenciales inválidas.
     */
    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ApiResponse<Void>> handleCredencialesInvalidas(CredencialesInvalidasException ex) {
        ApiResponse<Void> response = ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "CREDENCIALES_INVALIDAS");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja excepciones de usuario duplicado.
     */
    @ExceptionHandler(UsuarioDuplicadoException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsuarioDuplicado(UsuarioDuplicadoException ex) {
        ApiResponse<Void> response = ApiResponse.error(HttpStatus.CONFLICT.value(), "USUARIO_DUPLICADO");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Maneja excepciones no encontradas (ResourceNotFound).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        String errorMsg = ex.getMessage() != null ? ex.getMessage() : "Error interno del servidor";
        ApiResponse<Void> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMsg);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
