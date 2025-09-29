package infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import funcionarios.model.FuncionarioModel;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String gerarToken(FuncionarioModel funcionario){
    try{
      var algoritmo = Algorithm.HMAC256(secret);
      return JWT.create()
        .withIssuer("APIAlunos")
        .withSubject(funcionario.getNome())
        .withClaim("roles", funcionario.getRoles().stream().map(Enum::name).toList())
        .withExpiresAt(dataExpiracao())
        .sign(algoritmo);
    }catch(JWTCreationException e){
      throw new RuntimeException("erro ao gerar token jwt", e);
    }
  }

  public String getSubject(String tokenJWT){
    try{
      var algoritmo = Algorithm.HMAC256(secret);
      return JWT.require(algoritmo)
        .withIssuer("APIAlunos")
        .build()
        .verify(tokenJWT)
        .getSubject();  
      
    }catch(JWTVerificationException e){
      throw new RuntimeException("Token JWT inválido ou espirado");
    }
  }

  private Instant dataExpiracao(){
    return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-04:00"));
  }

  
}
