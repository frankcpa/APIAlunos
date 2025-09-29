package infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import funcionarios.repository.FuncionarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

  @Autowired
  private TokenService tokenService;

  @Autowired
  private FuncionarioRepository repository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        try {
          if (tokenJWT != null) {
              var subject = tokenService.getSubject(tokenJWT);
              var usuario = repository.findByUsername(subject);
              var authentication = new UsernamePasswordAuthenticationToken(
                      usuario, null, usuario.getAuthorities()
              );
              SecurityContextHolder.getContext().setAuthentication(authentication);
          }
          filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado");
          }

  }


  private String recuperarToken(HttpServletRequest request){
    var autorizationHeader = request.getHeader("Authorization");
    if(autorizationHeader != null){
      return autorizationHeader.replace("Bearer ","");
    }
    return null;
  }
  
  


}