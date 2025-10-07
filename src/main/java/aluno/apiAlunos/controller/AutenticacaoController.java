package aluno.apiAlunos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import funcionarios.DTO.DadosAuth;
import funcionarios.DTO.TokenJWT;
import funcionarios.model.FuncionarioModel;
import infra.security.TokenService;

@RestController
@RequestMapping("/auth/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAuth dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                dados.nome(), 
                dados.password()
            );
            
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            
            if (!(authentication.getPrincipal() instanceof FuncionarioModel)) {
                return ResponseEntity.badRequest()
                    .body("Tipo de usuário não suportado para autenticação");
            }
            
            FuncionarioModel funcionarioAutenticado = (FuncionarioModel) authentication.getPrincipal();
            String tokenJwt = tokenService.gerarToken(funcionarioAutenticado);
            
            return ResponseEntity.ok(new TokenJWT(tokenJwt));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest()
                .body("Credenciais inválidas");
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest()
                .body("Erro na autenticação: " + e.getMessage());
        }
    }
}