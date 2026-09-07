package com.helamiel.helamiel.exception;

import com.helamiel.helamiel.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.stream.Collectors;

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

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Void>> handleProductoNoEncontrado(ProductoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiResponse<Void>> handleValidation(Exception ex) {
        String message;
        if (ex instanceof MethodArgumentNotValidException validationException) {
            message = validationException.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
        } else {
            message = "Los datos enviados no son validos.";
        }
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMalformedRequest() {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "El cuerpo de la solicitud no es valido."));
    }

    /**
     * Maneja excepciones no encontradas (ResourceNotFound).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        ApiResponse<Void> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Ocurrio un error interno. Intenta nuevamente.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
