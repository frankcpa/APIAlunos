package carregabanco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import carregabanco.model.AlunoModel;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoModel, Long> {
List<AlunoModel> findByNomeEstudanteContainingIgnoreCase(String nome);

List<AlunoModel> findByCursoContainingIgnoreCase(String curso);
List<AlunoModel> findByCampusContainingIgnoreCase(String campus);



}
