package carregabanco.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import carregabanco.model.AlunoModel;
import carregabanco.service.AlunoService;
import carregabanco.service.CarregarBancoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/alunos")
public class AlunoControllerAPi {

    @Autowired
    private AlunoService AlunoService;

    @Autowired
    private CarregarBancoService carregarBancoService;

    // Criar a função de buscar todos;
    @GetMapping("/todos")
    public ResponseEntity<List<AlunoModel>> buscarTodos() {
        List<AlunoModel> alunos = AlunoService.buscarTodosAlunos();

        if (alunos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    // Criar a função de inserir;

    @PostMapping("/inserir")
    public ResponseEntity<AlunoModel> inserirAluno(@Valid @RequestBody AlunoModel aluno) {
        AlunoModel salvo = AlunoService.inserir(aluno);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    // Criar a função de excluir;

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> excluirAluno(@PathVariable Long id) {
        boolean excluido = AlunoService.excluir(id);

        if (excluido) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Criar três maneiras diferentes de buscar (por nome, curso e outra);

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<AlunoModel>> buscarPorNome(@PathVariable String nome) {
        List<AlunoModel> alunos = AlunoService.buscarPorNome(nome);

        if (alunos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    @GetMapping("/buscar/curso/{curso}")
    public ResponseEntity<List<AlunoModel>> buscarPorCurso(@PathVariable String curso) {
        List<AlunoModel> alunos = AlunoService.buscarPorCurso(curso);

        if (alunos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    @GetMapping("/buscar/campus/{campus}")
    public ResponseEntity<List<AlunoModel>> buscarPorCampus(@PathVariable String campus) {
        List<AlunoModel> alunos = AlunoService.buscarPorCampus(campus);

        if (alunos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    @PutMapping("/alunos/editar/{id}")
    public ResponseEntity<AlunoModel> atualizarAluno(@PathVariable Long id, @RequestBody AlunoModel novoAluno) {
        Optional<AlunoModel> existente = AlunoService.buscarAlunoId(id);

        if (existente.isPresent()) {
            AlunoModel atualizado = AlunoService.atualizar(id, novoAluno);
            return new ResponseEntity<>(atualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
        String resultado = carregarBancoService.carregarAlunosDoArquivo(arquivo);
        return ResponseEntity.ok(resultado);
    }

}
