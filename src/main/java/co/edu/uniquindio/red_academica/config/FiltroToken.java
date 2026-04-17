package co.edu.uniquindio.red_academica.config;

import co.edu.uniquindio.red_academica.dto.MensajeDTO;
import co.edu.uniquindio.red_academica.modelo.enums.Rol;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FiltroToken extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String requestURI = request.getRequestURI();

        // Rutas públicas: no requieren token
        if (requestURI.startsWith("/api/auth/") || requestURI.startsWith("/api/publico/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        try {
            if (token == null || token.isBlank()) {
                crearRespuestaError("Token requerido", HttpServletResponse.SC_UNAUTHORIZED, response);
                return;
            }

            // Aquí puedes validar rol según la ruta
            boolean error = validarToken(token, Rol.ESTUDIANTE);

            if (error) {
                crearRespuestaError("No tiene permisos para acceder a este recurso", HttpServletResponse.SC_FORBIDDEN, response);
                return;
            }

            filterChain.doFilter(request, response);

        } catch (MalformedJwtException | SignatureException e) {
            crearRespuestaError("El token es incorrecto", HttpServletResponse.SC_UNAUTHORIZED, response);
        } catch (ExpiredJwtException e) {
            crearRespuestaError("El token está vencido", HttpServletResponse.SC_UNAUTHORIZED, response);
        } catch (Exception e) {
            crearRespuestaError(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
        }
    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ") ? header.replace("Bearer ", "") : null;
    }

    private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse response) throws IOException {
        MensajeDTO<String> dto = new MensajeDTO<>(true, mensaje);

        response.setContentType("application/json");
        response.setStatus(codigoError);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
    }

    private boolean validarToken(String token, Rol rol) {
        boolean error = true;
        if (token != null) {
            Jws<Claims> jws = jwtUtils.parseJwt(token);
            if (Rol.valueOf(jws.getPayload().get("rol").toString()) == rol) {
                error = false;
            }
        }
        return error;
    }
}