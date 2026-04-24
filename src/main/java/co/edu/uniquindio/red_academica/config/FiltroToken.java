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
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FiltroToken extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String origin = request.getHeader("Origin");
        if (origin != null && (origin.equals("http://localhost:4200") || origin.equals("https://red-academica-fronted.web.app"))) {
            response.addHeader("Access-Control-Allow-Origin", origin);
        } else {
            response.addHeader("Access-Control-Allow-Origin", "https://red-academica-fronted.web.app");
        }
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String requestURI = request.getRequestURI();

        // Rutas públicas reales
        if (esRutaPublica(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        try {
            if (token == null || token.isBlank()) {
                crearRespuestaError("Token requerido", HttpServletResponse.SC_UNAUTHORIZED, response);
                return;
            }

            Rol rolUsuario = obtenerRolDesdeToken(token);

            if (rolUsuario == null) {
                crearRespuestaError("No fue posible identificar el rol del usuario", HttpServletResponse.SC_UNAUTHORIZED, response);
                return;
            }

            if (!tienePermiso(requestURI, rolUsuario)) {
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

    private boolean esRutaPublica(String requestURI) {
        return requestURI.startsWith("/api/auth/")
                || requestURI.startsWith("/api/publico/");
    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ")
                ? header.replace("Bearer ", "")
                : null;
    }

    private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse response) throws IOException {
        MensajeDTO<String> dto = new MensajeDTO<>(true, mensaje);

        response.setContentType("application/json");
        response.setStatus(codigoError);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
    }

    private Rol obtenerRolDesdeToken(String token) {
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        Object rolClaim = jws.getPayload().get("rol");

        if (rolClaim == null) {
            return null;
        }

        return Rol.valueOf(rolClaim.toString());
    }

    private boolean tienePermiso(String requestURI, Rol rolUsuario) {

        // ADMINISTRADOR
        if (requestURI.startsWith("/api/admin-mentores")) {
            return rolUsuario == Rol.ADMINISTRADOR;
        }

        // ASESORIAS MENTOR
        if (requestURI.startsWith("/api/asesorias-mentor")) {
            return rolUsuario == Rol.ASESOR;
        }

        // AGENDAR ASESORIA
        if (requestURI.startsWith("/api/agendar-asesoria")) {
            return rolUsuario == Rol.ESTUDIANTE;
        }

        // ASESOR - VER SUS ASESORIAS
        if (requestURI.startsWith("/api/asesorias/asesor")) {
            return rolUsuario == Rol.ASESOR;
        }

        // ASESOR - CAMBIAR ESTADO DE ASESORIA
        if (requestURI.contains("/estado/")) {
            return rolUsuario == Rol.ASESOR;
        }

        // ESTUDIANTE - VER SUS ASESORIAS
        if (requestURI.startsWith("/api/asesorias")) {
            return rolUsuario == Rol.ESTUDIANTE;
        }

        // SOLICITUDES DE AYUDA
        if (requestURI.startsWith("/api/solicitudes-ayuda")) {
            return rolUsuario == Rol.ESTUDIANTE || rolUsuario == Rol.ASESOR;
        }

        // RESPUESTAS DE SOLICITUD
        if (requestURI.startsWith("/api/respuestas-solicitud")) {
            return rolUsuario == Rol.ESTUDIANTE || rolUsuario == Rol.ASESOR;
        }

        // CHAT
        if (requestURI.startsWith("/api/chats")) {
            return rolUsuario == Rol.ESTUDIANTE || rolUsuario == Rol.ASESOR;
        }

        // CONTENIDOS ACADÉMICOS
        if (requestURI.startsWith("/api/contenidos-academicos")) {
            return rolUsuario == Rol.ESTUDIANTE || rolUsuario == Rol.ASESOR;
        }

        // SUBIR CONTENIDO / ARCHIVOS
        if (requestURI.startsWith("/api/subir-contenido")
                || requestURI.startsWith("/api/archivos-academicos")) {
            return rolUsuario == Rol.ESTUDIANTE || rolUsuario == Rol.ASESOR;
        }

        // MENTORES
        if (requestURI.startsWith("/api/mentores")) {
            return rolUsuario == Rol.ESTUDIANTE || rolUsuario == Rol.ADMINISTRADOR;
        }

        // Si no coincide con ninguna regla, negar por seguridad
        return false;
    }
}