package carregabanco.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pessoa")
public class PessoaModel implements Serializable{
	private static final long serialVersionUID = 1L;
	//campus,polo,coordenacao,curso,nome_estudante,situacao,idade,sexo,email_institucional,periodo_entrada
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPessoa;
	@Column(name = "nome_estudante")
	private String nomeEstudante;
	private int idade;
	private String sexo;
	
	public PessoaModel() {}
	
	public PessoaModel(int idade, String sexo, String nome_estudante) {
		super();
		this.nomeEstudante = nome_estudante;
		this.idade = idade;
		this.sexo = sexo;
	}
	
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getNomeEstudante() {
		return nomeEstudante;
	}

	public void setNomeEstudante(String nome_estudante) {
		this.nomeEstudante = nome_estudante;
	}

	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}	
}

