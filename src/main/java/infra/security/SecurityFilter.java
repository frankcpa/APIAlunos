package infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import funcionarios.repository.FuncionarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws ServletException, IOException {

    String token = recuperarToken(request);

    if (token == null) {
        filterChain.doFilter(request, response);
        return;
    }

    try {
        String subject = tokenService.getSubject(token);
        var funcionario = funcionarioRepository.findByUsername(subject);

        if (funcionario != null) {
            var authentication = new UsernamePasswordAuthenticationToken(
                    funcionario,
                    null,
                    funcionario.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    } catch (JWTVerificationException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }

    filterChain.doFilter(request, response);
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