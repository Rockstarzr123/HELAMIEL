package com.helamiel.helamiel.config;

import com.helamiel.helamiel.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Configuracion de seguridad para el modulo de autenticacion.
 *
 * <p>Expone el bean {@link PasswordEncoder} (implementado con BCrypt)
 * necesario para cifrar y verificar contrasenas de usuario.</p>
 *
 * <p>Ademas define explicitamente un {@link SecurityFilterChain} que
 * permite el acceso publico a todas las rutas. Esto es necesario porque
 * el proyecto incluye la dependencia {@code spring-boot-starter-security}
 * en el classpath: sin un filtro propio, Spring Boot activa su seguridad
 * automatica por defecto (Basic Auth con usuario "user" y una contrasena
 * aleatoria generada en cada arranque), bloqueando con 401 hasta el propio
 * endpoint de registro. Como HELAMIEL maneja su propia autenticacion vía
 * {@code /api/auth/register} y {@code /api/auth/login}, se desactiva aqui
 * la seguridad automatica de Spring y se deja el control de acceso a la
 * logica de negocio de {@code AuthController} y {@code AuthService}.</p>
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

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return username -> usuarioRepository.findByUsuario(username)
                .filter(usuario -> usuario.isEstado())
                .map(usuario -> User.withUsername(usuario.getUsuario())
                        .password(usuario.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));
    }

    /**
     * Define la cadena de filtros de seguridad HTTP.
     *
     * <p>Permite el acceso sin autenticacion a todas las rutas (paginas
     * Thymeleaf y API REST) y desactiva CSRF, ya que la API se consume
     * con clientes como Postman que no manejan token CSRF de sesion.</p>
     *
     * @param http configurador de seguridad HTTP proporcionado por Spring.
     * @return cadena de filtros lista para usar.
     * @throws Exception si ocurre un error al construir la configuracion.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/**"))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/api/auth/**", "/css/**", "/js/**", "/img/**",
                    "/webjars/**", "/swagger-ui/**", "/v3/api-docs/**", "/error").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }
}