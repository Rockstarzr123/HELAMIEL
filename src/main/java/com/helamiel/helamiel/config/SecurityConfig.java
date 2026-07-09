package com.helamiel.helamiel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuracion minima de seguridad para el modulo de autenticacion.
 *
 * <p>Expone unicamente el bean {@link PasswordEncoder} (implementado con
 * BCrypt) necesario para cifrar y verificar contrasenas de usuario.</p>
 *
 * <p>Importante: esta clase NO habilita Spring Security como framework
 * (no hay {@code @EnableWebSecurity} ni {@code SecurityFilterChain}), por lo
 * que no exige autenticacion en ninguna ruta existente del proyecto. Su
 * unico proposito es poner a disposicion el algoritmo de cifrado BCrypt
 * mediante inyeccion de dependencias.</p>
 */
@Configuration
public class SecurityConfig {

    /**
     * Crea el codificador de contrasenas basado en BCrypt.
     *
     * <p>BCrypt agrega automaticamente un "salt" aleatorio a cada
     * contrasena antes de cifrarla, por lo que dos usuarios con la misma
     * contrasena en texto plano terminan con hashes distintos en la base
     * de datos.</p>
     *
     * @return instancia de {@link PasswordEncoder} lista para inyectar.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}