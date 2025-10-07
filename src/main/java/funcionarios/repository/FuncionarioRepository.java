package funcionarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import funcionarios.model.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long>{

  UserDetails findByUsername(String name);

  
} 
