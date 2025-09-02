package aluno.apiAlunos.repository;

import aluno.apiAlunos.model.AlunoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryAluno extends JpaRepository<AlunoModel, Long> {
}
