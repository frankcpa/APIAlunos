package carregabanco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carregabanco.model.AlunoModel;
import carregabanco.repository.AlunoRepository;

@Service
public class AlunoService {
@Autowired
  private AlunoRepository AlunoRepository;
  

    public Optional<AlunoModel> buscarAlunoId(Long id){
        return AlunoRepository.findById(id);
    }

    public List<AlunoModel> buscarTodosAlunos() {
    return AlunoRepository.findAll();
}

public AlunoModel inserir(AlunoModel aluno) {
    return AlunoRepository.save(aluno);
}

public boolean excluir(Long id) {
    Optional<AlunoModel> aluno = AlunoRepository.findById(id);
    
    if (aluno.isPresent()) {
        AlunoRepository.deleteById(id);
        return true;
    }
    
    return false;
}

public List<AlunoModel> buscarPorNome(String nome) {
    return AlunoRepository.findByNomeEstudanteContainingIgnoreCase(nome);
}

public List<AlunoModel> buscarPorCurso(String curso) {
    return AlunoRepository.findByCursoContainingIgnoreCase(curso);
}

public List<AlunoModel> buscarPorCampus(String campus) {
    return AlunoRepository.findByCampusContainingIgnoreCase(campus);
}

public AlunoModel atualizar(Long id, AlunoModel novoAluno) {
    AlunoModel aluno = AlunoRepository.findById(id).get();

    aluno.setNomeEstudante(novoAluno.getNomeEstudante());
    aluno.setIdade(novoAluno.getIdade());
    aluno.setSexo(novoAluno.getSexo());
    aluno.setCurso(novoAluno.getCurso());
    aluno.setCoordenacao(novoAluno.getCoordenacao());
    aluno.setEmailInstitucional(novoAluno.getEmailInstitucional());
    aluno.setPolo(novoAluno.getPolo());
    aluno.setCampus(novoAluno.getCampus());
    aluno.setPeriodoEntrada(novoAluno.getPeriodoEntrada());
    aluno.setSituacao(novoAluno.getSituacao());

    return AlunoRepository.save(aluno);
}






}

