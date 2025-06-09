package carregabanco.controller;

import java.util.List;

import carregabanco.model.AlunoModel;
import carregabanco.repository.AlunoDao;

public class AlunoController {
	
	public void inserir(AlunoModel aluno) {
		AlunoDao alunoDao = AlunoDao.getInstance();
		alunoDao.persist(aluno);
	}
	
	public List<AlunoModel> buscar() {
		AlunoDao alunoDao = AlunoDao.getInstance();
		return alunoDao.findAll();
	}
	
	public void removerPorID(Long id) {
		AlunoDao alunoDao = AlunoDao.getInstance();
		alunoDao.removeById(id);
	}
	
	public void editar(AlunoModel aluno) {
		AlunoDao alunoDao = AlunoDao.getInstance();
		alunoDao.merge(aluno);;
	}
}
