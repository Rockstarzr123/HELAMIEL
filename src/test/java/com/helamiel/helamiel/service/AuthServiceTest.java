package com.helamiel.helamiel.service;

import com.helamiel.helamiel.dto.LoginRequest;
import com.helamiel.helamiel.dto.RegistroUsuarioDTO;
import com.helamiel.helamiel.exception.CredencialesInvalidasException;
import com.helamiel.helamiel.exception.UsuarioDuplicadoException;
import com.helamiel.helamiel.model.Usuario;
import com.helamiel.helamiel.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Test
    void registrarShouldEncodePasswordAndPersistActiveUser() {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthService service = new AuthService(repository, encoder);
        RegistroUsuarioDTO request = new RegistroUsuarioDTO();
        request.setUsuario("  ana  ");
        request.setPassword("Secret123");

        when(repository.existsByUsuario("ana")).thenReturn(false);
        when(encoder.encode("Secret123")).thenReturn("$2a$encoded");
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = service.registrar(request);

        assertEquals("ana", result.getUsuario());
        assertEquals("$2a$encoded", result.getPassword());
        assertTrue(result.isEstado());
        assertNotNull(result.getFechaRegistro());
        verify(encoder).encode("Secret123");
        verify(repository).save(any(Usuario.class));
    }

    @Test
    void registrarShouldRejectDuplicateUser() {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthService service = new AuthService(repository, encoder);
        RegistroUsuarioDTO request = new RegistroUsuarioDTO();
        request.setUsuario("ana");
        request.setPassword("Secret123");

        when(repository.existsByUsuario("ana")).thenReturn(true);

        assertThrows(UsuarioDuplicadoException.class, () -> service.registrar(request));
        verifyNoInteractions(encoder);
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    void iniciarSesionShouldAcceptMatchingActiveCredentials() {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthService service = new AuthService(repository, encoder);
        LoginRequest request = new LoginRequest();
        request.setUsuario("ana");
        request.setPassword("Secret123");
        Usuario user = new Usuario("ana", "$2a$encoded", LocalDateTime.now(), true);

        when(repository.findByUsuario("ana")).thenReturn(Optional.of(user));
        when(encoder.matches("Secret123", "$2a$encoded")).thenReturn(true);

        assertDoesNotThrow(() -> service.iniciarSesion(request));
    }

    @Test
    void iniciarSesionShouldRejectInvalidPassword() {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthService service = new AuthService(repository, encoder);
        LoginRequest request = new LoginRequest();
        request.setUsuario("ana");
        request.setPassword("bad");
        Usuario user = new Usuario("ana", "$2a$encoded", LocalDateTime.now(), true);

        when(repository.findByUsuario("ana")).thenReturn(Optional.of(user));
        when(encoder.matches("bad", "$2a$encoded")).thenReturn(false);

        assertThrows(CredencialesInvalidasException.class, () -> service.iniciarSesion(request));
    }

    @Test
    void iniciarSesionShouldRejectInactiveUser() {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthService service = new AuthService(repository, encoder);
        LoginRequest request = new LoginRequest();
        request.setUsuario("ana");
        request.setPassword("Secret123");
        Usuario user = new Usuario("ana", "$2a$encoded", LocalDateTime.now(), false);

        when(repository.findByUsuario("ana")).thenReturn(Optional.of(user));
        when(encoder.matches("Secret123", "$2a$encoded")).thenReturn(true);

        assertThrows(CredencialesInvalidasException.class, () -> service.iniciarSesion(request));
    }
}
