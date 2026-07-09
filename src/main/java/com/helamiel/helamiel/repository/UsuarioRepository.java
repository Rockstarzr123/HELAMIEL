package com.helamiel.helamiel.repository;

import com.helamiel.helamiel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para el acceso a datos de {@link Usuario}.
 *
 * <p>Provee las operaciones CRUD basicas heredadas de {@link JpaRepository}
 * mas las consultas adicionales requeridas por el modulo de autenticacion
 * (registro e inicio de sesion) de la evidencia "Construccion API".</p>
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario exacto.
     *
     * @param usuario nombre de usuario a buscar.
     * @return el usuario encontrado, o {@link Optional#empty()} si no existe.
     */
    Optional<Usuario> findByUsuario(String usuario);

    /**
     * Verifica si ya existe un usuario registrado con ese nombre de usuario.
     *
     * @param usuario nombre de usuario a verificar.
     * @return true si ya existe un usuario con ese nombre.
     */
    boolean existsByUsuario(String usuario);
}