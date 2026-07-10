package com.helamiel.helamiel.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor que exige una sesion HTTP activa (usuario logueado) para
 * poder acceder a las paginas del panel administrativo.
 *
 * <p>Si no hay sesion o el atributo "usuarioLogueado" no esta presente,
 * redirige a {@code /login} en vez de dejar continuar la peticion.</p>
 */
public class SesionInterceptor implements HandlerInterceptor {

    private static final String ATRIBUTO_SESION_USUARIO = "usuarioLogueado";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);
        boolean haySesionActiva = session != null && session.getAttribute(ATRIBUTO_SESION_USUARIO) != null;

        if (!haySesionActiva) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
