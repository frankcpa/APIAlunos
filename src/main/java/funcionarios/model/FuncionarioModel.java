package funcionarios.model;


import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import funcionarios.DTO.FuncionarioDto;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcionarios")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class FuncionarioModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String cpf;

  @Column(nullable = false)
  @Email
  private String email;
  
  @Column(nullable = false)
  private String registro;

  @Column(nullable = false)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<Roles> roles = new HashSet<>();


  public FuncionarioModel(FuncionarioDto funcionario, String password){
    
  }

}
