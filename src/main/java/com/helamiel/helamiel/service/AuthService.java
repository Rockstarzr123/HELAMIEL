package com.helamiel.helamiel.service;

import com.helamiel.helamiel.dto.LoginRequest;
import com.helamiel.helamiel.dto.RegistroUsuarioDTO;
import com.helamiel.helamiel.exception.CredencialesInvalidasException;
import com.helamiel.helamiel.exception.UsuarioDuplicadoException;
import com.helamiel.helamiel.model.Usuario;
import com.helamiel.helamiel.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Contiene la logica de negocio del modulo de autenticacion: registro de
 * usuarios e inicio de sesion.
 *
 * <p>Se apoya en {@link UsuarioRepository} (Spring Data JPA) para el acceso
 * a datos y en {@link PasswordEncoder} (BCrypt) para cifrar y verificar
 * contrasenas, sin exponer nunca la contrasena en texto plano fuera de
 * esta capa.</p>
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea el servicio inyectando el repositorio de usuarios y el
     * codificador de contrasenas.
     *
     * @param usuarioRepository repositorio JPA de usuarios.
     * @param passwordEncoder codificador BCrypt de contrasenas.
     */
    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * <p>Valida que el nombre de usuario no este ya en uso, cifra la
     * contrasena recibida en texto plano usando BCrypt (nunca se almacena
     * sin cifrar) y persiste el usuario con fecha de registro actual y
     * estado activo.</p>
     *
     * @param registroUsuarioDTO datos de registro ya validados por Bean Validation.
     * @return el usuario recien creado (con su id generado).
     * @throws UsuarioDuplicadoException si ya existe un usuario con ese nombre.
     */
    public Usuario registrar(RegistroUsuarioDTO registroUsuarioDTO) {
        String nombreUsuario = registroUsuarioDTO.getUsuario().trim();

        if (usuarioRepository.existsByUsuario(nombreUsuario)) {
            throw new UsuarioDuplicadoException(nombreUsuario);
        }

        String passwordCifrada = passwordEncoder.encode(registroUsuarioDTO.getPassword());

        Usuario nuevoUsuario = new Usuario(nombreUsuario, passwordCifrada, LocalDateTime.now(), true);

        return usuarioRepository.save(nuevoUsuario);
    }

    /**
     * Verifica las credenciales de inicio de sesion.
     *
     * <p>Busca el usuario por su nombre, compara la contrasena en texto
     * plano recibida contra el hash BCrypt almacenado y confirma que el
     * usuario este activo. El mensaje de error es siempre el mismo,
     * independientemente de cual dato fallo, para no revelar informacion
     * a un posible atacante.</p>
     *
     * @param loginRequest credenciales ingresadas por el usuario.
     * @throws CredencialesInvalidasException si el usuario no existe, la
     *         contrasena no coincide o el usuario esta inactivo.
     */
    public void iniciarSesion(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByUsuario(loginRequest.getUsuario().trim())
                .orElseThrow(CredencialesInvalidasException::new);

        boolean passwordCorrecta = passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword());

        if (!passwordCorrecta || !usuario.isEstado()) {
            throw new CredencialesInvalidasException();
        }
    }
}