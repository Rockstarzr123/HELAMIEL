package com.helamiel.helamiel.config;

import com.helamiel.helamiel.model.Usuario;
import com.helamiel.helamiel.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

/**
 * Inicializa datos de ejemplo en la base de datos al arrancar la aplicacion.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String usuarioInicial;
    private final String contrasenaInicial;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                           @Value("${HELAMIEL_ADMIN_USER:}") String usuarioInicial,
                           @Value("${HELAMIEL_ADMIN_PASSWORD:}") String contrasenaInicial) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioInicial = usuarioInicial;
        this.contrasenaInicial = contrasenaInicial;
    }

    @Override
    public void run(String... args) {
        if (usuarioInicial.isBlank() || contrasenaInicial.isBlank()) {
            return;
        }

        if (!usuarioRepository.existsByUsuario(usuarioInicial)) {
            Usuario usuario = new Usuario(
                    usuarioInicial,
                    passwordEncoder.encode(contrasenaInicial),
                    LocalDateTime.now(),
                    true
            );
            usuarioRepository.save(usuario);
        }
    }
}
