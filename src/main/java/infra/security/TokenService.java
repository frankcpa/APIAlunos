package infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    public String gerarToken(FuncionarioModel funcionario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer("APIAlunos")
                .withSubject(funcionario.getNome())
                .withClaim("id", funcionario.getId())
                .withClaim("nome", funcionario.getNome())
                .withClaim("roles", funcionario.getRoles().stream().map(Enum::name).toList())
                .withExpiresAt(dataExpiracao())
                .sign(algoritmo);
        } catch (JWTCreationException e) {
            logger.error("Erro ao gerar token JWT", e);
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                .withIssuer("APIAlunos")
                .build()
                .verify(tokenJWT)
                .getSubject();
        } catch (JWTVerificationException e) {
            logger.warn("Token JWT inválido ou expirado: {}", e.getMessage());
            throw new JWTVerificationException("Token JWT inválido ou expirado");
        }
    }

    public Long getIdUsuarioFromToken(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            var decodedJWT = JWT.require(algoritmo)
                .withIssuer("APIAlunos")
                .build()
                .verify(tokenJWT);
            return decodedJWT.getClaim("id").asLong();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token JWT inválido ou expirado");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-04:00"));
    }
}