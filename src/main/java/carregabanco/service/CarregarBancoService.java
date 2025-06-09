package carregabanco.service;

import carregabanco.model.AlunoModel;
import carregabanco.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CarregarBancoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public String carregarAlunosDoArquivo(MultipartFile file) {
        List<AlunoModel> alunos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String cabecalho = br.readLine();
            if (cabecalho == null) return "Arquivo vazio";

            String[] colunas = cabecalho.split(",");
            Map<String, Integer> campos = new HashMap<>();

            for (String nomeCampo : Arrays.asList("campus", "polo", "coordenacao", "curso", "nome_estudante", "situacao", "idade", "sexo", "email_institucional", "periodo_entrada")) {
                campos.put(nomeCampo, Arrays.asList(colunas).indexOf(nomeCampo));
            }

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");

                AlunoModel aluno = new AlunoModel();

                if (campos.get("campus") != -1) aluno.setCampus(dados[campos.get("campus")]);
                if (campos.get("polo") != -1) aluno.setPolo(dados[campos.get("polo")]);
                if (campos.get("coordenacao") != -1) aluno.setCoordenacao(dados[campos.get("coordenacao")]);
                if (campos.get("curso") != -1) aluno.setCurso(dados[campos.get("curso")]);
                if (campos.get("nome_estudante") != -1) aluno.setNomeEstudante(dados[campos.get("nome_estudante")]);
                if (campos.get("situacao") != -1) aluno.setSituacao(dados[campos.get("situacao")]);
                if (campos.get("idade") != -1) aluno.setIdade(!dados[campos.get("idade")].isBlank() ? Integer.parseInt(dados[campos.get("idade")]) : 0);
                if (campos.get("sexo") != -1) aluno.setSexo(dados[campos.get("sexo")]);
                if (campos.get("email_institucional") != -1) aluno.setEmailInstitucional(dados[campos.get("email_institucional")]);
                if (campos.get("periodo_entrada") != -1) aluno.setPeriodoEntrada(dados[campos.get("periodo_entrada")]);

                alunos.add(aluno);
            }

            alunoRepository.saveAll(alunos);
            return alunos.size() + " alunos importados com sucesso.";

        } catch (Exception e) {
            return "Erro ao processar o arquivo: " + e.getMessage();
        }
    }
}
