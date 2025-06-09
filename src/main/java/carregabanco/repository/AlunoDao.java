package carregabanco.repository;

import java.util.ArrayList;
import java.util.List;

import carregabanco.model.AlunoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AlunoDao {

	private static AlunoDao instance;
	protected EntityManager entityManager;

	public AlunoDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public static AlunoDao getInstance() {
		if (instance == null) {
			instance = new AlunoDao();
		}
		return instance;
	}

	public AlunoModel getById(final long id) {
		return entityManager.find(AlunoModel.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<AlunoModel> findAll() {
		return entityManager.createQuery("FROM " + AlunoModel.class.getName()).getResultList();
	}

	public String inserirNoBanco(ArrayList<AlunoModel> listaDeAlunos) {
		try {
            entityManager.getTransaction().begin();
			entityManager.createQuery("DELETE FROM " + AlunoModel.class.getName()).executeUpdate();
           	for (AlunoModel aluno : listaDeAlunos) {
				entityManager.persist(aluno);
			}
            entityManager.getTransaction().commit();
			return "Inserido com sucesso!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return "Erro ao atualizar a lista de alunos: " + e.getMessage();
        }
	}

	public void persist(AlunoModel aluno) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(AlunoModel aluno) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(AlunoModel aluno) {
		try {
			entityManager.getTransaction().begin();
			aluno = entityManager.find(AlunoModel.class, aluno.getIdPessoa());
			entityManager.remove(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeTodos() {
		try {
			entityManager.getTransaction().begin();
			entityManager.createQuery("DELETE FROM " + AlunoModel.class).executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final long id) {
		try {
			AlunoModel aluno = getById(id);
			remove(aluno);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}