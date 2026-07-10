package com.helamiel.helamiel.controller;

import com.helamiel.helamiel.dto.LoginRequest;
import com.helamiel.helamiel.exception.CredencialesInvalidasException;
import com.helamiel.helamiel.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador MVC de la pagina web de inicio de sesion.
 *
 * <p>Reutiliza la misma logica de negocio de {@link AuthService} que ya
 * usa la API REST ({@code /api/auth/login}), pero en vez de responder
 * JSON, renderiza una vista Thymeleaf y guarda el usuario autenticado
 * en la sesion HTTP para poder redirigir al panel administrativo.</p>
 */
@Controller
public class LoginController {

    private static final String ATRIBUTO_SESION_USUARIO = "usuarioLogueado";

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Muestra el formulario de inicio de sesion.
     *
     * @param model modelo que alimenta la vista.
     * @return nombre de la vista de login.
     */
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new LoginRequest());
        }
        return "login";
    }

    /**
     * Procesa el formulario de inicio de sesion.
     *
     * <p>Si las credenciales son correctas, guarda el nombre de usuario
     * en la sesion HTTP y redirige al listado de productos. Si son
     * incorrectas, vuelve a mostrar el formulario con un mensaje de
     * error, sin revelar cual dato especifico fallo.</p>
     *
     * @param loginRequest credenciales capturadas en el formulario.
     * @param session sesion HTTP actual, usada para recordar al usuario.
     * @param model modelo que alimenta la vista en caso de error.
     * @return redireccion a /productos si el login es correcto, o la
     *         vista de login nuevamente si falla.
     */
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute LoginRequest loginRequest, HttpSession session, Model model) {
        try {
            authService.iniciarSesion(loginRequest);
            session.setAttribute(ATRIBUTO_SESION_USUARIO, loginRequest.getUsuario());
            return "redirect:/productos";
        } catch (CredencialesInvalidasException excepcion) {
            model.addAttribute("mensajeError", excepcion.getMessage());
            model.addAttribute("loginRequest", loginRequest);
            return "login";
        }
    }

    /**
     * Cierra la sesion actual y redirige nuevamente al login.
     *
     * @param session sesion HTTP actual.
     * @return redireccion a la pagina de login.
     */
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
