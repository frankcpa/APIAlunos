package aluno.apiAlunos.service;

import aluno.apiAlunos.model.AlunoModel;
import aluno.apiAlunos.repository.RepositoryAluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    @Autowired
    private RepositoryAluno repositoryAluno;

    public void salvar(AlunoModel aluno){
        repositoryAluno.saveAndFlush(aluno);
    }
}
