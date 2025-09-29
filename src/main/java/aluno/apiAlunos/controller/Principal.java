package aluno.apiAlunos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class Principal {

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  public ResponseEntity<TokenJWT> efetuarLogin(@RequestBody @Valid DadosAuth dados){
    var token = new UsernamePasswordAuthenticationToken(dados.nome(), dados.password());
    var authentication = manager.authenticate(token);
    String tokenJwt = tokenService.gerarToken((FuncionarioModel) authentication.getPrincipal());


    return ResponseEntity.ok(new TokenJWT(tokenJwt));
  }
  

}
