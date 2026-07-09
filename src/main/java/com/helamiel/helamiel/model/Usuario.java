package com.helamiel.helamiel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

/**
 * Representa un usuario registrado en el sistema HELAMIEL.
 *
 * <p>La entidad se mapea sobre la tabla {@code usuarios} y es administrada
 * mediante Spring Data JPA desde {@code UsuarioRepository}. Es utilizada
 * exclusivamente por el modulo de autenticacion (registro e inicio de
 * sesion) agregado para la evidencia "Construccion API".</p>
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_usuario", columnNames = "usuario")
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario", nullable = false, unique = true, length = 60)
    private String usuario;

    /**
     * Contrasena cifrada con BCrypt. Nunca se almacena en texto plano.
     */
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    /**
     * Crea un usuario sin datos iniciales. Requerido por JPA e Hibernate.
     */
    public Usuario() {
    }

    /**
     * Crea un usuario con todos sus datos.
     *
     * @param usuario nombre de usuario unico.
     * @param password contrasena ya cifrada con BCrypt.
     * @param fechaRegistro fecha y hora en que se registro el usuario.
     * @param estado indica si el usuario esta activo.
     */
    public Usuario(String usuario, String password, LocalDateTime fechaRegistro, boolean estado) {
        this.usuario = usuario;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    /**
     * @return identificador unico del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id identificador unico del usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return nombre de usuario unico usado para iniciar sesion.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario nombre de usuario unico usado para iniciar sesion.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return contrasena cifrada con BCrypt (nunca en texto plano).
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password contrasena ya cifrada con BCrypt.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return fecha y hora en que se registro el usuario.
     */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro fecha y hora en que se registro el usuario.
     */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return true si el usuario esta activo.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado indica si el usuario esta activo.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "id=" + id
                + ", usuario='" + usuario + '\''
                + ", estado=" + estado
                + '}';
    }
}