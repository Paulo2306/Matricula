package com.ifsp.Matricula.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.Matricula.Model.Aluno;
import com.ifsp.Matricula.Repository.AlunoRepository;
import com.ifsp.Matricula.Repository.MatriculaRepository;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AlunoController {
    
    @Autowired
    private AlunoRepository AlunoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @GetMapping("cadastraAluno")
    public String cadastroAluno(){
        return "/cadastraAluno";
    }
    
    @PostMapping("Alunos")
    public String saveAlunos(@RequestParam String email, 
                                @RequestParam String nome,
                                @RequestParam String prontuario,
                                @RequestParam int anoIngresso,
                                @RequestParam int anoSaida,
                                @RequestParam int idade,
                                @RequestParam String endereco,
                                @RequestParam String telefone){
        AlunoRepository.save(new Aluno(prontuario,anoSaida,anoIngresso,nome,email,idade,endereco,telefone));
        return "redirect:/cadastraAluno?sucesso=true";
    }

    @GetMapping("listaAluno")
    public String listaAlunos(Model model){
        List<Aluno> listaAlunos = AlunoRepository.findAll();
        model.addAttribute("listaAlunos",listaAlunos);
        return "listaAlunos";
    }

    // CARREGAR DADOS PARA EDIÇÃO
    @GetMapping("editarAluno")
    public String editarAluno(@RequestParam long id, Model model){
        Aluno Aluno = AlunoRepository.findById(id).orElse(null);
        model.addAttribute("aluno", Aluno);
        return "editarAluno";
    }

    // SALVAR EDIÇÃO
    @PostMapping("atualizarAluno")
    public String atualizarAluno(@RequestParam long id,
                                @RequestParam String email, 
                                @RequestParam String nome,
                                @RequestParam String prontuario,
                                @RequestParam int anoIngresso,
                                @RequestParam int anoSaida,
                                @RequestParam int idade,
                                @RequestParam String endereco,
                                @RequestParam String telefone){

        Aluno aluno = AlunoRepository.findById(id).orElse(null);;
        aluno.setEmail(email);
        aluno.setNome(nome);
        aluno.setProntuario(prontuario);
        aluno.setEndereco(endereco);
        aluno.setAnoIngresso(anoIngresso);
        aluno.setIdade(idade);
        aluno.setAnoSaida(anoSaida);
        aluno.setTelefone(telefone);
        AlunoRepository.save(aluno);
        AlunoRepository.save(aluno);
        return "redirect:/listaAluno";
    }

    @GetMapping("excluirAluno")
    public String excluirAluno(
            @RequestParam long id,
            RedirectAttributes redirectAttributes) {

        if (matriculaRepository.existsByAlunoIdPessoa(id)) {

            redirectAttributes.addFlashAttribute(
                "erro",
                "Não é possível excluir este aluno porque ele possui uma matrícula."
            );

            return "redirect:/listaAluno";
        }

        AlunoRepository.deleteById(id);

        redirectAttributes.addFlashAttribute(
            "sucesso",
            "Aluno excluído com sucesso."
        );

        return "redirect:/listaAluno";
    }
    
}
