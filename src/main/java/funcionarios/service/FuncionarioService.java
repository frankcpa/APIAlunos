package funcionarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import funcionarios.DTO.FuncionarioDto;
import funcionarios.model.FuncionarioModel;
import funcionarios.repository.FuncionarioRepository;

public class FuncionarioService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private FuncionarioRepository funcionarioRepository;
  
  public void create(FuncionarioDto funcionarioDto){
    if(funcionarioDto.nome().length() < 6){
      throw new IllegalArgumentException("the name must have at least 6 characters!");
    }
    String encodedPassword = passwordEncoder.encode(funcionarioDto.password());
    FuncionarioModel funcionario = new FuncionarioModel(funcionarioDto, encodedPassword);
    funcionarioRepository.save(funcionario);
    
  }



}
