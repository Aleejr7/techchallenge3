package com.appserviceagendamento.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            try {
                String role = jwtUtil.getRole(token);
                Long userId = jwtUtil.getUserId(token);

                // Adiciona os atributos na request para serem acessados no controller
                request.setAttribute("userRole", role);
                request.setAttribute("userId", userId);
            } catch (Exception e) {
                // Token inválido ou expirado - deixa passar para o controller tratar
            }
        }

        return true;
    }
}

