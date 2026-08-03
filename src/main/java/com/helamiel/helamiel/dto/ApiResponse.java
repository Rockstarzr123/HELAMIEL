package com.helamiel.helamiel.dto;

/**
 * Clase genérica para respuestas JSON en endpoints REST API.
 * Proporciona una estructura uniforme para todas las respuestas del sistema.
 */
public class ApiResponse<T> {
    
    private final int code;
    private final String message;
    private final long timestamp;
    private final T data;

    /**
     * Crea una respuesta de éxito con datos.
     */
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Crea una respuesta de éxito sin datos adicionales.
     */
    public ApiResponse(int code, String message) {
        this(code, message, null);
    }

    // Getters
    public int getCode() { return code; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
    public T getData() { return data; }

    /**
     * Crea respuesta de éxito para listado.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    /**
     * Crea respuesta de éxito vacío.
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(200, message, null);
    }

    /**
     * Crea respuesta de error personalizada.
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

}
