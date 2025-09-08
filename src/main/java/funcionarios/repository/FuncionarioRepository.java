package funcionarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import funcionarios.model.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long>{

  
} 
