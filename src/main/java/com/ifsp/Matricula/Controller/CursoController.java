package com.ifsp.Matricula.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.Matricula.Model.Curso;
import com.ifsp.Matricula.Repository.CursoRepository;
import com.ifsp.Matricula.Repository.DisciplinaRepository;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CursoController {
    
    @Autowired
    private CursoRepository CursoRepository;

    @Autowired
    private DisciplinaRepository DisciplinaRepository; 
    
    @GetMapping("cadastraCurso")
    public String cadastroCurso(){
        return "/cadastraCurso";
    }
    
    @PostMapping("Cursos")
    public String saveCursos(@RequestParam String duracao, 
                               @RequestParam String nome){
        CursoRepository.save(new Curso(nome, duracao));
        return "redirect:/cadastraCurso?sucesso=true";
    }

    @GetMapping("listaCurso")
    public String listaCursos(Model model){
        List<Curso> listaCursos = CursoRepository.findAll();
        model.addAttribute("listaCursos",listaCursos);
        return "/listaCurso";
    }

    
    @GetMapping("editarCurso")
    public String editarCurso(@RequestParam long id, Model model){
        Curso Curso = CursoRepository.findById(id).orElse(null);
        model.addAttribute("Curso", Curso);
        return "editarCurso";
    }


    @PostMapping("atualizarCurso")
    public String atualizarCurso(@RequestParam long id,
                                   @RequestParam String duracao,
                                   @RequestParam String nome){

        Curso Curso = CursoRepository.findById(id).orElse(null);
        Curso.setDuracao(duracao);
        Curso.setNome(nome);
        CursoRepository.save(Curso);
        return "redirect:/listaCurso";
    }

    @GetMapping("excluirCurso")
    public String excluirCurso(
            @RequestParam long id,
            RedirectAttributes redirectAttributes) {

        if (DisciplinaRepository.existsByCursoId(id)) {

            redirectAttributes.addFlashAttribute(
                "erro",
                "Não é possível excluir este curso porque existem disciplinas vinculadas a ele."
            );

            return "redirect:/listaCurso";
        }

        CursoRepository.deleteById(id);

        redirectAttributes.addFlashAttribute(
            "sucesso",
            "Curso excluído com sucesso."
        );

        return "redirect:/listaCurso";
    }
    
}
