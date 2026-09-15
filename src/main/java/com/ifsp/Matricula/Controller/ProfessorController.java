package com.ifsp.Matricula.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.Matricula.Model.Professor;
import com.ifsp.Matricula.Repository.OfertaDisciplinaRepository;
import com.ifsp.Matricula.Repository.ProfessorRepository;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfessorController {
    
    @Autowired
    private ProfessorRepository ProfessorRepository;

    @Autowired
    private OfertaDisciplinaRepository ofertaDisciplinaRepository;

    @GetMapping("cadastraProfessor")
    public String cadastroProfessor(){
        return "/cadastraProfessor";
    }
    
    @PostMapping("Professores")
    public String saveProfessores(@RequestParam String email, 
                                @RequestParam String nome,
                                @RequestParam String siape,
                                @RequestParam String area,
                                @RequestParam String formacao,
                                @RequestParam int idade,
                                @RequestParam String endereco,
                                @RequestParam String telefone){
        ProfessorRepository.save(new Professor(siape,area,formacao,nome,email,idade,endereco,telefone));
        return "redirect:/cadastraProfessor?sucesso=true";
    }

    @GetMapping("listaProfessor")
    public String listaProfessores(Model model){
        List<Professor> listaProfessores = ProfessorRepository.findAll();
        model.addAttribute("listaProfessores",listaProfessores);
        return "/listaProfessor";
    }

    @GetMapping("editarProfessor")
    public String editarProfessor(@RequestParam long id, Model model){
        Professor Professor = ProfessorRepository.findById(id).orElse(null);
        model.addAttribute("Professor", Professor);
        return "editarProfessor";
    }

    @PostMapping("atualizarProfessor")
    public String atualizarProfessor(@RequestParam long id,
                                @RequestParam String email, 
                                @RequestParam String nome,
                                @RequestParam String siape,
                                @RequestParam String area,
                                @RequestParam String formacao,
                                @RequestParam int idade,
                                @RequestParam String endereco,
                                @RequestParam String telefone){

        Professor Professor = ProfessorRepository.findById(id).orElse(null);;
        Professor.setEmail(email);
        Professor.setNome(nome);
        Professor.setArea(area);
        Professor.setEndereco(endereco);
        Professor.setFormacao(formacao);
        Professor.setIdade(idade);
        Professor.setSiape(siape);
        Professor.setTelefone(telefone);
        ProfessorRepository.save(Professor);
        return "redirect:/listaProfessor";
    }

    @GetMapping("excluirProfessor")
    public String excluirProfessor(
            @RequestParam long id,
            RedirectAttributes redirectAttributes) {

        if (ofertaDisciplinaRepository.existsByProfessorIdPessoa(id)) {

            redirectAttributes.addFlashAttribute(
                "erro",
                "Não é possível excluir este professor porque ele está vinculado a uma oferta de disciplina."
            );

            return "redirect:/listaProfessor";
        }

        ProfessorRepository.deleteById(id);

        redirectAttributes.addFlashAttribute(
            "sucesso",
            "Professor excluído com sucesso."
        );

        return "redirect:/listaProfessor";
    }
    
}
