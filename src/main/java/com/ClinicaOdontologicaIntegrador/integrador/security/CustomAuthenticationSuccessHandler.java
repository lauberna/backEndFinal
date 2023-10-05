package com.ClinicaOdontologicaIntegrador.integrador.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // Aquí obtienes los roles del usuario autenticado
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));

        // Redirige según el rol
        if (isAdmin) {
            getRedirectStrategy().sendRedirect(request, response, "/admin");
        } else if (isUser) {
            getRedirectStrategy().sendRedirect(request, response, "/user");
        }else {
            super.onAuthenticationSuccess(request, response, authentication); // Redirige a la página principal por defecto
        }
    }
}
