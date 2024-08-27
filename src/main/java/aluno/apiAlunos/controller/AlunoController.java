package aluno.apiAlunos.controller;

import aluno.apiAlunos.model.AlunoModel;
import aluno.apiAlunos.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    AlunoService alunoService = new AlunoService();

    @GetMapping
    public String aluno(){
        return "Em Aluno";
    }

    @PostMapping
    public ResponseEntity<AlunoModel> salvar (@RequestBody AlunoModel aluno){
        try {
            alunoService.salvar(aluno);
            return new ResponseEntity<AlunoModel>(aluno, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<AlunoModel>(aluno, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
