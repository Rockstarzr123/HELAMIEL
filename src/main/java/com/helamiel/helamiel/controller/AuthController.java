package com.helamiel.helamiel.controller;

import com.helamiel.helamiel.dto.LoginRequest;
import com.helamiel.helamiel.dto.LoginResponse;
import com.helamiel.helamiel.dto.RegistroUsuarioDTO;
import com.helamiel.helamiel.exception.CredencialesInvalidasException;
import com.helamiel.helamiel.exception.UsuarioDuplicadoException;
import com.helamiel.helamiel.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST del modulo de autenticacion de HELAMIEL.
 *
 * <p>Expone unicamente dos operaciones, tal como exige la evidencia
 * "Construccion API": registrar un usuario nuevo e iniciar sesion. No
 * expone edicion, eliminacion ni listado de usuarios.</p>
 *
 * <p>Toda la logica de negocio (validacion de duplicados, cifrado BCrypt,
 * verificacion de credenciales) vive en {@link AuthService}; este
 * controlador solo traduce el resultado a respuestas HTTP.</p>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Crea el controlador inyectando el servicio de autenticacion.
     *
     * @param authService servicio con la logica de negocio de autenticacion.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * <p>Valida los datos de entrada con Bean Validation (usuario
     * obligatorio, contrasena obligatoria de minimo 8 caracteres). Si el
     * nombre de usuario ya existe, responde 409 Conflict; si el registro
     * es exitoso, responde 201 Created.</p>
     *
     * @param registroUsuarioDTO datos de registro enviados en el cuerpo de la peticion.
     * @return 201 Created si se registro correctamente, 409 Conflict si el usuario ya existe.
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> registrar(@Valid @RequestBody RegistroUsuarioDTO registroUsuarioDTO) {
        try {
            authService.registrar(registroUsuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new LoginResponse("Usuario registrado exitosamente"));
        } catch (UsuarioDuplicadoException excepcion) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new LoginResponse(excepcion.getMessage()));
        }
    }

    /**
     * Inicia sesion validando las credenciales del usuario.
     *
     * <p>Si el usuario y la contrasena son correctos, responde 200 OK con
     * el mensaje "Autenticación satisfactoria". Si son incorrectos (usuario
     * inexistente, contrasena erronea o usuario inactivo), responde 401
     * Unauthorized con el mensaje "Error en la autenticación", tal como
     * exige el requerimiento del SENA.</p>
     *
     * @param loginRequest credenciales enviadas en el cuerpo de la peticion.
     * @return 200 OK si la autenticacion es correcta, 401 Unauthorized si no lo es.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> iniciarSesion(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            authService.iniciarSesion(loginRequest);
            return ResponseEntity.ok(new LoginResponse("Autenticación satisfactoria"));
        } catch (CredencialesInvalidasException excepcion) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(excepcion.getMessage()));
        }
    }
}