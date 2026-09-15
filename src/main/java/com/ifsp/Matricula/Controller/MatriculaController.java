package com.ifsp.Matricula.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.Matricula.Model.Aluno;
import com.ifsp.Matricula.Model.Matricula;
import com.ifsp.Matricula.Model.OfertaDisciplina;
import com.ifsp.Matricula.Repository.AlunoRepository;
import com.ifsp.Matricula.Repository.CursoRepository;
import com.ifsp.Matricula.Repository.DisciplinaRepository;
import com.ifsp.Matricula.Repository.MatriculaRepository;
import com.ifsp.Matricula.Repository.OfertaDisciplinaRepository;
import com.ifsp.Matricula.Repository.ProfessorRepository;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MatriculaController {
    
    @Autowired
    private MatriculaRepository MatriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;    

    @Autowired
    private OfertaDisciplinaRepository ofertaDisciplinaRepository; 

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;   
    
    @GetMapping("cadastraMatricula")
    public String cadastroMatricula(Model model){
        model.addAttribute("listaOfertaDisciplina",ofertaDisciplinaRepository.findAll());
        model.addAttribute("listaCurso",cursoRepository.findAll());
        model.addAttribute("listaAluno",alunoRepository.findAll());
        model.addAttribute("listaProfessor",professorRepository.findAll());
        model.addAttribute("listaDisciplina", disciplinaRepository.findAll());
        return "/cadastraMatricula";
    }
    
    @PostMapping("Matricula")
    public String saveMatriculas(   @RequestParam Aluno aluno_id,
                                    @RequestParam OfertaDisciplina ofertaDisciplina_id){
        MatriculaRepository.save(new Matricula(aluno_id, ofertaDisciplina_id));
        return "redirect:/cadastraMatricula?sucesso=true";
    }

    @GetMapping("listaMatricula")
    public String listaMatriculas(Model model){
        List<Matricula> listaMatriculas = MatriculaRepository.findAll();
        model.addAttribute("listaCurso",cursoRepository.findAll());
        model.addAttribute("listaAluno",alunoRepository.findAll());
        model.addAttribute("listaDisciplina", disciplinaRepository.findAll());
        model.addAttribute("listaMatriculas",listaMatriculas);
        return "/listaMatricula";
    }

    // CARREGAR DADOS PARA EDIÇÃO
    @GetMapping("editarMatricula")
    public String editarMatricula(@RequestParam long id, Model model){
        Matricula Matricula = MatriculaRepository.findById(id).orElse(null);
        model.addAttribute("listaCurso",cursoRepository.findAll());
        model.addAttribute("listaAluno",alunoRepository.findAll());
        model.addAttribute("listaDisciplina", ofertaDisciplinaRepository.findAll());
        model.addAttribute("matricula", Matricula);
        return "editarMatricula";
    }

    // SALVAR EDIÇÃO
    @PostMapping("atualizarMatricula")
    public String atualizarMatricula(@RequestParam long id,
                                    @RequestParam Aluno aluno,
                                    @RequestParam OfertaDisciplina ofertaDisciplina  
                                ){

        Matricula Matricula = MatriculaRepository.findById(id).orElse(null);;
        Matricula.setAluno(aluno);
        Matricula.setOfertaDisciplina(ofertaDisciplina);
        MatriculaRepository.save(Matricula);
        return "redirect:/listaMatricula";
    }

    // EXCLUIR
    @GetMapping("excluirMatricula")
    public String excluirMatricula(@RequestParam long id){
        MatriculaRepository.deleteById(id);
        return "redirect:/listaMatricula";
    }
    
}
