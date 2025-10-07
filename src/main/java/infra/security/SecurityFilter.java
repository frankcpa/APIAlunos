package infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import funcionarios.repository.FuncionarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private FuncionarioRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        var tokenJWT = recuperarToken(request);

        try {
            if (tokenJWT != null) {
                var subject = tokenService.getSubject(tokenJWT);
                
                var usuario = repository.findByUsername(subject);
                
                if (usuario == null) {
                    logger.warn("Usuário não encontrado para o token: {}", subject);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Usuário não encontrado");
                    return;
                }
                
                if (!usuario.isEnabled()) { 
                    logger.warn("Usuário inativo tentou acessar: {}", subject);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Usuário inativo");
                    return;
                }
                
                var authentication = new UsernamePasswordAuthenticationToken(
                    usuario, null, usuario.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
            
        } catch (JWTVerificationException e) {
            logger.warn("Token JWT inválido: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado");
        } catch (Exception e) {
            logger.error("Erro inesperado no filtro de segurança", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro interno no servidor");
        }
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            if (authorizationHeader.startsWith("Bearer ")) {
                return authorizationHeader.replace("Bearer ", "");
            }
        }
        return null;
    }
}