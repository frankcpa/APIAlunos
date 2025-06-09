package carregabanco.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JOptionPane;

import carregabanco.model.AlunoModel;
import carregabanco.repository.AlunoDao;

public class CarregaBancoController {
	private static ArrayList<AlunoModel> valores = new ArrayList<AlunoModel>();
	// campus,polo,coordenacao,curso,nome_estudante,situacao,idade,sexo,email_institucional,periodo_entrada,turno,cep
	// campus-coordenacao-curso-nome_estudante-situacao-idade-sexo-email_institucional-periodo_entrada
	public ArrayList<AlunoModel> loader(String file) {
		try {
			Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader bf = new BufferedReader(reader);
			String linha = bf.readLine();
			String[] data = linha.split(",");

			HashMap<String, Integer> camposEsperados = new HashMap<>();
			camposEsperados.put("campus", -2);
			camposEsperados.put("polo", -2);
			camposEsperados.put("coordenacao", -2);
			camposEsperados.put("curso", -2);
			camposEsperados.put("nome_estudante", -2);
			camposEsperados.put("situacao", -2);
			camposEsperados.put("idade", -2);
			camposEsperados.put("sexo", -2);
			camposEsperados.put("email_institucional", -2);
			camposEsperados.put("periodo_entrada", -2);

			int index = 0;
			String camposNaoEncontradoString = "";
			for (String chave : camposEsperados.keySet()) {
				index = Arrays.asList(data).indexOf(chave);
				if (index == -1) {
					camposNaoEncontradoString += chave + ", ";
				}else {
					camposEsperados.replace(chave, index);
				}
			}

			if (!camposNaoEncontradoString.isBlank()) {
				int resposta = JOptionPane.showConfirmDialog(null, "Os campos abaixo não foram encontrados no arquivo\n\n"
								+ camposNaoEncontradoString.substring(0, camposNaoEncontradoString.length() - 2)
								+ "\n\nDeseja continuar mesmo assim?","Confirmação", JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.NO_OPTION) {
					bf.close();
					return valores;
				}
			}

			linha = bf.readLine();
			while (linha != null) {
				data = linha.split(",");
				AlunoModel aluno = new AlunoModel();
				if (camposEsperados.get("campus") != -2)
					aluno.setCampus(data[camposEsperados.get("campus")]);
				if (camposEsperados.get("coordenacao") != -2)
					aluno.setCoordenacao(data[camposEsperados.get("coordenacao")]);
				if (camposEsperados.get("curso") != -2)
					aluno.setCurso(data[camposEsperados.get("curso")]);
				if (camposEsperados.get("nome_estudante") != -2)
					aluno.setNomeEstudante(data[camposEsperados.get("nome_estudante")]);
				if (camposEsperados.get("situacao") != -2)
					aluno.setSituacao(data[camposEsperados.get("situacao")]);
				if (camposEsperados.get("idade") != -2)
					aluno.setIdade(!data[camposEsperados.get("idade")].isBlank() && !data[camposEsperados.get("idade")].isEmpty() ? Integer.parseInt(data[camposEsperados.get("idade")]) : 0);
				if (camposEsperados.get("sexo") != -2)
					aluno.setSexo(data[camposEsperados.get("sexo")]);
				if (camposEsperados.get("email_institucional") != -2)
					aluno.setEmailInstitucional(data[camposEsperados.get("email_institucional")]);
				if (camposEsperados.get("periodo_entrada") != -2)
					aluno.setPeriodoEntrada(data[camposEsperados.get("periodo_entrada")]);
				/*aluno.setCampus(data[0]);
				aluno.setCoordenacao(data[2]);
				aluno.setCurso(data[3]);
				aluno.setNome_estudante(data[4]);
				aluno.setSituacao(data[5]);
				aluno.setIdade(!data[6].isBlank() && !data[6].isEmpty() ? Integer.parseInt(data[6]) : 0);
				aluno.setSexo(data[7]);
				aluno.setEmail_institucional(data[8]);
				aluno.setPeriodo_entrada(data[9]);*/
				valores.add(aluno);
				linha = bf.readLine();
			}

			bf.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Arquivo em formato incorreto.");
		}
		return valores;
	}

	public void inserirNoBanco(ArrayList<AlunoModel> listaDeAlunos) {
		String retorno = new AlunoDao().inserirNoBanco(listaDeAlunos);
		JOptionPane.showMessageDialog(null, retorno);
	}
}
