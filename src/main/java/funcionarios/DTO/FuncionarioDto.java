package funcionarios.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import funcionarios.model.Roles;

public record FuncionarioDto( 
  @NotBlank @Size(min=6, max=30) String nome,
  @NotBlank @Size(min=11, max=11)String cpf,
  @NotBlank @Email String email,
  @NotBlank String registro,
  @NotBlank  @Size(min=6, max=254) String password,
  Roles roles) {
  
}
