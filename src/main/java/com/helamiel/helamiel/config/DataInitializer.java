package com.helamiel.helamiel.config;

import com.helamiel.helamiel.model.Usuario;
import com.helamiel.helamiel.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Inicializa datos de ejemplo en la base de datos al arrancar la aplicacion.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String usuarioInicial = "admin";
        String contrasenaInicial = "Admin1234";

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
